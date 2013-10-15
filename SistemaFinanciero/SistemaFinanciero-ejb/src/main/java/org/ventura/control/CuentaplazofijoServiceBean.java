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

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.boundary.remote.CuentaplazofijoServiceRemote;
import org.ventura.dao.impl.CuentaplazofijoDAO;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentaplazofijoServiceLocal.class)
@Remote(CuentaplazofijoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaplazofijoServiceBean implements CuentaplazofijoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentaplazofijoDAO oCuentaplazofijoDAO;

	@Override
	public void create(Cuentaplazofijo oCuentaplazofijo) {
		try {
			oCuentaplazofijoDAO.create(oCuentaplazofijo);
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
	}

	@Override
	public Cuentaplazofijo find(Object id) {
		Cuentaplazofijo Cuentaplazofijo = null;
		try {
			Cuentaplazofijo = oCuentaplazofijoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentaplazofijo;
	}

	@Override
	public void delete(Cuentaplazofijo oCuentaplazofijo) {
		try {
			oCuentaplazofijoDAO.delete(oCuentaplazofijo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Cuentaplazofijo oCuentaplazofijo) {
		Cuentaplazofijo Cuentaplazofijo = null;
		try {
			Cuentaplazofijo = oCuentaplazofijoDAO.update(oCuentaplazofijo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName) {
		Collection<Cuentaplazofijo> collection = null;
		try {
			collection = oCuentaplazofijoDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Cuentaplazofijo> collection = null;
		try {
			collection = oCuentaplazofijoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaplazofijo,
			Map<String, Object> parameters) {
		List<Cuentaplazofijo> list = null;
		try {
			list = oCuentaplazofijoDAO.findByNamedQuery(Cuentaplazofijo, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Cuentaplazofijo> list = null;
		try {
			list = oCuentaplazofijoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
