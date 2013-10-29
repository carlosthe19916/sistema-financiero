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

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.PersonanaturalServiceRemote;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonanaturalServiceLocal.class)
@Remote(PersonanaturalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalServiceBean implements PersonanaturalServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonanaturalDAO oPersonanaturalDAO;

	@Override
	public void create(Personanatural oPersonanatural) throws Exception {
		try {
			oPersonanaturalDAO.create(oPersonanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}	
	}

	@Override
	public void update(Personanatural oPersonanatural) throws Exception {
		try {
			oPersonanaturalDAO.update(oPersonanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public void delete(Personanatural oPersonanatural) throws Exception {
		try {
			oPersonanaturalDAO.delete(oPersonanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public Personanatural find(Object id) throws Exception {
		Personanatural Personanatural = null;		
		try {
			Personanatural = oPersonanaturalDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Personanatural;
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName) throws Exception {
		Collection<Personanatural> collection = null;
		try {
			collection = oPersonanaturalDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personanatural> collection = null;
		try {
			collection = oPersonanaturalDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters) throws Exception {
		List<Personanatural> list = null;
		try {
			list = oPersonanaturalDAO.findByNamedQuery(Personanatural, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personanatural> list = null;
		try {
			list = oPersonanaturalDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

}
