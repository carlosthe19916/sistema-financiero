package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Tarjetadebitoasignadocuentacorriente;

@Local
public interface TarjetadebitoasignadocuentacorrienteFacadeLocal {
	
	public Tarjetadebitoasignadocuentacorriente create(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Tarjetadebitoasignadocuentacorriente find(Integer id);

	public void delete(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Tarjetadebitoasignadocuentacorriente update(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente);

	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName);

	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName, int resultLimit);

	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String Tarjetadebitoasignadocuentacorriente, Map<String, Object> parameters);

	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
