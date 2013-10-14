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

import org.ventura.boundary.local.ChequeraServiceLocal;
import org.ventura.boundary.remote.ChequeraServiceRemote;
import org.ventura.dao.impl.ChequeraDAO;
import org.ventura.entity.Chequera;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(ChequeraServiceLocal.class)
@Remote(ChequeraServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeraServiceBean implements ChequeraServiceLocal {

	@Inject
	private Log log;

	@EJB
	private ChequeraDAO oChequeraDAO;

	@Override
	public Chequera create(Chequera oChequera) {
		try {
			oChequeraDAO.create(oChequera);
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
		return oChequera;
	}

	@Override
	public Chequera find(Integer id) {
		Chequera Chequera = null;
		try {
			Chequera = oChequeraDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequera;
	}

	@Override
	public void delete(Chequera oChequera) {
		try {
			oChequeraDAO.delete(oChequera);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Chequera update(Chequera oChequera) {
		Chequera Chequera = null;
		try {
			Chequera = oChequeraDAO.update(oChequera);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequera;
	}

	@Override
	public Collection<Chequera> findByNamedQuery(String queryName) {
		Collection<Chequera> collection = null;
		try {
			collection = oChequeraDAO.findByNamedQuery(queryName);
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
	public Collection<Chequera> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Chequera> collection = null;
		try {
			collection = oChequeraDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Chequera> findByNamedQuery(String Chequera,
			Map<String, Object> parameters) {
		List<Chequera> list = null;
		try {
			list = oChequeraDAO.findByNamedQuery(Chequera, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Chequera> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Chequera> list = null;
		try {
			list = oChequeraDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
