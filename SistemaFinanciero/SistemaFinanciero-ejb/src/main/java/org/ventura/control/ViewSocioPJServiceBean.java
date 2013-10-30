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

import org.ventura.boundary.local.ViewSocioPJServiceLocal;
import org.ventura.boundary.local.ViewSocioPNServiceLocal;
import org.ventura.boundary.remote.ViewSocioPJServiceRemote;
import org.ventura.boundary.remote.ViewSocioPNServiceRemote;
import org.ventura.dao.impl.ViewSocioPJDAO;
import org.ventura.dao.impl.ViewSocioPNDAO;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;
import org.ventura.util.logger.Log;

@Stateless
@Local(ViewSocioPJServiceLocal.class)
@Remote(ViewSocioPJServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ViewSocioPJServiceBean implements ViewSocioPJServiceLocal{

	@Inject
	private Log log;

	@EJB
	private ViewSocioPJDAO viewSocioPJDAO;

	@Override
	public List<ViewSocioPJ> findByNamedQuery(String viewSocioPJ, Map<String, Object> parameters) throws Exception {
		List<ViewSocioPJ> list = null;
		try {
			list = viewSocioPJDAO.findByNamedQuery(viewSocioPJ, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<ViewSocioPJ> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<ViewSocioPJ> list = null;
		try {
			list = viewSocioPJDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}
}
