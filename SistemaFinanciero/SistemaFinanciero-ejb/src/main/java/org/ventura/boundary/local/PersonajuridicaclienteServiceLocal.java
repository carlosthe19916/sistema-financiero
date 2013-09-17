package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.PersonajuridicaclienteServiceRemote;
import org.ventura.entity.Personajuridicacliente;

@Local
public interface PersonajuridicaclienteServiceLocal extends PersonajuridicaclienteServiceRemote{
	
	public Personajuridicacliente create(Personajuridicacliente oPersonajuridicacliente);

	public Personajuridicacliente find(Integer id);

	public void delete(Personajuridicacliente oPersonajuridicacliente);

	public Personajuridicacliente update(Personajuridicacliente oPersonajuridicacliente);

	public Collection<Personajuridicacliente> findByNamedQuery(String queryName);

	public Collection<Personajuridicacliente> findByNamedQuery(String queryName, int resultLimit);

	public List<Personajuridicacliente> findByNamedQuery(String Personajuridicacliente, Map<String, Object> parameters);

	public List<Personajuridicacliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
