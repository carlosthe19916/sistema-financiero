package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Tarjetadebitoasignadocuentacorriente;

@Remote
public interface TarjetadebitoasignadocuentacorrienteServiceRemote {
	
	public Tarjetadebitoasignadocuentacorriente create(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Tarjetadebitoasignadocuentacorriente find(Integer id);

	public void delete(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Tarjetadebitoasignadocuentacorriente update(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName);

	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName, int resultLimit);

	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String Tarjetadebitoasignadocuentacorriente, Map<String, Object> parameters);

	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
