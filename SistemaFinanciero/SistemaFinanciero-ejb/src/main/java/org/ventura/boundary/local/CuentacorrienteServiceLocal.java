package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentacorrienteServiceRemote;
import org.ventura.entity.Cuentacorriente;

@Local
public interface CuentacorrienteServiceLocal extends CuentacorrienteServiceRemote{
	
	public void create(Cuentacorriente oCuentacorriente)throws Exception;

	public Cuentacorriente find(Object id)throws Exception;

	public void delete(Cuentacorriente oCuentacorriente)throws Exception;

	public void update(Cuentacorriente oCuentacorriente)throws Exception;

	public Collection<Cuentacorriente> findByNamedQuery(String queryName)throws Exception;

	public Collection<Cuentacorriente> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Cuentacorriente> findByNamedQuery(String Cuentacorriente, Map<String, Object> parameters)throws Exception;

	public List<Cuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
