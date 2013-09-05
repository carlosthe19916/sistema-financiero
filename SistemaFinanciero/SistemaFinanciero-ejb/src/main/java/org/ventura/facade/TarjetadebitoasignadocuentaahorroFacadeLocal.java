package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Tarjetadebitoasignadocuentaahorro;

@Local
public interface TarjetadebitoasignadocuentaahorroFacadeLocal {
	
	public Tarjetadebitoasignadocuentaahorro create(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Tarjetadebitoasignadocuentaahorro find(Integer id);

	public void delete(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Tarjetadebitoasignadocuentaahorro update(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName);

	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName, int resultLimit);

	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String Tarjetadebitoasignadocuentaahorro, Map<String, Object> parameters);

	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
