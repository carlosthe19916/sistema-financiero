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
import org.ventura.entity.schema.seguridad.Menu;
import org.ventura.entity.schema.seguridad.Modulo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.util.exception.RollbackFailureException;

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
	public Collection<Modulo> getModule(Usuario usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", usuario.getUsername());

		List<Modulo> result = null;
		try {
			result = moduloDAO.findByNamedQuery(Modulo.ALL_FOR_USER, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Collection<Menu> getMenu(Usuario usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", usuario.getUsername());

		List<Menu> result = null;
		
		try {
			result = menuDAO.findByNamedQuery(Menu.ALL_FOR_USER, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
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

		List<Usuario> list = null;
		try {
			list = usuarioDAO.findByNamedQuery(Usuario.FIND_USER,parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Iterator<Usuario> iterator = list.iterator(); iterator.hasNext();) {
			user = iterator.next();			
		}
		
		return user;
	}

}
