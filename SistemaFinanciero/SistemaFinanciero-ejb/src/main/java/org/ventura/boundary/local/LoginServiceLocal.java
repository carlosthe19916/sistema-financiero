package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.seguridad.Menu;
import org.ventura.entity.schema.seguridad.Modulo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;

@Local
public interface LoginServiceLocal extends LoginServiceRemote {

	public Collection<Rol> findRolByNamedQuery(String queryName);

	public Collection<Menu> findMenuByNamedQuery(String queryName);

	public Usuario findUserByNamedQuery(Usuario usuario);
	
	public Collection<Menu> getMenu(Usuario usuario) throws Exception;

	public Collection<Modulo> getModule(Usuario usuario) throws Exception;
	
	public List<Rol> getRoles(Usuario usuario) throws Exception;
	
	public List<Caja> getCajas(Usuario usuario) throws Exception;
}
