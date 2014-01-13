package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;

@Local
public interface SocioServiceLocal extends SocioServiceRemote{
	
	public Socio createSocioPersonanatural(Socio socio) throws Exception;
	
	public Socio createSocioPersonajuridica(Socio socio) throws Exception;
	
	public Socio find(Object id)throws Exception;
	
	public Socio findByDNI(Object dni)throws Exception;
	
	public Socio findByRUC(Object ruc)throws Exception;

	public void delete(Socio oSocio)throws Exception;

	public void update(Socio oSocio)throws Exception;

	public Collection<Socio> findByNamedQuery(String queryName)throws Exception;

	public Collection<Socio> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Socio> findByNamedQuery(String Socio, Map<String, Object> parameters)throws Exception;
	
	public List<ViewSocioPN> findByNamedQueryViewSocioPN(String ViewSocioPN, Map<String, Object> parameters)throws Exception;
	
	public List<ViewSocioPJ> findByNamedQueryViewSocioPJ(String ViewSocioPN, Map<String, Object> parameters)throws Exception;
	
	public List<Socio> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
