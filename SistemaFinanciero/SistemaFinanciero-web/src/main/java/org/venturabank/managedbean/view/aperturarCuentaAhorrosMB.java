package org.venturabank.managedbean.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.facade.CuentaahorroFacadeLocal;
import org.ventura.model.Cuentaahorro;
import org.ventura.model.Personajuridica;
import org.ventura.model.Personajuridicacliente;
import org.ventura.model.Personanatural;
import org.ventura.model.Personanaturalcliente;
import org.venturabank.managedbean.BeneficiariosMB;
import org.venturabank.managedbean.PersonaJuridicaMB;
import org.venturabank.managedbean.PersonaNaturalMB;
import org.venturabank.managedbean.TitularesMB;
import org.venturabank.util.ComboMB;

@ManagedBean
@ViewScoped
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaahorroFacadeLocal cuentaahorroFacadeLocal;

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

	// Constructor
	public aperturarCuentaAhorrosMB() {
		this.cuentaahorro = new Cuentaahorro();
	}

	@PostConstruct
	private void initValues() {
		//Inicializar
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);

		Personanatural personanatural = personaNaturalMB.getPersonaNatural();
		Personajuridica personajuridica = personaJuridicaMB.getoPersonajuridica();
		
		Personajuridicacliente personajuridicacliente = new Personajuridicacliente();
		Personanaturalcliente personanaturalcliente = new Personanaturalcliente();
		
		personajuridicacliente.setPersonajuridica(personajuridica);
		personanaturalcliente.setPersonanatural(personanatural);
		
		this.cuentaahorro.setPersonanaturalcliente(personanaturalcliente);
		this.cuentaahorro.setPersonajuridicacliente(personajuridicacliente);
		

		
		
		this.cuentaahorro.setTitularcuentas(titularesMB.getTablaTitulares().getRows());
		this.cuentaahorro.setBeneficiariocuentas(beneficiariosMB.getTablaBeneficiarios().getRows());
	}

	// crear cuenta Ahorro
	public void createCuentaahorro() {
		cuentaahorro.setNumerocuentaahorro("12345678912345");
		cuentaahorroFacadeLocal.create(cuentaahorro);
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

}
