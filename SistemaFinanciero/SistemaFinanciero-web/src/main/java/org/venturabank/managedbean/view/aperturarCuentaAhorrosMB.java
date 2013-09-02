package org.venturabank.managedbean.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.venturabank.managedbean.PersonaNaturalManagedBean;

@ManagedBean
@ViewScoped
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Integer, String> tipoPersonas;
	private Integer tipoPersonaSelected;

	@ManagedProperty(name="personaNaturalManagedBean", value = "#{personaNaturalManagedBean}")
    private PersonaNaturalManagedBean personaNaturalManagedBean;
	
	private List<PersonaNaturalManagedBean> titulares;

	public aperturarCuentaAhorrosMB() {
		// tipoPersonaSeleccionada = "0";
		// tipoPersona = 0;
		this.tipoPersonas = new HashMap<Integer, String>();

		this.initTipoPersonas();
		this.initTipoPersonaSelected();
		this.initTitulares();
	}

	private void initTipoPersonas() {
		tipoPersonas.put(1, "P. Natural");
		tipoPersonas.put(2, "P. Jurídica");
	}

	private void initTipoPersonaSelected() {
		this.tipoPersonaSelected = new Integer(1);
	}

	private void initTitulares(){
		this.titulares = new ArrayList<PersonaNaturalManagedBean>();
	}
	
	public String isPersonaNatural() {
		if (getTipoPersonaSelected() == 1)
			return "true";
		else
			return "false";
	}

	public String isPersonaJuridica() {
		if (getTipoPersonaSelected() == 2)
			return "true";
		else
			return "false";
	}

	public Map<Integer, String> getTipoPersonas() {
		return tipoPersonas;
	}

	public void setTipoPersonas(Map<Integer, String> tipoPersonas) {
		this.tipoPersonas = tipoPersonas;
	}

	public Integer getTipoPersonaSelected() {
		return tipoPersonaSelected;
	}

	public void setTipoPersonaSelected(Integer tipoPersonaSelected) {
		this.tipoPersonaSelected = tipoPersonaSelected;
	}

	public List<PersonaNaturalManagedBean> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<PersonaNaturalManagedBean> titulares) {
		this.titulares = titulares;
	}

	public PersonaNaturalManagedBean getPersonaNaturalManagedBean() {
		return personaNaturalManagedBean;
	}

	public void setPersonaNaturalManagedBean(
			PersonaNaturalManagedBean personaNaturalManagedBean) {
		this.personaNaturalManagedBean = personaNaturalManagedBean;
	}
}
