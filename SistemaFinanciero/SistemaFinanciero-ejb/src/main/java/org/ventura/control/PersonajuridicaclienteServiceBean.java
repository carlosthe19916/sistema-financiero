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
import org.ventura.boundary.local.PersonajuridicaclienteServiceLocal;
import org.ventura.boundary.remote.PersonajuridicaclienteServiceRemote;
import org.ventura.dao.impl.PersonajuridicaclienteDAO;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonajuridicaclienteServiceLocal.class)
@Remote(PersonajuridicaclienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonajuridicaclienteServiceBean implements PersonajuridicaclienteServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonajuridicaclienteDAO oPersonajuridicaclienteDAO;
	
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;

	@Override
	public void create(Personajuridicacliente personajuridicacliente) throws RollbackFailureException {
		try {
			Personajuridica personajuridica = personajuridicacliente.getPersonajuridica();
			if (personajuridica != null) {
				Object key = personajuridica.getRuc();
				Object result = this.find(key);
				if (result == null) {
					personajuridicaServiceLocal.create(personajuridica);
				}
			}
			oPersonajuridicaclienteDAO.create(personajuridicacliente);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new RollbackFailureException("Error al insertar los datos");
		}
	}


	@Override
	public Personajuridicacliente find(Object id) throws NonexistentEntityException {
		Personajuridicacliente Personajuridicacliente = null;
		try {
			Personajuridicacliente = oPersonajuridicaclienteDAO.find(id);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new NonexistentEntityException("Error buscar entity");
		}
		return Personajuridicacliente;
	}

	@Override
	public void delete(Personajuridicacliente oPersonajuridicacliente) throws NonexistentEntityException {
		try {
			oPersonajuridicaclienteDAO.delete(oPersonajuridicacliente);
		}  catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new NonexistentEntityException("Error eliminar entity");
		}
	}

	@Override
	public void update(Personajuridicacliente oPersonajuridicacliente) throws RollbackFailureException{
		try {
			oPersonajuridicaclienteDAO.update(oPersonajuridicacliente);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new RollbackFailureException("Error eliminar entity");
		}
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName) {
		Collection<Personajuridicacliente> collection = null;
		try {
			collection = oPersonajuridicaclienteDAO.findByNamedQuery(queryName);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Personajuridicacliente> collection = null;
		try {
			collection = oPersonajuridicaclienteDAO.findByNamedQuery(queryName, resultLimit);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String Personajuridicacliente,
			Map<String, Object> parameters) {
		List<Personajuridicacliente> list = null;
		try {
			list = oPersonajuridicaclienteDAO.findByNamedQuery(Personajuridicacliente, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Personajuridicacliente> list = null;
		try {
			list = oPersonajuridicaclienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
