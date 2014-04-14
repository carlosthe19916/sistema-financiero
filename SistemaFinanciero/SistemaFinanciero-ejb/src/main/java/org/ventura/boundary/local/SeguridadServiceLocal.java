package org.ventura.boundary.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.SeguridadServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;

@Local
public interface SeguridadServiceLocal extends SeguridadServiceRemote{
	
	public List<Rol> getRoles() throws Exception;
	
	public List<Rol> getRoles(Usuario usuario) throws Exception;
	
	public List<Rol> getRolsFromGrupo(Grupo grupo) throws Exception;
	
	public Usuario findUsuario(Object idusuario) throws Exception;
	
	public List<Usuario> getUsuarios() throws Exception;;
	
	public List<Usuario> getUsuariosFromAgencia(Agencia agencia) throws Exception;
	
	public List<Usuario> getUsuariosFromRol(Rol rol, Agencia agencia) throws Exception;
	
	public List<Grupo> getGrupos() throws Exception;
	
	public List<Grupo> getGrupos(Usuario usuario) throws Exception;
	
	public List<Usuario> getUsuariosFromGrupo(Grupo rol, Agencia agencia) throws Exception;

	/*Transaccional*/
	public void asignarUsuarios(Grupo grupo, List<Usuario> listUsuarios) throws Exception;

	public void asignarGrupos(Usuario usuario, List<Grupo> listGrupos) throws Exception;

	public void create(Usuario usuario) throws Exception;

	public void update(Usuario usuario) throws Exception;

	public void delete(Usuario usuario);
	/*caja*/
	public List<Usuario> findByNamedQuery(String namedQueryName, Map<String, Object> parameters) throws Exception;

	

	
}
