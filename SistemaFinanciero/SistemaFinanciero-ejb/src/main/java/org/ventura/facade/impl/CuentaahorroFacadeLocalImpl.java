package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.facade.CuentaahorroFacadeLocal;
import org.ventura.model.Cuentaahorro;

@Stateless
public class CuentaahorroFacadeLocalImpl implements CuentaahorroFacadeLocal {

	@EJB
	private CuentaahorroDAO oCuentaahorroDAO;

	@Override
	public Cuentaahorro create(Cuentaahorro oCuentaahorro) {
		oCuentaahorroDAO.create(oCuentaahorro);
		return oCuentaahorro;
	}

	@Override
	public Cuentaahorro find(Integer id) {		
		return oCuentaahorroDAO.find(id);
	}

	@Override
	public void delete(Cuentaahorro oCuentaahorro) {
		oCuentaahorroDAO.delete(oCuentaahorro);
	}

	@Override
	public Cuentaahorro update(Cuentaahorro oCuentaahorro) {		
		return oCuentaahorroDAO.update(oCuentaahorro);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName) {		
		return oCuentaahorroDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro, Map<String, Object> parameters) {
		return oCuentaahorroDAO.findByNamedQuery(Cuentaahorro, parameters);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
