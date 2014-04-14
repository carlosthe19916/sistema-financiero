package org.ventura.control;

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

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.boundary.remote.TrabajadorServiceRemote;
import org.ventura.dao.impl.TrabajadorDAO;
import org.ventura.dao.impl.UsuarioDAO;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(TrabajadorServiceLocal.class)
@Remote(TrabajadorServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorServiceBean implements TrabajadorServiceLocal {

	@Inject
	private Log log;

	@EJB private TrabajadorDAO trabajadorDAO;
	@EJB private UsuarioDAO usuarioDAO;
	
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@Override
	public List<Trabajador> getTrabajadores(Agencia agencia) throws Exception {
		List<Trabajador> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agencia.getIdagencia());
			list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Trabajador> find(Agencia agencia,Tipodocumento tipodocumento, String valorBusqueda) throws Exception {
		List<Trabajador> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(agencia == null){
				if(tipodocumento == null){
					parameters.put("searched", "%" + valorBusqueda.toUpperCase() + "%");
					list = trabajadorDAO.findByNamedQuery(Trabajador.f_searched, parameters,10);
				} else {
					parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
					parameters.put("numerodocumento", valorBusqueda);
					list = trabajadorDAO.findByNamedQuery(Trabajador.f_idtipodocumento_numerodocumento, parameters,10);
				}
			} else {
				if(tipodocumento == null){
					parameters.put("idagencia", agencia.getIdagencia());
					parameters.put("searched", "%" + valorBusqueda.toUpperCase() + "%");
					list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia_searched, parameters,10);
				} else {
					parameters.put("idagencia", agencia.getIdagencia());
					parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
					parameters.put("numerodocumento", valorBusqueda);
					list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia_idtipodocumento_numerodocumento, parameters,10);
				}	
			}
				
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public void create(Trabajador trabajador) throws Exception {		
		try {
			Personanatural personanatural = trabajador.getPersonanatural();			
			Personanatural personanaturalDB = personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			trabajador.setPersonanatural(personanaturalDB);
			trabajadorDAO.create(trabajador);	
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void update(Trabajador trabajador) throws Exception {
		try {
			Personanatural personanatural = trabajador.getPersonanatural();			
			Personanatural personanaturalDB = personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			trabajador.setPersonanatural(personanaturalDB);
			trabajadorDAO.update(trabajador);	
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void delete(Trabajador trabajador) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtrabajador", trabajador.getIdtrabajador());
			List<Usuario> list = usuarioDAO.findByNamedQuery(Usuario.f_idtrabajador, parameters);
			if(list.size() != 0){
				throw new Exception("Primero elimine al usuario antes de eliminar al trabajador");
			}
			Trabajador trabajadorDB = trabajadorDAO.find(trabajador.getIdtrabajador());
			trabajadorDB.setEstado(false);
			trabajadorDAO.update(trabajadorDB);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public Trabajador find(Object id) throws Exception {
		Trabajador trabajador;
		try {
			trabajador = trabajadorDAO.find(id);	
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return trabajador;
	}

	
}
