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
	Titularcuenta titularcuenta;
	List<Titularcuenta> listTitularcuenta;

	public TitularesMB() {
		this.titularcuenta = new Titularcuenta();
		this.listTitularcuenta = new ArrayList<Titularcuenta>();
	}

	public void addTitular() {
		Titularcuenta titularcuenta = new Titularcuenta();
		listTitularcuenta.add(titularcuenta);
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
