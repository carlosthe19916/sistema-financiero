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

import org.ventura.boundary.local.CuentacorrientehistorialServiceLocal;
import org.ventura.boundary.remote.CuentacorrientehistorialServiceRemote;
import org.ventura.dao.impl.CuentacorrientehistorialDAO;
import org.ventura.entity.Cuentacorrientehistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentacorrientehistorialServiceLocal.class)
@Remote(CuentacorrientehistorialServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrientehistorialServiceBean implements CuentacorrientehistorialServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentacorrientehistorialDAO oCuentacorrientehistorialDAO;

	@Override
	public Cuentacorrientehistorial create(Cuentacorrientehistorial oCuentacorrientehistorial) {
		try {
			oCuentacorrientehistorialDAO.create(oCuentacorrientehistorial);
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
		return oCuentacorrientehistorial;
	}

	@Override
	public Cuentacorrientehistorial find(Integer id) {
		Cuentacorrientehistorial Cuentacorrientehistorial = null;
		try {
			Cuentacorrientehistorial = oCuentacorrientehistorialDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentacorrientehistorial;
	}

	@Override
	public void delete(Cuentacorrientehistorial oCuentacorrientehistorial) {
		try {
			oCuentacorrientehistorialDAO.delete(oCuentacorrientehistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cuentacorrientehistorial update(Cuentacorrientehistorial oCuentacorrientehistorial) {
		Cuentacorrientehistorial Cuentacorrientehistorial = null;
		try {
			Cuentacorrientehistorial = oCuentacorrientehistorialDAO.update(oCuentacorrientehistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentacorrientehistorial;
	}

	@Override
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName) {
		Collection<Cuentacorrientehistorial> collection = null;
		try {
			collection = oCuentacorrientehistorialDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Cuentacorrientehistorial> collection = null;
		try {
			collection = oCuentacorrientehistorialDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentacorrientehistorial> findByNamedQuery(String Cuentacorrientehistorial,
			Map<String, Object> parameters) {
		List<Cuentacorrientehistorial> list = null;
		try {
			list = oCuentacorrientehistorialDAO.findByNamedQuery(Cuentacorrientehistorial, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cuentacorrientehistorial> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Cuentacorrientehistorial> list = null;
		try {
			list = oCuentacorrientehistorialDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
