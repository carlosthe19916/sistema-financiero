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

import org.ventura.boundary.local.ChequeraestadoServiceLocal;
import org.ventura.boundary.remote.ChequeraestadoServiceRemote;
import org.ventura.dao.impl.ChequeraestadoDAO;
import org.ventura.entity.Chequeraestado;

@Stateless
@Local(ChequeraestadoServiceLocal.class)
@Remote(ChequeraestadoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeraestadoServiceBean implements ChequeraestadoServiceLocal {

	@EJB
	private ChequeraestadoDAO oChequeraestadoDAO;

	@Override
	public Chequeraestado create(Chequeraestado oChequeraestado) {
		oChequeraestadoDAO.create(oChequeraestado);
		return oChequeraestado;
	}

	@Override
	public Chequeraestado find(Integer id) {		
		return oChequeraestadoDAO.find(id);
	}

	@Override
	public void delete(Chequeraestado oChequeraestado) {
		oChequeraestadoDAO.delete(oChequeraestado);
	}

	@Override
	public Chequeraestado update(Chequeraestado oChequeraestado) {		
		return oChequeraestadoDAO.update(oChequeraestado);
	}

	@Override
	public Collection<Chequeraestado> findByNamedQuery(String queryName) {		
		return oChequeraestadoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Chequeraestado> findByNamedQuery(String queryName, int resultLimit) {
		return oChequeraestadoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Chequeraestado> findByNamedQuery(String Chequeraestado, Map<String, Object> parameters) {
		return oChequeraestadoDAO.findByNamedQuery(Chequeraestado, parameters);
	}

	@Override
	public List<Chequeraestado> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oChequeraestadoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
