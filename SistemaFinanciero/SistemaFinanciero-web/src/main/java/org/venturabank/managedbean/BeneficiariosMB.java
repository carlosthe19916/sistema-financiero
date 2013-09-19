package org.venturabank.managedbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import org.ventura.boundary.local.BeneficiariocuentaServiceLocal;
import org.ventura.entity.Beneficiariocuenta;
import org.venturabank.util.TablaMB;

@ManagedBean
@NoneScoped
public class BeneficiariosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BeneficiariocuentaServiceLocal beneficiariocuentaFacadeLocal;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Beneficiariocuenta> tablaBeneficiarios;

	public void addBeneficiario() {
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();
		
		this.tablaBeneficiarios.addRow(beneficiariocuenta);
	}

	public void removeBeneficiario() {
		this.tablaBeneficiarios.removeSelectedRow();
	}
	
	public void editBeneficiario() {
		
	}

	public TablaMB<Beneficiariocuenta> getTablaBeneficiarios() {
		return tablaBeneficiarios;
	}

	public void setTablaBeneficiarios(TablaMB<Beneficiariocuenta> tablaBeneficiarios) {
		this.tablaBeneficiarios = tablaBeneficiarios;
	}

}
