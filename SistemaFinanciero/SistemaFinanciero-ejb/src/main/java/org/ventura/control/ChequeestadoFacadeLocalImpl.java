package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.ChequeestadoDAO;
import org.ventura.facade.ChequeestadoFacadeLocal;
import org.ventura.model.Chequeestado;

@Stateless
public class ChequeestadoFacadeLocalImpl implements ChequeestadoFacadeLocal {

	@EJB
	private ChequeestadoDAO oChequeestadoDAO;

	@Override
	public Chequeestado create(Chequeestado oChequeestado) {
		oChequeestadoDAO.create(oChequeestado);
		return oChequeestado;
	}

	@Override
	public Chequeestado find(Integer id) {		
		return oChequeestadoDAO.find(id);
	}

	@Override
	public void delete(Chequeestado oChequeestado) {
		oChequeestadoDAO.delete(oChequeestado);
	}

	@Override
	public Chequeestado update(Chequeestado oChequeestado) {		
		return oChequeestadoDAO.update(oChequeestado);
	}

	@Override
	public Collection<Chequeestado> findByNamedQuery(String queryName) {		
		return oChequeestadoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Chequeestado> findByNamedQuery(String queryName, int resultLimit) {
		return oChequeestadoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Chequeestado> findByNamedQuery(String Chequeestado, Map<String, Object> parameters) {
		return oChequeestadoDAO.findByNamedQuery(Chequeestado, parameters);
	}

	@Override
	public List<Chequeestado> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oChequeestadoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
