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

import org.ventura.boundary.local.UbigeoServiceLocal;
import org.ventura.boundary.remote.UbigeoServiceRemote;
import org.ventura.dao.impl.UbigeoDAO;
import org.ventura.entity.Ubigeo;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(UbigeoServiceLocal.class)
@Remote(UbigeoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UbigeoServiceBean implements UbigeoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private UbigeoDAO oUbigeoDAO;

	@Override
	public Ubigeo create(Ubigeo oUbigeo) {
		try {
			oUbigeoDAO.create(oUbigeo);
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
		return oUbigeo;
	}

	@Override
	public Ubigeo find(Integer id) {
		Ubigeo Ubigeo = null;
		try {
			Ubigeo = oUbigeoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Ubigeo;
	}

	@Override
	public void delete(Ubigeo oUbigeo) {
		try {
			oUbigeoDAO.delete(oUbigeo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Ubigeo update(Ubigeo oUbigeo) {
		Ubigeo Ubigeo = null;
		try {
			Ubigeo = oUbigeoDAO.update(oUbigeo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Ubigeo;
	}

	@Override
	public Collection<Ubigeo> findByNamedQuery(String queryName) {
		Collection<Ubigeo> collection = null;
		try {
			collection = oUbigeoDAO.findByNamedQuery(queryName);
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
	public Collection<Ubigeo> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Ubigeo> collection = null;
		try {
			collection = oUbigeoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Ubigeo> findByNamedQuery(String Ubigeo,
			Map<String, Object> parameters) {
		List<Ubigeo> list = null;
		try {
			list = oUbigeoDAO.findByNamedQuery(Ubigeo, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Ubigeo> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Ubigeo> list = null;
		try {
			list = oUbigeoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
