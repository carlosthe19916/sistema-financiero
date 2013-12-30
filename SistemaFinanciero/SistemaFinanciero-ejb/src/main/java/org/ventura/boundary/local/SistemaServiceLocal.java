package org.ventura.boundary.local;

import java.util.Date;

import javax.ejb.Local;

import org.ventura.boundary.remote.BovedaServiceRemote;

@Local
public interface SistemaServiceLocal extends BovedaServiceRemote{
	
	public void closeSistema(Date fecha) throws Exception;
}
