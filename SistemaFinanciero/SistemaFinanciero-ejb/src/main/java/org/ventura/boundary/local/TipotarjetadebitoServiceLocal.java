package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.TipotarjetadebitoServiceRemote;
import org.ventura.entity.Tipotarjetadebito;

@Local
public interface TipotarjetadebitoServiceLocal extends TipotarjetadebitoServiceRemote{
	
	public Tipotarjetadebito create(Tipotarjetadebito oTipotarjetadebito);

	public Tipotarjetadebito find(Integer id);

	public void delete(Tipotarjetadebito oTipotarjetadebito);

	public Tipotarjetadebito update(Tipotarjetadebito oTipotarjetadebito);

	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName);

	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName, int resultLimit);

	public List<Tipotarjetadebito> findByNamedQuery(String Tipotarjetadebito, Map<String, Object> parameters);

	public List<Tipotarjetadebito> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
