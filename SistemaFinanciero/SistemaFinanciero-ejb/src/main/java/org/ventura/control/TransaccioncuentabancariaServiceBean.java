package org.ventura.control;

import java.util.Calendar;

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
import org.ventura.boundary.local.TransaccioncuentabancariaServiceLocal;
import org.ventura.boundary.remote.TransaccioncuentabancariaServiceRemote;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.TransaccioncajaDAO;
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
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
@Local(TransaccioncuentabancariaServiceLocal.class)
@Remote(TransaccioncuentabancariaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TransaccioncuentabancariaServiceBean implements TransaccioncuentabancariaServiceLocal {

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
	private CuentabancariaDAO cuentabancariaDAO;
	
	
	@Override
	public Transaccioncuentabancaria create(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria)throws Exception {
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

}
