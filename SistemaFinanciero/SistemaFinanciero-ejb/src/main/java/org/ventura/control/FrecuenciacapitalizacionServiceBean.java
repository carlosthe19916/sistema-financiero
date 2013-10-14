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

import org.ventura.boundary.local.FrecuenciacapitalizacionServiceLocal;
import org.ventura.boundary.remote.FrecuenciacapitalizacionServiceRemote;
import org.ventura.dao.impl.FrecuenciacapitalizacionDAO;
import org.ventura.entity.Frecuenciacapitalizacion;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(FrecuenciacapitalizacionServiceLocal.class)
@Remote(FrecuenciacapitalizacionServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FrecuenciacapitalizacionServiceBean implements FrecuenciacapitalizacionServiceLocal {

	@Inject
	private Log log;

	@EJB
	private FrecuenciacapitalizacionDAO oFrecuenciacapitalizacionDAO;

	@Override
	public Frecuenciacapitalizacion create(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {
		try {
			oFrecuenciacapitalizacionDAO.create(oFrecuenciacapitalizacion);
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
		return oFrecuenciacapitalizacion;
	}

	@Override
	public Frecuenciacapitalizacion find(Integer id) {
		Frecuenciacapitalizacion Frecuenciacapitalizacion = null;
		try {
			Frecuenciacapitalizacion = oFrecuenciacapitalizacionDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Frecuenciacapitalizacion;
	}

	@Override
	public void delete(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {
		try {
			oFrecuenciacapitalizacionDAO.delete(oFrecuenciacapitalizacion);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Frecuenciacapitalizacion update(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {
		Frecuenciacapitalizacion Frecuenciacapitalizacion = null;
		try {
			Frecuenciacapitalizacion = oFrecuenciacapitalizacionDAO.update(oFrecuenciacapitalizacion);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Frecuenciacapitalizacion;
	}

	@Override
	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName) {
		Collection<Frecuenciacapitalizacion> collection = null;
		try {
			collection = oFrecuenciacapitalizacionDAO.findByNamedQuery(queryName);
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
	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Frecuenciacapitalizacion> collection = null;
		try {
			collection = oFrecuenciacapitalizacionDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Frecuenciacapitalizacion> findByNamedQuery(String Frecuenciacapitalizacion,
			Map<String, Object> parameters) {
		List<Frecuenciacapitalizacion> list = null;
		try {
			list = oFrecuenciacapitalizacionDAO.findByNamedQuery(Frecuenciacapitalizacion, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Frecuenciacapitalizacion> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Frecuenciacapitalizacion> list = null;
		try {
			list = oFrecuenciacapitalizacionDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
