package org.ventura.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonajuridicaServiceLocal.class)
@Remote(PersonajuridicaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonajuridicaServiceBean implements PersonajuridicaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonajuridicaDAO personajuridicaDAO;
	
	@EJB
	private AccionistaDAO accionistaDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;

	@Override
	public Personajuridica create(Personajuridica personajuridica) throws Exception  {
		try{
			personajuridicaDAO.create(personajuridica);
		}catch(Exception e){
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return personajuridica;		
	}
	
	@Override
	public void update(Personajuridica oPersonajuridica) throws Exception {
		try {
			personajuridicaDAO.update(oPersonajuridica);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public void delete(Personajuridica oPersonajuridica) throws Exception {
		try {
			personajuridicaDAO.delete(oPersonajuridica);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	public Accionista findAccionista(Object id) throws Exception{
		try {
			return accionistaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public Personajuridica find(Object id) throws Exception {
		Personajuridica Personajuridica = null;
		try {
			Personajuridica = personajuridicaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return Personajuridica;
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName) throws Exception { Collection<Personajuridica> collection = null;
		try {
			collection = personajuridicaDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personajuridica> collection = null;
		try {
			collection = personajuridicaDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String Personajuridica, Map<String, Object> parameters) throws Exception {
		List<Personajuridica> list = null;
		try {
			list = personajuridicaDAO.findByNamedQuery(Personajuridica, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personajuridica> list = null;
		try {
			list = personajuridicaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}

		return list;
	}

	@Override
	public List<Personajuridica> find(String searched, int resultLimit) throws Exception {
		List<Personajuridica> resultList = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("searched", "%" + searched + "%");
			resultList = personajuridicaDAO.findByNamedQuery(Personajuridica.f_searched, parameters, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return resultList;
	}
	
	@Override
	public Personajuridica find(Tipodocumento tipodocumento, String numerodocumento) throws Exception {
		Personajuridica personajuridica = null;
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", tipodocumento);
			parameters.put("numerodocumento", numerodocumento);
			List<Personajuridica> list = personajuridicaDAO.findByNamedQuery(Personajuridica.FindByTipodocumentoNumerodocumento, parameters);
			if(list.size() == 1){
				personajuridica = list.get(0);
				
				Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
				List<Accionista> accionistas = personajuridica.getAccionistas();
				
				personajuridica.setRepresentanteLegal(representanteLegal);
				
				for (Accionista accionista : accionistas) {
					Personanatural personanatural = accionista.getPersonanatural();
					accionista.setPersonanatural(personanatural);
				}
				personajuridica.setAccionistas(accionistas);
			} else {
				if(list.size() == 0){
					personajuridica = null;
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
		return personajuridica;
	}

	@Override
	public Personajuridica createIfNotExistsUpdateIfExist(Personajuridica personajuridica)throws Exception {
		try {		
			Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
			List<Accionista> listAccionistasView = personajuridica.getAccionistas();
			representanteLegal = personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(representanteLegal);
				
			Tipodocumento tipodocumento = personajuridica.getTipodocumento();
			String numerodocumento = personajuridica.getNumerodocumento();			
			Personajuridica personajuridicaBD = find(tipodocumento, numerodocumento);
			if(personajuridicaBD != null){
				personajuridicaBD.setTipodocumento(personajuridica.getTipodocumento());
				personajuridicaBD.setNumerodocumento(personajuridica.getNumerodocumento());
				personajuridicaBD.setRepresentanteLegal(representanteLegal);
				personajuridicaBD.setRazonsocial(personajuridica.getRazonsocial());
				personajuridicaBD.setNombrecomercial(personajuridica.getNombrecomercial());
				personajuridicaBD.setActividadprincipal(personajuridica.getActividadprincipal());
				personajuridicaBD.setFechaconstitucion(personajuridica.getFechaconstitucion());
				personajuridicaBD.setTipoempresa(personajuridica.getTipoempresa());
				personajuridicaBD.setFindelucro(personajuridicaBD.getFindelucro());
				personajuridicaBD.setDireccion(personajuridica.getDireccion());
				personajuridicaBD.setReferencia(personajuridica.getReferencia());
				personajuridicaBD.setTelefono(personajuridica.getTelefono());
				personajuridicaBD.setCelular(personajuridica.getCelular());
				personajuridicaBD.setEmail(personajuridica.getEmail());
				update(personajuridicaBD);
				personajuridica = personajuridicaBD;
			} else {
				create(personajuridica);
			}	
				
			Map<String, Accionista> mapFromView = new HashMap<String, Accionista>();
			for (Accionista accionista : listAccionistasView) {
				Personanatural personanatural = accionista.getPersonanatural();
				String key = personanatural.getNumerodocumento() + personanatural.getTipodocumento().hashCode();
				mapFromView.put(key, accionista);
			}
			
			Map<String, Accionista> mapFromDB = new HashMap<String, Accionista>();
			for (Accionista accionista : personajuridica.getAccionistas()) {
				Personanatural personanatural = accionista.getPersonanatural();
				String key = personanatural.getNumerodocumento() + personanatural.getTipodocumento().hashCode();
				mapFromDB.put(key, accionista);
			}
			
			Set<String> union = new HashSet<String>(mapFromView.keySet());
			union.addAll(mapFromDB.keySet());
			
			Set<String> intersection = new HashSet<String>(mapFromView.keySet());
			intersection.retainAll(mapFromDB.keySet());
			
			Set<String> restDelete = new HashSet<String>(union);
			restDelete.removeAll(mapFromView.keySet());
			
			Set<String> restCreate = new HashSet<String>(union);
			restCreate.removeAll(mapFromDB.keySet());
				
			for (String key : restDelete) {
				Accionista accionista = mapFromDB.get(key);
				accionistaDAO.delete(accionista);
			}
			
			for (String key : intersection) {
				Accionista accionistaView = mapFromView.get(key);
				Personanatural personanatural = accionistaView.getPersonanatural();			
				personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(personanatural);		
				
				Accionista accionistaDB = mapFromDB.get(key);
				accionistaDB.setPorcentajeparticipacion(accionistaView.getPorcentajeparticipacion());
				accionistaDAO.update(accionistaDB);
			}
			
			for (String key : restCreate) {
				Accionista accionista = mapFromView.get(key);
				Personanatural accionistaPersonanatural = accionista.getPersonanatural();			
				personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(accionistaPersonanatural);				
				accionista.setPersonajuridica(personajuridica);
				accionistaDAO.create(accionista);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw new EJBException(e);
		}
		return personajuridica;
	}

	@Override
	public Personajuridica createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(Personajuridica personajuridica) throws Exception {
		try {		
			Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
			personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(representanteLegal);
			
			Tipodocumento tipodocumento = personajuridica.getTipodocumento();
			String numerodocumento = personajuridica.getNumerodocumento();			
			Personajuridica personajuridicaBD = find(tipodocumento, numerodocumento);
			if(personajuridicaBD != null){
				personajuridicaBD.setTipodocumento(personajuridica.getTipodocumento());
				personajuridicaBD.setNumerodocumento(personajuridica.getNumerodocumento());
				personajuridicaBD.setRepresentanteLegal(personajuridica.getRepresentanteLegal());
				personajuridicaBD.setRazonsocial(personajuridica.getRazonsocial());				
				personajuridicaBD.setFechaconstitucion(personajuridica.getFechaconstitucion());
				personajuridicaBD.setTipoempresa(personajuridica.getTipoempresa());	
				update(personajuridicaBD);
				personajuridica = personajuridicaBD;
			} else {
				create(personajuridica);
			}					
			List<Accionista> listAccionistas = personajuridica.getAccionistas();
			for (Accionista accionista : listAccionistas) {
				accionistaDAO.delete(accionista);
			}	
			for (Accionista accionista : listAccionistas) {
				Personanatural accionistaPersonanatural = accionista.getPersonanatural();			
				personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(accionistaPersonanatural);		
				Accionista accionistaNew = new Accionista();		
				accionistaNew.setPersonanatural(accionistaPersonanatural);
				accionistaNew.setPersonajuridica(personajuridica);
				accionistaNew.setPorcentajeparticipacion(accionista.getPorcentajeparticipacion());
				accionistaDAO.create(accionistaNew);
			}	
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw new EJBException(e);
		}
		return personajuridica;
	}


	
}
