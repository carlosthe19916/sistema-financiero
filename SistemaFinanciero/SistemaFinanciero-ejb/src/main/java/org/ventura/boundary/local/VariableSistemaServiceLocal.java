package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.VariableSistemaServiceRemote;
import org.ventura.entity.schema.maestro.VariableSistema;
import org.ventura.util.maestro.VariableSistemaType;

@Local
public interface VariableSistemaServiceLocal extends VariableSistemaServiceRemote{
	
	public VariableSistema getVariableSistema(VariableSistemaType variableSistemaType) throws Exception;
	
	public void updateVariableSistema(VariableSistema variableSistema) throws Exception;
	
}
