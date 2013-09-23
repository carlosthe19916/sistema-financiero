package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.PersonanaturalclienteServiceRemote;
import org.ventura.entity.Personanaturalcliente;

@Local
public interface PersonanaturalclienteServiceLocal extends PersonanaturalclienteServiceRemote{
	
	public Personanaturalcliente create(Personanaturalcliente oPersonanaturalcliente);

	public Personanaturalcliente find(Integer id);

	public void delete(Personanaturalcliente oPersonanaturalcliente);

	public Personanaturalcliente update(Personanaturalcliente oPersonanaturalcliente);

	public Collection<Personanaturalcliente> findByNamedQuery(String queryName);

	public Collection<Personanaturalcliente> findByNamedQuery(String queryName, int resultLimit);

	public List<Personanaturalcliente> findByNamedQuery(String Personanaturalcliente, Map<String, Object> parameters);

	public List<Personanaturalcliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}