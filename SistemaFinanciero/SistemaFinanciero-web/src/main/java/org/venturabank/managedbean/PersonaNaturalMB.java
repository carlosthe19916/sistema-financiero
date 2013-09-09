package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.EstadocivilFacadeLocal;
import org.ventura.facade.PersonanaturalFacadeLocal;
import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Estadocivil;
import org.ventura.model.Personanatural;
import org.ventura.model.Sexo;

@ManagedBean
@NoneScoped
public class PersonaNaturalMB implements Serializable {

	@EJB
	SexoFacadeLocal sexoFacadeLocal;
	@EJB
	private EstadocivilFacadeLocal estadoCivilFacadeLoal;
	@EJB
	PersonanaturalFacadeLocal personaNauralFacadeLocal;

	
	private static final long serialVersionUID = 1L;

	private Personanatural oPersonaNatural;
		
	private Sexo oSexoSeleccionado;
	private List<Sexo> listaSexos;
	
	private Estadocivil oEstadoCivilSeleccionado;
	private List<Estadocivil> listaEstadoCivil;


	// constructor
	public PersonaNaturalMB() {
		this.oPersonaNatural = new Personanatural();
	}

	public Personanatural getPersonanatural() {
		return oPersonaNatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.oPersonaNatural = personanatural;
	}

}
