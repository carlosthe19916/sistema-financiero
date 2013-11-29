package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;

@Local
public interface CajaServiceLocal extends CajaServiceRemote{
	
	public Caja create(Caja oCaja)throws Exception;
	
	public Caja find(Object id)throws Exception;

	public void delete(Caja oCaja)throws Exception;

	public void update(Caja oCaja)throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName) throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Caja> findByNamedQuery(String Boveda, Map<String, Object> parameters) throws Exception;
	
	public List<Caja> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
}
