package org.ventura.control;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import org.ventura.dao.impl.CajaMovimientoViewDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.DetalletransaccioncajaDAO;
import org.ventura.dao.impl.TasainteresDAO;
import org.ventura.dao.impl.TransaccioncajaDAO;
import org.ventura.dao.impl.TransaccioncajacajaDAO;
import org.ventura.dao.impl.TransaccioncompraventaDAO;
import org.ventura.dao.impl.TransaccioncuentaaporteDAO;
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.dao.impl.TransaccionmayorcuantiaDAO;
import org.ventura.dao.impl.VouchercajaCuentaaporteViewDAO;
import org.ventura.dao.impl.VouchercajaViewDAO;
import org.ventura.dao.impl.VouchercompraventaViewDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.BovedaCaja;
import org.ventura.entity.schema.caja.BovedaCajaPK;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detalletransaccioncaja;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncajacaja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.Transaccionmayorcuantia;
import org.ventura.entity.schema.caja.view.CajaMovimientoView;
import org.ventura.entity.schema.caja.view.ViewvouchercompraventaView;
import org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.InsufficientMoneyForTransactionException;
import org.ventura.util.exception.InvalidTransactionBovedaException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;

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
	private VouchercajaCuentaaporteViewDAO vouchercajaCuentaaporteViewDAO;
	@EJB
	private VouchercompraventaViewDAO vouchercompraventaDAO;
	@EJB
	private BovedaCajaDAO bovedaCajaDAO;
	@EJB
	private TasainteresDAO tasainteresDAO;
	@EJB
	private DetalletransaccioncajaDAO detalletransaccioncajaDAO;
	
	@EJB
	private TransaccionmayorcuantiaDAO transasccionmayorcuantiaDAO;
	
	@EJB
	private CajaMovimientoViewDAO cajaMovimientoViewDAO;

	@EJB
	private TransaccioncajacajaDAO transaccioncajacajaDAO;
	
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
	public Transaccioncompraventa createTransaccionCompraVenta(Caja caja,Transaccioncompraventa transaccioncompraventa, Usuario usuario) throws Exception {
		try {
			boolean extornar = false;
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncaja.setUsuario(usuario);
			transaccioncajaDAO.create(transaccioncaja);

			transaccioncompraventa.setTransaccioncaja(transaccioncaja);
			transaccioncompraventaDAO.create(transaccioncompraventa);

			//actualizando saldo de caja TipomonedaType tipomonedaRecibido
			actualizarMontoEntregadoCaja(caja, transaccioncompraventa, extornar); 
			actualizarMontoRecibidoCaja(caja, transaccioncompraventa, extornar);
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
	public void extornarTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception{
		try {
			boolean extornar = true;
			//actualizando saldo de caja TipomonedaType tipomonedaRecibido
			actualizarMontoEntregadoCaja(caja, transaccioncompraventa, extornar); 
			actualizarMontoRecibidoCaja(caja, transaccioncompraventa, extornar);
			transaccioncompraventaDAO.update(transaccioncompraventa);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}
	
	// actualizar el saldo de boveda caja con el monto recibido
	public void actualizarMontoRecibidoCaja(Caja caja, Transaccioncompraventa transaccioncompraventa, boolean extornar) throws Exception {
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
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoRecibido));
						break;
					}
				case DOLAR:
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoRecibido));
						break;
					}
				case EURO:
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoRecibido));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoRecibido));
						break;
					}
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
			throw new EJBException(e.getMessage());
		}
	}
	
	// actualizar el saldo de boveda caja con el monto Entregado
	public void actualizarMontoEntregadoCaja(Caja caja, Transaccioncompraventa transaccioncompraventa, boolean extornar) throws Exception{
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
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoEntregado));
						break;
					}
				case DOLAR:
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoEntregado));
						break;
					}
				case EURO:
					if (extornar != true) {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().subtract(montoEntregado));
						break;
					}else {
						bovedaCaja.setSaldototal(bovedaCaja.getSaldototal().add(montoEntregado));
						break;
					}
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
			throw new EJBException(e.getMessage());
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
	public VouchercajaCuentaaporteView getVoucherTransaccionCuentaaporte(Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		VouchercajaCuentaaporteView vouchercajaCuentaaporteView = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtransaccioncuentaaporte", transaccioncuentaaporte.getIdtransaccioncuentaaporte());
		List<VouchercajaCuentaaporteView> list;
		try {
			list = vouchercajaCuentaaporteViewDAO.findByNamedQuery(VouchercajaCuentaaporteView.FindByIdTransaccioncuentaaporte, parameters);
			if(list.size() == 1){
				vouchercajaCuentaaporteView = list.get(0);
			} else {
				if(list.size() == 0){
					vouchercajaCuentaaporteView = null;
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
		
		return vouchercajaCuentaaporteView;		
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
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte, Usuario usuario, Transaccionmayorcuantia transasccionmayorcuantia) throws Exception {
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
				
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(Calendar.getInstance().getTime());
			transaccioncaja.setHora(Calendar.getInstance().getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncaja.setUsuario(usuario);
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentaaporte.setTransaccioncaja(transaccioncaja);
			transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
			transaccioncuentaaporte.setEstado(true);
			transaccioncuentaaporte.setSaldodisponible(saldoFinal.getValue());
			transaccioncuentaaporteDAO.create(transaccioncuentaaporte);
										
			cuentaaporte.setSaldo(saldoFinal);		
			cuentaaporteDAO.update(cuentaaporte);
			
			//creando la transaccion de mayor cuantia en caso de existir
			if(transasccionmayorcuantia != null)
				transasccionmayorcuantiaDAO.create(transasccionmayorcuantia);
			
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
			throw e;
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

	
	/**
	 * Transccionales*/
	@Override
	public Transaccioncuentabancaria deposito(Caja caja,Cuentabancaria cuentabancariaVista,Transaccioncuentabancaria transaccioncuentabancaria, Map<Denominacionmoneda, Integer> detalleTransaccion, Usuario usuario, Transaccionmayorcuantia transasccionmayorcuantia) throws Exception {
		try {
			int cuentabancariaPKey = cuentabancariaVista.getIdcuentabancaria();			
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaPKey);		
			if(cuentabancaria == null){
				throw new Exception("La cuenta bancaria no existe");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();						
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda TRANSACCION y CUENTA BANCARIA no coinciden");
			}
			
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA, no se puede realizar transacciones");
			}
			
			//validacion superada
			Calendar calendar = Calendar.getInstance();
			
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(calendar.getTime());
			transaccioncaja.setHora(calendar.getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncaja.setUsuario(usuario);
			transaccioncajaDAO.create(transaccioncaja);
				
			//creando el detalle de la transaccion
			if(detalleTransaccion != null){
				for(Denominacionmoneda key : detalleTransaccion.keySet()) {
					Integer cantidad = detalleTransaccion.get(key);
					
					Detalletransaccioncaja detalletransaccioncaja = new Detalletransaccioncaja();
					detalletransaccioncaja.setCantidad(cantidad);
					detalletransaccioncaja.setDenominacionmoneda(key);
					detalletransaccioncaja.setTransaccioncaja(transaccioncaja);
					detalletransaccioncajaDAO.create(detalletransaccioncaja);
				}				
			}
			
			//si el monto es mucho se crea una transaccion de mayor cuantia
			if(transasccionmayorcuantia != null)
				transasccionmayorcuantiaDAO.create(transasccionmayorcuantia);
			
			//
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			saldoFinal = saldoFinal.add(montoTransaccion);
				
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO));
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setSaldodisponible(saldoFinal);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
			
			cuentabancaria.setSaldo(saldoFinal);		
			cuentabancariaDAO.update(cuentabancaria);
			
			//actualizando saldo de caja
			cajaServiceLocal.updateSaldo(caja, transaccioncuentabancaria);	
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentabancaria;
	}

	@Override
	public Transaccioncuentabancaria retiro(Caja caja,Cuentabancaria cuentabancariaVista,Transaccioncuentabancaria transaccioncuentabancaria, Map<Denominacionmoneda, Integer> detalleTransaccion, Usuario usuario) throws Exception {
		try {
			int cuentabancariaPKey = cuentabancariaVista.getIdcuentabancaria();			
			Cuentabancaria cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaPKey);		
			if(cuentabancaria == null){
				throw new Exception("La cuenta bancaria no existe");
			}
			
			Tipomoneda tipomonedaCuentabancaria = cuentabancaria.getTipomoneda();						
			Tipomoneda tipomonedaTransaccionbancaria = transaccioncuentabancaria.getTipomoneda();
			if(!tipomonedaCuentabancaria.equals(tipomonedaTransaccionbancaria)){
				throw new Exception("Tipos de moneda TRANSACCION y CUENTA BANCARIA no coinciden");
			}
			
			Estadocuenta estadocuentaCuentabancaria = cuentabancaria.getEstadocuenta();
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentabancaria.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA, no se puede realizar transacciones");
			}
			
			Moneda saldoFinal = cuentabancaria.getSaldo();
			Moneda montoTransaccion = transaccioncuentabancaria.getMonto();
			saldoFinal = saldoFinal.subtract(montoTransaccion);
			if(saldoFinal.isLessThan(new Moneda(0))){
				throw new Exception("La cuenta no tiene saldo suficiente para realizar la transaccion");
			}
			
			//validacion superada
			Calendar calendar = Calendar.getInstance();
			
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(calendar.getTime());
			transaccioncaja.setHora(calendar.getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncaja.setUsuario(usuario);
			transaccioncajaDAO.create(transaccioncaja);
			
			//creando el detalle de la transaccion
			if(detalleTransaccion != null){
				for(Denominacionmoneda key : detalleTransaccion.keySet()) {
					Integer cantidad = detalleTransaccion.get(key);
					
					Detalletransaccioncaja detalletransaccioncaja = new Detalletransaccioncaja();
					detalletransaccioncaja.setCantidad(cantidad);
					detalletransaccioncaja.setDenominacionmoneda(key);
					detalletransaccioncaja.setTransaccioncaja(transaccioncaja);
					detalletransaccioncajaDAO.create(detalletransaccioncaja);
				}				
			}
			
			//creando la transaccion bancaria
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));
			transaccioncuentabancaria.setTransaccioncaja(transaccioncaja);
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setSaldodisponible(saldoFinal);
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
			
			cuentabancaria.setSaldo(saldoFinal);		
			cuentabancariaDAO.update(cuentabancaria);
			
			//actualizando saldo de caja
			cajaServiceLocal.updateSaldo(caja, transaccioncuentabancaria);				
		} catch (Exception e) {
			transaccioncuentabancaria.setIdtransaccioncuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentabancaria;
	}


	@Override
	public Transaccioncuentaaporte deposito(Caja caja, Cuentaaporte cuentaaporteVista, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		try {
			int cuentaaportePK = cuentaaporteVista.getIdcuentaaporte();
			Cuentaaporte cuentaaporte = cuentaaporteServiceLocal.find(cuentaaportePK);		
			if(cuentaaporte == null){
				throw new Exception("Cuenta de aportes no existe");
			}
			
			Tipomoneda tipomonedaCuentaaporte = cuentaaporte.getTipomoneda();
			Tipomoneda tipomonedaTransaccioncuentaaporte = transaccioncuentaaporte.getTipomoneda();
			if(!tipomonedaCuentaaporte.equals(tipomonedaTransaccioncuentaaporte)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			
			Estadocuenta estadocuentaCuentaaporte = cuentaaporte.getEstadocuenta();											
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentaaporte.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA, no se puede realizar transacciones");
			}
			
			//validacion superada
			Calendar calendar = Calendar.getInstance();
			
			Moneda saldoFinal = cuentaaporte.getSaldo();
			Moneda montoTransaccion = transaccioncuentaaporte.getMonto();
			saldoFinal = saldoFinal.add(montoTransaccion);
				
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(calendar.getTime());
			transaccioncaja.setHora(calendar.getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentaaporte.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO));
			transaccioncuentaaporte.setTransaccioncaja(transaccioncaja);
			transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
			transaccioncuentaaporte.setEstado(true);
			transaccioncuentaaporte.setSaldodisponible(saldoFinal.getValue());
			transaccioncuentaaporteDAO.create(transaccioncuentaaporte);
										
			cuentaaporte.setSaldo(saldoFinal);		
			cuentaaporteDAO.update(cuentaaporte);
			
			//actualizando saldo de caja
			cajaServiceLocal.updateSaldo(caja, transaccioncuentaaporte);			
		} catch (Exception e) {
			transaccioncuentaaporte.setIdtransaccioncuentaaporte(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentaaporte;
	}


	@Override
	public Transaccioncuentaaporte retiro(Caja caja, Cuentaaporte cuentaaporteVista, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		try {
			int cuentaaportePK = cuentaaporteVista.getIdcuentaaporte();
			Cuentaaporte cuentaaporte = cuentaaporteServiceLocal.find(cuentaaportePK);		
			if(cuentaaporte == null){
				throw new Exception("Cuenta de aportes no existe");
			}
			
			Tipomoneda tipomonedaCuentaaporte = cuentaaporte.getTipomoneda();
			Tipomoneda tipomonedaTransaccioncuentaaporte = transaccioncuentaaporte.getTipomoneda();
			if(!tipomonedaCuentaaporte.equals(tipomonedaTransaccioncuentaaporte)){
				throw new Exception("Tipos de moneda incompatibles");
			}
			
			Estadocuenta estadocuentaCuentaaporte = cuentaaporte.getEstadocuenta();											
			Estadocuenta estadocuentaActivo = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			if(!estadocuentaCuentaaporte.equals(estadocuentaActivo)){
				throw new Exception("La cuenta no esta ACTIVA, no se puede realizar transacciones");
			}
			
			Moneda saldoFinal = cuentaaporte.getSaldo();
			Moneda montoTransaccion = transaccioncuentaaporte.getMonto();
			saldoFinal = saldoFinal.add(montoTransaccion);
			if(saldoFinal.isLessThan(new Moneda(0))){
				throw new Exception("Fondos insuficientes para realizar la transaccion");
			}
			
			//validacion superada
			Calendar calendar = Calendar.getInstance();
								
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setFecha(calendar.getTime());
			transaccioncaja.setHora(calendar.getTime());
			transaccioncaja.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
			transaccioncajaDAO.create(transaccioncaja);
			
			transaccioncuentaaporte.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));
			transaccioncuentaaporte.setTransaccioncaja(transaccioncaja);
			transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
			transaccioncuentaaporte.setEstado(true);
			transaccioncuentaaporte.setSaldodisponible(saldoFinal.getValue());
			transaccioncuentaaporteDAO.create(transaccioncuentaaporte);
										
			cuentaaporte.setSaldo(saldoFinal);		
			cuentaaporteDAO.update(cuentaaporte);
			
			//actualizando saldo de caja
			cajaServiceLocal.updateSaldo(caja, transaccioncuentaaporte);			
		} catch (Exception e) {
			transaccioncuentaaporte.setIdtransaccioncuentaaporte(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentaaporte;
	}


	@Override
	public List<CajaMovimientoView> getTransaccionesCajaWithHistorialActivo(Caja caja) throws Exception {
		List<CajaMovimientoView> cajaMovimientoViews;
		try {
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			Map<String, Object> parameters = new HashMap<String, Object>();		
			parameters.put("idcaja", caja.getIdcaja());
			parameters.put("idhistorialcaja", historialcaja.getIdhistorialcaja());
			
			cajaMovimientoViews = cajaMovimientoViewDAO.findByNamedQuery(CajaMovimientoView.f_idcaja_idhistorialcaja, parameters);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cajaMovimientoViews;
	}
	
	@Override
	public List<CajaMovimientoView> buscarTransaccionCaja(Caja caja, Integer id) throws Exception {
		List<CajaMovimientoView> cajaMovimientoViews;
		try {
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtransaccioncaja", id);
			parameters.put("idcaja", caja.getIdcaja());
			parameters.put("idhistorialcaja", historialcaja.getIdhistorialcaja());
			
			cajaMovimientoViews = cajaMovimientoViewDAO.findByNamedQuery(CajaMovimientoView.f_idtransaccioncaja_idcaja_idhistorialcaja, parameters, 50);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cajaMovimientoViews;
	}


	@Override
	public void extornarTransaccion(CajaMovimientoView cajaMovimientoView) throws Exception {
		try {
			int idTransaccioncaja = cajaMovimientoView.getIdTransaccioncaja();
			Transaccioncaja transaccioncaja = transaccioncajaDAO.find(idTransaccioncaja);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtransaccioncaja", transaccioncaja.getIdtransaccioncaja());
			
			List<Transaccioncuentaaporte> listTransaccionCuentaaporte = transaccioncuentaaporteDAO.findByNamedQuery(Transaccioncuentaaporte.f_idtransaccioncaja, parameters);
			List<Transaccioncuentabancaria> listTransaccionCuentabancaria = transaccioncuentabancariaDAO.findByNamedQuery(Transaccioncuentabancaria.f_idtransaccioncaja, parameters);
			List<Transaccioncompraventa> listTransaccionCompraventa = transaccioncompraventaDAO.findByNamedQuery(Transaccioncompraventa.f_idtransaccioncaja, parameters);
			
			if(listTransaccionCuentaaporte.size() + listTransaccionCuentabancaria.size() + listTransaccionCompraventa.size() == 1){
				Caja caja = new Caja();
				caja.setIdcaja(cajaMovimientoView.getIdCaja());
				if(listTransaccionCuentaaporte.size() == 1) {
					Transaccioncuentaaporte transaccioncuentaaporte = listTransaccionCuentaaporte.get(0);
					extornarTransaccionCuentaaporte(caja, transaccioncuentaaporte);
				}
				if(listTransaccionCuentabancaria.size() == 1) {
					Transaccioncuentabancaria transaccioncuentabancaria = listTransaccionCuentabancaria.get(0);
					extornarTransaccionCuentabancaria(caja, transaccioncuentabancaria);
				}
				if(listTransaccionCompraventa.size() == 1) {
					Transaccioncompraventa transaccioncompraventa = listTransaccionCompraventa.get(0);
					extornarTransaccionCompraVenta(caja, transaccioncompraventa);
				}
			} else {
				throw new Exception("No se encontró transaccion válida para extornar");
			}
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}


	@Override
	public void extornarTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception {
		try {
			Integer idTransaccion = transaccioncuentaaporte.getIdtransaccioncuentaaporte();								
			Transaccioncuentaaporte transaccioncuentaaporteDB = transaccioncuentaaporteDAO.find(idTransaccion);
					
			//poner la transaccion en estado false
			if(transaccioncuentaaporteDB.getEstado() == true){
				Calendar calendar = Calendar.getInstance();
				transaccioncuentaaporteDB.setEstado(false);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				transaccioncuentaaporteDB.setReferencia("Extornado el " + formatter.format(calendar.getTime()));
				transaccioncuentaaporteDAO.update(transaccioncuentaaporteDB);
			} else {
				throw new Exception("La transaccion ya esta extornada");
			}
			
			//actualizar el saldo de la caja
			Transaccioncaja transaccioncaja = transaccioncuentaaporteDB.getTransaccioncaja();
			Caja cajaTransaccion = transaccioncaja.getHistorialcaja().getCaja();
			
			if(caja.equals(cajaTransaccion)){
				cajaServiceLocal.updateSaldo(caja, transaccioncuentaaporteDB);
			} else {
				throw new Exception("Solo la caja que relizó la transaccion puede extornar la operacion");
			}	
			
			//actualizar saldo de cuenta de aportes			
			Cuentaaporte cuentaaporte = transaccioncuentaaporteDB.getCuentaaporte();
			Moneda montoTransaccion = transaccioncuentaaporteDB.getMonto();
			Moneda saldoCuenta = cuentaaporte.getSaldo();
			
			Tipotransaccion tipotransaccion = transaccioncuentaaporteDB.getTipotransaccion();
			TipoTransaccionType tipoTransaccionType = ProduceObject.getTipotransaccion(tipotransaccion);
			switch (tipoTransaccionType) {
			case DEPOSITO:
				if(saldoCuenta.isGreaterThanOrEqual(montoTransaccion)){
					Moneda saldoFinal = saldoCuenta.subtract(montoTransaccion);
					cuentaaporte.setSaldo(saldoFinal);
					cuentaaporteDAO.update(cuentaaporte);
					
					//recalcular las transacciones anteriores a la transaccion extornada
					Date fechaHoraTransaccion = transaccioncuentaaporteDB.getTransaccioncaja().getHora();
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("begindate", fechaHoraTransaccion);
					parameters.put("idcuentaaporte", cuentaaporte.getIdcuentaaporte());
									
					List<Transaccioncuentaaporte> listTransaccionesAnteriores = transaccioncuentaaporteDAO.findByNamedQuery(Transaccioncuentaaporte.f_get_begindate_idcuentaaporte,parameters);
					for (Transaccioncuentaaporte t : listTransaccionesAnteriores) {
						t.setSaldodisponible(t.getSaldodisponible().subtract(montoTransaccion.getValue()));
						transaccioncuentaaporteDAO.update(t);
					}
				} else {
					throw new Exception("No se puede extornar la operacion porque la cuenta no cuenta con saldo suficienta para extornar");
				}
				break;
			case RETIRO :
				throw new Exception("La cuenta fue cancelada no se puede extornar la transaccion");
			default:
				break;
			}	
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void extornarTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception {
		try {
			Integer idTransaccion = transaccioncuentabancaria.getIdtransaccioncuentabancaria();								
			Transaccioncuentabancaria transaccioncuentabancariaDB = transaccioncuentabancariaDAO.find(idTransaccion);
					
			//poner la transaccion en estado false
			if(transaccioncuentabancariaDB.getEstado() == true){
				Calendar calendar = Calendar.getInstance();
				transaccioncuentabancariaDB.setEstado(false);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				transaccioncuentabancariaDB.setReferencia("Extornado el " + formatter.format(calendar.getTime()));
				transaccioncuentabancariaDAO.update(transaccioncuentabancariaDB);
			} else {
				throw new Exception("La transaccion ya esta extornada");
			}
			
			//actualizar el saldo de la caja
			Transaccioncaja transaccioncaja = transaccioncuentabancariaDB.getTransaccioncaja();
			Caja cajaTransaccion = transaccioncaja.getHistorialcaja().getCaja();
			
			if(caja.equals(cajaTransaccion)){
				cajaServiceLocal.updateSaldo(caja, transaccioncuentabancariaDB);
			} else {
				throw new Exception("Solo la caja que relizó la transaccion puede extornar la operacion");
			}	
			
			//actualizar saldo de cuenta de aportes			
			Cuentabancaria cuentabancaria = transaccioncuentabancariaDB.getCuentabancaria();
			Moneda montoTransaccion = transaccioncuentabancariaDB.getMonto();
			Moneda saldoCuenta = cuentabancaria.getSaldo();
			
			Tipotransaccion tipotransaccion = transaccioncuentabancariaDB.getTipotransaccion();
			TipoTransaccionType tipoTransaccionType = ProduceObject.getTipotransaccion(tipotransaccion);
			switch (tipoTransaccionType) {
			case DEPOSITO:
				if(saldoCuenta.isGreaterThanOrEqual(montoTransaccion)){
					Moneda saldoFinal = saldoCuenta.subtract(montoTransaccion);
					cuentabancaria.setSaldo(saldoFinal);
					cuentabancariaDAO.update(cuentabancaria);
					
					//recalcular las transacciones anteriores a la transaccion extornada
					Date fechaHoraTransaccion = transaccioncuentabancariaDB.getTransaccioncaja().getHora();
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("begindate", fechaHoraTransaccion);
					parameters.put("idcuentabancaria", cuentabancaria.getIdcuentabancaria());
									
					List<Transaccioncuentabancaria> listTransaccionesAnteriores = transaccioncuentabancariaDAO.findByNamedQuery(Transaccioncuentabancaria.f_get_begindate_idcuentaaporte,parameters);
					for (Transaccioncuentabancaria t : listTransaccionesAnteriores) {
						t.setSaldodisponible(t.getSaldodisponible().subtract(montoTransaccion));
						transaccioncuentabancariaDAO.update(t);
					}
				} else {
					throw new Exception("No se puede extornar la operacion porque la cuenta no cuenta con saldo suficienta para extornar");
				}
				break;
			case RETIRO :				
				Moneda saldoFinal = saldoCuenta.add(montoTransaccion);
				cuentabancaria.setSaldo(saldoFinal);
				cuentabancariaDAO.update(cuentabancaria);

				// recalcular las transacciones anteriores a la transaccion
				// extornada
				Date fechaHoraTransaccion = transaccioncuentabancariaDB.getTransaccioncaja().getHora();
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("begindate", fechaHoraTransaccion);
				parameters.put("idcuentabancaria",cuentabancaria.getIdcuentabancaria());

				List<Transaccioncuentabancaria> listTransaccionesAnteriores = transaccioncuentabancariaDAO.findByNamedQuery(Transaccioncuentabancaria.f_get_begindate_idcuentaaporte,parameters);
				for (Transaccioncuentabancaria t : listTransaccionesAnteriores) {
					t.setSaldodisponible(t.getSaldodisponible().add(montoTransaccion));
					transaccioncuentabancariaDAO.update(t);
				}
			default:
				break;
			}	
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}


	@Override
	public Transaccioncajacaja crearTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja,Caja origen, Caja destino, Usuario usuarioSolicita) throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			
			Historialcaja historialOrigen = cajaServiceLocal.getHistorialcajaLastActive(origen);
			Historialcaja historialDestino = cajaServiceLocal.getHistorialcajaLastActive(destino);
			
			Caja cajaOrigen = historialOrigen.getCaja();
			Caja cajaDestino = historialDestino.getCaja();
			if(cajaOrigen.equals(cajaDestino)){
				throw new Exception("Caja origen y destino deben de ser diferentes");
			}
			
			//verificar si ambas cajas tienen las monedas de la transaccion
			Tipomoneda tipomoneda = transaccioncajacaja.getTipomoneda();
			
			List<Tipomoneda> monedasOrigen = new ArrayList<Tipomoneda>();
			List<Tipomoneda> monedasDestino = new ArrayList<Tipomoneda>();
			for (Boveda b : cajaOrigen.getBovedas()) {
				monedasOrigen.add(b.getTipomoneda());				
			}
			for (Boveda b : cajaDestino.getBovedas()) {
				monedasDestino.add(b.getTipomoneda());				
			}
			if(!monedasOrigen.contains(tipomoneda) || !monedasDestino.contains(tipomoneda)){
				throw new Exception("La caja origen y/o destino no esta asignado con este tipo de moneda");
			}
			
			//verificar si la caja origen tiene el monto de la transaccion en su boveda
			Boveda bovedaTransaccion = null;
			for (Boveda b : cajaOrigen.getBovedas()) {
				if(b.getTipomoneda().equals(tipomoneda)){
					bovedaTransaccion = b;
				}
			}
			if(bovedaTransaccion == null){
				throw new Exception("No se encontraron bovedas para la moneda, en la caja transaccion origen");
			}
			BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
			bovedaCajaPK.setIdboveda(bovedaTransaccion.getIdboveda());
			bovedaCajaPK.setIdcaja(cajaOrigen.getIdcaja());
			BovedaCaja bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);
			if(bovedaCaja.getSaldototal().isLessThan(new Moneda(transaccioncajacaja.getMonto()))){
				throw new Exception("El saldo de la caja es:"+bovedaCaja.getSaldototal().getValue()+" y la transaccion es:"+transaccioncajacaja.getMonto());
			}
			
			//verificacion superada
			transaccioncajacaja.setHistorialcajaorigen(historialOrigen);
			transaccioncajacaja.setHistorialcajadestino(historialDestino);
			transaccioncajacaja.setEstadoconfirmacion(false);
			transaccioncajacaja.setEstadosolicitud(true);
			transaccioncajacaja.setFecha(date);
			transaccioncajacaja.setHora(new Timestamp(date.getTime()));
			transaccioncajacaja.setUsuarioSolicita(usuarioSolicita);
			transaccioncajacajaDAO.create(transaccioncajacaja);
			
			//no se actualizan los saldos porque la transaccion no fue confirmada
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncajacaja;
	}

	@Override
	public Transaccioncajacaja confirmarTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja, Usuario usuarioConfirma) throws Exception {
		Transaccioncajacaja transaccioncajacajaDB = null;
		try {
			transaccioncajacajaDB = transaccioncajacajaDAO.find(transaccioncajacaja.getIdtransaccioncajacaja());
			if(transaccioncajacajaDB.getEstadosolicitud() == true){
				transaccioncajacajaDB.setEstadoconfirmacion(true);
				transaccioncajacajaDB.setUsuarioConfirma(usuarioConfirma);
				transaccioncajacajaDAO.update(transaccioncajacajaDB);
				
				//actualizar saldos de caja
				Tipomoneda tipomoneda = transaccioncajacajaDB.getTipomoneda();
				BigDecimal montoTransaccion = transaccioncajacajaDB.getMonto();
				
				Historialcaja historialOrigen = transaccioncajacajaDB.getHistorialcajaorigen();
				Historialcaja historialDestino = transaccioncajacajaDB.getHistorialcajadestino();
					
				boolean saldoModificadoOrigen = false;
				boolean saldoModificadoDestino = false;
				for (Boveda b : historialOrigen.getCaja().getBovedas()) {
					if(b.getTipomoneda().equals(tipomoneda)){
						saldoModificadoOrigen = true;
						
						BovedaCajaPK pk = new BovedaCajaPK();
						pk.setIdboveda(b.getIdboveda());
						pk.setIdcaja(historialOrigen.getCaja().getIdcaja());
						
						BovedaCaja bovedaCaja = bovedaCajaDAO.find(pk);
						Moneda saldoFinal = bovedaCaja.getSaldototal().subtract(new Moneda(montoTransaccion));	
						if(saldoFinal.isGreaterThanOrEqual(new Moneda())){
							bovedaCaja.setSaldototal(saldoFinal);
							bovedaCajaDAO.update(bovedaCaja);
						} else{
							throw new EJBException("El saldo actual de la caja es insuficiente para realizar la transaccion");
						}						
					}				
				}
				for (Boveda b : historialDestino.getCaja().getBovedas()) {
					if(b.getTipomoneda().equals(tipomoneda)){
						saldoModificadoDestino = true;
						
						BovedaCajaPK pk = new BovedaCajaPK();
						pk.setIdboveda(b.getIdboveda());
						pk.setIdcaja(historialDestino.getCaja().getIdcaja());
						
						BovedaCaja bovedaCaja = bovedaCajaDAO.find(pk);
						Moneda saldoFinal = bovedaCaja.getSaldototal().add(new Moneda(montoTransaccion));						
						bovedaCaja.setSaldototal(saldoFinal);
						bovedaCajaDAO.update(bovedaCaja);
					}				
				}
				
				if(saldoModificadoOrigen == false || saldoModificadoDestino == false){
					throw new Exception("Los saldos de las cajas no pudieron ser modificadas");
				}
			} else{
				throw new Exception("La transaccion fue cancelada por la caja origen");
			}		
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		transaccioncajacajaDB.getHistorialcajaorigen().getCaja();
		transaccioncajacajaDB.getHistorialcajadestino().getCaja();
		return transaccioncajacajaDB;
	}


	@Override
	public void cancelarTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja) throws Exception {
		try {
			Transaccioncajacaja transaccioncajacajaDB = transaccioncajacajaDAO.find(transaccioncajacaja.getIdtransaccioncajacaja());
			if(transaccioncajacajaDB.getEstadosolicitud() == true && transaccioncajacajaDB.getEstadoconfirmacion() == true){
				throw new Exception("La transaccion ya fue confirmada no se puede cancelar");
			} else{
				transaccioncajacajaDB.setEstadosolicitud(false);
				transaccioncajacajaDB.setEstadoconfirmacion(false);
				transaccioncajacajaDAO.update(transaccioncajacajaDB);
			}		
		} catch (Exception e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public List<Transaccioncajacaja> getTransaccionesCajaCaja(Caja caja) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Transaccioncajacaja> getTransaccionesEnviadasCajaCaja(Caja caja) throws Exception {
		List<Transaccioncajacaja> transaccioncajacajas;
		try {
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idhistorialcajaorigen", historialcaja.getIdhistorialcaja());
			
			transaccioncajacajas = transaccioncajacajaDAO.findByNamedQuery(Transaccioncajacaja.f_idhistorialorigen,parameters);	
			for (Transaccioncajacaja transaccioncajacaja : transaccioncajacajas) {
				Historialcaja ho = transaccioncajacaja.getHistorialcajaorigen();
				Historialcaja hd = transaccioncajacaja.getHistorialcajadestino();
				Caja co = ho.getCaja();
				Caja cd = hd.getCaja();
				
				ho.setCaja(co);
				hd.setCaja(cd);
				
				transaccioncajacaja.setHistorialcajadestino(ho);
				transaccioncajacaja.setHistorialcajadestino(hd);
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return transaccioncajacajas;
	}


	@Override
	public List<Transaccioncajacaja> getTransaccionesPorConfirmarCajaCaja(Caja caja) throws Exception {
		List<Transaccioncajacaja> transaccioncajacajas;
		try {
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idhistorialcajadestino", historialcaja.getIdhistorialcaja());
			
			transaccioncajacajas = transaccioncajacajaDAO.findByNamedQuery(Transaccioncajacaja.f_idhistorialdestino,parameters);	
			for (Transaccioncajacaja transaccioncajacaja : transaccioncajacajas) {
				Historialcaja ho = transaccioncajacaja.getHistorialcajaorigen();
				Historialcaja hd = transaccioncajacaja.getHistorialcajadestino();
				Caja co = ho.getCaja();
				Caja cd = hd.getCaja();
				
				ho.setCaja(co);
				hd.setCaja(cd);
				
				transaccioncajacaja.setHistorialcajadestino(ho);
				transaccioncajacaja.setHistorialcajadestino(hd);
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return transaccioncajacajas;
	}


	@Override
	public List<Detalletransaccioncaja> getDetalleTransaccionCaja(Transaccioncaja transaccioncaja) throws Exception {
		List<Detalletransaccioncaja> detalletransaccioncajas;
		try {		
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtransaccioncaja", transaccioncaja.getIdtransaccioncaja());
			
			detalletransaccioncajas = detalletransaccioncajaDAO.findByNamedQuery(Detalletransaccioncaja.f_idtransaccioncaja,parameters);	
						
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return detalletransaccioncajas;
	}

}


