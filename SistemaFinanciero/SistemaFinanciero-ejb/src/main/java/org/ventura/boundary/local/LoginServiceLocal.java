package org.ventura.boundary.local;

import java.util.Collection;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.Menu;
import org.ventura.entity.Modulo;
import org.ventura.entity.Rol;
import org.ventura.entity.Usuario;

@Local
public interface LoginServiceLocal extends LoginServiceRemote {

	public Collection<Rol> findRolByNamedQuery(String queryName);

	public Collection<Menu> findMenuByNamedQuery(String queryName);

	public Usuario findUserByNamedQuery(Usuario usuario);
	
	public Collection<Menu> getMenu(Usuario usuario);

	Collection<Modulo> getModule(Usuario usuario);
}
