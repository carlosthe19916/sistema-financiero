package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.TarjetadebitoasignadocuentaahorroServiceRemote;
import org.ventura.entity.Tarjetadebitoasignadocuentaahorro;

@Local
public interface TarjetadebitoasignadocuentaahorroServiceLocal extends TarjetadebitoasignadocuentaahorroServiceRemote{
	
	public Tarjetadebitoasignadocuentaahorro create(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Tarjetadebitoasignadocuentaahorro find(Integer id);

	public void delete(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Tarjetadebitoasignadocuentaahorro update(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro);

	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName);

	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName, int resultLimit);

	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String Tarjetadebitoasignadocuentaahorro, Map<String, Object> parameters);

	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
