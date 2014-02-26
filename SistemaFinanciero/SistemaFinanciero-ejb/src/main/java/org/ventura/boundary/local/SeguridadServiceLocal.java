package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.SeguridadServiceRemote;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;

@Local
public interface SeguridadServiceLocal extends SeguridadServiceRemote{
	
	public List<Rol> getRoles() throws Exception;
	
	public List<Usuario> getUsuariosFromAgencia(Agencia agencia) throws Exception;
	
	public List<Usuario> getUsuariosFromRol(Rol rol, Agencia agencia) throws Exception;
	
	public List<Grupo> getGrupos() throws Exception;
	
	public List<Usuario> getUsuariosFromGrupo(Grupo rol, Agencia agencia) throws Exception;
	
}
