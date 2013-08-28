package org.venturabank.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean

public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Integer, String> listTipoPersona;
	private Integer tipoPersonaSeleccionada;

	public aperturarCuentaAhorrosMB() {
		listTipoPersona = new HashMap<Integer, String>();
		listTipoPersona.put(1, "Persona Natural");
		listTipoPersona.put(2, "Persona Juridica");
		
		this.tipoPersonaSeleccionada = 5;
	}
	
	public String mostrarDialog(){
		if(this.tipoPersonaSeleccionada == 2)
			return "/views/maestros/titulares.xhtml";
		return "/views/maestros/personaJuridica.xhtml";
	}

	public Map<Integer, String> getListTipoPersona() {
		return listTipoPersona;
	}

	public void setListTipoPersona(Map<Integer, String> listTipoPersona) {
		this.listTipoPersona = listTipoPersona;
	}

	public Integer getTipoPersonaSeleccionada() {
		return tipoPersonaSeleccionada;
	}

	public void setTipoPersonaSeleccionada(Integer tipoPersonaSeleccionada) {
		this.tipoPersonaSeleccionada = tipoPersonaSeleccionada;
	}

}
