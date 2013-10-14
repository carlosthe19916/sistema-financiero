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

import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.dao.impl.SucursalDAO;
import org.ventura.entity.Sucursal;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(SucursalServiceLocal.class)
@Remote(SucursalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SucursalServiceBean implements SucursalServiceLocal {

	@Inject
	private Log log;

	@EJB
	private SucursalDAO oSucursalDAO;

	@Override
	public Sucursal create(Sucursal oSucursal) {
		try {
			oSucursalDAO.create(oSucursal);
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
		return oSucursal;
	}

	@Override
	public Sucursal find(Integer id) {
		Sucursal Sucursal = null;
		try {
			Sucursal = oSucursalDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sucursal;
	}

	@Override
	public void delete(Sucursal oSucursal) {
		try {
			oSucursalDAO.delete(oSucursal);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Sucursal update(Sucursal oSucursal) {
		Sucursal Sucursal = null;
		try {
			Sucursal = oSucursalDAO.update(oSucursal);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Sucursal;
	}

	@Override
	public Collection<Sucursal> findByNamedQuery(String queryName) {
		Collection<Sucursal> collection = null;
		try {
			collection = oSucursalDAO.findByNamedQuery(queryName);
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
	public Collection<Sucursal> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Sucursal> collection = null;
		try {
			collection = oSucursalDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Sucursal> findByNamedQuery(String Sucursal,
			Map<String, Object> parameters) {
		List<Sucursal> list = null;
		try {
			list = oSucursalDAO.findByNamedQuery(Sucursal, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Sucursal> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Sucursal> list = null;
		try {
			list = oSucursalDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
