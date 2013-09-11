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
	private PersonaNaturalMB personaNaturalMB;

	@ManagedProperty(value = "#{personaJuridicaMB}")
	private PersonaJuridicaMB personaJuridicaMB;
	
	@ManagedProperty(value = "#{titularesMB}")
	private TitularesMB titularesMB;

	@ManagedProperty(value = "#{beneficiariosMB}")
	private BeneficiariosMB beneficiariosMB;

	//Constructor
	public aperturarCuentaAhorrosMB(){
		this.cuentaahorro =  new Cuentaahorro();
	}
	
	@PostConstruct
	private void initValues(){
		this.cuentaahorro.setPersonanatural(personaNaturalMB.getPersonaNatural());
		this.cuentaahorro.setPersonajuridica(personaJuridicaMB.getoPersonajuridica());
		this.cuentaahorro.setTitularcuentas(titularesMB.getTablaTitulares().getRows());
		this.cuentaahorro.setBeneficiariocuentas(beneficiariosMB.getTablaBeneficiarios().getRows());
	}
	
	//crear cuenta Ahorro
	public void createCuentaahorro() {
		cuentaahorroFacadeLocal.create(cuentaahorro);
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

	
	


}
