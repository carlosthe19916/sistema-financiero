package org.ventura.control;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ventura.boundary.local.ViewSocioServiceLocal;
import org.ventura.boundary.remote.ViewSocioServiceRemote;
import org.ventura.dao.impl.ViewSocioDAO;
import org.ventura.entity.ViewSocioPN;
import org.ventura.util.logger.Log;

@Stateless
@Local(ViewSocioServiceLocal.class)
@Remote(ViewSocioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ViewSocioServiceBean implements ViewSocioServiceLocal {

	@Inject
	private Log log;

	@EJB
	private ViewSocioDAO viewSocioDAO;

	@Override
	public List<ViewSocioPN> findByNamedQuery(String viewSocioPN, Map<String, Object> parameters) throws Exception {
		List<ViewSocioPN> list = null;
		try {
			list = viewSocioDAO.findByNamedQuery(viewSocioPN, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<ViewSocioPN> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<ViewSocioPN> list = null;
		try {
			list = viewSocioDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}
}
