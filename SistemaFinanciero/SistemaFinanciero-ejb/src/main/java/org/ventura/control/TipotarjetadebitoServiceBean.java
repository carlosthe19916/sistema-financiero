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

import org.ventura.boundary.local.TipotarjetadebitoServiceLocal;
import org.ventura.boundary.remote.TipotarjetadebitoServiceRemote;
import org.ventura.dao.impl.TipotarjetadebitoDAO;
import org.ventura.entity.Tipotarjetadebito;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TipotarjetadebitoServiceLocal.class)
@Remote(TipotarjetadebitoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipotarjetadebitoServiceBean implements TipotarjetadebitoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TipotarjetadebitoDAO oTipotarjetadebitoDAO;

	@Override
	public Tipotarjetadebito create(Tipotarjetadebito oTipotarjetadebito) {
		try {
			oTipotarjetadebitoDAO.create(oTipotarjetadebito);
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
		return oTipotarjetadebito;
	}

	@Override
	public Tipotarjetadebito find(Integer id) {
		Tipotarjetadebito Tipotarjetadebito = null;
		try {
			Tipotarjetadebito = oTipotarjetadebitoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipotarjetadebito;
	}

	@Override
	public void delete(Tipotarjetadebito oTipotarjetadebito) {
		try {
			oTipotarjetadebitoDAO.delete(oTipotarjetadebito);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tipotarjetadebito update(Tipotarjetadebito oTipotarjetadebito) {
		Tipotarjetadebito Tipotarjetadebito = null;
		try {
			Tipotarjetadebito = oTipotarjetadebitoDAO.update(oTipotarjetadebito);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tipotarjetadebito;
	}

	@Override
	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName) {
		Collection<Tipotarjetadebito> collection = null;
		try {
			collection = oTipotarjetadebitoDAO.findByNamedQuery(queryName);
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
	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tipotarjetadebito> collection = null;
		try {
			collection = oTipotarjetadebitoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tipotarjetadebito> findByNamedQuery(String Tipotarjetadebito,
			Map<String, Object> parameters) {
		List<Tipotarjetadebito> list = null;
		try {
			list = oTipotarjetadebitoDAO.findByNamedQuery(Tipotarjetadebito, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tipotarjetadebito> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tipotarjetadebito> list = null;
		try {
			list = oTipotarjetadebitoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
