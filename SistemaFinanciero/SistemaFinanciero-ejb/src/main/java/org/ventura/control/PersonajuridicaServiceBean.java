package org.ventura.control;

import java.util.Collection;
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

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.PersonajuridicaServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.PersonajuridicaDAO;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonajuridicaServiceLocal.class)
@Remote(PersonajuridicaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonajuridicaServiceBean implements PersonajuridicaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonajuridicaDAO oPersonajuridicaDAO;
	
	@EJB
	private AccionistaDAO accionistaDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;

	@Override
	public Personajuridica create(Personajuridica oPersonajuridica) throws Exception  {
		try{
			Personanatural representantelegal = oPersonajuridica.getPersonanatural();
			if (representantelegal != null) {
				Object key = representantelegal.getDni();
				Object result = personanaturalServiceLocal.find(key);
				if (result == null) {
					personanaturalServiceLocal.create(representantelegal);
				}
			}
			
			oPersonajuridicaDAO.create(oPersonajuridica);
			
			List<Accionista> accionistas = oPersonajuridica.getListAccionista();
			if (accionistas != null) {
				for (Iterator<Accionista> iterator = accionistas.iterator(); iterator.hasNext();) {
					Accionista accionista = (Accionista) iterator.next();
					
					Personanatural personanatural = accionista.getPersonanatural();
					if(personanatural != null){
						Object key = personanatural.getDni();
						Object result = personanaturalServiceLocal.find(key);
						if(result == null){
							personanaturalServiceLocal.create(personanatural);
						}
						createAccionista(accionista);				
					}
					
				}
			}
		}catch(Exception e){
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return oPersonajuridica;		
	}
	
	@Override
	public void update(Personajuridica oPersonajuridica) throws Exception {
		try {
			oPersonajuridicaDAO.update(oPersonajuridica);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	protected void createAccionista(Accionista accionista) throws Exception{
		try {
			accionistaDAO.create(accionista);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public void updateAccionista(Personajuridica oPersonaJuridica) throws Exception{
		try{
			List<Accionista> accionistas = oPersonaJuridica.getListAccionista();
			if (accionistas != null) {
				for (Iterator<Accionista> iterator = accionistas.iterator(); iterator.hasNext();) {
					Accionista accionista = (Accionista) iterator.next();
					accionista.setPersonajuridica(oPersonaJuridica);
			
					Personanatural personanatural = accionista.getPersonanatural();
					if(personanatural != null){
						Object key = personanatural.getDni();
						Object result = personanaturalServiceLocal.find(key);
						if(result == null){
							createAccionista(accionista);
						}
						accionistaDAO.update(accionista);			
					}
				}
			}
		}catch(Exception e){
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public void deleteAccionista(String Personajuridica, Object parameters) throws Exception {
		try {
			oPersonajuridicaDAO.executeQuerry(Personajuridica, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public void delete(Personajuridica oPersonajuridica) throws Exception {
		try {
			oPersonajuridicaDAO.delete(oPersonajuridica);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	public Accionista findAccionista(Object id) throws Exception{
		try {
			return accionistaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Personajuridica find(Object id) throws Exception {
		Personajuridica Personajuridica = null;
		try {
			Personajuridica = oPersonajuridicaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Personajuridica;
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName) throws Exception { Collection<Personajuridica> collection = null;
		try {
			collection = oPersonajuridicaDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personajuridica> collection = null;
		try {
			collection = oPersonajuridicaDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String Personajuridica, Map<String, Object> parameters) throws Exception {
		List<Personajuridica> list = null;
		try {
			list = oPersonajuridicaDAO.findByNamedQuery(Personajuridica, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personajuridica> list = null;
		try {
			list = oPersonajuridicaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}
}
