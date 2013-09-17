package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Cuentaahorro;

@Remote
public interface CuentaahorroServiceRemote {
	
	public Cuentaahorro create(Cuentaahorro oCuentaahorro);

	public Cuentaahorro find(Integer id);

	public void delete(Cuentaahorro oCuentaahorro);

	public Cuentaahorro update(Cuentaahorro oCuentaahorro);

	public Collection<Cuentaahorro> findByNamedQuery(String queryName);

	public Collection<Cuentaahorro> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro, Map<String, Object> parameters);

	public List<Cuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
