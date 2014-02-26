package org.ventura.boundary.local;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.EstadoCuentaServiceRemote;
import org.ventura.entity.schema.caja.view.EstadocuentaView;

@Local
public interface EstadoCuentaServiceLocal extends EstadoCuentaServiceRemote{
	
	public List<EstadocuentaView> getTransaccionesEstadoCuenta(String numerocuenta, Date fechaInicio, Date fechaFin) throws Exception;
}
