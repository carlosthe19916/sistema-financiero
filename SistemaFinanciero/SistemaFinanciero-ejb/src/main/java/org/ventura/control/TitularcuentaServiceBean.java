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

import org.ventura.boundary.local.TitularcuentaServiceLocal;
import org.ventura.boundary.remote.TitularcuentaServiceRemote;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Titularcuenta;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TitularcuentaServiceLocal.class)
@Remote(TitularcuentaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TitularcuentaServiceBean implements TitularcuentaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TitularcuentaDAO oTitularcuentaDAO;

	@Override
	public Titularcuenta create(Titularcuenta oTitularcuenta) {
		try {
			oTitularcuentaDAO.create(oTitularcuenta);
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
		return oTitularcuenta;
	}

	@Override
	public Titularcuenta find(Object id) {
		Titularcuenta Titularcuenta = null;
		try {
			Titularcuenta = oTitularcuentaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Titularcuenta;
	}

	@Override
	public void delete(Titularcuenta oTitularcuenta) {
		try {
			oTitularcuentaDAO.delete(oTitularcuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Titularcuenta update(Titularcuenta oTitularcuenta) {
		Titularcuenta Titularcuenta = null;
		try {
			Titularcuenta = oTitularcuentaDAO.update(oTitularcuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Titularcuenta;
	}

	@Override
	public Collection<Titularcuenta> findByNamedQuery(String queryName) {
		Collection<Titularcuenta> collection = null;
		try {
			collection = oTitularcuentaDAO.findByNamedQuery(queryName);
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
	public Collection<Titularcuenta> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Titularcuenta> collection = null;
		try {
			collection = oTitularcuentaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Titularcuenta> findByNamedQuery(String Titularcuenta,
			Map<String, Object> parameters) {
		List<Titularcuenta> list = null;
		try {
			list = oTitularcuentaDAO.findByNamedQuery(Titularcuenta, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Titularcuenta> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Titularcuenta> list = null;
		try {
			list = oTitularcuentaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
