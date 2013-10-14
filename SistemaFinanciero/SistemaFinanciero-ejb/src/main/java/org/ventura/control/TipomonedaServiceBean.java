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

import org.ventura.boundary.local.TipomonedaServiceLocal;
import org.ventura.boundary.remote.TipomonedaServiceRemote;
import org.ventura.dao.impl.TipomonedaDAO;
import org.ventura.entity.Tipomoneda;
import org.ventura.entity.Tipomoneda;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TipomonedaServiceLocal.class)
@Remote(TipomonedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipomonedaServiceBean implements TipomonedaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TipomonedaDAO oTipomonedaDAO;

	@Override
	public Tipomoneda create(Tipomoneda oTipomoneda) {
		try {
			oTipomonedaDAO.create(oTipomoneda);
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
		return oTipomoneda;
	}

	@Override
	public Tipomoneda find(Integer id) {
		Tipomoneda Tipomoneda = null;
		try {
			Tipomoneda = oTipomonedaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipomoneda;
	}

	@Override
	public void delete(Tipomoneda oTipomoneda) {
		try {
			oTipomonedaDAO.delete(oTipomoneda);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tipomoneda update(Tipomoneda oTipomoneda) {
		Tipomoneda Tipomoneda = null;
		try {
			Tipomoneda = oTipomonedaDAO.update(oTipomoneda);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipomoneda;
	}

	@Override
	public Collection<Tipomoneda> findByNamedQuery(String queryName) {
		Collection<Tipomoneda> collection = null;
		try {
			collection = oTipomonedaDAO.findByNamedQuery(queryName);
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
	public Collection<Tipomoneda> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tipomoneda> collection = null;
		try {
			collection = oTipomonedaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tipomoneda> findByNamedQuery(String Tipomoneda,
			Map<String, Object> parameters) {
		List<Tipomoneda> list = null;
		try {
			list = oTipomonedaDAO.findByNamedQuery(Tipomoneda, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tipomoneda> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tipomoneda> list = null;
		try {
			list = oTipomonedaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	
}
