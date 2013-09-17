package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.SexoServiceRemote;
import org.ventura.entity.Sexo;

@Local
public interface SexoServiceLocal extends SexoServiceRemote{
	
	public Sexo create(Sexo oSexo);

	public Sexo find(Integer id);
	
	public Collection<Sexo> findAll();

	public void delete(Sexo oSexo);

	public Sexo update(Sexo oSexo);

	public Collection<Sexo> findByNamedQuery(String queryName);

	public Collection<Sexo> findByNamedQuery(String queryName, int resultLimit);

	public List<Sexo> findByNamedQuery(String Sexo, Map<String, Object> parameters);

	public List<Sexo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
