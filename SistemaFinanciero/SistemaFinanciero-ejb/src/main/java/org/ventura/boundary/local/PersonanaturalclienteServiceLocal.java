package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.PersonanaturalclienteServiceRemote;
import org.ventura.entity.Personanaturalcliente;

@Local
public interface PersonanaturalclienteServiceLocal extends PersonanaturalclienteServiceRemote{
	
	public Personanaturalcliente create(Personanaturalcliente oPersonanaturalcliente)throws Exception;

	public Personanaturalcliente find(Object id)throws Exception;

	public void delete(Personanaturalcliente oPersonanaturalcliente)throws Exception;

	public void update(Personanaturalcliente oPersonanaturalcliente)throws Exception;

	public Collection<Personanaturalcliente> findByNamedQuery(String queryName)throws Exception;

	public Collection<Personanaturalcliente> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Personanaturalcliente> findByNamedQuery(String Personanaturalcliente, Map<String, Object> parameters)throws Exception;

	public List<Personanaturalcliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
