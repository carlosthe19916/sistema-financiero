package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.UbigeoServiceRemote;
import org.ventura.entity.Ubigeo;

@Local
public interface UbigeoServiceLocal extends UbigeoServiceRemote{
	
	public Ubigeo create(Ubigeo oUbigeo);

	public Ubigeo find(Integer id);

	public void delete(Ubigeo oUbigeo);

	public Ubigeo update(Ubigeo oUbigeo);

	public Collection<Ubigeo> findByNamedQuery(String queryName);

	public Collection<Ubigeo> findByNamedQuery(String queryName, int resultLimit);

	public List<Ubigeo> findByNamedQuery(String Ubigeo, Map<String, Object> parameters);

	public List<Ubigeo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
