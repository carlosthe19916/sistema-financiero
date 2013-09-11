package org.venturabank.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.facade.PersonajuridicaFacadeLocal;
import org.ventura.model.Personajuridica;
import org.ventura.model.Tipoempresa;
import org.venturabank.util.ComboMB;

@ManagedBean
@NoneScoped
public class PersonaJuridicaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Personajuridica oPersonajuridica;
	@EJB
	PersonajuridicaFacadeLocal personaJuridicaFacadeLocal;
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipoempresa> comboTipoempresa;

	public PersonaJuridicaMB() {
		oPersonajuridica = new Personajuridica();
	}

	@PostConstruct
	private void initValues() {
		//getComboTipoempresa().initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
	}
	
	public void changeTipoempresa(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.setTipoempresa(getComboTipoempresa().getObjectItemSelected(key));
	}
	
	public void insertarPersonaJuridica(){
		personaJuridicaFacadeLocal.create(oPersonajuridica);
	}
	
	public Personajuridica getoPersonajuridica() {
		return oPersonajuridica;
	}

	public void setoPersonajuridica(Personajuridica oPersonajuridica) {
		this.oPersonajuridica = oPersonajuridica;
	}

	public ComboMB<Tipoempresa> getComboTipoempresa() {
		return comboTipoempresa;
	}

	public void setComboTipoempresa(ComboMB<Tipoempresa> comboTipoempresa) {
		this.comboTipoempresa = comboTipoempresa;
	}

}
