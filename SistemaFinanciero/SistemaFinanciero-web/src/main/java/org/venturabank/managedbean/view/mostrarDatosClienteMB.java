package org.venturabank.managedbean.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.entity.Personanaturalcliente;
import org.venturabank.managedbean.PersonaNaturalClienteMB;
import org.venturabank.managedbean.PersonaNaturalMB;

@ManagedBean
@ViewScoped
public class mostrarDatosClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{personaNaturalMB}")
	private PersonaNaturalMB personaNaturalMB;
	
	@ManagedProperty(value = "#{personaNaturalClienteMB}")
	private PersonaNaturalClienteMB personaNaturalClienteMB;
	
	private Personanaturalcliente personaEdit;
	
	private String prueba;
	
	public mostrarDatosClienteMB() {
		
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
	
	public void imprimirDatos(){
		System.out.println(prueba);
		/*this.personaNaturalMB.setPersonaNatural(this.personaNaturalClienteMB.getSelectedPersonaNaturalCliente().getPersonanatural());
		System.out.println("Llego  recuperar datos");
		System.out.println(personaNaturalClienteMB.getSelectedPersonaNaturalCliente().getDni());
		System.out.println(personaNaturalMB.getPersonaNatural().getDni());
		System.out.println(personaNaturalMB.getPersonaNatural().getNombreCompleto());*/
	}


	public Personanaturalcliente getPersonaEdit() {
		return personaEdit;
	}


	public void setPersonaEdit(Personanaturalcliente personaEdit) {
		this.personaEdit = personaEdit;
	}


	public String getPrueba() {
		return prueba;
	}


	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

}
