package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Frecuenciacapitalizacion;

@Remote
public interface FrecuenciacapitalizacionServiceRemote {
	
	public Frecuenciacapitalizacion create(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Frecuenciacapitalizacion find(Integer id);

	public void delete(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Frecuenciacapitalizacion update(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName);

	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName, int resultLimit);

	public List<Frecuenciacapitalizacion> findByNamedQuery(String Frecuenciacapitalizacion, Map<String, Object> parameters);

	public List<Frecuenciacapitalizacion> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
