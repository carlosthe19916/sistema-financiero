package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;

@Local
public interface SocioServiceLocal extends SocioServiceRemote{
	
	public Socio createSocioPersonanatural(Socio socio) throws Exception;
	
	public Socio createSocioPersonajuridica(Socio socio) throws Exception;
	
	public Socio find(Object id) throws Exception;
	
	public Socio find(Personanatural personanatural) throws Exception;
	
	public Socio find(Personajuridica personajuridica) throws Exception;

	public void delete(Socio socio) throws Exception;

	public void update(Socio socio) throws Exception;

	public Collection<Socio> findByNamedQuery(String queryName) throws Exception;

	public Collection<Socio> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Socio> findByNamedQuery(String Socio, Map<String, Object> parameters) throws Exception;
	
	public List<ViewSocioPN> findByNamedQueryViewSocioPN(String ViewSocioPN, Map<String, Object> parameters) throws Exception;
	
	public List<ViewSocioPJ> findByNamedQueryViewSocioPJ(String ViewSocioPN, Map<String, Object> parameters) throws Exception;
	
	public List<Socio> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;

}
