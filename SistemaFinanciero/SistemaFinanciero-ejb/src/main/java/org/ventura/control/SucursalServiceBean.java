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

import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.boundary.remote.SucursalServiceRemote;
import org.ventura.dao.impl.SucursalDAO;
import org.ventura.entity.schema.sucursal.Sucursal;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(SucursalServiceLocal.class)
@Remote(SucursalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SucursalServiceBean implements SucursalServiceLocal {

	@Inject
	private Log log;
	
	private @EJB SucursalDAO sucursalDAO;

	@Override
	public void create(Sucursal sucursal) throws Exception {
		try {
			sucursal.setEstado(true);
			sucursalDAO.create(sucursal);
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
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return sucursal;
	}

}
