package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Titularcuenta;

@Remote
public interface TitularcuentaServiceRemote {
	
	public Titularcuenta create(Titularcuenta oTitularcuenta);

	public Titularcuenta find(Object id);

	public void delete(Titularcuenta oTitularcuenta);

	public Titularcuenta update(Titularcuenta oTitularcuenta);

	public Collection<Titularcuenta> findByNamedQuery(String queryName);

	public Collection<Titularcuenta> findByNamedQuery(String queryName, int resultLimit);

	public List<Titularcuenta> findByNamedQuery(String Titularcuenta, Map<String, Object> parameters);

	public List<Titularcuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
