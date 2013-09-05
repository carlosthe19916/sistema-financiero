package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Cuentaahorrohistorial;

@Local
public interface CuentaahorrohistorialFacadeLocal {
	
	public Cuentaahorrohistorial create(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Cuentaahorrohistorial find(Integer id);

	public void delete(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Cuentaahorrohistorial update(Cuentaahorrohistorial oCuentaahorrohistorial);

	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName);

	public Collection<Cuentaahorrohistorial> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentaahorrohistorial> findByNamedQuery(String Cuentaahorrohistorial, Map<String, Object> parameters);

	public List<Cuentaahorrohistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
