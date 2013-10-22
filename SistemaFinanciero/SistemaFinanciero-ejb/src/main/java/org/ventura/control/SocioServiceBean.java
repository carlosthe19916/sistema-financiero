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

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.util.logger.Log;

@Stateless
@Local(SocioServiceLocal.class)
@Remote(SocioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SocioServiceBean implements SocioServiceLocal {

	@Inject
	private Log log;

	@EJB
	private SocioDAO socioDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	

	@Override
	public Socio create(Socio socio) throws Exception {
		try {
			Personanatural personanatural = socio.getPersonanatural();
			if (personanatural != null) {
				Object key = personanatural.getDni();
				Object result = personanaturalServiceLocal.find(key);
				if (result == null) {
					personanaturalServiceLocal.create(personanatural);
				}
			}
			socioDAO.create(socio);		
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return socio;
	}

	@Override
	public Socio find(Object id) throws Exception {
		Socio socio = null;
		try {
			socio = socioDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return socio;
	}

	@Override
	public void delete(Socio socio) throws Exception {
		try {
			socioDAO.delete(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Socio socio) throws Exception {
		try {
			 socioDAO.update(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName) throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Socio> findByNamedQuery(String socio,Map<String, Object> parameters) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(socio, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Socio> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}
}
