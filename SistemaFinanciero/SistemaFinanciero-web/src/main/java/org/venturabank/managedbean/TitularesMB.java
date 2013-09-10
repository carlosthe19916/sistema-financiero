package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.TitularcuentaFacadeLocal;
import org.ventura.model.Personanatural;
import org.ventura.model.Titularcuenta;

@ManagedBean
@NoneScoped
public class TitularesMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	TitularcuentaFacadeLocal facadeLocal;
	
	private Integer cantidadRetirantes;
	private Titularcuenta titularcuenta;
	private List<Titularcuenta> listTitularcuenta;

	public TitularesMB() {
		this.cantidadRetirantes = new Integer(0);
		this.titularcuenta = new Titularcuenta();
		this.listTitularcuenta = new ArrayList<Titularcuenta>();
	}
	
	public void addTitular(){
		this.listTitularcuenta.add(new Titularcuenta());
	}

	public Integer getCantidadRetirantes() {
		return cantidadRetirantes;
	}

	public void setCantidadRetirantes(Integer cantidadRetirantes) {
		this.cantidadRetirantes = cantidadRetirantes;
	}

	public Titularcuenta getTitularcuenta() {
		return titularcuenta;
	}

	public void setTitularcuenta(Titularcuenta titularcuenta) {
		this.titularcuenta = titularcuenta;
	}

	public List<Titularcuenta> getListTitularcuenta() {
		return listTitularcuenta;
	}

	public void setListTitularcuenta(List<Titularcuenta> listTitularcuenta) {
		this.listTitularcuenta = listTitularcuenta;
	}


}
