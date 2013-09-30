package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.PersonanaturalServiceRemote;
import org.ventura.entity.Personanatural;

@Local
public interface PersonanaturalServiceLocal extends PersonanaturalServiceRemote{
	
	public Personanatural create(Personanatural oPersonanatural);

	public Personanatural find(Object id);

	public void delete(Personanatural oPersonanatural);

	public Personanatural update(Personanatural oPersonanatural);

	public Collection<Personanatural> findByNamedQuery(String queryName);

	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit);

	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters);

	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
