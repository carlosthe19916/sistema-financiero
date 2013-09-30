package org.venturabank.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.entity.Estadocivil;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Sexo;
import org.venturabank.util.ComboMB;

@ManagedBean
@NoneScoped
public class PersonaNaturalMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalServiceLocal personanaturalServiceLocal;

	private Personanatural personaNatural;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Sexo> comboSexo;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Estadocivil> comboEstadoCivil;
	
	private boolean isEditing;
	
	// constructor
	public PersonaNaturalMB() {
		this.personaNatural = new Personanatural();		
		isEditing = false;
	}

	//Para despues de crear el objeto
	@PostConstruct
	private void initValues() {
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadoCivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);		
	}

	public void buscarPersona(){
		Personanatural personanatural = personanaturalServiceLocal.find(personaNatural.getDni());

		if (personanatural != null) {
			this.personaNatural = personanatural;
		} else {
			personanatural = new Personanatural();
			personanatural.setDni(getPersonaNatural().getDni());
			this.personaNatural = personanatural;
			this.changeEditingState();
		}

	}
	
	public boolean isValid(){		
		return personaNatural.isValid() ? true : false;
	}
	
	public void changeSexo(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Sexo sexoSelected = comboSexo.getObjectItemSelected(key);
		this.personaNatural.setSexo(sexoSelected);
	}

	public void changeEstadoCivil(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Estadocivil estadocivilSelected = comboEstadoCivil.getObjectItemSelected(key);
		this.personaNatural.setEstadocivil(estadocivilSelected);
		
	}

	public Personanatural getPersonaNatural() {
		return personaNatural;
	}

	public void setPersonaNatural(Personanatural personaNatural) {
		this.personaNatural = personaNatural;
	}

	public ComboMB<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboMB<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboMB<Estadocivil> getComboEstadoCivil() {
		return comboEstadoCivil;
	}

	public void setComboEstadoCivil(ComboMB<Estadocivil> comboEstadoCivil) {
		this.comboEstadoCivil = comboEstadoCivil;
	}

	public void changeEditingState() {
		this.isEditing = !isEditing;
	}
	
	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

}
