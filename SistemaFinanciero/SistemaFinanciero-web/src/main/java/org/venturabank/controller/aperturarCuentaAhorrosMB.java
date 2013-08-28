package org.venturabank.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Integer, String> listTipoPersona;
	private Integer tipoPersonaSeleccionada;

	public aperturarCuentaAhorrosMB() {
		listTipoPersona = new HashMap<Integer, String>();
		listTipoPersona.put(1, "Persona Natural");
		listTipoPersona.put(2, "Persona Juridica");

	}
	
	public String mostrarDialog(){
		/*if(this.tipoPersonaSeleccionada == 1)
			return "/views/maestros/titulares.xhtml";
		if(this.tipoPersonaSeleccionada == 1)
			return "/views/maestros/personaJuridica.xhtml";*/
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
