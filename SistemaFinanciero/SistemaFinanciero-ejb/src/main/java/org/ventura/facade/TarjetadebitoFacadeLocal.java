package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Tarjetadebito;

@Remote
public interface TarjetadebitoFacadeLocal {
	
	public Tarjetadebito create(Tarjetadebito oTarjetadebito);

	public Tarjetadebito find(Integer id);

	public void delete(Tarjetadebito oTarjetadebito);

	public Tarjetadebito update(Tarjetadebito oTarjetadebito);

	public Collection<Tarjetadebito> findByNamedQuery(String queryName);

	public Collection<Tarjetadebito> findByNamedQuery(String queryName, int resultLimit);

	public List<Tarjetadebito> findByNamedQuery(String Tarjetadebito, Map<String, Object> parameters);

	public List<Tarjetadebito> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
