package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Titularcuentahistorial;

@Remote
public interface TitularcuentahistorialServiceRemote {
	
	public Titularcuentahistorial create(Titularcuentahistorial oTitularcuentahistorial);

	public Titularcuentahistorial find(Integer id);

	public void delete(Titularcuentahistorial oTitularcuentahistorial);

	public Titularcuentahistorial update(Titularcuentahistorial oTitularcuentahistorial);

	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName);

	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName, int resultLimit);

	public List<Titularcuentahistorial> findByNamedQuery(String Titularcuentahistorial, Map<String, Object> parameters);

	public List<Titularcuentahistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
