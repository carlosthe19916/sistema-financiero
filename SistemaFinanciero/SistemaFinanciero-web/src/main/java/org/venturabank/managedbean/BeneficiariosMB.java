package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.swing.text.DefaultEditorKit.BeepAction;

import org.ventura.boundary.local.BeneficiariocuentaServiceLocal;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Titularcuenta;
import org.venturabank.util.TablaMB;

@ManagedBean
@NoneScoped
public class BeneficiariosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BeneficiariocuentaServiceLocal beneficiariocuentaFacadeLocal;
	private Beneficiariocuenta beneficiariocuenta;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Beneficiariocuenta> tablaBeneficiarios;

	// CONTRUCTOR
	public BeneficiariosMB() {
		this.beneficiariocuenta = new Beneficiariocuenta();
	}

	public void addBeneficiario() {
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();
		beneficiariocuenta.setDni("00000000");
		
		beneficiariocuenta.setApellidopaterno("Apellido Paterno");
		beneficiariocuenta.setApellidomaterno("Apellido Materno");
		beneficiariocuenta.setNombres("Nombres");
		
		this.tablaBeneficiarios.addRow(beneficiariocuenta);
	}

	public void removeBeneficiario() {
		this.tablaBeneficiarios.removeSelectedRow();
	}
	
	public void editBeneficiario() {
		
	}

	public Beneficiariocuenta getBeneficiariocuenta() {
		return beneficiariocuenta;
	}

	public void setBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		this.beneficiariocuenta = beneficiariocuenta;
	}

	public TablaMB<Beneficiariocuenta> getTablaBeneficiarios() {
		return tablaBeneficiarios;
	}

	public void setTablaBeneficiarios(TablaMB<Beneficiariocuenta> tablaBeneficiarios) {
		this.tablaBeneficiarios = tablaBeneficiarios;
	}

}
