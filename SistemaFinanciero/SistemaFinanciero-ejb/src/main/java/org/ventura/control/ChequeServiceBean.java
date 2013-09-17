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

import org.ventura.boundary.local.ChequeServiceLocal;
import org.ventura.boundary.remote.ChequeServiceRemote;
import org.ventura.dao.impl.ChequeDAO;
import org.ventura.entity.Cheque;

@Stateless
@Local(ChequeServiceLocal.class)
@Remote(ChequeServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

public class ChequeServiceBean implements ChequeServiceLocal {

	@EJB
	private ChequeDAO oChequeDAO;

	@Override
	public Cheque create(Cheque oCheque) {
		oChequeDAO.create(oCheque);
		return oCheque;
	}

	@Override
	public Cheque find(Integer id) {		
		return oChequeDAO.find(id);
	}

	@Override
	public void delete(Cheque oCheque) {
		oChequeDAO.delete(oCheque);
	}

	@Override
	public Cheque update(Cheque oCheque) {		
		return oChequeDAO.update(oCheque);
	}

	@Override
	public Collection<Cheque> findByNamedQuery(String queryName) {		
		return oChequeDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cheque> findByNamedQuery(String queryName, int resultLimit) {
		return oChequeDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cheque> findByNamedQuery(String Cheque, Map<String, Object> parameters) {
		return oChequeDAO.findByNamedQuery(Cheque, parameters);
	}

	@Override
	public List<Cheque> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oChequeDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
