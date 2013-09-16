package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.CuentacorrienteDAO;
import org.ventura.facade.CuentacorrienteFacadeLocal;
import org.ventura.model.Cuentacorriente;

@Stateless
public class CuentacorrienteFacadeLocalImpl implements CuentacorrienteFacadeLocal {

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
