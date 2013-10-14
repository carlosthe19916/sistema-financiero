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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.remote.CuentacorrienteServiceRemote;
import org.ventura.dao.impl.CuentacorrienteDAO;
import org.ventura.entity.Cuentacorriente;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentacorrienteServiceLocal.class)
@Remote(CuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrienteServiceBean implements CuentacorrienteServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentacorrienteDAO oCuentacorrienteDAO;

	@Override
	public Cuentacorriente create(Cuentacorriente oCuentacorriente) {
		try {
			oCuentacorrienteDAO.create(oCuentacorriente);
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
		return oCuentacorriente;
	}

	@Override
	public Cuentacorriente find(Integer id) {
		Cuentacorriente Cuentacorriente = null;
		try {
			Cuentacorriente = oCuentacorrienteDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentacorriente;
	}

	@Override
	public void delete(Cuentacorriente oCuentacorriente) {
		try {
			oCuentacorrienteDAO.delete(oCuentacorriente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Cuentacorriente update(Cuentacorriente oCuentacorriente) {
		Cuentacorriente Cuentacorriente = null;
		try {
			Cuentacorriente = oCuentacorrienteDAO.update(oCuentacorriente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cuentacorriente;
	}

	@Override
	public Collection<Cuentacorriente> findByNamedQuery(String queryName) {
		Collection<Cuentacorriente> collection = null;
		try {
			collection = oCuentacorrienteDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentacorriente> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Cuentacorriente> collection = null;
		try {
			collection = oCuentacorrienteDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentacorriente> findByNamedQuery(String Cuentacorriente,
			Map<String, Object> parameters) {
		List<Cuentacorriente> list = null;
		try {
			list = oCuentacorrienteDAO.findByNamedQuery(Cuentacorriente, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cuentacorriente> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Cuentacorriente> list = null;
		try {
			list = oCuentacorrienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
