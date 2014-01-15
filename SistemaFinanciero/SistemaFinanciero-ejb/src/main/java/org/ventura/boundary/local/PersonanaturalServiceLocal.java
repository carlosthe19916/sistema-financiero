package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.PersonanaturalServiceRemote;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;

@Local
public interface PersonanaturalServiceLocal extends PersonanaturalServiceRemote{
	
	public void create(Personanatural personanatural) throws Exception;

	public Personanatural find(Object id) throws Exception;
	
	public Personanatural find(Tipodocumento tipodocumento, String numerodocumento) throws Exception;
	
	public void update(Personanatural personanatural) throws Exception;
	
	public Personanatural createIfNotExistsUpdateIfExist(Personanatural personanatural) throws Exception;
	
	public Personanatural createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(Personanatural personanatural) throws Exception;
	
	public void delete(Personanatural personanatural) throws Exception;

	public Collection<Personanatural> findByNamedQuery(String queryName) throws Exception;

	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters) throws Exception;

	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;

}
