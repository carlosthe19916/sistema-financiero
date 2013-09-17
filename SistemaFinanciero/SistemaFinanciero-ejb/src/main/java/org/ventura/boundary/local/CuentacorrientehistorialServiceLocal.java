package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.CuentacorrientehistorialServiceRemote;
import org.ventura.entity.Cuentacorrientehistorial;

@Local
public interface CuentacorrientehistorialServiceLocal extends CuentacorrientehistorialServiceRemote{
	
	public Cuentacorrientehistorial create(Cuentacorrientehistorial oCuentacorrientehistorial);

	public Cuentacorrientehistorial find(Integer id);

	public void delete(Cuentacorrientehistorial oCuentacorrientehistorial);

	public Cuentacorrientehistorial update(Cuentacorrientehistorial oCuentacorrientehistorial);

	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName);

	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentacorrientehistorial> findByNamedQuery(String Cuentacorrientehistorial, Map<String, Object> parameters);

	public List<Cuentacorrientehistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
