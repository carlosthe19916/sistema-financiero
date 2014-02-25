package org.ventura.control;

import java.util.Date;
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
import org.ventura.util.logger.Log;
import org.ventura.boundary.local.EstadoCuentaServiceLocal;
import org.ventura.boundary.remote.EstadoCuentaServiceRemote;
import org.ventura.dao.impl.ViewEstadoCuentaDAO;
import org.ventura.entity.schema.caja.view.EstadocuentaView;

@Stateless
@Local(EstadoCuentaServiceLocal.class)
@Remote(EstadoCuentaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EstadoCuentaServiceBean implements EstadoCuentaServiceLocal {

	@Inject
	private Log log;
	
	@EJB
	private ViewEstadoCuentaDAO viewEstadoCuentaDAO;

	@Override
	public List<EstadocuentaView> getTransaccionesEstadoCuenta(String numerocuenta, Date fechaInicio, Date fechaFin) throws Exception {
		List<EstadocuentaView> estadocuentaViews;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuenta", numerocuenta);
			parameters.put("fechaInicio", fechaInicio);
			parameters.put("fechaFin", fechaFin);

			estadocuentaViews = viewEstadoCuentaDAO.findByNamedQuery(EstadocuentaView.f_transacciones_estadocuenta, parameters);

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return estadocuentaViews;
	}
}


