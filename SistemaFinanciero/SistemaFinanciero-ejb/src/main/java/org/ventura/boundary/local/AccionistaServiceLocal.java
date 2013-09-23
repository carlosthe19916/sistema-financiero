package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.AccionistaServiceRemote;
import org.ventura.entity.Accionista;

@Local
public interface AccionistaServiceLocal extends AccionistaServiceRemote {
	
	public Accionista create(Accionista oAccionista);

	public Accionista find(Integer id);

	public void delete(Accionista oAccionista);

	public Accionista update(Accionista oAccionista);

	public Collection<Accionista> findByNamedQuery(String queryName);

	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit);

	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters);

	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}