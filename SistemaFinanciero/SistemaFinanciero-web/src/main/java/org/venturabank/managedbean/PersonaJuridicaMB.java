package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.PersonajuridicaFacadeLocal;
import org.ventura.facade.TipoempresaFacadeLocal;
import org.ventura.model.Personajuridica;
import org.ventura.model.Tipoempresa;

@ManagedBean
@NoneScoped
public class PersonaJuridicaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Personajuridica oPersonajuridica;
	@EJB
	TipoempresaFacadeLocal tipoEmpresaFacadeLocal;
	@EJB
	PersonajuridicaFacadeLocal personaJuridicaFacadeLocal;
	private List<Tipoempresa> listaTipoempresas;
	private Tipoempresa otipoempresaSeleccionada;

	public PersonaJuridicaMB() {
		this.oPersonajuridica = new Personajuridica();
		this.otipoempresaSeleccionada = new Tipoempresa();
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

	public List<Tipoempresa> getListaTipoempresas() {
		initListaTipoempresas();
		return listaTipoempresas;
	}

	public void setListaTipoempresas(List<Tipoempresa> listaTipoempresas) {
		this.listaTipoempresas = listaTipoempresas;
	}

	public Tipoempresa getTipoempresaSeleccionada() {
		return otipoempresaSeleccionada;
	}

	public void setTipoempresaSeleccionada(Tipoempresa tipoempresaSeleccionada) {
		this.otipoempresaSeleccionada = tipoempresaSeleccionada;
	}
	
	public void initListaTipoempresas() {
		listaTipoempresas = (List<Tipoempresa>) tipoEmpresaFacadeLocal.findAll();
	}

}
