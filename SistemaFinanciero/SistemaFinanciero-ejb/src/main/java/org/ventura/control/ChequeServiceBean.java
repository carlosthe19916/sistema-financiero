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

import org.ventura.boundary.local.ChequeServiceLocal;
import org.ventura.boundary.remote.ChequeServiceRemote;
import org.ventura.dao.impl.ChequeDAO;
import org.ventura.entity.Cheque;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(ChequeServiceLocal.class)
@Remote(ChequeServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeServiceBean implements ChequeServiceLocal {

	@Inject
	private Log log;

	@EJB
	private ChequeDAO oChequeDAO;

	@Override
	public Cheque create(Cheque oCheque) {
		try {
			oChequeDAO.create(oCheque);
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
		return oCheque;
	}

	@Override
	public Cheque find(Integer id) {
		Cheque Cheque = null;
		try {
			Cheque = oChequeDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cheque;
	}

	@Override
	public void delete(Cheque oCheque) {
		try {
			oChequeDAO.delete(oCheque);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cheque update(Cheque oCheque) {
		Cheque Cheque = null;
		try {
			Cheque = oChequeDAO.update(oCheque);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cheque;
	}

	@Override
	public Collection<Cheque> findByNamedQuery(String queryName) {
		Collection<Cheque> collection = null;
		try {
			collection = oChequeDAO.findByNamedQuery(queryName);
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
	public Collection<Cheque> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Cheque> collection = null;
		try {
			collection = oChequeDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cheque> findByNamedQuery(String Cheque,
			Map<String, Object> parameters) {
		List<Cheque> list = null;
		try {
			list = oChequeDAO.findByNamedQuery(Cheque, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cheque> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Cheque> list = null;
		try {
			list = oChequeDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
