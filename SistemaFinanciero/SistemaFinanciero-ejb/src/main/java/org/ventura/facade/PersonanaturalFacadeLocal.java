package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Personanatural;

@Local
public interface PersonanaturalFacadeLocal {
	
	public Personanatural create(Personanatural oPersonanatural);

	public Personanatural find(Integer id);

	public void delete(Personanatural oPersonanatural);

	public Personanatural update(Personanatural oPersonanatural);

	public Collection<Personanatural> findByNamedQuery(String queryName);

	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit);

	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters);

	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
