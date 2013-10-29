package org.ventura.boundary.local;

import java.util.Collection;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.schema.seguridad.Menu;
import org.ventura.entity.schema.seguridad.Modulo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;

@Local
public interface LoginServiceLocal extends LoginServiceRemote {

	public Collection<Rol> findRolByNamedQuery(String queryName);

	public Collection<Menu> findMenuByNamedQuery(String queryName);

	public Usuario findUserByNamedQuery(Usuario usuario);
	
	public Collection<Menu> getMenu(Usuario usuario);

	Collection<Modulo> getModule(Usuario usuario);
}
