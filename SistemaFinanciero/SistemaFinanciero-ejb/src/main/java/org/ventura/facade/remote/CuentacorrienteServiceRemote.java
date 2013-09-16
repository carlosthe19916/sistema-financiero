package org.ventura.facade.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Cuentacorriente;

@Remote
public interface CuentacorrienteServiceRemote {
	
	public Cuentacorriente create(Cuentacorriente oCuentacorriente);

	public Cuentacorriente find(Integer id);

	public void delete(Cuentacorriente oCuentacorriente);

	public Cuentacorriente update(Cuentacorriente oCuentacorriente);

	public Collection<Cuentacorriente> findByNamedQuery(String queryName);

	public Collection<Cuentacorriente> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentacorriente> findByNamedQuery(String Cuentacorriente, Map<String, Object> parameters);

	public List<Cuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
