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

@Stateless
@Local(PersonanaturalServiceLocal.class)
@Remote(PersonanaturalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonanaturalServiceBean implements PersonanaturalServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonanaturalDAO personanaturalDAO;

	@Override
	public void create(Personanatural personanatural) throws Exception {
		try {
			personanaturalDAO.create(personanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}	
	}

	@Override
	public void update(Personanatural personanatural) throws Exception {
		try {
			personanaturalDAO.update(personanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public void delete(Personanatural personanatural) throws Exception {
		try {
			personanaturalDAO.delete(personanatural);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public Personanatural find(Object id) throws Exception {
		Personanatural Personanatural = null;		
		try {
			Personanatural = personanaturalDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return Personanatural;
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName) throws Exception {
		Collection<Personanatural> collection = null;
		try {
			collection = personanaturalDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personanatural> collection = null;
		try {
			collection = personanaturalDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters) throws Exception {
		List<Personanatural> list = null;
		try {
			list = personanaturalDAO.findByNamedQuery(Personanatural, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personanatural> list = null;
		try {
			list = personanaturalDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public Personanatural find(Tipodocumento tipodocumento,String numerodocumento) throws Exception {
		Personanatural personanatural = null;
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", tipodocumento);
			parameters.put("numerodocumento", numerodocumento);
			List<Personanatural> list = personanaturalDAO.findByNamedQuery(Personanatural.f_tipodocumento_numerodocumento, parameters);
			if(list.size() == 1){
				personanatural = list.get(0);
			} else {
				if(list.size() == 0){
					personanatural = null;
				} else {
					throw new Exception("Existen 2 o mas resultados para la consulta");	
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

	@Override
	public Personanatural createIfNotExistsUpdateIfExist(Personanatural personanatural) throws Exception {
		try {
			Tipodocumento tipodocumento = personanatural.getTipodocumento();
			String numerodocumento = personanatural.getNumerodocumento();			
			Personanatural personanaturalBD = find(tipodocumento, numerodocumento);
			if(personanaturalBD != null){
				personanaturalBD.setTipodocumento(personanatural.getTipodocumento());
				personanaturalBD.setNumerodocumento(personanatural.getNumerodocumento());
				personanaturalBD.setApellidopaterno(personanatural.getApellidopaterno());
				personanaturalBD.setApellidomaterno(personanatural.getApellidomaterno());
				personanaturalBD.setNombres(personanatural.getNombres());
				personanaturalBD.setFechanacimiento(personanatural.getFechanacimiento());
				personanaturalBD.setSexo(personanatural.getSexo());
				personanaturalBD.setEstadocivil(personanatural.getEstadocivil());
				personanaturalBD.setOcupacion(personanatural.getOcupacion());
				personanaturalBD.setDireccion(personanatural.getDireccion());
				personanaturalBD.setReferencia(personanatural.getReferencia());
				personanaturalBD.setTelefono(personanatural.getTelefono());
				personanaturalBD.setCelular(personanatural.getCelular());
				personanaturalBD.setEmail(personanatural.getEmail());
				update(personanaturalBD);
				personanatural = personanaturalBD;
			} else {
				create(personanatural);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw e;
		}
		return personanatural;
	}

	@Override
	public Personanatural createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(Personanatural personanatural) throws Exception {
		try {
			Tipodocumento tipodocumento = personanatural.getTipodocumento();
			String numerodocumento = personanatural.getNumerodocumento();			
			Personanatural personanaturalBD = find(tipodocumento, numerodocumento);
			if(personanaturalBD != null){
				personanaturalBD.setTipodocumento(personanatural.getTipodocumento());
				personanaturalBD.setNumerodocumento(personanatural.getNumerodocumento());
				personanaturalBD.setApellidopaterno(personanatural.getApellidopaterno());
				personanaturalBD.setApellidomaterno(personanatural.getApellidomaterno());
				personanaturalBD.setNombres(personanatural.getNombres());
				personanaturalBD.setFechanacimiento(personanatural.getFechanacimiento());
				personanaturalBD.setSexo(personanatural.getSexo());
				update(personanaturalBD);
				personanatural = personanaturalBD;
			} else {
				create(personanatural);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw e;
		}
		return personanatural;
	}

}
