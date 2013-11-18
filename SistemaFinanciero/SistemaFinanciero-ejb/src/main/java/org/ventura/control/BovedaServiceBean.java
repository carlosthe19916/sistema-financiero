package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.HistorialbovedaDAO;
import org.ventura.dao.impl.ViewBovedadetalleDAO;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.caja.ViewBovedadetalle;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.EstadoValue;

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
	private HistorialbovedaDAO historialbovedaDAO;
	
	@EJB
	private DetallehistorialbovedaDAO detallehistorialbovedaDAO;
	
	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;

	@EJB
	private ViewBovedadetalleDAO viewBovedadetalleDAO;

	@Override
	public Boveda create(Boveda boveda) throws Exception {
		try {
			preCreateBoveda(boveda);
			bovedaDAO.create(boveda);
		} catch (EntityExistsException | IllegalArgumentException
				| TransactionRequiredException e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return boveda;
	}

	public void preCreateBoveda(Boveda boveda) throws Exception {
		String denominacionBoveda = boveda.getDenominacion();
		Integer idTipomoneda = boveda.getIdtipomoneda();
		Integer idAgencia = boveda.getIdagencia();

		if (denominacionBoveda == null || denominacionBoveda.isEmpty()
				|| denominacionBoveda.trim().isEmpty()) {
			throw new Exception("Denominación de Bóveda Inválida");
		}
		if (idTipomoneda == null) {
			throw new Exception("Tipo de Moneda Invalida");
		}
		if (idAgencia == null) {
			throw new Exception("Agencia Invalida");
		}

		boveda.setEstado(true);
		boveda.setIdestadomovimiento(EstadoValue
				.getEstadoMovimientoValue(EstadoMovimientoType.CERRADO));
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
	public List<Detallehistorialboveda> getLastDetallehistorialboveda(
			Boveda oBoveda) throws Exception {
		List<Detallehistorialboveda> detallehistorialbovedaList = new ArrayList<Detallehistorialboveda>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idboveda", oBoveda.getIdboveda());

		List<ViewBovedadetalle> bovedadetalleList = viewBovedadetalleDAO
				.findByNamedQuery(
						ViewBovedadetalle.findLastBovedaDetalleByBoveda,
						parameters);
		for (Iterator<ViewBovedadetalle> iterator = bovedadetalleList
				.iterator(); iterator.hasNext();) {
			ViewBovedadetalle viewBovedadetalle = iterator.next();
			Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
			detallehistorialboveda
					.setIddetallehistorialboveda(viewBovedadetalle
							.getIddetallehistorialboveda());
			detallehistorialboveda.setCantidad(viewBovedadetalle
					.getCantidaddetallehistorialboveda());

			Denominacionmoneda denominacionmoneda = new Denominacionmoneda();
			denominacionmoneda.setIddenominacionmoneda(viewBovedadetalle
					.getIddenominacionmoneda());
			denominacionmoneda.setDenominacion(viewBovedadetalle
					.getDenominaciondenominacionmoneda());
			denominacionmoneda.setIddenominacionmoneda(viewBovedadetalle
					.getIddenominacionmoneda());
			denominacionmoneda.setIdtipomoneda(viewBovedadetalle
					.getIdtipomoneda());
			denominacionmoneda.setValor(viewBovedadetalle
					.getValordenominacionmoneda());

			detallehistorialboveda.setDenominacionmoneda(denominacionmoneda);

			detallehistorialbovedaList.add(detallehistorialboveda);
		}

		return detallehistorialbovedaList;
	}

	@Override
	public void openBoveda(Boveda boveda) throws Exception {
		try {
			boveda = bovedaDAO.find(boveda.getIdagencia());
			EstadoMovimientoType estadoBoveda = EstadoValue.getEstadoType(boveda.getIdestadomovimiento());
			switch (estadoBoveda) {
			case CERRADO:
				log.info("ESTADO DE BOVEDA VERIFICADA: CERRADO");
				break;
			default:
				log.error("Estado de Boveda: " + boveda.getDenominacion() + ": ABIERTO");
				throw new Exception();
			}

			List<Caja> cajaList = boveda.getCajas();
			for (Iterator<Caja> iterator = cajaList.iterator(); iterator.hasNext();) {
				Caja caja = iterator.next();
				EstadoMovimientoType estadoCaja = EstadoValue.getEstadoType(caja.getIdestadomovimiento());
				switch (estadoCaja) {
				case CERRADO:
					log.info("ESTADO DE CAJA " + caja.getDenominacion() + " VERIFICADA: CERRADO");
					break;
				default:
					log.error("ESTADO DE CAJA " + caja.getDenominacion() + " VERIFICADA: ABIERTO");
					throw new Exception();
				}
			}
			log.info("Todas las Cajas verificadas correctamente");
			log.info("Boveda lista para Abrir");

			
						
			Historialboveda historialboveda = new Historialboveda();
			historialboveda.setFechaapertura(Calendar.getInstance().getTime());
			historialboveda.setHoraapertura(Calendar.getInstance().getTime());
			historialboveda.setSaldoinicial(boveda.getSaldo());					
			
			
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedas = denominacionmonedaDAO.findAll();
			
			List<Detallehistorialboveda> detallehistorialbovedaList = new ArrayList<Detallehistorialboveda>();
			
			for (Iterator<Denominacionmoneda> iterator = denominacionmonedas.iterator(); iterator.hasNext();) {
				Denominacionmoneda denominacionmoneda = iterator.next();
				
				Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();			
				detallehistorialboveda.setDenominacionmoneda(denominacionmoneda);
				detallehistorialboveda.setCantidad(0);
				
				detallehistorialbovedaList.add(detallehistorialboveda);
			}
			
			
			historialboveda.setDetallehistorialbovedas(detallehistorialbovedaList);
			historialbovedaDAO.create(historialboveda);
			
			Integer estadoMovimientoAbierto = EstadoValue.getEstadoMovimientoValue(EstadoMovimientoType.ABIERTO_CONGELADO);
			boveda.setIdestadomovimiento(estadoMovimientoAbierto);
			bovedaDAO.update(boveda);
			
			log.info("FINISH SUCCESSFULLY: BOVEDA ABIERTA");

		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
	}

	@Override
	public void openBovedaWithPendiente(Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeBoveda(Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEstadomovimientoBoveda(Estadomovimiento estadomovimiento,
			Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

}
