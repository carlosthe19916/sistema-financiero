package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;

@Local
public interface DenominacionmonedaServiceLocal extends SocioServiceRemote{
	
	public Socio create(Socio oSocio)throws Exception;

	public Socio find(Object id)throws Exception;

	public void delete(Socio oSocio)throws Exception;

	public void update(Socio oSocio)throws Exception;

	public List<Denominacionmoneda> getDenominacionmonedasActive(Tipomoneda tipomoneda) throws Exception;

}
