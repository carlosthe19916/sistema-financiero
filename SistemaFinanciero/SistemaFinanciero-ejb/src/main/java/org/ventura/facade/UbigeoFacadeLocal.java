package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Ubigeo;

@Local
public interface UbigeoFacadeLocal {
	
	public Ubigeo create(Ubigeo oUbigeo);

	public Ubigeo find(Integer id);

	public void delete(Ubigeo oUbigeo);

	public Ubigeo update(Ubigeo oUbigeo);

	public Collection<Ubigeo> findByNamedQuery(String queryName);

	public Collection<Ubigeo> findByNamedQuery(String queryName, int resultLimit);

	public List<Ubigeo> findByNamedQuery(String Ubigeo, Map<String, Object> parameters);

	public List<Ubigeo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
