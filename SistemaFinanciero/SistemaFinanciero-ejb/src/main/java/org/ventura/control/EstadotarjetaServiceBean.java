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

import org.ventura.boundary.local.EstadotarjetaServiceLocal;
import org.ventura.boundary.remote.EstadotarjetaServiceRemote;
import org.ventura.dao.impl.EstadotarjetaDAO;
import org.ventura.entity.Estadotarjeta;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(EstadotarjetaServiceLocal.class)
@Remote(EstadotarjetaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadotarjetaServiceBean implements EstadotarjetaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private EstadotarjetaDAO oEstadotarjetaDAO;

	@Override
	public Estadotarjeta create(Estadotarjeta oEstadotarjeta) {
		try {
			oEstadotarjetaDAO.create(oEstadotarjeta);
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
		return oEstadotarjeta;
	}

	@Override
	public Estadotarjeta find(Integer id) {
		Estadotarjeta Estadotarjeta = null;
		try {
			Estadotarjeta = oEstadotarjetaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadotarjeta;
	}

	@Override
	public void delete(Estadotarjeta oEstadotarjeta) {
		try {
			oEstadotarjetaDAO.delete(oEstadotarjeta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Estadotarjeta update(Estadotarjeta oEstadotarjeta) {
		Estadotarjeta Estadotarjeta = null;
		try {
			Estadotarjeta = oEstadotarjetaDAO.update(oEstadotarjeta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Estadotarjeta;
	}

	@Override
	public Collection<Estadotarjeta> findByNamedQuery(String queryName) {
		Collection<Estadotarjeta> collection = null;
		try {
			collection = oEstadotarjetaDAO.findByNamedQuery(queryName);
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
	public Collection<Estadotarjeta> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Estadotarjeta> collection = null;
		try {
			collection = oEstadotarjetaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Estadotarjeta> findByNamedQuery(String Estadotarjeta,
			Map<String, Object> parameters) {
		List<Estadotarjeta> list = null;
		try {
			list = oEstadotarjetaDAO.findByNamedQuery(Estadotarjeta, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Estadotarjeta> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Estadotarjeta> list = null;
		try {
			list = oEstadotarjetaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
