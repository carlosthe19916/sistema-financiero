package org.venturabank.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.PersonanaturalFacadeLocal;
import org.ventura.model.Estadocivil;
import org.ventura.model.Personanatural;
import org.ventura.model.Sexo;
import org.venturabank.util.Combo;

@ManagedBean
@NoneScoped
public class PersonaNaturalMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalFacadeLocal personaNaturalFacadeLocal;

	private Personanatural personaNatural;

	// combos
	private Combo<Sexo> comboSexo;
	private Combo<Estadocivil> comboEstadocivil;

	// constructor
	public PersonaNaturalMB() {
		this.personaNatural = new Personanatural();

		// Inicializar combos
		this.comboSexo = new Combo<Sexo>();
		this.comboEstadocivil = new Combo<Estadocivil>();
	}

	@PostConstruct
	private void initValues() {
		// inicializar los combos
		comboSexo.initValues(Sexo.findAll);
		comboEstadocivil.initValues("SELECT s FROM Estadocivil s");

		// poner los valores de los combos a la persona natural
		this.personaNatural.setSexo(comboSexo.getSelectedItem());
		this.personaNatural.setEstadocivil(comboEstadocivil.getSelectedItem());
	}

	public Personanatural getPersonaNatural() {
		return personaNatural;
	}

	public void setPersonaNatural(Personanatural personaNatural) {
		this.personaNatural = personaNatural;
	}

	public Combo<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(Combo<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public Combo<Estadocivil> getComboEstadocivil() {
		return comboEstadocivil;
	}

	public void setComboEstadocivil(Combo<Estadocivil> comboEstadocivil) {
		this.comboEstadocivil = comboEstadocivil;
	}

}
