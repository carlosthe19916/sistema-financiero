package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.SucursalDAO;
import org.ventura.facade.SucursalFacadeLocal;
import org.ventura.model.Sucursal;

@Stateless
public class SucursalFacadeLocalImpl implements SucursalFacadeLocal {

	@EJB
	private SucursalDAO oSucursalDAO;

	@Override
	public Sucursal create(Sucursal oSucursal) {
		oSucursalDAO.create(oSucursal);
		return oSucursal;
	}

	@Override
	public Sucursal find(Integer id) {		
		return oSucursalDAO.find(id);
	}

	@Override
	public void delete(Sucursal oSucursal) {
		oSucursalDAO.delete(oSucursal);
	}

	@Override
	public Sucursal update(Sucursal oSucursal) {		
		return oSucursalDAO.update(oSucursal);
	}

	@Override
	public Collection<Sucursal> findByNamedQuery(String queryName) {		
		return oSucursalDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Sucursal> findByNamedQuery(String queryName, int resultLimit) {
		return oSucursalDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Sucursal> findByNamedQuery(String Sucursal, Map<String, Object> parameters) {
		return oSucursalDAO.findByNamedQuery(Sucursal, parameters);
	}

	@Override
	public List<Sucursal> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oSucursalDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
