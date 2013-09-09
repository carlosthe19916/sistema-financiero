package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.model.Beneficiariocuenta;
import org.ventura.model.Personanatural;

@ManagedBean
@NoneScoped
public class BeneficiariosMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Beneficiariocuenta> oListBeneficiariocuenta;

	public BeneficiariosMB() {
		// TODO Auto-generated constructor stub
		oListBeneficiariocuenta = new ArrayList<Beneficiariocuenta>();
	}

	public List<Beneficiariocuenta> getoListBeneficiariocuenta() {
		return oListBeneficiariocuenta;
	}

	public void setoListBeneficiariocuenta(
			List<Beneficiariocuenta> oListBeneficiariocuenta) {
		this.oListBeneficiariocuenta = oListBeneficiariocuenta;
	}
}
