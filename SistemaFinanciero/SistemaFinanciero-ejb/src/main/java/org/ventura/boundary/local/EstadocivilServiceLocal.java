package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.EstadocivilServiceRemote;
import org.ventura.entity.Estadocivil;

@Local
public interface EstadocivilServiceLocal extends EstadocivilServiceRemote {
	
	public Estadocivil create(Estadocivil oEstadocivil);

	public Estadocivil find(Integer id);
	
	public Collection<Estadocivil> findAll();

	public void delete(Estadocivil oEstadocivil);

	public Estadocivil update(Estadocivil oEstadocivil);

	public Collection<Estadocivil> findByNamedQuery(String queryName);

	public Collection<Estadocivil> findByNamedQuery(String queryName, int resultLimit);

	public List<Estadocivil> findByNamedQuery(String Estadocivil, Map<String, Object> parameters);

	public List<Estadocivil> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
