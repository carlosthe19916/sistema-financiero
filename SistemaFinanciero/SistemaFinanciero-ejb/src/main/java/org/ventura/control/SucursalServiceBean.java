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

import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.dao.impl.SucursalDAO;
import org.ventura.entity.Sucursal;

@Stateless
@Local(SucursalServiceLocal.class)
@Remote(SucursalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SucursalServiceBean implements SucursalServiceLocal {

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
