package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Titularcuentahistorial;

@Remote
public interface TitularcuentahistorialFacadeLocal {
	
	public Titularcuentahistorial create(Titularcuentahistorial oTitularcuentahistorial);

	public Titularcuentahistorial find(Integer id);

	public void delete(Titularcuentahistorial oTitularcuentahistorial);

	public Titularcuentahistorial update(Titularcuentahistorial oTitularcuentahistorial);

	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName);

	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName, int resultLimit);

	public List<Titularcuentahistorial> findByNamedQuery(String Titularcuentahistorial, Map<String, Object> parameters);

	public List<Titularcuentahistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
