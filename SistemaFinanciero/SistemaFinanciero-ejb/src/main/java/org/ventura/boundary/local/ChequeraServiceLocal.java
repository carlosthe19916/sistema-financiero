package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.ChequeraServiceRemote;
import org.ventura.entity.Chequera;

@Local
public interface ChequeraServiceLocal extends ChequeraServiceRemote{
	
	public Chequera create(Chequera oChequera);

	public Chequera find(Integer id);

	public void delete(Chequera oChequera);

	public Chequera update(Chequera oChequera);

	public Collection<Chequera> findByNamedQuery(String queryName);

	public Collection<Chequera> findByNamedQuery(String queryName, int resultLimit);

	public List<Chequera> findByNamedQuery(String Chequera, Map<String, Object> parameters);

	public List<Chequera> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}