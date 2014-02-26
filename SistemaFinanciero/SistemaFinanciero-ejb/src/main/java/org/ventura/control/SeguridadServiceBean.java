package org.ventura.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
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
import org.ventura.entity.schema.caja.Boveda;
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
	public List<Usuario> getUsuariosFromAgencia(Agencia agencia)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
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
			list = grupoDAO.findAll();
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
	
}
