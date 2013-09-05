package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.CuentaplazofijoDAO;
import org.ventura.facade.CuentaplazofijoFacadeLocal;
import org.ventura.model.Cuentaplazofijo;

@Stateless
public class CuentaplazofijoFacadeLocalImpl implements CuentaplazofijoFacadeLocal {

	@EJB
	private CuentaplazofijoDAO oCuentaplazofijoDAO;

	@Override
	public Cuentaplazofijo create(Cuentaplazofijo oCuentaplazofijo) {
		oCuentaplazofijoDAO.create(oCuentaplazofijo);
		return oCuentaplazofijo;
	}

	@Override
	public Cuentaplazofijo find(Integer id) {		
		return oCuentaplazofijoDAO.find(id);
	}

	@Override
	public void delete(Cuentaplazofijo oCuentaplazofijo) {
		oCuentaplazofijoDAO.delete(oCuentaplazofijo);
	}

	@Override
	public Cuentaplazofijo update(Cuentaplazofijo oCuentaplazofijo) {		
		return oCuentaplazofijoDAO.update(oCuentaplazofijo);
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName) {		
		return oCuentaplazofijoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentaplazofijoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaplazofijo, Map<String, Object> parameters) {
		return oCuentaplazofijoDAO.findByNamedQuery(Cuentaplazofijo, parameters);
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentaplazofijoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
