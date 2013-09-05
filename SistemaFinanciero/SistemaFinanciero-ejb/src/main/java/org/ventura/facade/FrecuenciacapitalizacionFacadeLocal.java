package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Frecuenciacapitalizacion;

@Local
public interface FrecuenciacapitalizacionFacadeLocal {
	
	public Frecuenciacapitalizacion create(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Frecuenciacapitalizacion find(Integer id);

	public void delete(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Frecuenciacapitalizacion update(Frecuenciacapitalizacion oFrecuenciacapitalizacion);

	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName);

	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName, int resultLimit);

	public List<Frecuenciacapitalizacion> findByNamedQuery(String Frecuenciacapitalizacion, Map<String, Object> parameters);

	public List<Frecuenciacapitalizacion> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
