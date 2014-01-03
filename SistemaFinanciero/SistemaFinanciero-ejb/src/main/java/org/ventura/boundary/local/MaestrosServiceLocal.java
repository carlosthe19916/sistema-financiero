package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.schema.persona.Tipodocumento;

@Local
public interface MaestrosServiceLocal extends LoginServiceRemote {

	public List<Tipodocumento> getTipodocumentoForPersonaNatural() throws Exception;
	
	public List<Tipodocumento> getTipodocumentoForPersonaJuridica() throws Exception;
}
