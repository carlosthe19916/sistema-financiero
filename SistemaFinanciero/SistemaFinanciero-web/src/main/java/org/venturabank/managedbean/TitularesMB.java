package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.model.Personanatural;
import org.ventura.model.Titularcuenta;


@ManagedBean
@NoneScoped
public class TitularesMB implements Serializable{

	
	private List<Titularcuenta> listTitularcuenta;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cantidadRetirantes;

	private List<Personanatural> titulares;
	private Personanatural titularSeleccionado;

	public TitularesMB() {
		this.listTitularcuenta = new ArrayList<Titularcuenta>();
		
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

	public List<Titularcuenta> getListTitularcuenta() {
		return listTitularcuenta;
	}

	public void setListTitularcuenta(List<Titularcuenta> listTitularcuenta) {
		this.listTitularcuenta = listTitularcuenta;
	}
}
