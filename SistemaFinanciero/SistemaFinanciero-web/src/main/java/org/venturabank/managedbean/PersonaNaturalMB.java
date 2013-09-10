package org.venturabank.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.facade.PersonanaturalFacadeLocal;
import org.ventura.model.Estadocivil;
import org.ventura.model.Personanatural;
import org.ventura.model.Sexo;
import org.venturabank.util.ComboMB;

@ManagedBean
@NoneScoped
public class PersonaNaturalMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalFacadeLocal personaNaturalFacadeLocal;

	private Personanatural personaNatural;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Sexo> comboSexo;
	
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Estadocivil> comboEstadoCivil;
	
	// constructor
	public PersonaNaturalMB() {
		this.personaNatural = new Personanatural();		
	}

	@PostConstruct
	private void initValues() {
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL);
		comboEstadoCivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
	}

	public void changeSexo(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.personaNatural.setSexo(comboSexo.getObjectItemSelected(key));
	}
	
	public void changeEstadoCivil(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.personaNatural.setEstadocivil(comboEstadoCivil.getObjectItemSelected(key));
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


}
