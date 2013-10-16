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
import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.PersonanaturalclienteServiceRemote;
import org.ventura.dao.impl.PersonanaturalclienteDAO;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonanaturalclienteServiceLocal.class)
@Remote(PersonanaturalclienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalclienteServiceBean implements PersonanaturalclienteServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonanaturalclienteDAO oPersonanaturalclienteDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;

	@Override
	public void create(Personanaturalcliente oPersonanaturalcliente) throws Exception {
		try {
			Personanatural personanatural = oPersonanaturalcliente.getPersonanatural();
			if (personanatural != null) {
				Object key = personanatural.getDni();
				Object result = personanaturalServiceLocal.find(key);
				if (result == null) {
					personanaturalServiceLocal.create(personanatural);
				}
			}
			oPersonanaturalclienteDAO.create(oPersonanaturalcliente);		
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Personanaturalcliente find(Object id) throws Exception {
		Personanaturalcliente Personanaturalcliente = null;
		try {
			Personanaturalcliente = oPersonanaturalclienteDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Personanaturalcliente;
	}

	@Override
	public void delete(Personanaturalcliente oPersonanaturalcliente) throws Exception {
		try {
			oPersonanaturalclienteDAO.delete(oPersonanaturalcliente);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Personanaturalcliente oPersonanaturalcliente) throws Exception {
		try {
			 oPersonanaturalclienteDAO.update(oPersonanaturalcliente);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName) throws Exception {
		Collection<Personanaturalcliente> collection = null;
		try {
			collection = oPersonanaturalclienteDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personanaturalcliente> collection = null;
		try {
			collection = oPersonanaturalclienteDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Personanaturalcliente> findByNamedQuery(String Personanaturalcliente,Map<String, Object> parameters) throws Exception {
		List<Personanaturalcliente> list = null;
		try {
			list = oPersonanaturalclienteDAO.findByNamedQuery(Personanaturalcliente, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Personanaturalcliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personanaturalcliente> list = null;
		try {
			list = oPersonanaturalclienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

}
