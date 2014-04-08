package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.entity.schema.sucursal.Sucursal;

@Local
public interface SucursalServiceLocal extends SucursalServiceRemote {

	public void create(Sucursal sucursal) throws Exception;
	
	public void update(Sucursal sucursal) throws Exception;
	
	public List<Sucursal> getAllActive() throws Exception;

	public Sucursal find(Object idsucursal) throws Exception;
	
	public List<Agencia> getAllAgenciasActive() throws Exception;

	public List<Caja> getCajas(Agencia agencia) throws Exception;

	public Agencia findAgencia(Integer idAgencia);

}
