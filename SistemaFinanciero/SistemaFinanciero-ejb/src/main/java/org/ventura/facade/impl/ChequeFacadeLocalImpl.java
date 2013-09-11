package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.ChequeDAO;
import org.ventura.facade.ChequeFacadeLocal;
import org.ventura.model.Cheque;

@Stateless
public class ChequeFacadeLocalImpl implements ChequeFacadeLocal {

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
