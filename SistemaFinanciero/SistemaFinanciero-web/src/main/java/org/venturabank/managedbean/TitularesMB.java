package org.venturabank.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.ventura.model.Personanatural;
import org.ventura.model.Titularcuenta;

@ManagedBean
@SessionScoped
public class TitularesMB {

	private Integer cantidadRetirantes;

	private List<Personanatural> titulares;
	private Personanatural titularSeleccionado;

	public TitularesMB() {
		this.titulares = new ArrayList<Personanatural>();
		this.titularSeleccionado = new Personanatural();
	}

	public void addTitular() {
		Personanatural oPersonanatural = new Personanatural();
		titulares.add(oPersonanatural);
	}

	public List<Personanatural> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Personanatural> titulares) {
		this.titulares = titulares;
	}

	public Personanatural getTitularSeleccionado() {
		return titularSeleccionado;
	}

	public void setTitularSeleccionado(Personanatural titularSeleccionado) {
		this.titularSeleccionado = titularSeleccionado;
	}

	public Integer getCantidadRetirantes() {
		return cantidadRetirantes;
	}

	public void setCantidadRetirantes(Integer cantidadRetirantes) {
		this.cantidadRetirantes = cantidadRetirantes;
	}
}
