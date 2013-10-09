package org.ventura.boundary.remote;

import java.util.Collection;

import javax.ejb.Remote;

import org.ventura.entity.Menu;
import org.ventura.entity.Modulo;
import org.ventura.entity.Rol;
import org.ventura.entity.Usuario;

@Remote
public interface LoginServiceRemote {
	
	public Usuario login(Usuario usuario);
	
	public Collection<Rol> getRoles(Usuario usuario);
	
	public Collection<Modulo> getModule(Usuario usuario);
	
	public Collection<Menu> getMenu(Usuario usuario);
	

}
