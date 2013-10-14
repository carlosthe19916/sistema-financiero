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

import org.ventura.boundary.local.BeneficiariocuentaServiceLocal;
import org.ventura.boundary.remote.BeneficiariocuentaServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(BeneficiariocuentaServiceLocal.class)
@Remote(BeneficiariocuentaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BeneficiariocuentaServiceBean implements BeneficiariocuentaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private BeneficiariocuentaDAO oBeneficiariocuentaDAO;

	@Override
	public Beneficiariocuenta create(Beneficiariocuenta oBeneficiariocuenta) {
		try {
			oBeneficiariocuentaDAO.create(oBeneficiariocuenta);
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
		return oBeneficiariocuenta;
	}

	@Override
	public Beneficiariocuenta find(Integer id) {
		Beneficiariocuenta Beneficiariocuenta = null;
		try {
			Beneficiariocuenta = oBeneficiariocuentaDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Beneficiariocuenta;
	}

	@Override
	public void delete(Beneficiariocuenta oBeneficiariocuenta) {
		try {
			oBeneficiariocuentaDAO.delete(oBeneficiariocuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Beneficiariocuenta update(Beneficiariocuenta oBeneficiariocuenta) {
		Beneficiariocuenta Beneficiariocuenta = null;
		try {
			Beneficiariocuenta = oBeneficiariocuentaDAO.update(oBeneficiariocuenta);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Beneficiariocuenta;
	}

	@Override
	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName) {
		Collection<Beneficiariocuenta> collection = null;
		try {
			collection = oBeneficiariocuentaDAO.findByNamedQuery(queryName);
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
	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Beneficiariocuenta> collection = null;
		try {
			collection = oBeneficiariocuentaDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Beneficiariocuenta> findByNamedQuery(String Beneficiariocuenta,
			Map<String, Object> parameters) {
		List<Beneficiariocuenta> list = null;
		try {
			list = oBeneficiariocuentaDAO.findByNamedQuery(Beneficiariocuenta, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Beneficiariocuenta> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Beneficiariocuenta> list = null;
		try {
			list = oBeneficiariocuentaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
