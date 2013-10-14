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
import org.ventura.boundary.remote.PersonajuridicaServiceRemote;
import org.ventura.dao.impl.PersonajuridicaDAO;
import org.ventura.entity.Personajuridica;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonajuridicaServiceLocal.class)
@Remote(PersonajuridicaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonajuridicaServiceBean implements PersonajuridicaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonajuridicaDAO oPersonajuridicaDAO;

	@Override
	public Personajuridica create(Personajuridica oPersonajuridica) {
		try {
			oPersonajuridicaDAO.create(oPersonajuridica);
		} catch (PreexistingEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (IllegalEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (RollbackFailureException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (Exception e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		}
		return oPersonajuridica;
	}

	@Override
	public Personajuridica find(Object id) {
		Personajuridica Personajuridica = null;
		try {
			Personajuridica = oPersonajuridicaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Personajuridica;
	}

	@Override
	public void delete(Personajuridica oPersonajuridica) {
		try {
			oPersonajuridicaDAO.delete(oPersonajuridica);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Personajuridica update(Personajuridica oPersonajuridica) {
		Personajuridica Personajuridica = null;
		try {
			Personajuridica = oPersonajuridicaDAO.update(oPersonajuridica);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Personajuridica;
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName) {
		Collection<Personajuridica> collection = null;
		try {
			collection = oPersonajuridicaDAO.findByNamedQuery(queryName);
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
	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Personajuridica> collection = null;
		try {
			collection = oPersonajuridicaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Personajuridica> findByNamedQuery(String Personajuridica,
			Map<String, Object> parameters) {
		List<Personajuridica> list = null;
		try {
			list = oPersonajuridicaDAO.findByNamedQuery(Personajuridica, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Personajuridica> list = null;
		try {
			list = oPersonajuridicaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
