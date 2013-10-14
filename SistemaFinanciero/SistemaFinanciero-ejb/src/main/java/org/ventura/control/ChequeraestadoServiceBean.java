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

import org.ventura.boundary.local.ChequeraestadoServiceLocal;
import org.ventura.boundary.remote.ChequeraestadoServiceRemote;
import org.ventura.dao.impl.ChequeraestadoDAO;
import org.ventura.entity.Chequeraestado;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(ChequeraestadoServiceLocal.class)
@Remote(ChequeraestadoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeraestadoServiceBean implements ChequeraestadoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private ChequeraestadoDAO oChequeraestadoDAO;

	@Override
	public Chequeraestado create(Chequeraestado oChequeraestado) {
		try {
			oChequeraestadoDAO.create(oChequeraestado);
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
		return oChequeraestado;
	}

	@Override
	public Chequeraestado find(Integer id) {
		Chequeraestado Chequeraestado = null;
		try {
			Chequeraestado = oChequeraestadoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequeraestado;
	}

	@Override
	public void delete(Chequeraestado oChequeraestado) {
		try {
			oChequeraestadoDAO.delete(oChequeraestado);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Chequeraestado update(Chequeraestado oChequeraestado) {
		Chequeraestado Chequeraestado = null;
		try {
			Chequeraestado = oChequeraestadoDAO.update(oChequeraestado);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Chequeraestado;
	}

	@Override
	public Collection<Chequeraestado> findByNamedQuery(String queryName) {
		Collection<Chequeraestado> collection = null;
		try {
			collection = oChequeraestadoDAO.findByNamedQuery(queryName);
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
	public Collection<Chequeraestado> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Chequeraestado> collection = null;
		try {
			collection = oChequeraestadoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Chequeraestado> findByNamedQuery(String Chequeraestado,
			Map<String, Object> parameters) {
		List<Chequeraestado> list = null;
		try {
			list = oChequeraestadoDAO.findByNamedQuery(Chequeraestado, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Chequeraestado> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Chequeraestado> list = null;
		try {
			list = oChequeraestadoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
