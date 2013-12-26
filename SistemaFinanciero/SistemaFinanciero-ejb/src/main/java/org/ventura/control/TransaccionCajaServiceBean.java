package org.ventura.control;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.boundary.remote.TransaccionCajaServiceRemote;
import org.ventura.dao.impl.BovedaCajaDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.TransaccioncajaDAO;
import org.ventura.dao.impl.TransaccioncompraventaDAO;
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.dao.impl.VouchercajaViewDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.BovedaCaja;
import org.ventura.entity.schema.caja.BovedaCajaPK;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.exception.InsufficientMoneyForTransactionException;
import org.ventura.util.exception.InvalidTransactionBovedaException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;
import org.ventura.util.maestro.TipocuentabancariaType;

@Stateless
@Local(TransaccionCajaServiceLocal.class)
@Remote(TransaccionCajaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TransaccionCajaServiceBean implements TransaccionCajaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private TransaccioncajaDAO transaccioncajaDAO;
	@EJB
	private TransaccioncuentabancariaDAO transaccioncuentabancariaDAO;
	@EJB
	private TransaccioncompraventaDAO transaccioncompraventaDAO;
	@EJB
	private CuentabancariaDAO cuentabancariaDAO;
	@EJB
	private VouchercajaViewDAO vouchercajaViewDAO;
	@EJB
	private BovedaCajaDAO bovedaCajaDAO;
	
	@Override
	public Transaccioncuentabancaria createTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria)throws Exception {
		try {
			String numeroCuentabancaria = transaccioncuentabancaria.getCuentabancaria().getNumerocuenta();
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.findByNumerocuenta(numeroCuentabancaria);
			if(cuentabancaria == null){
				throw new Exception("Cuenta bancaria no encontrada");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Tipocuentabancaria tipocuentabancaria = cuentabancaria.getTipocuentabancaria();
			
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA y no se puede realizar transacciones");
			}
			Tipocuentabancaria tipocuentabancariaActive = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			if(tipocuentabancaria.equals(tipocuentabancariaActive)){
				throw new Exception("No se puede hacer operaciones sobre una cuenta a plazo fijo");
			}

			TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccioncuentabancaria.getTipotransaccion());
			boolean isTransaccionvalida = false;

			switch (tipoTransaccion) {
			case DEPOSITO:
				isTransaccionvalida = isDepositoValido(cuentabancaria,transaccioncuentabancaria.getMonto());
				break;
			case RETIRO:
				isTransaccionvalida = isRetiroValido(cuentabancaria,transaccioncuentabancaria.getMonto());
				break;
			default:
				throw new Exception("Tipo de transaccion no valida");
			}
			
			if(isTransaccionvalida == false) {
				switch (tipoTransaccion) {
				case DEPOSITO:
					throw new InvalidTransactionBovedaException("Transaccion no permitida");
				case RETIRO:
					throw new InsufficientMoneyForTransactionException("Fondos Insuficientes para Retiro");
				}
			}
			
			//validacion superada
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
				
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			switch (tipoTransaccion) {
			case DEPOSITO:
				saldoFinal = saldoFinal.add(montoTransaccion);
				break;
			case RETIRO:
				saldoFinal = saldoFinal.subtract(montoTransaccion);
				break;
			}
			
			cuentabancaria.setSaldo(saldoFinal);		
			cuentabancariaDAO.update(cuentabancaria);
			
			//actualizando saldo de caja
			Tipomoneda tipomoneda = transaccioncuentabancaria.getTipomoneda();
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccion = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if(tipomoneda.equals(tipomonedaBoveda)){
					bovedaTransaccion = boveda;
					break;
				}
			}
			if(bovedaTransaccion != null){
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();				
				bovedaCajaPK.setIdboveda(bovedaTransaccion.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());
				
				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);
				
				switch (tipoTransaccion) {
				case DEPOSITO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoTransaccion));
					break;
				case RETIRO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoTransaccion));
					break;
				}
				
				bovedaCajaDAO.update(bovedaCaja);
				
			} else {
				throw new Exception("No se puede modificar el saldo de la caja");
			}
			
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);;
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncuentabancaria;
	}
	
	public Transaccioncuentabancaria createDepositoCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria)throws Exception {
		try {
			String numeroCuentabancaria = transaccioncuentabancaria.getCuentabancaria().getNumerocuenta();
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.findByNumerocuenta(numeroCuentabancaria);
			if(cuentabancaria == null){
				throw new Exception("Cuenta bancaria no encontrada");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Tipocuentabancaria tipocuentabancaria = cuentabancaria.getTipocuentabancaria();
			
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA y no se puede realizar transacciones");
			}
			Tipocuentabancaria tipocuentabancariaActive = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			if(tipocuentabancaria.equals(tipocuentabancariaActive)){
				throw new Exception("No se puede hacer operaciones sobre una cuenta a plazo fijo");
			}
			
			//validacion superada
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
				
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			
			//actualizando saldo final
			saldoFinal = saldoFinal.add(montoTransaccion);
				
			cuentabancaria.setSaldo(saldoFinal);
			cuentabancariaDAO.update(cuentabancaria);
			
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);;
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncuentabancaria;
	}
	
	public Transaccioncuentabancaria createRetiroCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria)throws Exception {
		try {
			String numeroCuentabancaria = transaccioncuentabancaria.getCuentabancaria().getNumerocuenta();
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.findByNumerocuenta(numeroCuentabancaria);
			if(cuentabancaria == null){
				throw new Exception("Cuenta bancaria no encontrada");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Tipocuentabancaria tipocuentabancaria = cuentabancaria.getTipocuentabancaria();
			
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA y no se puede realizar transacciones");
			}
			Tipocuentabancaria tipocuentabancariaActive = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			if(tipocuentabancaria.equals(tipocuentabancariaActive)){
				throw new Exception("No se puede hacer operaciones sobre una cuenta a plazo fijo");
			}

			TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccioncuentabancaria.getTipotransaccion());
			boolean isTransaccionvalida = false;

			isTransaccionvalida = isRetiroValido(cuentabancaria,transaccioncuentabancaria.getMonto());
					
			if(isTransaccionvalida == false) {
				throw new InsufficientMoneyForTransactionException("Fondos Insuficientes para Retiro");	
			}
			
			//validacion superada
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
				
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			
			//actualizando saldo final
			saldoFinal = saldoFinal.subtract(montoTransaccion);
			cuentabancaria.setSaldo(saldoFinal);
			cuentabancariaDAO.update(cuentabancaria);
			
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);;
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncuentabancaria;
	}
	
	public boolean isRetiroValido(Cuentabancaria cuentabancaria, Moneda monto) throws Exception {
		if(cuentabancaria.getSaldo().isLessThan(monto))
			return false;
		else 
			return true;
	}

	public boolean isDepositoValido(Cuentabancaria cuentabancaria, Moneda monto) {
		return true;
	}

	@Override
	public Transaccioncompraventa createTransaccionCompraVenta(Caja caja,
			Transaccioncompraventa transaccioncompraventa) throws Exception {
		try {
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal
					.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);

			transaccioncompraventa.setTransaccioncaja(transaccioncaja);
			transaccioncompraventaDAO.create(transaccioncompraventa);

			//actualizando saldo de caja TipomonedaType tipomonedaRecibido
			actualizarMontoEntregadoCaja(caja, transaccioncompraventa); 
			actualizarMontoRecibidoCaja(caja, transaccioncompraventa);
			
		} catch (Exception e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncompraventa;
	}
	
	
	// actualizar el saldo de boveda caja con el monto recibido
	public void actualizarMontoRecibidoCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception {
		try {
			TipomonedaType tipomonedaRecibido = ProduceObject.getTipomoneda(transaccioncompraventa.getTipomonedaRecibido());
			Tipomoneda tipomonedaRecibidaCV = transaccioncompraventa.getTipomonedaRecibido();
			Moneda montoRecibido = transaccioncompraventa.getMontorecibido();
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccionCVRecibida = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if (tipomonedaRecibidaCV.equals(tipomonedaBoveda)) {
					bovedaTransaccionCVRecibida = boveda;
					break;
				}
			}
			if (bovedaTransaccionCVRecibida != null) {
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
				bovedaCajaPK.setIdboveda(bovedaTransaccionCVRecibida.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());

				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);

				switch (tipomonedaRecibido) {
				case NUEVO_SOL:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
					break;
				case DOLAR:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
					break;
				case EURO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
					break;
				default:
					throw new Exception("Este tipo de moneda no esta registrado");
				}
				bovedaCajaDAO.update(bovedaCaja);
			}
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
	}
	
	// actualizar el saldo de boeda caja con el monto Entregado
	public void actualizarMontoEntregadoCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception{
		try {
			TipomonedaType tipomonedaEntregado = ProduceObject.getTipomoneda(transaccioncompraventa.getTipomonedaEntregado());
			Tipomoneda tipomonedaEntregadaCV = transaccioncompraventa.getTipomonedaEntregado();
			Moneda montoEntregado = transaccioncompraventa.getMontoentregado();
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccionCVEntregada = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if (tipomonedaEntregadaCV.equals(tipomonedaBoveda)) {
					bovedaTransaccionCVEntregada = boveda;
					break;
				}
			}
			if (bovedaTransaccionCVEntregada != null) {
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
				bovedaCajaPK.setIdboveda(bovedaTransaccionCVEntregada.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());

				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);

				switch (tipomonedaEntregado) {
				case NUEVO_SOL:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
					break;
				case DOLAR:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
					break;
				case EURO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
					break;
				default:
					throw new Exception("Este tipo de moneda no esta registrado");
				}
				bovedaCajaDAO.update(bovedaCaja);
			}
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
	}
	
	@Override
	public VouchercajaView getVoucherTransaccionBancaria(Transaccioncuentabancaria transaccioncuentabancaria) throws Exception {
		VouchercajaView vouchercajaView = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtransaccioncuentabancaria", transaccioncuentabancaria.getIdtransaccioncuentabancaria());
		List<VouchercajaView> list;
		try {
			list = vouchercajaViewDAO.findByNamedQuery(VouchercajaView.FindByIdTransaccioncuentabancaria, parameters);
			if(list.size() == 1){
				vouchercajaView = list.get(0);
			} else {
				if(list.size() == 0){
					vouchercajaView = null;
				} else {
					throw new Exception("Error: Query resultado >= 2");
				}
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return vouchercajaView;		
	}

	@Override
	public VouchercajaView getVoucherTransaccionCompraVentaMoneda(
			Transaccioncompraventa transaccioncompraventa) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		/*try {
			String numeroCuentabancaria = transaccioncuentabancaria.getCuentabancaria().getNumerocuenta();
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.findByNumerocuenta(numeroCuentabancaria);
			if(cuentabancaria == null){
				throw new Exception("Cuenta bancaria no encontrada");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Tipocuentabancaria tipocuentabancaria = cuentabancaria.getTipocuentabancaria();
			
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA y no se puede realizar transacciones");
			}
			Tipocuentabancaria tipocuentabancariaActive = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			if(tipocuentabancaria.equals(tipocuentabancariaActive)){
				throw new Exception("No se puede hacer operaciones sobre una cuenta a plazo fijo");
			}

			TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccioncuentabancaria.getTipotransaccion());
			boolean isTransaccionvalida = false;

			switch (tipoTransaccion) {
			case DEPOSITO:
				isTransaccionvalida = isDepositoValido(cuentabancaria,transaccioncuentabancaria.getMonto());
				break;
			case RETIRO:
				isTransaccionvalida = isRetiroValido(cuentabancaria,transaccioncuentabancaria.getMonto());
				break;
			default:
				throw new Exception("Tipo de transaccion no valida");
			}
			
			if(isTransaccionvalida == false) {
				switch (tipoTransaccion) {
				case DEPOSITO:
					throw new InvalidTransactionBovedaException("Transaccion no permitida");
				case RETIRO:
					throw new InsufficientMoneyForTransactionException("Fondos Insuficientes para Retiro");
				}
			}
			
			//validacion superada
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
				
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			switch (tipoTransaccion) {
			case DEPOSITO:
				saldoFinal = saldoFinal.add(montoTransaccion);
				break;
			case RETIRO:
				saldoFinal = saldoFinal.subtract(montoTransaccion);
				break;
			}
			
			cuentabancaria.setSaldo(saldoFinal);		
			cuentabancariaDAO.update(cuentabancaria);
			
			//actualizando saldo de caja
			Tipomoneda tipomoneda = transaccioncuentabancaria.getTipomoneda();
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccion = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if(tipomoneda.equals(tipomonedaBoveda)){
					bovedaTransaccion = boveda;
					break;
				}
			}
			if(bovedaTransaccion != null){
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();				
				bovedaCajaPK.setIdboveda(bovedaTransaccion.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());
				
				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);
				
				switch (tipoTransaccion) {
				case DEPOSITO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoTransaccion));
					break;
				case RETIRO:
					bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoTransaccion));
					break;
				}
				
				bovedaCajaDAO.update(bovedaCaja);
				
			} else {
				throw new Exception("No se puede modificar el saldo de la caja");
			}
			
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);;
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncuentabancaria;*/
		return null;
	}

}
