package org.ventura.control;

import java.util.Collection;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.Menu;
import org.ventura.entity.Modulo;
import org.ventura.entity.Rol;
import org.ventura.entity.Usuario;

public class LoginServiceBean implements LoginServiceLocal{

	@Override
	public Usuario login(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Rol> getRoles(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Modulo> getModule(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Menu> getMenu(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Rol> findRolByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Menu> findMenuByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

}
