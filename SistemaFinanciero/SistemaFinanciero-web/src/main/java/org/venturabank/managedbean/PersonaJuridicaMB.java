package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.facade.AccionistaFacadeLocal;
import org.ventura.facade.PersonajuridicaFacadeLocal;
import org.ventura.model.Accionista;
import org.ventura.model.Estadocivil;
import org.ventura.model.Personajuridica;
import org.ventura.model.Personanatural;
import org.ventura.model.Sexo;
import org.ventura.model.Tipoempresa;
import org.venturabank.util.ComboMB;
import org.venturabank.util.TablaMB;

@ManagedBean
@NoneScoped
public class PersonaJuridicaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Personajuridica oPersonajuridica;	
	@EJB
	PersonajuridicaFacadeLocal personaJuridicaFacadeLocal;
	
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipoempresa> comboTipoempresa;
	
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Sexo> comboSexo;
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Estadocivil> comboEstadocivil;
	@EJB
	AccionistaFacadeLocal accionistaFacadeLocal;
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Accionista> tablaAccionistas;
	
	
	public PersonaJuridicaMB() {
		oPersonajuridica = new Personajuridica();		
	}

	@PostConstruct
	private void initValues() {
		getComboTipoempresa().initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
		getComboSexo().initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);	
		getComboEstadocivil().initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);		
		oPersonajuridica.setListAccionista(new ArrayList<Accionista>());
		oPersonajuridica.setPersonanatural(new Personanatural());
	}
	
	public void addAccionista() {
		Accionista accionista = new Accionista();
		
		accionista.setPersonanatural(new Personanatural());
		accionista.getPersonanatural().setDni("00000000");
		accionista.getPersonanatural().setApellidopaterno("aaa");
		accionista.getPersonanatural().setApellidomaterno("aaa");
		accionista.getPersonanatural().setNombres("aaa");		
		oPersonajuridica.getListAccionista().add(accionista);
		tablaAccionistas.addRow(accionista);			
	}

	public void removeAccionista() {
		this.getTablaAccionistas().removeSelectedRow();
	}
	
	public void editTitular() {
		
	}
	
	public void changeTipoempresa(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.setTipoempresa(getComboTipoempresa().getObjectItemSelected(key));
	}
	
	public void changeSexo(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.getPersonanatural().setSexo(getComboSexo().getObjectItemSelected(key));
	}
	public void changeEstadoCivil(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.getPersonanatural().setEstadocivil(getComboEstadocivil().getObjectItemSelected(key));
	}
	
	public void insertarPersonaJuridica(){
		personaJuridicaFacadeLocal.create(oPersonajuridica);
	}
	
	public String razonSocial(boolean finsocial){
		if(finsocial)
			return "Con fines de lucro";
			else 
				return "Sin fines de lucro";
			
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

	public ComboMB<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboMB<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboMB<Estadocivil> getComboEstadocivil() {
		return comboEstadocivil;
	}

	public void setComboEstadocivil(ComboMB<Estadocivil> comboEstadocivil) {
		this.comboEstadocivil = comboEstadocivil;
	}

	public TablaMB<Accionista> getTablaAccionistas() {
		return tablaAccionistas;
	}

	public void setTablaAccionistas(TablaMB<Accionista> tablaAccionistas) {
		this.tablaAccionistas = tablaAccionistas;
	}		

}
