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
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(SocioServiceLocal.class)
@Remote(SocioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonajuridicaclienteServiceBean implements SocioServiceLocal {

	@Inject
	private Log log;

	@EJB
	private SocioDAO oPersonajuridicaclienteDAO;
	
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;

	@Override
	public Personajuridicacliente create(Personajuridicacliente personajuridicacliente) throws Exception {
		try {
			Personajuridica personajuridica = personajuridicacliente.getPersonajuridica();
			if (personajuridica != null) {
				Object key = personajuridica.getRuc();
				Object result = personajuridicaServiceLocal.find(key);
				if (result == null) {
					personajuridicaServiceLocal.create(personajuridica);
				}
			}
			oPersonajuridicaclienteDAO.create(personajuridicacliente);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return personajuridicacliente;
	}

	@Override
	public Personajuridicacliente find(Object id) throws Exception {
		Personajuridicacliente Personajuridicacliente = null;
		try {
			Personajuridicacliente = oPersonajuridicaclienteDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Personajuridicacliente;
	}

	@Override
	public void delete(Personajuridicacliente oPersonajuridicacliente) throws Exception {
		try {
			oPersonajuridicaclienteDAO.delete(oPersonajuridicacliente);
		}  catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Personajuridicacliente oPersonajuridicacliente) throws Exception{
		try {
			oPersonajuridicaclienteDAO.update(oPersonajuridicacliente);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName) throws Exception {
		Collection<Personajuridicacliente> collection = null;
		try {
			collection = oPersonajuridicaclienteDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Personajuridicacliente> collection = null;
		try {
			collection = oPersonajuridicaclienteDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String Personajuridicacliente, Map<String, Object> parameters) throws Exception {
		List<Personajuridicacliente> list = null;
		try {
			list = oPersonajuridicaclienteDAO.findByNamedQuery(Personajuridicacliente, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Personajuridicacliente> list = null;
		try {
			list = oPersonajuridicaclienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

}
