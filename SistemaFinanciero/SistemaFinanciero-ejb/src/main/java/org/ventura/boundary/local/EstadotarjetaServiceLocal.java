package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.EstadotarjetaServiceRemote;
import org.ventura.entity.Estadotarjeta;

@Local
public interface EstadotarjetaServiceLocal extends EstadotarjetaServiceRemote {
	
	public Estadotarjeta create(Estadotarjeta oEstadotarjeta);

	public Estadotarjeta find(Integer id);

	public void delete(Estadotarjeta oEstadotarjeta);

	public Estadotarjeta update(Estadotarjeta oEstadotarjeta);

	public Collection<Estadotarjeta> findByNamedQuery(String queryName);

	public Collection<Estadotarjeta> findByNamedQuery(String queryName, int resultLimit);

	public List<Estadotarjeta> findByNamedQuery(String Estadotarjeta, Map<String, Object> parameters);

	public List<Estadotarjeta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
