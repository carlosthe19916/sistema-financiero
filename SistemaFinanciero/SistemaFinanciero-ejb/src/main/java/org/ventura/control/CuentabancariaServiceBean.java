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

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.CuentabancariaViewDAO;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentabancariaServiceLocal.class)
@Remote(CuentabancariaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentabancariaServiceBean implements CuentabancariaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentabancariaDAO cuentabancariaDAO;

	@EJB
	private CuentabancariaViewDAO cuentabancariaViewDAO;

	@Override
	public Cuentabancaria create(Cuentabancaria cuentabancaria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cuentabancaria findById(Object id) throws Exception {
		Cuentabancaria cuentabancaria;
		try {
			cuentabancaria = cuentabancariaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public Cuentabancaria findByNumerocuenta(String numerocuenta)
			throws Exception {
		Cuentabancaria cuentabancaria;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuenta", numerocuenta);

			List<Cuentabancaria> historialbovedaList = cuentabancariaDAO
					.findByNamedQuery(Cuentabancaria.findByNumerocuenta,
							parameters);
			if (historialbovedaList.size() == 1) {
				cuentabancaria = historialbovedaList.get(0);
			} else {
				throw new Exception("No se encontro cuenta bancaria valida");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public List<CuentabancariaView> findByDni(String dni) throws Exception {
		List<CuentabancariaView> CuentabancariaView;
		try {
			// Map<String, Object> parameters = new HashMap<String, Object>();
			// parameters.put("numerocuenta", numerocuenta);

			CuentabancariaView = cuentabancariaViewDAO.findByNamedQuery(Cuentabancaria.findByNumerocuenta);

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return CuentabancariaView;
	}

}
