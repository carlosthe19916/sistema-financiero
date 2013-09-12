package org.venturabank.managedbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.AccionistaFacadeLocal;
import org.ventura.model.Accionista;
import org.ventura.model.Personanatural;
import org.venturabank.util.TablaMB;

@NoneScoped
@ManagedBean
public class AccionistasMB implements Serializable{
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AccionistaFacadeLocal fagffhjgjh;
	private Integer cantidadAccionistas;
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Accionista> tablaAccionistas;
	
	
	public AccionistasMB() {
		// TODO Auto-generated constructor stub
		cantidadAccionistas=new Integer(0);
		
	}
	
	public void addAccionista() {
		Accionista accionista = new Accionista();
		accionista.getId().setDni("00000000");
		
		Personanatural personanatural = new Personanatural();
		personanatural.setApellidopaterno("Apellido Paterno");
		personanatural.setApellidomaterno("Apellido Materno");
		personanatural.setNombres("Nombres");
		
		accionista.setPersonanatural(personanatural);
		
		this.tablaAccionistas.addRow(accionista);
	}
	
	public void removeAccionista() {
		this.tablaAccionistas.removeSelectedRow();
	}
	
	public void editTitular() {
		
	}


	public Integer getCantidadAccionistas() {
		return cantidadAccionistas;
	}


	public void setCantidadAccionistas(Integer cantidadAccionistas) {
		this.cantidadAccionistas = cantidadAccionistas;
	}

	public TablaMB<Accionista> getTablaAccionistas() {
		return tablaAccionistas;
	}

	public void setTablaAccionistas(TablaMB<Accionista> tablaAccionistas) {
		this.tablaAccionistas = tablaAccionistas;
	}

}
