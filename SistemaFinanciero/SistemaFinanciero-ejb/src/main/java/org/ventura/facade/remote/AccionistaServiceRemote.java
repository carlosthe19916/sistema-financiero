package org.ventura.facade.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import org.ventura.model.Accionista;

@Remote
public interface AccionistaServiceRemote {
	
	public Accionista create(Accionista oAccionista);

	public Accionista find(Integer id);

	public void delete(Accionista oAccionista);

	public Accionista update(Accionista oAccionista);

	public Collection<Accionista> findByNamedQuery(String queryName);

	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit);

	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters);

	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
