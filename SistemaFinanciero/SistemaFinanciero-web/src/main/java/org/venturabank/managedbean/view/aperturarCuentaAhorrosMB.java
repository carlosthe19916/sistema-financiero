package org.venturabank.managedbean.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;
import org.venturabank.managedbean.BeneficiariosMB;
import org.venturabank.managedbean.DatosFinancierosCuentaAhorroMB;
import org.venturabank.managedbean.PersonaJuridicaMB;
import org.venturabank.managedbean.PersonaNaturalMB;
import org.venturabank.managedbean.TitularesMB;
import org.venturabank.util.ComboMB;

@ManagedBean
@ViewScoped
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentaahorroServiceLocal cuentaahorroServiceLocal;

	private Cuentaahorro cuentaahorro;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<String> comboTipoPersona;

	@ManagedProperty(value = "#{personaNaturalMB}")
	private PersonaNaturalMB personaNaturalMB;

	@ManagedProperty(value = "#{personaJuridicaMB}")
	private PersonaJuridicaMB personaJuridicaMB;

	@ManagedProperty(value = "#{datosFinancierosCuentaAhorroMB}")
	private DatosFinancierosCuentaAhorroMB datosFinancierosCuentaAhorroMB;

	@ManagedProperty(value = "#{titularesMB}")
	private TitularesMB titularesMB;

	@ManagedProperty(value = "#{beneficiariosMB}")
	private BeneficiariosMB beneficiariosMB;

	/**
	 * 
	 * CONTRUCT POSTCONTRUC PREDESTROY
	 * 
	 **/

	public aperturarCuentaAhorrosMB() {
		this.cuentaahorro = new Cuentaahorro();
	}

	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}

	/**
	 * 
	 * BUSSINES LOGIC
	 * 
	 * **/

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentaahorro() {
		try {
			if (validarCuentaAhorro()) {
				this.establecerParametrosCuentaahorro();
				if (isPersonaNatural()) {
					this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonanatural(cuentaahorro);
				}
				if (isPersonaJuridica()) {
					this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonajuridica(cuentaahorro);
				}				
				return "cuentaAhorroImprimirDatos";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "System Error", mensaje);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	public void establecerParametrosCuentaahorro() throws Exception {

		if (comboTipoPersona.getItemSelected() == 1) {
			// se recuperan los datos de los Managed Bean invocados
			Cuentaahorro cuentaahorro = datosFinancierosCuentaAhorroMB.getCuentaahorro();
			Personanatural personanatural = personaNaturalMB.getPersonaNatural();

			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			List<Beneficiariocuenta> listBeneficiariocuenta = beneficiariosMB.getTablaBeneficiarios().getRows();

			// se crean las clases a relacionar con la Cuenta de Ahorros
			Personanaturalcliente personanaturalcliente = new Personanaturalcliente();
			personanaturalcliente.setPersonanatural(personanatural);

			// Se relaciona la Cuenta de Ahorros con los objetos recuperados
			this.cuentaahorro = cuentaahorro;
			this.cuentaahorro.setPersonanaturalcliente(personanaturalcliente);
			this.cuentaahorro.setTitularcuentas(listTitularcuenta);
			this.cuentaahorro.setBeneficiariocuentas(listBeneficiariocuenta);

			// se relacionan los titulares y beneficiarios a la cuentacorriente
			List<Cuentaahorrohistorial> historiales = cuentaahorro.getCuentaahorrohistorials();
			Cuentaahorrohistorial cuentaahorrohistorial = historiales.get(0);

			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);

			List<Beneficiariocuenta> beneficiariocuentas = cuentaahorro.getBeneficiariocuentas();

			for (Iterator<Beneficiariocuenta> iterator = beneficiariocuentas.iterator(); iterator.hasNext();) {
				Beneficiariocuenta var = (Beneficiariocuenta) iterator.next();
				var.setCuentaahorro(cuentaahorro);
			}

			List<Titularcuenta> titularcuentas = cuentaahorro.getTitularcuentas();

			for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
				Titularcuenta var = (Titularcuenta) iterator.next();
				String dni = var.getPersonanatural().getDni();
				var.setDni(dni);
				var.setCuentaahorro(cuentaahorro);
			}
			String dniCliente = cuentaahorro.getPersonanaturalcliente().getPersonanatural().getDni();
			cuentaahorro.getPersonanaturalcliente().setDni(dniCliente);
			cuentaahorro.setDni(dniCliente);

		} if (comboTipoPersona.getItemSelected() == 2) {
			Personajuridica personajuridica = personaJuridicaMB.getoPersonajuridica();
			Personajuridicacliente personajuridicacliente = new Personajuridicacliente();
			personajuridicacliente.setPersonajuridica(personajuridica);
		}
		if (comboTipoPersona.getItemSelected() < 1 || comboTipoPersona.getItemSelected() > 2) {
			throw new Exception("Error al establecer los parametros de la cuenta de ahorros");
		}
	}

	public boolean validarCuentaAhorro() {

		boolean result = true;

		this.mensaje = "ERROR:\n";

		if (isPersonaNatural()) {
			if (!personaNaturalMB.isValid()) {
				result = false;
				this.mensaje = mensaje
						+ "Datos de Persona Natural Invalidos \n";
			}
			if (!beneficiariosMB.isValid()) {
				result = false;
				this.mensaje = mensaje + "Datos de Beneficiarios \n";
			}
		}

		if (isPersonaJuridica()) {
			if (!personaJuridicaMB.isValid()) {
				result = false;
				this.mensaje = mensaje
						+ "Datos de Persona Juridica invalidos \n";
			}
		}

		if (!datosFinancierosCuentaAhorroMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos Financieros invalidos \n";
		}

		if (!titularesMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos de Titulares \n";
		}

		// falta validar datos financieros

		return result;
	}

	public boolean isPersonaNatural() {
		if (comboTipoPersona.getItemSelected() == 1)
			return true;
		else
			return false;
	}

	public boolean isPersonaJuridica() {
		if (comboTipoPersona.getItemSelected() == 2)
			return true;
		else
			return false;
	}

	public void establecerTitularDefecto() {
		Personanatural personanatural = personaNaturalMB.getPersonaNatural();

		Titularcuenta titularcuenta = new Titularcuenta();
		titularcuenta.setPersonanatural(personanatural);

		List<Titularcuenta> titularcuentas = titularesMB.getTablaTitulares()
				.getRows();

		if (titularcuentas.size() == 0) {
			titularcuentas.add(titularcuenta);
		} else {
			titularcuentas.set(0, titularcuenta);
		}
	}

	/**
	 * 
	 * GETTER AND SETTER
	 * 
	 * **/

	public Cuentaahorro getCuentaahorro() {
		return cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public PersonaNaturalMB getPersonaNaturalMB() {
		return personaNaturalMB;
	}

	public void setPersonaNaturalMB(PersonaNaturalMB personaNaturalMB) {
		this.personaNaturalMB = personaNaturalMB;
	}

	public PersonaJuridicaMB getPersonaJuridicaMB() {
		return personaJuridicaMB;
	}

	public void setPersonaJuridicaMB(PersonaJuridicaMB personaJuridicaMB) {
		this.personaJuridicaMB = personaJuridicaMB;
	}

	public TitularesMB getTitularesMB() {
		return titularesMB;
	}

	public void setTitularesMB(TitularesMB titularesMB) {
		this.titularesMB = titularesMB;
	}

	public BeneficiariosMB getBeneficiariosMB() {
		return beneficiariosMB;
	}

	public void setBeneficiariosMB(BeneficiariosMB beneficiariosMB) {
		this.beneficiariosMB = beneficiariosMB;
	}

	public ComboMB<String> getComboTipoPersona() {
		return comboTipoPersona;
	}

	public void setComboTipoPersona(ComboMB<String> comboTipoPersona) {
		this.comboTipoPersona = comboTipoPersona;
	}

	public DatosFinancierosCuentaAhorroMB getDatosFinancierosCuentaAhorroMB() {
		return datosFinancierosCuentaAhorroMB;
	}

	public void setDatosFinancierosCuentaAhorroMB(
			DatosFinancierosCuentaAhorroMB datosFinancierosCuentaAhorroMB) {
		this.datosFinancierosCuentaAhorroMB = datosFinancierosCuentaAhorroMB;
	}

}
