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
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
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
	private TransaccioncuentabancariaDAO transaccioncuentabancariaDAO;
	
	@Override
	public Transaccioncuentabancaria create(Transaccioncuentabancaria transaccioncuentabancaria)throws Exception {
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
			//transaccioncaja.setHistorialcaja(cajaServiceLocal.);
					
			
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
				
			transaccioncuentabancariaDAO.create(transaccioncuentabancaria);
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

}
