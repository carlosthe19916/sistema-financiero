package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.PersonajuridicaclienteServiceRemote;
import org.ventura.entity.Personajuridicacliente;

@Local
public interface PersonajuridicaclienteServiceLocal extends PersonajuridicaclienteServiceRemote{
	
	public void create(Personajuridicacliente oPersonajuridicacliente)throws Exception;

	public Personajuridicacliente find(Object id)throws Exception;

	public void delete(Personajuridicacliente oPersonajuridicacliente)throws Exception;

	public void update(Personajuridicacliente oPersonajuridicacliente)throws Exception;

	public Collection<Personajuridicacliente> findByNamedQuery(String queryName)throws Exception;

	public Collection<Personajuridicacliente> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Personajuridicacliente> findByNamedQuery(String Personajuridicacliente, Map<String, Object> parameters)throws Exception;

	public List<Personajuridicacliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
