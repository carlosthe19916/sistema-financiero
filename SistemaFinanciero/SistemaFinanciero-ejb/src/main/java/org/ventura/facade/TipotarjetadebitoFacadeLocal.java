package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Tipotarjetadebito;

@Local
public interface TipotarjetadebitoFacadeLocal {
	
	public Tipotarjetadebito create(Tipotarjetadebito oTipotarjetadebito);

	public Tipotarjetadebito find(Integer id);

	public void delete(Tipotarjetadebito oTipotarjetadebito);

	public Tipotarjetadebito update(Tipotarjetadebito oTipotarjetadebito);

	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName);

	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName, int resultLimit);

	public List<Tipotarjetadebito> findByNamedQuery(String Tipotarjetadebito, Map<String, Object> parameters);

	public List<Tipotarjetadebito> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
