package org.venturabank.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class aperturarCuentaAhorrosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoPersonaSeleccionada;

	public aperturarCuentaAhorrosMB() {
		tipoPersonaSeleccionada = "0";
		tipoPersona = 0;
	}

	public String mostrarDialog() {
		if (this.tipoPersonaSeleccionada.compareTo("1") == 1)
			return "/views/maestros/titulares.xhtml";
		return "/views/maestros/beneficiarios.xhtml";
	}

	public String getTipoPersonaSeleccionada() {
		return tipoPersonaSeleccionada;
	}

	public void setTipoPersonaSeleccionada(String tipoPersonaSeleccionada) {
		this.tipoPersonaSeleccionada = tipoPersonaSeleccionada;
	}

	public void cambiar(String tipoPersonaSeleccionada) {
		this.tipoPersonaSeleccionada = tipoPersonaSeleccionada;
	}

	private Integer tipoPersona;

	public static final int PERSONA_NATURAL = 1;
	public static final int PERSONA_JURIDICA = 2;

	private static final String PERSONA_NATURAL_DIALOG = "/views/maestros/personaNatural.xhtml";
	private static final String PERSONA_JURIRICA_DIALOG = "/views/maestros/personaJuridica.xhtml";

	public String nombreDialog() {
		switch (tipoPersona) {
		case 1:
			return PERSONA_NATURAL_DIALOG.toString();

		case 2:
			return PERSONA_JURIRICA_DIALOG.toString();
		default:
			return PERSONA_NATURAL_DIALOG.toString();
		}

	}

	public void tipoPersonaChanged() {
		tipoPersona = tipoPersona;
	}

	public Integer getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(Integer tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

}
