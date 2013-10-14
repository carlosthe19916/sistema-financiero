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

import org.ventura.boundary.local.TipoempresaServiceLocal;
import org.ventura.boundary.remote.TipoempresaServiceRemote;
import org.ventura.dao.impl.TipoempresaDAO;
import org.ventura.entity.Tipoempresa;
import org.ventura.entity.Tipoempresa;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TipoempresaServiceLocal.class)
@Remote(TipoempresaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoempresaServiceBean implements TipoempresaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TipoempresaDAO oTipoempresaDAO;

	@Override
	public Tipoempresa create(Tipoempresa oTipoempresa) {
		try {
			oTipoempresaDAO.create(oTipoempresa);
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
		return oTipoempresa;
	}

	@Override
	public Tipoempresa find(Integer id) {
		Tipoempresa Tipoempresa = null;
		try {
			Tipoempresa = oTipoempresaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipoempresa;
	}

	@Override
	public void delete(Tipoempresa oTipoempresa) {
		try {
			oTipoempresaDAO.delete(oTipoempresa);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tipoempresa update(Tipoempresa oTipoempresa) {
		Tipoempresa Tipoempresa = null;
		try {
			Tipoempresa = oTipoempresaDAO.update(oTipoempresa);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipoempresa;
	}

	@Override
	public Collection<Tipoempresa> findByNamedQuery(String queryName) {
		Collection<Tipoempresa> collection = null;
		try {
			collection = oTipoempresaDAO.findByNamedQuery(queryName);
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
	public Collection<Tipoempresa> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tipoempresa> collection = null;
		try {
			collection = oTipoempresaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tipoempresa> findByNamedQuery(String Tipoempresa,
			Map<String, Object> parameters) {
		List<Tipoempresa> list = null;
		try {
			list = oTipoempresaDAO.findByNamedQuery(Tipoempresa, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tipoempresa> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tipoempresa> list = null;
		try {
			list = oTipoempresaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Collection<Tipoempresa> findAll() {
		Collection<Tipoempresa> collection = null;
		try {
			collection = oTipoempresaDAO.findAll();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

}
