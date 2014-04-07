package org.ventura.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import javax.inject.Inject;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.remote.MaestrosServiceRemote;
import org.ventura.dao.CrudService;
import org.ventura.dao.impl.TipomonedaDAO;
import org.ventura.dao.impl.UbigeoDAO;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.maestro.Ubigeo;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.logger.Log;

@Stateless
@Local(MaestrosServiceLocal.class)
@Remote(MaestrosServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MaestrosServiceBean implements MaestrosServiceLocal {

	@Inject
	private Log log;
	
	@EJB
	private TipomonedaDAO tipomonedaDAO;
	@EJB
	private CrudService crudService;
	@EJB
	private UbigeoDAO ubigeoDAO;
	
	@Override
	public List<Tipodocumento> getTipodocumentoForPersonaNatural() throws Exception {
		List<Tipodocumento> list = null;
		try{
			list = crudService.findWithNamedQuery(Tipodocumento.AllForPersonaNatural);
		} catch(Exception e){
			
		}
		return list;
	}

	@Override
	public List<Tipodocumento> getTipodocumentoForPersonaJuridica()
			throws Exception {
		List<Tipodocumento> list = null;
		try{
			list = crudService.findWithNamedQuery(Tipodocumento.AllForPersonaJuridica);
		} catch(Exception e){
			
		}
		return list;
	}

	@Override
	public Tipomoneda find(Object id) throws Exception {
		Tipomoneda tipomoneda = null;
		try {
			tipomoneda = tipomonedaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return tipomoneda;
	}

	@Override
	public List<Ubigeo> getUbigeos() throws Exception {
		List<Ubigeo> list = null;
		try {
			list = ubigeoDAO.findAll();
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Ubigeo> getDepartamentos() throws Exception {
		List<Ubigeo> list = null;
		try {
			list = ubigeoDAO.findByNamedQuery(Ubigeo.f_departamentos);
			HashMap<String, Ubigeo> mapDepartamentos = new HashMap<String, Ubigeo>();
			for (Ubigeo ubigeo : list) {
				String idDepartamento = ubigeo.getIdubigeo().substring(0, 2);
				mapDepartamentos.put(idDepartamento, ubigeo);
			}	
			list = new ArrayList<Ubigeo>(mapDepartamentos.values());
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Ubigeo> getProvincias(Ubigeo ubigeo) throws Exception {
		List<Ubigeo> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("iddepartamento", ubigeo.getIdubigeo().substring(0, 2));
			list = ubigeoDAO.findByNamedQuery(Ubigeo.f_provincias,parameters);
			HashMap<String, Ubigeo> mapProvincias = new HashMap<String, Ubigeo>();
			for (Ubigeo u : list) {
				String idProvincia = u.getIdubigeo().substring(2, 4);
				mapProvincias.put(idProvincia, u);
			}	
			list = new ArrayList<Ubigeo>(mapProvincias.values());
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Ubigeo> getDistritos(Ubigeo ubigeo) throws Exception {
		List<Ubigeo> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idprovincia", ubigeo.getIdubigeo().substring(0, 4));
			list = ubigeoDAO.findByNamedQuery(Ubigeo.f_distritos,parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
}
