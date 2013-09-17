package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.entity.Sucursal;

@Local
public interface SucursalServiceLocal extends SucursalServiceRemote{
	
	public Sucursal create(Sucursal oSucursal);

	public Sucursal find(Integer id);

	public void delete(Sucursal oSucursal);

	public Sucursal update(Sucursal oSucursal);

	public Collection<Sucursal> findByNamedQuery(String queryName);

	public Collection<Sucursal> findByNamedQuery(String queryName, int resultLimit);

	public List<Sucursal> findByNamedQuery(String Sucursal, Map<String, Object> parameters);

	public List<Sucursal> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
