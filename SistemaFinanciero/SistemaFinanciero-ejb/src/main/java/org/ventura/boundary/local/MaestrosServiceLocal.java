package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.maestro.Ubigeo;
import org.ventura.entity.schema.maestro.VariableSistema;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.maestro.VariableSistemaType;

@Local
public interface MaestrosServiceLocal extends LoginServiceRemote {

	public List<Tipodocumento> getTipodocumentoForPersonaNatural() throws Exception;
	
	public List<Tipodocumento> getTipodocumentoForPersonaJuridica() throws Exception;
	
	public Tipomoneda find(Object id) throws Exception;
	
	public List<Ubigeo> getUbigeos() throws Exception;
	
	public List<Ubigeo> getDepartamentos() throws Exception;
	
	public List<Ubigeo> getProvincias(Ubigeo ubigeo) throws Exception;
	
	public List<Ubigeo> getDistritos(Ubigeo ubigeo) throws Exception;
	
	public VariableSistema getVariableSistema(VariableSistemaType variableSistemaType) throws Exception;
	
}
