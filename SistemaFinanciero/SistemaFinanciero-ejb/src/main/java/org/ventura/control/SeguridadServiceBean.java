package org.ventura.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.boundary.remote.SeguridadServiceRemote;
import org.ventura.dao.impl.GrupoDAO;
import org.ventura.dao.impl.RolDAO;
import org.ventura.dao.impl.UsuarioDAO;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(SeguridadServiceLocal.class)
@Remote(SeguridadServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SeguridadServiceBean implements SeguridadServiceLocal {

	@Inject
	private Log log;

	@EJB private RolDAO rolDAO;
	@EJB private GrupoDAO grupoDAO;
	@EJB private UsuarioDAO usuarioDAO;
	
	@Override
	public List<Rol> getRoles() throws Exception {
		List<Rol> list;
		try {		
			list = rolDAO.findAll();
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Rol> getRoles(Usuario usuario) throws Exception {
		List<Rol> list;
		try {		
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idusuario", usuario.getIdusuario());
			list = rolDAO.findByNamedQuery(Rol.f_idusuario, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Rol> getRolsFromGrupo(Grupo grupo) throws Exception {
		List<Rol> list;
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idgrupo", grupo.getIdgrupo());			
			list = rolDAO.findByNamedQuery(Rol.f_idgrupo,parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
	
	@Override
	public Usuario findUsuario(Object idusuario) throws Exception {
		Usuario usuario = null;
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idusuario", idusuario);
			
			List<Usuario> list = usuarioDAO.findByNamedQuery(Usuario.FIND_BYID, parameters);
			
			usuario = list.get(0);
			
			List<Grupo> gruposADD = new ArrayList<Grupo>();
			List<Grupo> grupos = usuario.getGrupos();
			for (Grupo grupo : grupos) {
				gruposADD.add(grupo);
			}
			
			usuario.setGrupos(gruposADD);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return usuario;
	} 
	
	@Override
	public List<Usuario> getUsuariosFromAgencia(Agencia agencia) throws Exception {
		List<Usuario> list;
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agencia.getIdagencia());
			
			list = usuarioDAO.findByNamedQuery(Usuario.f_idagencia,parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	
	@Override
	public List<Usuario> getUsuariosFromRol(Rol rol, Agencia agencia) throws Exception {
		List<Usuario> list;
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idrol", rol.getIdrol());
			parameters.put("idagencia", agencia.getIdagencia());
			
			list = usuarioDAO.findByNamedQuery(Usuario.f_idrol_idagencia,parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Grupo> getGrupos() throws Exception {
		List<Grupo> list;
		try {		
			list = grupoDAO.findByNamedQuery(Grupo.all_active);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Grupo> getGrupos(Usuario usuario) throws Exception {
		List<Grupo> list;
		try {		
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idusuario", usuario.getIdusuario());
			list = grupoDAO.findByNamedQuery(Grupo.f_idusuario, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Usuario> getUsuariosFromGrupo(Grupo grupo, Agencia agencia) throws Exception {
		List<Usuario> list;
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idgrupo", grupo.getIdgrupo());
			parameters.put("idagencia", agencia.getIdagencia());

			list = usuarioDAO.findByNamedQuery(Usuario.f_idgrupo_idagencia,parameters);
				
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public void asignarUsuarios(Grupo grupo, List<Usuario> listUsuarios) throws Exception{	
		try {	
			Grupo grupoDB = grupoDAO.find(grupo.getIdgrupo());
			List<Usuario> listUsuariosDB = grupoDB.getUsuarios();
			
			List<Usuario> listUsuariosNuevos = new ArrayList<Usuario>(listUsuarios);
			listUsuariosNuevos.removeAll(listUsuariosDB);
			
			List<Usuario> listUsuariosEliminar = new ArrayList<Usuario>(listUsuariosDB);
			listUsuariosEliminar.removeAll(listUsuarios);
			
			for (Usuario usuario : listUsuariosNuevos) {
				grupoDB.getUsuarios().add(usuario);
			}
			for (Usuario usuario : listUsuariosEliminar) {
				grupoDB.getUsuarios().remove(usuario);
			}
			grupoDAO.update(grupoDB);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void asignarGrupos(Usuario usuario, List<Grupo> listGrupos) throws Exception {
		try {	
			Usuario usuarioDB = usuarioDAO.find(usuario.getIdusuario());
			List<Grupo> listGrupoDB = usuarioDB.getGrupos();
			
			List<Grupo> listGruposNuevos = new ArrayList<Grupo>(listGrupos);
			listGruposNuevos.removeAll(listGrupoDB);
			
			List<Grupo> listGruposEliminar = new ArrayList<Grupo>(listGrupoDB);
			listGruposEliminar.removeAll(listGrupos);
			
			for (Grupo grupo : listGruposNuevos) {
				usuarioDB.getGrupos().add(grupo);
			}
			for (Grupo grupo : listGruposEliminar) {
				usuarioDB.getGrupos().remove(grupo);
			}
			usuarioDAO.update(usuarioDB);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void create(Usuario usuario) throws Exception{
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", usuario.getUsername());
			
			List<Usuario> listDB = usuarioDAO.findByNamedQuery(Usuario.find_byusername_active, parameters);
			if(listDB.size() != 0){
				throw new Exception("el nombre de usuario ya existe");
			}
			usuario.setEstado(true);
			usuarioDAO.create(usuario);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void update(Usuario usuario) throws Exception {
		try {	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", usuario.getUsername());
			
			List<Usuario> listDB = usuarioDAO.findByNamedQuery(Usuario.find_byusername_active, parameters);
			if(listDB.size() != 0){
				if(listDB.size() == 1){
					if(!listDB.get(0).equals(usuario)){
						throw new Exception("el nombre de usuario ya existe");
					}
				} else {
					throw new Exception("existe mas de 1 usuario con el username");
				}			
			}
			
			usuarioDAO.update(usuario);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public List<Usuario> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		List<Usuario> list = null;
		try {
			list = usuarioDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, intÃ©ntelo nuevamente");
		}
		return list;
	}
	
}
