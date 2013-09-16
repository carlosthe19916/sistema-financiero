package org.ventura.facade.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Estadocuenta;

@Remote
public interface EstadocuentaServiceRemote {
	
	public Estadocuenta create(Estadocuenta oEstadocuenta);

	public Estadocuenta find(Integer id);

	public void delete(Estadocuenta oEstadocuenta);

	public Estadocuenta update(Estadocuenta oEstadocuenta);

	public Collection<Estadocuenta> findByNamedQuery(String queryName);

	public Collection<Estadocuenta> findByNamedQuery(String queryName, int resultLimit);

	public List<Estadocuenta> findByNamedQuery(String Estadocuenta, Map<String, Object> parameters);

	public List<Estadocuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
