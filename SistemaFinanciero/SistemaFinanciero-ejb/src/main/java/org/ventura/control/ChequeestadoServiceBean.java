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


import org.ventura.boundary.local.ChequeestadoServiceLocal;
import org.ventura.boundary.remote.ChequeestadoServiceRemote;
import org.ventura.dao.impl.ChequeestadoDAO;
import org.ventura.entity.Chequeestado;

@Stateless
@Local(ChequeestadoServiceLocal.class)
@Remote(ChequeestadoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeestadoServiceBean implements ChequeestadoServiceLocal {

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
