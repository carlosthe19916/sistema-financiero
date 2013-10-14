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

import org.ventura.boundary.local.EstadocivilServiceLocal;
import org.ventura.boundary.remote.EstadocivilServiceRemote;
import org.ventura.dao.impl.EstadocivilDAO;
import org.ventura.entity.Estadocivil;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(EstadocivilServiceLocal.class)
@Remote(EstadocivilServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadocivilServiceBean implements EstadocivilServiceLocal {

	@Inject
	private Log log;

	@EJB
	private EstadocivilDAO oEstadocivilDAO;

	@Override
	public Estadocivil create(Estadocivil oEstadocivil) {
		try {
			oEstadocivilDAO.create(oEstadocivil);
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
		return oEstadocivil;
	}

	@Override
	public Estadocivil find(Integer id) {
		Estadocivil Estadocivil = null;
		try {
			Estadocivil = oEstadocivilDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadocivil;
	}

	@Override
	public void delete(Estadocivil oEstadocivil) {
		try {
			oEstadocivilDAO.delete(oEstadocivil);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Estadocivil update(Estadocivil oEstadocivil) {
		Estadocivil Estadocivil = null;
		try {
			Estadocivil = oEstadocivilDAO.update(oEstadocivil);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadocivil;
	}

	@Override
	public Collection<Estadocivil> findByNamedQuery(String queryName) {
		Collection<Estadocivil> collection = null;
		try {
			collection = oEstadocivilDAO.findByNamedQuery(queryName);
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
	public Collection<Estadocivil> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Estadocivil> collection = null;
		try {
			collection = oEstadocivilDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Estadocivil> findByNamedQuery(String Estadocivil,
			Map<String, Object> parameters) {
		List<Estadocivil> list = null;
		try {
			list = oEstadocivilDAO.findByNamedQuery(Estadocivil, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Estadocivil> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Estadocivil> list = null;
		try {
			list = oEstadocivilDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Collection<Estadocivil> findAll() {
		Collection<Estadocivil> collection = null;
		try {
			collection = oEstadocivilDAO.findAll();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

}
