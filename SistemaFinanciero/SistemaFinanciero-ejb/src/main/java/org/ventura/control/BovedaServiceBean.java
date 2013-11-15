package org.ventura.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.ViewBovedadetalleDAO;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.ViewBovedadetalle;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(BovedaServiceLocal.class)
@Remote(BovedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBean implements BovedaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private BovedaDAO bovedaDAO;

	@EJB
	private DetallehistorialbovedaDAO detallehistorialbovedaDAO;

	@EJB
	private ViewBovedadetalleDAO viewBovedadetalleDAO;
	
	@Override
	public Boveda create(Boveda Boveda) throws Exception {
		try {

			bovedaDAO.create(Boveda);

		} catch (Exception e) {
			Boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return Boveda;
	}

	@Override
	public Boveda find(Object id) throws Exception {
		Boveda Boveda = null;
		try {
			Boveda = bovedaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Boveda;
	}

	@Override
	public void delete(Boveda Boveda) throws Exception {
		try {
			bovedaDAO.delete(Boveda);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Boveda Boveda) throws Exception {
		try {
			bovedaDAO.update(Boveda);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Boveda> findByNamedQuery(String queryName)
			throws Exception {
		Collection<Boveda> collection = null;
		try {
			collection = bovedaDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Boveda> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		Collection<Boveda> collection = null;
		try {
			collection = bovedaDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Boveda> findByNamedQuery(String Boveda,
			Map<String, Object> parameters) throws Exception {
		List<Boveda> list = null;
		try {
			list = bovedaDAO.findByNamedQuery(Boveda, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Boveda> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Boveda> list = null;
		try {
			list = bovedaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

	@Override
	public List<Detallehistorialboveda> getLastDetallehistorialboveda(Boveda oBoveda) throws Exception {
		List<Detallehistorialboveda> detallehistorialbovedaList = new ArrayList<Detallehistorialboveda>();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idboveda", oBoveda.getIdboveda());
		
		
		List<ViewBovedadetalle> bovedadetalleList = viewBovedadetalleDAO.findByNamedQuery(ViewBovedadetalle.findLastBovedaDetalleByBoveda, parameters);		
		for (Iterator<ViewBovedadetalle> iterator = bovedadetalleList.iterator(); iterator.hasNext();) {
			ViewBovedadetalle viewBovedadetalle = iterator.next();
			Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
			detallehistorialboveda.setIddetallehistorialboveda(viewBovedadetalle.getIddetallehistorialboveda());
			detallehistorialboveda.setCantidad(viewBovedadetalle.getCantidaddetallehistorialboveda());
			
			Denominacionmoneda denominacionmoneda = new Denominacionmoneda();
			denominacionmoneda.setIddenominacionmoneda(viewBovedadetalle.getIddenominacionmoneda());
			denominacionmoneda.setDenominacion(viewBovedadetalle.getDenominaciondenominacionmoneda());
			denominacionmoneda.setIddenominacionmoneda(viewBovedadetalle.getIddenominacionmoneda());
			denominacionmoneda.setIdtipomoneda(viewBovedadetalle.getIdtipomoneda());
			denominacionmoneda.setValor(viewBovedadetalle.getValordenominacionmoneda());
			
			detallehistorialboveda.setDenominacionmoneda(denominacionmoneda);
			
			detallehistorialbovedaList.add(detallehistorialboveda);
		}
		 
		return detallehistorialbovedaList;
	}
}
