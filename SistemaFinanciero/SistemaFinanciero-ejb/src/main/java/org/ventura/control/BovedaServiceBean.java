package org.ventura.control;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.validate.Validator;

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
}
