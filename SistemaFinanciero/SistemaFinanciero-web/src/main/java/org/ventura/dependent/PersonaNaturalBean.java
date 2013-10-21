package org.ventura.dependent;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.entity.Estadocivil;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Sexo;

@Named
@Dependent
public class PersonaNaturalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalServiceLocal personanaturalServiceLocal;

	private Personanatural personaNatural;

	@Inject
	private ComboBean<Sexo> comboSexo;

	@Inject
	private ComboBean<Estadocivil> comboEstadoCivil;

	private boolean isEditing;

	public PersonaNaturalBean() {
		this.personaNatural = new Personanatural();
		isEditing = false;
	}

	@PostConstruct
	private void initValues() {
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadoCivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
	}

	public void buscarPersona() {
		try {
			Personanatural personanatural;
			personanatural = personanaturalServiceLocal.find(personaNatural.getDni());
			
			if (personanatural != null) {
				this.personaNatural = personanatural;
				this.comboSexo.setItemSelected(personanatural.getSexo());
				this.comboEstadoCivil.setItemSelected(personanatural.getEstadocivil());
			} else {
				personanatural = new Personanatural();
				personanatural.setDni(getPersonaNatural().getDni());

				this.personaNatural = personanatural;
				this.comboSexo.setItemSelected(-1);
				this.comboEstadoCivil.setItemSelected(-1);

				this.changeEditingState();
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public boolean isValid() {
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

	public void changeEditingState() {
		this.isEditing = !isEditing;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboBean<Estadocivil> getComboEstadoCivil() {
		return comboEstadoCivil;
	}

	public void setComboEstadoCivil(ComboBean<Estadocivil> comboEstadoCivil) {
		this.comboEstadoCivil = comboEstadoCivil;
	}

}
