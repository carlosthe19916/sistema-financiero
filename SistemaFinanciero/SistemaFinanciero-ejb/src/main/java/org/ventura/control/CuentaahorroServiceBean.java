package org.ventura.control;

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
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personanatural;

@Named
@Stateless
@Local(CuentaahorroServiceLocal.class)
@Remote(CuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaahorroServiceBean implements CuentaahorroServiceLocal {

	@EJB
	private CuentaahorroDAO oCuentaahorroDAO;

	@Inject
	Cuentaahorro cuentaahorro;

	@Override
	public Cuentaahorro create(Cuentaahorro oCuentaahorro) {
		try {

			this.cuentaahorro = oCuentaahorro;

			if (isPersonanatural()) {
				validarCuentaahorroPersonanatural();
			}

			if (isPersonajuridica()) {
				validarCuentaahorroPersonajuridica();
			}

			generarDatosDeRegistro();
			generarNumeroCuenta();

			oCuentaahorroDAO.create(oCuentaahorro);

		} catch (Exception e) {
			System.out.println("ERROR:" + e.getMessage());
		} finally {
			this.cuentaahorro = null;
		}

		return oCuentaahorro;
	}

	private void generarDatosDeRegistro() {
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("activo");
		estadocuenta.setEstado(true);
		estadocuenta.setIdestadocuenta(1);

		cuentaahorro.setEstadocuenta(estadocuenta);
		cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
	}

	private void generarNumeroCuenta() {
		Random a = new Random();
		cuentaahorro.setNumerocuentaahorro("12345678912345");
	}

	protected void validarCuentaahorroPersonanatural() {
		cuentaahorro.setPersonajuridicacliente(null);

		String dni = cuentaahorro.getPersonanaturalcliente().getPersonanatural().getDni();
		cuentaahorro.getPersonanaturalcliente().setDni(dni);
		cuentaahorro.setDni(dni);
	}

	protected void validarCuentaahorroPersonajuridica() {
		cuentaahorro.setPersonanaturalcliente(null);

		String ruc = cuentaahorro.getPersonajuridicacliente().getPersonajuridica().getRuc();
		cuentaahorro.getPersonajuridicacliente().setRuc(ruc);
		cuentaahorro.setDni(ruc);
	}

	protected boolean isPersonanatural() {
		Class tipoPersona = cuentaahorro.getTipoPersonaCliente();
		if (tipoPersona == Personanatural.class)
			return true;
		else
			return false;
	}

	protected boolean isPersonajuridica() {
		Class tipoPersona = cuentaahorro.getTipoPersonaCliente();
		if (tipoPersona == Personajuridica.class)
			return true;
		else
			return false;
	}

	@Override
	public Cuentaahorro find(Integer id) {
		return oCuentaahorroDAO.find(id);
	}

	@Override
	public void delete(Cuentaahorro oCuentaahorro) {
		oCuentaahorroDAO.delete(oCuentaahorro);
	}

	@Override
	public Cuentaahorro update(Cuentaahorro oCuentaahorro) {
		return oCuentaahorroDAO.update(oCuentaahorro);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName) {
		return oCuentaahorroDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName,
			int resultLimit) {
		return oCuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro,
			Map<String, Object> parameters) {
		return oCuentaahorroDAO.findByNamedQuery(Cuentaahorro, parameters);
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		return oCuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
