package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.PersonajuridicaServiceRemote;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;

@Local
public interface PersonajuridicaServiceLocal extends PersonajuridicaServiceRemote {

	public Personajuridica create(Personajuridica oPersonajuridica)throws Exception;

	public Personajuridica find(Object id)throws Exception;

	public void delete(Personajuridica oPersonajuridica)throws Exception;

	public void update(Personajuridica oPersonajuridica)throws Exception;
	
	public void updateAccionista(Personajuridica oPersonaJuridica)throws Exception;
	
	public void deleteAccionista(String Personajuridica, Object parameters)throws Exception;

	public Collection<Personajuridica> findByNamedQuery(String queryName)throws Exception;

	public Collection<Personajuridica> findByNamedQuery(String queryName,
			int resultLimit)throws Exception;

	public List<Personajuridica> findByNamedQuery(String Personajuridica,
			Map<String, Object> parameters)throws Exception;

	public List<Personajuridica> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit)throws Exception;

}
