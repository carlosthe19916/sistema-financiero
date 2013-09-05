package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Retirointere;

@Local
public interface RetirointeresFacadeLocal {
	
	public Retirointere create(Retirointere oRetirointere);

	public Retirointere find(Integer id);

	public void delete(Retirointere oRetirointere);

	public Retirointere update(Retirointere oRetirointere);

	public Collection<Retirointere> findByNamedQuery(String queryName);

	public Collection<Retirointere> findByNamedQuery(String queryName, int resultLimit);

	public List<Retirointere> findByNamedQuery(String Retirointere, Map<String, Object> parameters);

	public List<Retirointere> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
