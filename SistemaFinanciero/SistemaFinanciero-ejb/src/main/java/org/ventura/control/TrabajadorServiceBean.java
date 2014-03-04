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

import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.boundary.remote.TrabajadorServiceRemote;
import org.ventura.dao.impl.TrabajadorDAO;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
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
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	
}
