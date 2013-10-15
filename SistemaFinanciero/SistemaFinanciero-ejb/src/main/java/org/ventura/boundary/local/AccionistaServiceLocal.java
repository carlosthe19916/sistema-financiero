package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.AccionistaServiceRemote;
import org.ventura.entity.Accionista;

@Local
public interface AccionistaServiceLocal extends AccionistaServiceRemote {
	
	public void create(Accionista oAccionista) throws Exception;

	public Accionista find(Integer id) throws Exception;

	public void delete(Accionista oAccionista) throws Exception;

	public void update(Accionista oAccionista) throws Exception;

	public Collection<Accionista> findByNamedQuery(String queryName) throws Exception;

	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters) throws Exception;

	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;

}
