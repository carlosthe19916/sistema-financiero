package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.CuentacorrientehistorialDAO;
import org.ventura.facade.CuentacorrientehistorialFacadeLocal;
import org.ventura.model.Cuentacorrientehistorial;

@Stateless
public class CuentacorrientehistorialFacadeLocalImpl implements CuentacorrientehistorialFacadeLocal {

	@EJB
	private CuentacorrientehistorialDAO oCuentacorrientehistorialDAO;

	@Override
	public Cuentacorrientehistorial create(Cuentacorrientehistorial oCuentacorrientehistorial) {
		oCuentacorrientehistorialDAO.create(oCuentacorrientehistorial);
		return oCuentacorrientehistorial;
	}

	@Override
	public Cuentacorrientehistorial find(Integer id) {		
		return oCuentacorrientehistorialDAO.find(id);
	}

	@Override
	public void delete(Cuentacorrientehistorial oCuentacorrientehistorial) {
		oCuentacorrientehistorialDAO.delete(oCuentacorrientehistorial);
	}

	@Override
	public Cuentacorrientehistorial update(Cuentacorrientehistorial oCuentacorrientehistorial) {		
		return oCuentacorrientehistorialDAO.update(oCuentacorrientehistorial);
	}

	@Override
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName) {		
		return oCuentacorrientehistorialDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentacorrientehistorial> findByNamedQuery(String Cuentacorrientehistorial, Map<String, Object> parameters) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(Cuentacorrientehistorial, parameters);
	}

	@Override
	public List<Cuentacorrientehistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
