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

import org.ventura.boundary.local.ChequeestadoServiceLocal;
import org.ventura.boundary.remote.ChequeestadoServiceRemote;
import org.ventura.dao.impl.ChequeestadoDAO;
import org.ventura.entity.Chequeestado;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(ChequeestadoServiceLocal.class)
@Remote(ChequeestadoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeestadoServiceBean implements ChequeestadoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private ChequeestadoDAO oChequeestadoDAO;

	@Override
	public Chequeestado create(Chequeestado oChequeestado) {
		try {
			oChequeestadoDAO.create(oChequeestado);
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
		return oChequeestado;
	}

	@Override
	public Chequeestado find(Integer id) {
		Chequeestado Chequeestado = null;
		try {
			Chequeestado = oChequeestadoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequeestado;
	}

	@Override
	public void delete(Chequeestado oChequeestado) {
		try {
			oChequeestadoDAO.delete(oChequeestado);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Chequeestado update(Chequeestado oChequeestado) {
		Chequeestado Chequeestado = null;
		try {
			Chequeestado = oChequeestadoDAO.update(oChequeestado);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequeestado;
	}

	@Override
	public Collection<Chequeestado> findByNamedQuery(String queryName) {
		Collection<Chequeestado> collection = null;
		try {
			collection = oChequeestadoDAO.findByNamedQuery(queryName);
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
	public Collection<Chequeestado> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Chequeestado> collection = null;
		try {
			collection = oChequeestadoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Chequeestado> findByNamedQuery(String Chequeestado,
			Map<String, Object> parameters) {
		List<Chequeestado> list = null;
		try {
			list = oChequeestadoDAO.findByNamedQuery(Chequeestado, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Chequeestado> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Chequeestado> list = null;
		try {
			list = oChequeestadoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
