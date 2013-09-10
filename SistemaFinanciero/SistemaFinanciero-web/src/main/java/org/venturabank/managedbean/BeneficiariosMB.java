package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.BeneficiariocuentaFacadeLocal;
import org.ventura.model.Beneficiariocuenta;
import org.ventura.model.Personanatural;

@ManagedBean
@NoneScoped
public class BeneficiariosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BeneficiariocuentaFacadeLocal beneficiariocuentaFacadeLocal;
	private Beneficiariocuenta beneficiariocuenta;
	private List<Beneficiariocuenta> listBeneficiariocuenta;

	// CONTRUCTOR
	public BeneficiariosMB() {
		this.beneficiariocuenta = new Beneficiariocuenta();
		this.listBeneficiariocuenta = new ArrayList<Beneficiariocuenta>();
	}

	public BeneficiariocuentaFacadeLocal getBeneficiariocuentaFacadeLocal() {
		return beneficiariocuentaFacadeLocal;
	}

	public void setBeneficiariocuentaFacadeLocal(
			BeneficiariocuentaFacadeLocal beneficiariocuentaFacadeLocal) {
		this.beneficiariocuentaFacadeLocal = beneficiariocuentaFacadeLocal;
	}

	public Beneficiariocuenta getBeneficiariocuenta() {
		return beneficiariocuenta;
	}

	public void setBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		this.beneficiariocuenta = beneficiariocuenta;
	}

	public List<Beneficiariocuenta> getListBeneficiariocuenta() {
		return listBeneficiariocuenta;
	}

	public void setListBeneficiariocuenta(
			List<Beneficiariocuenta> listBeneficiariocuenta) {
		this.listBeneficiariocuenta = listBeneficiariocuenta;
	}

}
