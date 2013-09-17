package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Tipomoneda;

@Remote
public interface TipomonedaServiceRemote {
	
	public Tipomoneda create(Tipomoneda oTipomoneda);

	public Tipomoneda find(Integer id);

	public void delete(Tipomoneda oTipomoneda);

	public Tipomoneda update(Tipomoneda oTipomoneda);

	public Collection<Tipomoneda> findByNamedQuery(String queryName);

	public Collection<Tipomoneda> findByNamedQuery(String queryName, int resultLimit);

	public List<Tipomoneda> findByNamedQuery(String Tipomoneda, Map<String, Object> parameters);

	public List<Tipomoneda> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
