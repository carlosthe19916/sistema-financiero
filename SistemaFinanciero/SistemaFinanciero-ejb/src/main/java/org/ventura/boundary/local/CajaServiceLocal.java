package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;


@Local
public interface CajaServiceLocal extends CajaServiceRemote{

	public Caja create(Caja oCaja)throws Exception;
	
	public Caja find(Object id)throws Exception;

	public void delete(Caja oCaja)throws Exception;

	public void update(Caja oCaja)throws Exception;
}
