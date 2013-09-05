package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Chequera;

@Local
public interface ChequeraFacadeLocal {
	
	public Chequera create(Chequera oChequera);

	public Chequera find(Integer id);

	public void delete(Chequera oChequera);

	public Chequera update(Chequera oChequera);

	public Collection<Chequera> findByNamedQuery(String queryName);

	public Collection<Chequera> findByNamedQuery(String queryName, int resultLimit);

	public List<Chequera> findByNamedQuery(String Chequera, Map<String, Object> parameters);

	public List<Chequera> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
