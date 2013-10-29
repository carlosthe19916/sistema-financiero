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

import org.ventura.boundary.local.FunctionCuentasServiceLocal;
import org.ventura.boundary.remote.FuntionCuentasServiceRemote;
import org.ventura.dao.impl.FunctionCuentasDAO;
import org.ventura.entity.schema.socio.FunctionCuentas;
import org.ventura.util.logger.Log;

@Stateless
@Local(FunctionCuentasServiceLocal.class)
@Remote(FuntionCuentasServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FunctionCuentasServiceBean implements FunctionCuentasServiceLocal {

	@Inject
	private Log log;

	@EJB
	private FunctionCuentasDAO functionCuentasDAO;

	@Override
	public List<FunctionCuentas> findByNamedQuery(String functionCuentas, Map<String, Object> parameters) throws Exception {
		List<FunctionCuentas> list = null;
		try {
			list = functionCuentasDAO.findByNamedQuery(functionCuentas, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

}
