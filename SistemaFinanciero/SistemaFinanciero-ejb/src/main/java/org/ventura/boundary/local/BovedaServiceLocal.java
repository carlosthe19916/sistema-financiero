package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.entity.schema.caja.Boveda;

@Local
public interface BovedaServiceLocal extends BovedaServiceRemote{
	
	public Boveda create(Boveda oBoveda)throws Exception;

	public Boveda find(Object id)throws Exception;

	public void delete(Boveda oBoveda)throws Exception;

	public void update(Boveda oBoveda)throws Exception;

	public Collection<Boveda> findByNamedQuery(String queryName) throws Exception;

	public Collection<Boveda> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Boveda> findByNamedQuery(String Boveda, Map<String, Object> parameters) throws Exception;
	
	public List<Boveda> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;

}
