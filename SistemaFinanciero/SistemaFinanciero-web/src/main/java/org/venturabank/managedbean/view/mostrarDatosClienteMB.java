package org.venturabank.managedbean.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.entity.Personanaturalcliente;
import org.venturabank.managedbean.PersonaNaturalClienteMB;
import org.venturabank.managedbean.PersonaNaturalMB;

import sun.management.counter.perf.PerfByteArrayCounter;

@ManagedBean
@ViewScoped
public class mostrarDatosClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	static PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;
	
	@ManagedProperty(value = "#{personaNaturalMB}")
	private PersonaNaturalMB personaNaturalMB;
	
	@ManagedProperty(value = "#{personaNaturalClienteMB}")
	private PersonaNaturalClienteMB personaNaturalClienteMB;
	
	private Personanaturalcliente personaEditar;
	
	private String prueba;
	
	public mostrarDatosClienteMB() {
		personaEditar = new Personanaturalcliente();
	}
	
	public PersonaNaturalMB getPersonaNaturalMB() {
		return personaNaturalMB;
	}

	public void setPersonaNaturalMB(PersonaNaturalMB personaNaturalMB) {
		this.personaNaturalMB = personaNaturalMB;
	}
	
	public PersonaNaturalClienteMB getPersonaNaturalClienteMB() {
		return personaNaturalClienteMB;
	}

	public void setPersonaNaturalClienteMB(
			PersonaNaturalClienteMB personaNaturalClienteMB) {
		this.personaNaturalClienteMB = personaNaturalClienteMB;
	}

	public Personanaturalcliente getPersonaEdit() {
		return personaEditar;
	}

	public void setPersonaEdit(Personanaturalcliente personaEdit) {
		this.personaEditar = personaEdit;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
	
	public void mostrarPersonaNaturalCliente(){
		personaEditar = personaNaturalClienteServicesLocal.find(personaEditar.getDni());
	}
}
