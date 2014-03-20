package org.ventura.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.dao.impl.AgenciaDAO;
import org.ventura.dao.impl.CajaDAO;
import org.ventura.dao.impl.SucursalDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.entity.schema.sucursal.Sucursal;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;

@Named
@Stateless
@Local(SucursalServiceLocal.class)
@Remote(SucursalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SucursalServiceBean implements SucursalServiceLocal {

	@Inject
	private Log log;
	
	private @EJB SucursalDAO sucursalDAO;
	private @EJB AgenciaDAO agenciaDAO;
	private @EJB CajaDAO cajaDAO;
	
	@Override
	public void create(Sucursal sucursal) throws Exception {
		try {
			List<Agencia> listAgenciasView = sucursal.getAgencias();
			
			sucursal.setEstado(true);
			sucursalDAO.create(sucursal);
		
			for (Agencia agencia : listAgenciasView) {
				agencia.setEstado(true);
				agencia.setSucursal(sucursal);
				agenciaDAO.create(agencia);
			}

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void update(Sucursal sucursal) throws Exception {
		try {			
			Sucursal sucursalDB = sucursalDAO.find(sucursal.getIdsucursal());
			List<Agencia> listAgenciasView = sucursal.getAgencias();
			List<Agencia> listAgenciasDB = sucursalDB.getAgencias();
			
			
			Map<Integer, Agencia> mapFromView = new HashMap<Integer, Agencia>();
			for (Agencia agencia : listAgenciasView) {				
				mapFromView.put(agencia.getIdagencia(), agencia);
			}
			
			Map<Integer, Agencia> mapFromDB = new HashMap<Integer, Agencia>();
			for (Agencia agencia : listAgenciasDB) {
				mapFromDB.put(agencia.getIdagencia(), agencia);
			}
			
			Set<Integer> union = new HashSet<Integer>(mapFromView.keySet());
			union.addAll(mapFromDB.keySet());
			
			Set<Integer> intersection = new HashSet<Integer>(mapFromView.keySet());
			intersection.retainAll(mapFromDB.keySet());
			
			Set<Integer> restDelete = new HashSet<Integer>(union);
			restDelete.removeAll(mapFromView.keySet());
				
			for (Integer key : restDelete) {
				Agencia agencia = mapFromDB.get(key);
				agenciaDAO.delete(agencia);
			}
			
			for (Integer key : intersection) {
				Agencia agencia = mapFromView.get(key);
				agenciaDAO.update(agencia);
			}
			
			for (Agencia agencia : listAgenciasView) {
				if(agencia.getIdagencia() == null){
					agencia.setEstado(true);
					agencia.setSucursal(sucursalDB);
					agenciaDAO.create(agencia);
				}	
			}
			
			sucursalDAO.update(sucursal);			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}
	@Override
	public List<Sucursal> getAllActive() throws Exception {
		List<Sucursal> list;
		try {
			list = sucursalDAO.findByNamedQuery(Sucursal.f_allActive);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public Sucursal find(Object idsucursal) throws Exception {
		Sucursal sucursal;
		try {
			sucursal = sucursalDAO.find(idsucursal);
			List<Agencia> listAgencias = new ArrayList<Agencia>();			
			for (Agencia agencia : sucursal.getAgencias()) {
				listAgencias.add(agencia); 
			}
			sucursal.setAgencias(listAgencias);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return sucursal;
	}

	@Override
	public List<Agencia> getAllAgenciasActive() throws Exception {
		List<Agencia> list;
		try {
			list = agenciaDAO.findByNamedQuery(Agencia.f_allActive);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Caja> getCajas(Agencia agencia) throws Exception {
		List<Caja> list;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agencia.getIdagencia());
			parameters.put("idestadoapertura", ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO).getIdestadoapertura());
			list = cajaDAO.findByNamedQuery(Caja.f_idagencia_idestadoapertura,parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

}
