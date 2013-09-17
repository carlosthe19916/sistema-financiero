package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.CuentaahorrohistorialServiceRemote;
import org.ventura.entity.Cuentaahorrohistorial;

@Local
public interface CuentaahorrohistorialServiceLocal extends CuentaahorrohistorialServiceRemote{
	
	public Cuentaahorrohistorial create(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Cuentaahorrohistorial find(Integer id);

	public void delete(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Cuentaahorrohistorial update(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName);

	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentaahorrohistorial> findByNamedQuery(String Cuentaahorrohistorial, Map<String, Object> parameters);

	public List<Cuentaahorrohistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
