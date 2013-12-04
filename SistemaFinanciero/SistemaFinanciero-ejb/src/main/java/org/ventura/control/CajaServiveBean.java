package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.CajaDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;

@Named
@Stateless
@Local(CajaServiceLocal.class)
@Remote(CajaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiveBean implements CajaServiceLocal{

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private CajaDAO cajaDAO;
	@Inject
	private Log log;
	
	public CajaServiveBean() {
		
	}

	@Override
	public Caja create(Caja oCaja) throws Exception {
		try {
			preCreateCaja(oCaja);
			cajaDAO.create(oCaja);
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			oCaja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			oCaja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return oCaja;
	}
	
	public void preCreateCaja(Caja oCaja) throws Exception{
		String denominacionCaja = oCaja.getDenominacion();
		String abreviaturaCaja = oCaja.getAbreviatura();
		
		if (denominacionCaja == null || denominacionCaja.isEmpty()
				|| denominacionCaja.trim().isEmpty()) {
			throw new Exception("Denominación de Caja Inválida");
		}
		
		if (abreviaturaCaja == null || abreviaturaCaja.isEmpty()
				|| abreviaturaCaja.trim().isEmpty()) {
			throw new Exception("Abreviatura de Caja Inválida");
		}
		
		if(oCaja.getBovedas()==null){
			throw new Exception("No se selecciono ninguna Boveda");
		}
		
		oCaja.setEstado(true);
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		oCaja.setEstadoapertura(estadoapertura);
	}

	@Override
	public Caja find(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String Boveda,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
