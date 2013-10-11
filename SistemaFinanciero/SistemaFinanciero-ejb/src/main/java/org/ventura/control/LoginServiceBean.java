package org.ventura.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.boundary.remote.LoginServiceRemote;
import org.ventura.dao.impl.MenuDAO;
import org.ventura.dao.impl.ModuloDAO;
import org.ventura.dao.impl.UsuarioDAO;
import org.ventura.entity.Menu;
import org.ventura.entity.Modulo;
import org.ventura.entity.Rol;
import org.ventura.entity.Usuario;

@Stateless
@Local(LoginServiceLocal.class)
@Remote(LoginServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LoginServiceBean implements LoginServiceLocal {

	@EJB
	private ModuloDAO moduloDAO;

	@EJB
	private MenuDAO menuDAO;
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
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
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", usuario.getUsername());

		return moduloDAO.findByNamedQuery(Modulo.ALL_FOR_USER, parameters);
	}

	@Override
	public Collection<Menu> getMenu(Usuario usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", usuario.getUsername());

		return menuDAO.findByNamedQuery(Menu.ALL_FOR_USER, parameters);
		
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

	@Override
	public Usuario findUserByNamedQuery(Usuario usuario) {
		
		Usuario user = null;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", usuario.getUsername());

		List<Usuario> list = usuarioDAO.findByNamedQuery(Usuario.FIND_USER,parameters);
		
		for (Iterator<Usuario> iterator = list.iterator(); iterator.hasNext();) {
			user = iterator.next();			
		}
		
		return user;
	}

}
