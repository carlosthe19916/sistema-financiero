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
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.boundary.remote.TransaccionCajaServiceRemote;
import org.ventura.dao.impl.BovedaCajaDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.TasainteresDAO;
import org.ventura.dao.impl.TransaccioncajaDAO;
import org.ventura.dao.impl.TransaccioncompraventaDAO;
import org.ventura.dao.impl.TransaccioncuentaaporteDAO;
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.dao.impl.VouchercajaViewDAO;
import org.ventura.dao.impl.VouchercompraventaViewDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.BovedaCaja;
import org.ventura.entity.schema.caja.BovedaCajaPK;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.ViewvouchercompraventaView;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;
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
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransaccionCajaServiceBean implements TransaccionCajaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB
	private CuentaaporteServiceLocal cuentaaporteServiceLocal;
	
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private TransaccioncajaDAO transaccioncajaDAO;
	@EJB
	private TransaccioncuentabancariaDAO transaccioncuentabancariaDAO;
	@EJB
	private TransaccioncuentaaporteDAO transaccioncuentaaporteDAO;
	@EJB
	private TransaccioncompraventaDAO transaccioncompraventaDAO;
	@EJB
	private CuentabancariaDAO cuentabancariaDAO;
	@EJB
	private CuentaaporteDAO cuentaaporteDAO;
	@EJB
	private VouchercajaViewDAO vouchercajaViewDAO;
	@EJB
	private VouchercompraventaViewDAO vouchercompraventaDAO;
	@EJB
	private BovedaCajaDAO bovedaCajaDAO;
	@EJB
	private TasainteresDAO tasainteresDAO;

	
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
			/*Tipocuentabancaria tipocuentabancariaActive = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			if(tipocuentabancaria.equals(tipocuentabancariaActive)){
				throw new Exception("No se puede hacer operaciones sobre una cuenta a plazo fijo");
			}*/

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
			
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setSaldodisponible(saldoFinal);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
			
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
			throw e;
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
	
	public boolean isRetiroValido(Cuentaaporte cuentaaporte, Moneda monto) throws Exception {
		if(cuentaaporte.getSaldo().isEqual(monto))
			return true;
		else 
			return false;
	}

	public boolean isDepositoValido(Cuentabancaria cuentabancaria, Moneda monto) {
		return true;
	}
	
	public boolean isDepositoValido(Cuentaaporte cuentaaporte, Moneda monto) {
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
	
	@Override
	public void extornarTransaccionCompraVenta(Transaccioncompraventa transaccioncompraventa) throws Exception{
		try {
			transaccioncompraventaDAO.update(transaccioncompraventa);
		} catch (Exception e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo extornar la transacción compra venta");
		}
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
			throw new Exception("Error Interno: No se pudo actualizar el monto recibido");
		}
	}
	
	// actualizar el saldo de boveda caja con el monto Entregado
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
			throw new Exception("Error Interno: No se pudo actualizar el monto recibido");
		}
	}
	
	@Override
	public boolean validateSaldoBovedaCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception {
		boolean result = false;

		TipomonedaType tipomonedaEntregado = ProduceObject.getTipomoneda(transaccioncompraventa.getTipomonedaEntregado());
		Tipomoneda tipomonedaEntregadoCV = transaccioncompraventa.getTipomonedaEntregado();

		try {
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccionCVEntregado = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if (tipomonedaEntregadoCV.equals(tipomonedaBoveda)) {
					bovedaTransaccionCVEntregado = boveda;
					break;
				}
			}
			if (bovedaTransaccionCVEntregado != null) {
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
				bovedaCajaPK.setIdboveda(bovedaTransaccionCVEntregado
						.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());

				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);

				switch (tipomonedaEntregado) {
				case NUEVO_SOL:
					if (bovedaCaja.getSaldototal().isGreaterThanOrEqual(transaccioncompraventa.getMontoentregado())) {
						result = true;
					} 
					break;
				case DOLAR:
					if (bovedaCaja.getSaldototal().isGreaterThanOrEqual(transaccioncompraventa.getMontoentregado())) {
						result = true;
					} 
					break;
				case EURO:
					if (bovedaCaja.getSaldototal().isGreaterThanOrEqual(transaccioncompraventa.getMontoentregado())) {
						result = true;
					} 
					break;
				default:
					throw new Exception("Este tipo de moneda no esta registrado");
				}
			}
		} catch (EntityExistsException | IllegalArgumentException
				| TransactionRequiredException e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncompraventa.setIdtransaccioncompraventa(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(
					"Error Interno: No se pudo verificar el saldo total de la caja");
		}
		return result;
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
					throw new Exception("Error: Voucher >= 2");
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
	public ViewvouchercompraventaView getVoucherTransaccionCompraVentaMoneda(Transaccioncompraventa transaccioncompraventa) throws Exception {
		ViewvouchercompraventaView vouchercompraventaView = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtransaccioncompraventa", transaccioncompraventa.getIdtransaccioncompraventa());
		List<ViewvouchercompraventaView> list;
		try {
			list = vouchercompraventaDAO.findByNamedQuery(ViewvouchercompraventaView.FindByIdTransaccioncompraventa, parameters);
			if(list.size() == 1){
				vouchercompraventaView = list.get(0);
			} else {
				if(list.size() == 0){
					vouchercompraventaView = null;
				} else {
					throw new Exception("Error: Query resultado >= 2 transacciones compra venta");
				}
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return vouchercompraventaView;		
	}

	@Override
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		try {
			String numeroCuentaaporte = transaccioncuentaaporte.getCuentaaporte().getNumerocuentaaporte();
			Cuentaaporte cuentaaporte = cuentaaporteServiceLocal.findByNumerocuenta(numeroCuentaaporte);
			if(cuentaaporte == null){
				throw new Exception("Cuenta de aportes no encontrada");
			}
			
			Tipomoneda tipomonedaCuentaaporte = cuentaaporte.getTipomoneda();
			Estadocuenta estadocuentaCuentaaporte = cuentaaporte.getEstadocuenta();		
			Tipomoneda tipomonedaTransaccioncuentaaporte = transaccioncuentaaporte.getTipomoneda();
			
			if(!tipomonedaCuentaaporte.equals(tipomonedaTransaccioncuentaaporte)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentaaporte.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA y no se puede realizar transacciones");
			}

			TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccioncuentaaporte.getTipotransaccion());
			boolean isTransaccionvalida = false;

			switch (tipoTransaccion) {
			case DEPOSITO:
				isTransaccionvalida = isDepositoValido(cuentaaporte,transaccioncuentaaporte.getMonto());
				break;
			case RETIRO:
				isTransaccionvalida = isRetiroValido(cuentaaporte,transaccioncuentaaporte.getMonto());
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
			
			transaccioncuentaaporte.setTransaccioncaja(transaccioncaja);
			transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
			transaccioncuentaaporte.setEstado(true);
			transaccioncuentaaporteDAO.create(transaccioncuentaaporte);
				
			Moneda saldoFinal = cuentaaporte.getSaldo();
			Moneda montoTransaccion = transaccioncuentaaporte.getMonto();
			switch (tipoTransaccion) {
			case DEPOSITO:
				saldoFinal = saldoFinal.add(montoTransaccion);
				break;
			case RETIRO:
				{
					saldoFinal = saldoFinal.subtract(montoTransaccion);
					Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
					cuentaaporte.setEstadocuenta(estadocuenta);
					break;
				}	
			}
			
			cuentaaporte.setSaldo(saldoFinal);		
			cuentaaporteDAO.update(cuentaaporte);
			
			//actualizando saldo de caja
			Tipomoneda tipomoneda = transaccioncuentaaporte.getTipomoneda();
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
			transaccioncuentaaporte.setIdtransaccioncuentaaporte(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			transaccioncuentaaporte.setIdtransaccioncuentaaporte(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return transaccioncuentaaporte;
	}

	@Override
	public Transaccioncompraventa find(Object id) throws Exception {
		Transaccioncompraventa transaccioncompraventa = null;
		try {
			transaccioncompraventa = transaccioncompraventaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return transaccioncompraventa;
	}
}


