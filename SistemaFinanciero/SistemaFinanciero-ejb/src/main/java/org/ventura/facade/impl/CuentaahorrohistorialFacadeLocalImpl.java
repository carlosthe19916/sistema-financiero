package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.CuentaahorrohistorialDAO;
import org.ventura.facade.CuentaahorrohistorialFacadeLocal;
import org.ventura.model.Cuentaahorrohistorial;

@Stateless
public class CuentaahorrohistorialFacadeLocalImpl implements CuentaahorrohistorialFacadeLocal {

	@EJB
	private CuentaahorrohistorialDAO oCuentaahorrohistorialDAO;

	@Override
	public Cuentaahorrohistorial create(Cuentaahorrohistorial oCuentaahorrohistorial) {
		oCuentaahorrohistorialDAO.create(oCuentaahorrohistorial);
		return oCuentaahorrohistorial;
	}

	@Override
	public Cuentaahorrohistorial find(Integer id) {		
		return oCuentaahorrohistorialDAO.find(id);
	}

	@Override
	public void delete(Cuentaahorrohistorial oCuentaahorrohistorial) {
		oCuentaahorrohistorialDAO.delete(oCuentaahorrohistorial);
	}

	@Override
	public Cuentaahorrohistorial update(Cuentaahorrohistorial oCuentaahorrohistorial) {		
		return oCuentaahorrohistorialDAO.update(oCuentaahorrohistorial);
	}

	@Override
	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName) {		
		return oCuentaahorrohistorialDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentaahorrohistorialDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaahorrohistorial> findByNamedQuery(String Cuentaahorrohistorial, Map<String, Object> parameters) {
		return oCuentaahorrohistorialDAO.findByNamedQuery(Cuentaahorrohistorial, parameters);
	}

	@Override
	public List<Cuentaahorrohistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentaahorrohistorialDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
