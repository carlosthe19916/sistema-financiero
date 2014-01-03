package org.ventura.control;

import java.util.Collection;
import java.util.HashMap;
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
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipodocumentoType;

@Stateless
@Local(PersonanaturalServiceLocal.class)
@Remote(PersonanaturalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

	@Override
	public Personanatural findByDni(String dni) throws Exception {
		Personanatural personanatural = null;
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", ProduceObject.getTipodocumento(TipodocumentoType.DNI));
			parameters.put("numerodocumento", dni);
			List<Personanatural> list = oPersonanaturalDAO.findByNamedQuery(Personanatural.FindByTipodocumentoNumerodocumento, parameters);
			if(list.size() == 1){
				personanatural = list.get(0);
			} else {
				personanatural = null;
			}
		} catch(Exception e){
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
		}
		return personanatural;
	}

	@Override
	public Personanatural findByTipodocumento(Tipodocumento tipodocumento,String numerodocumento) throws Exception {
		Personanatural personanatural = null;
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", tipodocumento);
			parameters.put("numerodocumento", numerodocumento);
			List<Personanatural> list = oPersonanaturalDAO.findByNamedQuery(Personanatural.FindByTipodocumentoNumerodocumento, parameters);
			if(list.size() == 1){
				personanatural = list.get(0);
			} else {
				if(list.size() == 0){
					personanatural = null;
				} else {
					throw new Exception("Existen dos personas duplicadas");	
				}
			}
		} catch(Exception e){
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw e;
		}
		return personanatural;
	}

}
