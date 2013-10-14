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

import org.ventura.boundary.local.CuentaahorrohistorialServiceLocal;
import org.ventura.boundary.remote.CuentaahorrohistorialServiceRemote;
import org.ventura.dao.impl.CuentaahorrohistorialDAO;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentaahorrohistorialServiceLocal.class)
@Remote(CuentaahorrohistorialServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaahorrohistorialServiceBean implements CuentaahorrohistorialServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentaahorrohistorialDAO oCuentaahorrohistorialDAO;

	@Override
	public Cuentaahorrohistorial create(Cuentaahorrohistorial oCuentaahorrohistorial) {
		try {
			oCuentaahorrohistorialDAO.create(oCuentaahorrohistorial);
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
		return oCuentaahorrohistorial;
	}

	@Override
	public Cuentaahorrohistorial find(Integer id) {
		Cuentaahorrohistorial Cuentaahorrohistorial = null;
		try {
			Cuentaahorrohistorial = oCuentaahorrohistorialDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentaahorrohistorial;
	}

	@Override
	public void delete(Cuentaahorrohistorial oCuentaahorrohistorial) {
		try {
			oCuentaahorrohistorialDAO.delete(oCuentaahorrohistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cuentaahorrohistorial update(Cuentaahorrohistorial oCuentaahorrohistorial) {
		Cuentaahorrohistorial Cuentaahorrohistorial = null;
		try {
			Cuentaahorrohistorial = oCuentaahorrohistorialDAO.update(oCuentaahorrohistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentaahorrohistorial;
	}

	@Override
	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName) {
		Collection<Cuentaahorrohistorial> collection = null;
		try {
			collection = oCuentaahorrohistorialDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Cuentaahorrohistorial> collection = null;
		try {
			collection = oCuentaahorrohistorialDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentaahorrohistorial> findByNamedQuery(String Cuentaahorrohistorial,
			Map<String, Object> parameters) {
		List<Cuentaahorrohistorial> list = null;
		try {
			list = oCuentaahorrohistorialDAO.findByNamedQuery(Cuentaahorrohistorial, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cuentaahorrohistorial> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Cuentaahorrohistorial> list = null;
		try {
			list = oCuentaahorrohistorialDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
