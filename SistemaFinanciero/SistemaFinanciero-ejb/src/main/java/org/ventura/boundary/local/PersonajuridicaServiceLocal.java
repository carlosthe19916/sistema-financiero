package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.PersonajuridicaServiceRemote;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Tipodocumento;

@Local
public interface PersonajuridicaServiceLocal extends PersonajuridicaServiceRemote {

	public Personajuridica create(Personajuridica personajuridica) throws Exception;

	public Personajuridica createIfNotExistsUpdateIfExist(Personajuridica personajuridica) throws Exception;
	
	public Personajuridica createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(Personajuridica personajuridica) throws Exception;
	
	public Personajuridica find(Object id) throws Exception;

	public Personajuridica find(Tipodocumento tipodocumento, String numerodocumento) throws Exception;
	
	public void delete(Personajuridica personajuridica) throws Exception;

	public void update(Personajuridica personajuridica) throws Exception;
	
	public Collection<Personajuridica> findByNamedQuery(String queryName) throws Exception;

	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Personajuridica> findByNamedQuery(String Personajuridica, Map<String, Object> parameters) throws Exception;

	public List<Personajuridica> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
