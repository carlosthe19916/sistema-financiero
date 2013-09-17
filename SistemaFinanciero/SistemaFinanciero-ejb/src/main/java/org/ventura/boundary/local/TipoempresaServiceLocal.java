package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.TipoempresaServiceRemote;
import org.ventura.entity.Tipoempresa;

@Local
public interface TipoempresaServiceLocal extends TipoempresaServiceRemote{
	
	public Tipoempresa create(Tipoempresa oTipoempresa);

	public Tipoempresa find(Integer id);
	
	public Collection<Tipoempresa> findAll(); 

	public void delete(Tipoempresa oTipoempresa);

	public Tipoempresa update(Tipoempresa oTipoempresa);

	public Collection<Tipoempresa> findByNamedQuery(String queryName);

	public Collection<Tipoempresa> findByNamedQuery(String queryName, int resultLimit);

	public List<Tipoempresa> findByNamedQuery(String Tipoempresa, Map<String, Object> parameters);

	public List<Tipoempresa> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
