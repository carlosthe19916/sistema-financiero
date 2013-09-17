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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.remote.CuentacorrienteServiceRemote;
import org.ventura.dao.impl.CuentacorrienteDAO;
import org.ventura.entity.Cuentacorriente;

@Stateless
@Local(CuentacorrienteServiceLocal.class)
@Remote(CuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrienteServiceBean implements CuentacorrienteServiceLocal {

	@EJB
	private CuentacorrienteDAO oCuentacorrienteDAO;

	@Override
	public Cuentacorriente create(Cuentacorriente oCuentacorriente) {
		oCuentacorrienteDAO.create(oCuentacorriente);
		return oCuentacorriente;
	}

	@Override
	public Cuentacorriente find(Integer id) {		
		return oCuentacorrienteDAO.find(id);
	}

	@Override
	public void delete(Cuentacorriente oCuentacorriente) {
		oCuentacorrienteDAO.delete(oCuentacorriente);
	}

	@Override
	public Cuentacorriente update(Cuentacorriente oCuentacorriente) {		
		return oCuentacorrienteDAO.update(oCuentacorriente);
	}

	@Override
	public Collection<Cuentacorriente> findByNamedQuery(String queryName) {		
		return oCuentacorrienteDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentacorriente> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentacorrienteDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentacorriente> findByNamedQuery(String Cuentacorriente, Map<String, Object> parameters) {
		return oCuentacorrienteDAO.findByNamedQuery(Cuentacorriente, parameters);
	}

	@Override
	public List<Cuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentacorrienteDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
