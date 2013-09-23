package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Personajuridica;

@Remote
public interface PersonajuridicaServiceRemote {
	
	public Personajuridica create(Personajuridica oPersonajuridica);

	public Personajuridica find(Integer id);

	public void delete(Personajuridica oPersonajuridica);

	public Personajuridica update(Personajuridica oPersonajuridica);

	public Collection<Personajuridica> findByNamedQuery(String queryName);

	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit);

	public List<Personajuridica> findByNamedQuery(String Personajuridica, Map<String, Object> parameters);

	public List<Personajuridica> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}