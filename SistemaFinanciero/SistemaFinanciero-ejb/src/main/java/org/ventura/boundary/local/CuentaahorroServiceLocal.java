package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.entity.Cuentaahorro;

@Local
public interface CuentaahorroServiceLocal extends CuentaahorroServiceRemote{
	
	public void create(Cuentaahorro oCuentaahorro) throws Exception;

	public Cuentaahorro find(Object id) throws Exception;

	public void delete(Cuentaahorro oCuentaahorro)throws Exception;

	public void update(Cuentaahorro oCuentaahorro)throws Exception;

	public Collection<Cuentaahorro> findByNamedQuery(String queryName)throws Exception;

	public Collection<Cuentaahorro> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro, Map<String, Object> parameters)throws Exception;

	public List<Cuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
