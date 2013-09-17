package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import org.ventura.boundary.local.TitularcuentaServiceLocal;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Sexo;
import org.ventura.entity.Titularcuenta;
import org.venturabank.util.ComboMB;
import org.venturabank.util.TablaMB;

@ManagedBean
@NoneScoped
public class TitularesMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	TitularcuentaServiceLocal facadeLocal;

	private Integer cantidadRetirantes;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Titularcuenta> tablaTitulares;
	
	public TitularesMB() {
		this.cantidadRetirantes = new Integer(0);
	}

	public void addTitular() {
		Titularcuenta titularcuenta = new Titularcuenta();
		titularcuenta.setDni("00000000");
		
		Personanatural personanatural = new Personanatural();
		personanatural.setApellidopaterno("Apellido Paterno");
		personanatural.setApellidomaterno("Apellido Materno");
		personanatural.setNombres("Nombres");
		
		titularcuenta.setPersonanatural(personanatural);
		
		this.tablaTitulares.addRow(titularcuenta);
	}

	public void removeTitular() {
		this.tablaTitulares.removeSelectedRow();
	}
	
	public void editTitular() {
		
	}
	
	public Integer getCantidadRetirantes() {
		return cantidadRetirantes;
	}

	public void setCantidadRetirantes(Integer cantidadRetirantes) {
		this.cantidadRetirantes = cantidadRetirantes;
	}

	public TablaMB<Titularcuenta> getTablaTitulares() {
		return tablaTitulares;
	}

	public void setTablaTitulares(TablaMB<Titularcuenta> tablaTitulares) {
		this.tablaTitulares = tablaTitulares;
	}

}
