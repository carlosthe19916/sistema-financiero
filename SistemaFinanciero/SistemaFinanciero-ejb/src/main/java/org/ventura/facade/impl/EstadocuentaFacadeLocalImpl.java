package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.EstadocuentaDAO;
import org.ventura.facade.EstadocuentaFacadeLocal;
import org.ventura.model.Estadocuenta;

@Stateless
public class EstadocuentaFacadeLocalImpl implements EstadocuentaFacadeLocal {

	@EJB
	private EstadocuentaDAO oEstadocuentaDAO;

	@Override
	public Estadocuenta create(Estadocuenta oEstadocuenta) {
		oEstadocuentaDAO.create(oEstadocuenta);
		return oEstadocuenta;
	}

	@Override
	public Estadocuenta find(Integer id) {		
		return oEstadocuentaDAO.find(id);
	}

	@Override
	public void delete(Estadocuenta oEstadocuenta) {
		oEstadocuentaDAO.delete(oEstadocuenta);
	}

	@Override
	public Estadocuenta update(Estadocuenta oEstadocuenta) {		
		return oEstadocuentaDAO.update(oEstadocuenta);
	}

	@Override
	public Collection<Estadocuenta> findByNamedQuery(String queryName) {		
		return oEstadocuentaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Estadocuenta> findByNamedQuery(String queryName, int resultLimit) {
		return oEstadocuentaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Estadocuenta> findByNamedQuery(String Estadocuenta, Map<String, Object> parameters) {
		return oEstadocuentaDAO.findByNamedQuery(Estadocuenta, parameters);
	}

	@Override
	public List<Estadocuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oEstadocuentaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
