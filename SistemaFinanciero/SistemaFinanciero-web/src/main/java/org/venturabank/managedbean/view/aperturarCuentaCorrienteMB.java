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
public class aperturarCuentaCorrienteMB implements Serializable {

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

	@ManagedProperty(value = "#{titularesMB}")
	private TitularesMB titularesMB;

	@ManagedProperty(value = "#{beneficiariosMB}")
	private BeneficiariosMB beneficiariosMB;

	/**
	 * 
	 * CONTRUCT POSTCONTRUC PREDESTROY
	 * 
	 **/

	public aperturarCuentaCorrienteMB() {
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

	public void createCuentacorriente() {

		boolean valida = false;
		if (valida == true) {
			System.out.println("entro");
			//this.cuentaahorroServiceLocal.create(cuentaahorro);

		} else {
			System.out.println("eneeetro");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"System Error", "Please try again later.");

			FacesContext.getCurrentInstance().addMessage(null, message);
		}

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

	public ComboMB<String> getComboTipoPersona() {
		return comboTipoPersona;
	}

	public void setComboTipoPersona(ComboMB<String> comboTipoPersona) {
		this.comboTipoPersona = comboTipoPersona;
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

	public PersonaJuridicaMB getPersonaJuridicaMB() {
		return personaJuridicaMB;
	}

	public void setPersonaJuridicaMB(PersonaJuridicaMB personaJuridicaMB) {
		this.personaJuridicaMB = personaJuridicaMB;
	}

}
