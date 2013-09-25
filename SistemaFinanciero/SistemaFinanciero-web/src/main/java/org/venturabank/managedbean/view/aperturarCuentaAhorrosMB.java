package org.venturabank.managedbean.view;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
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

		// se recuperan los datos de los Managed Bean invocados
		Cuentaahorro cuentaahorro = datosFinancierosCuentaAhorroMB
				.getCuentaahorro();
		Personanatural personanatural = personaNaturalMB.getPersonaNatural();
		// Personajuridica personajuridica =
		// personaJuridicaMB.getoPersonajuridica();
		List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares()
				.getRows();
		List<Beneficiariocuenta> listBeneficiariocuenta = beneficiariosMB
				.getTablaBeneficiarios().getRows();

		// se crean las clases a relacionar con la Cuenta de Ahorros
		// Personajuridicacliente personajuridicacliente = new
		// Personajuridicacliente();
		Personanaturalcliente personanaturalcliente = new Personanaturalcliente();

		// personajuridicacliente.setPersonajuridica(personajuridica);
		personanaturalcliente.setPersonanatural(personanatural);

		// Se relaciona la Cuenta de Ahorros con los objetos recuperados
		this.cuentaahorro = cuentaahorro;
		this.cuentaahorro.setPersonanaturalcliente(personanaturalcliente);
		// this.cuentaahorro.setPersonajuridicacliente(personajuridicacliente);
		this.cuentaahorro.setTitularcuentas(listTitularcuenta);
		this.cuentaahorro.setBeneficiariocuentas(listBeneficiariocuenta);

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

	public void createCuentaahorro() {

		String mensaje = "";
		boolean result = validarCuentaAhorro(mensaje);
		
		if (result) {

			List<Cuentaahorrohistorial> historiales = cuentaahorro
					.getCuentaahorrohistorials();
			Cuentaahorrohistorial cuentaahorrohistorial = historiales.get(0);

			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);

			List<Beneficiariocuenta> beneficiariocuentas = cuentaahorro
					.getBeneficiariocuentas();

			for (Iterator iterator = beneficiariocuentas.iterator(); iterator
					.hasNext();) {
				Beneficiariocuenta var = (Beneficiariocuenta) iterator.next();
				var.setCuentaahorro(cuentaahorro);
			}

			List<Titularcuenta> titularcuentas = cuentaahorro
					.getTitularcuentas();

			for (Iterator iterator = titularcuentas.iterator(); iterator
					.hasNext();) {
				Titularcuenta var = (Titularcuenta) iterator.next();
				String dni = var.getPersonanatural().getDni();
				var.setDni(dni);
				var.setCuentaahorro(cuentaahorro);
			}

			String dniCliente = cuentaahorro.getPersonanaturalcliente()
					.getPersonanatural().getDni();
			cuentaahorro.getPersonanaturalcliente().setDni(dniCliente);

			this.cuentaahorroServiceLocal.create(cuentaahorro);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"System Error", "ERROR DE LOS DATOS INGRESADOS");

			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
	
	public boolean validarCuentaAhorro(String mensaje){
		
		boolean result = true;
		
		if (isPersonaNatural()) {
			if (!personaNaturalMB.isValid()) {
				result = false;
				mensaje = mensaje + "Persona Natural Invalido \n";
			}
			if (!beneficiariosMB.isValid()) {
				result = false;
				mensaje = mensaje + "Beneficiario \n";
			}
		}
		
		if (isPersonaJuridica()) {
			if (!personaJuridicaMB.isValid()) {
				result = false;
				mensaje = mensaje + "PersonaJuridica \n";
			}
		}
		
		if (!titularesMB.isValid()) {
			result = false;
			mensaje = mensaje + "Titulares \n";
		}
		
		//falta validar datos financieros
		
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
