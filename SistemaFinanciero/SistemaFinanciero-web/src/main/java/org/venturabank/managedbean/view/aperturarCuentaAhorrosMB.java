package org.venturabank.managedbean.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.facade.CuentaahorroFacadeLocal;
import org.ventura.model.Cuentaahorro;
import org.venturabank.managedbean.BeneficiariosMB;
import org.venturabank.managedbean.PersonaJuridicaMB;
import org.venturabank.managedbean.PersonaNaturalMB;
import org.venturabank.managedbean.TitularesMB;

@ManagedBean
@ViewScoped
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaahorroFacadeLocal cuentaahorroFacadeLocal;
	
	private Cuentaahorro cuentaahorro;
	
	@ManagedProperty(value = "#{personaNaturalMB}")
	private PersonaNaturalMB personaNatural;

	@ManagedProperty(value = "#{personaJuridicaMB}")
	private PersonaJuridicaMB personaJuridica;
	
	@ManagedProperty(value = "#{titularesMB}")
	private TitularesMB titulares;

	@ManagedProperty(value = "#{beneficiariosMB}")
	private BeneficiariosMB beneficiarios;

	//Constructor
	public aperturarCuentaAhorrosMB(){
		this.cuentaahorro =  new Cuentaahorro();
	}
	
	@PostConstruct
	private void initValues(){
		this.cuentaahorro.setPersonanatural(personaNatural.getPersonaNatural());
		this.cuentaahorro.setPersonajuridica(personaJuridica.getoPersonajuridica());
		this.cuentaahorro.setTitularcuentas(titulares.getListTitularcuenta());
		this.cuentaahorro.setBeneficiariocuentas(beneficiarios.getListBeneficiariocuenta());
	}
	
	//crear cuenta Ahorro
	public void createCuentaahorro() {
		cuentaahorroFacadeLocal.create(cuentaahorro);
	}

	//GETTERS AND SETTERS
	public CuentaahorroFacadeLocal getCuentaahorroFacadeLocal() {
		return cuentaahorroFacadeLocal;
	}

	public void setCuentaahorroFacadeLocal(
			CuentaahorroFacadeLocal cuentaahorroFacadeLocal) {
		this.cuentaahorroFacadeLocal = cuentaahorroFacadeLocal;
	}

	public Cuentaahorro getCuentaahorro() {
		return cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public PersonaNaturalMB getPersonaNaturalMB() {
		return personaNatural;
	}

	public void setPersonaNaturalMB(PersonaNaturalMB personaNaturalMB) {
		this.personaNatural = personaNaturalMB;
	}

	public PersonaJuridicaMB getPersonaJuridicaMB() {
		return personaJuridica;
	}

	public void setPersonaJuridicaMB(PersonaJuridicaMB personaJuridicaMB) {
		this.personaJuridica = personaJuridicaMB;
	}

	public TitularesMB getTitularesMB() {
		return titulares;
	}

	public void setTitularesMB(TitularesMB titularesMB) {
		this.titulares = titularesMB;
	}

	public BeneficiariosMB getBeneficiariosMB() {
		return beneficiarios;
	}

	public void setBeneficiariosMB(BeneficiariosMB beneficiariosMB) {
		this.beneficiarios = beneficiariosMB;
	}
	


}
