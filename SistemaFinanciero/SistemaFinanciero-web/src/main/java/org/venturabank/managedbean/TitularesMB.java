package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import org.ventura.boundary.local.TitularcuentaServiceLocal;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Titularcuenta;
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
		this.cantidadRetirantes = new Integer(1);
	}

	public boolean isValid(){
		if (cantidadRetirantes > tablaTitulares.getRows().size())
			return false;
		else
			return true;
	}
	
	public void addTitular() {
		Titularcuenta titularcuenta = new Titularcuenta();	
		Personanatural personanatural = new Personanatural();	
		titularcuenta.setPersonanatural(personanatural);
		
		this.tablaTitulares.addRow(titularcuenta);
	}
	
	public void addTitular(Personanatural personanatural){
		Titularcuenta titularcuenta = new Titularcuenta();		
		titularcuenta.setPersonanatural(personanatural);
	
		this.tablaTitulares.addRow(titularcuenta);
	}

	public void removeTitular() {
		this.tablaTitulares.removeSelectedRow();
	}
	
	public void validarTitulares(){
		
		List<Titularcuenta> titularcuentas = tablaTitulares.getRows();

		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
			Titularcuenta titular = (Titularcuenta) iterator.next();
			Personanatural personanatural = titular.getPersonanatural();
			
			boolean appellidoPaterno = true;
			boolean appellidoMaterno = true;
			boolean nombres= true;
			
			if(personanatural.getDni()==null||personanatural.getDni().isEmpty()||personanatural.getDni().trim().isEmpty()||personanatural.getDni().length()!=8){
				appellidoPaterno= false;
			}
			if(personanatural.getApellidopaterno()==null||personanatural.getApellidopaterno().isEmpty()||personanatural.getApellidopaterno().trim().isEmpty()){
				appellidoPaterno= false;
			}
			if(personanatural.getApellidomaterno()==null||personanatural.getApellidomaterno().isEmpty()||personanatural.getApellidomaterno().trim().isEmpty()){
				appellidoMaterno= false;
			}
			if(personanatural.getNombres()==null||personanatural.getNombres().isEmpty()||personanatural.getNombres().trim().isEmpty()){
				nombres= false;
			}
					
			if(!(appellidoPaterno&&appellidoMaterno&&nombres)){
				iterator.remove();
			}
			
		}
		
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
