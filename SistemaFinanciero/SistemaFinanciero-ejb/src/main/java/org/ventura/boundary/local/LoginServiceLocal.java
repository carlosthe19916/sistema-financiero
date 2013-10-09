package org.ventura.boundary.local;

import java.util.Collection;

import javax.ejb.Local;

import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.entity.Menu;
import org.ventura.entity.Rol;

@Local
public interface LoginServiceLocal extends LoginServiceRemote {

	public Collection<Rol> findRolByNamedQuery(String queryName);

	public Collection<Menu> findMenuByNamedQuery(String queryName);

}
