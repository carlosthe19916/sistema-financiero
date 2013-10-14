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

import org.ventura.boundary.local.EstadocuentaServiceLocal;
import org.ventura.boundary.remote.EstadocuentaServiceRemote;
import org.ventura.dao.impl.EstadocuentaDAO;
import org.ventura.entity.Estadocuenta;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(EstadocuentaServiceLocal.class)
@Remote(EstadocuentaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadocuentaServiceBean implements EstadocuentaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private EstadocuentaDAO oEstadocuentaDAO;

	@Override
	public Estadocuenta create(Estadocuenta oEstadocuenta) {
		try {
			oEstadocuentaDAO.create(oEstadocuenta);
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
		return oEstadocuenta;
	}

	@Override
	public Estadocuenta find(Integer id) {
		Estadocuenta Estadocuenta = null;
		try {
			Estadocuenta = oEstadocuentaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadocuenta;
	}

	@Override
	public void delete(Estadocuenta oEstadocuenta) {
		try {
			oEstadocuentaDAO.delete(oEstadocuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Estadocuenta update(Estadocuenta oEstadocuenta) {
		Estadocuenta Estadocuenta = null;
		try {
			Estadocuenta = oEstadocuentaDAO.update(oEstadocuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadocuenta;
	}

	@Override
	public Collection<Estadocuenta> findByNamedQuery(String queryName) {
		Collection<Estadocuenta> collection = null;
		try {
			collection = oEstadocuentaDAO.findByNamedQuery(queryName);
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
	public Collection<Estadocuenta> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Estadocuenta> collection = null;
		try {
			collection = oEstadocuentaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Estadocuenta> findByNamedQuery(String Estadocuenta,
			Map<String, Object> parameters) {
		List<Estadocuenta> list = null;
		try {
			list = oEstadocuentaDAO.findByNamedQuery(Estadocuenta, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Estadocuenta> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Estadocuenta> list = null;
		try {
			list = oEstadocuentaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
