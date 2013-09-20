package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventural.util.logger.Log;

@Named
@Stateless
@Local(CuentaahorroServiceLocal.class)
@Remote(CuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaahorroServiceBean implements CuentaahorroServiceLocal {

	@EJB
	private CuentaahorroDAO cuentaahorroDAO;

	@Inject
	Cuentaahorro cuentaahorro;

	@Inject
	Log log;

	@Override
	public Cuentaahorro create(Cuentaahorro oCuentaahorro) {
		try {

			this.cuentaahorro = oCuentaahorro;
			generarDatosDeRegistro();
			generarNumeroCuenta();

			cuentaahorroDAO.create(cuentaahorro);			

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally{
			log.info("Service Close");
		}

		return oCuentaahorro;
	}

	private void generarDatosDeRegistro() {
		cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
		cuentaahorro.setSaldo(0);
		cuentaahorro.setIdestadocuenta(1);
	}

	private void generarNumeroCuenta() {
		
		Random random = new Random();
		int length = 14;

		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}

		cuentaahorro.setNumerocuentaahorro(digits.toString());

	}

	@Override
	public Cuentaahorro find(Integer id) {
		return cuentaahorroDAO.find(id);
	}

	@Override
	public void delete(Cuentaahorro oCuentaahorro) {
		cuentaahorroDAO.delete(oCuentaahorro);
	}

	@Override
	public Cuentaahorro update(Cuentaahorro oCuentaahorro) {
		return cuentaahorroDAO.update(oCuentaahorro);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName) {
		return cuentaahorroDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName,
			int resultLimit) {
		return cuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro,
			Map<String, Object> parameters) {
		return cuentaahorroDAO.findByNamedQuery(Cuentaahorro, parameters);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		return cuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
