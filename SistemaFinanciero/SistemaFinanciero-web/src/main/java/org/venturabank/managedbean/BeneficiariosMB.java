package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

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

	public boolean isValid(){
		return true;
	}
	
	public void addBeneficiario() {
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();		
		this.tablaBeneficiarios.addRow(beneficiariocuenta);
	}
	
	public void validarBeneficiarios(){
	
		List<Beneficiariocuenta> beneficiarios = tablaBeneficiarios.getRows();

		for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiariocuenta beneficiariocuenta = (Beneficiariocuenta) iterator.next();
			
			boolean appellidoPaterno = true;
			boolean appellidoMaterno = true;
			boolean nombres= true;
			
			if(beneficiariocuenta.getApellidopaterno()==null||beneficiariocuenta.getApellidopaterno().isEmpty()||beneficiariocuenta.getApellidopaterno().trim().isEmpty()){
				appellidoPaterno= false;
			}
			if(beneficiariocuenta.getApellidomaterno()==null||beneficiariocuenta.getApellidomaterno().isEmpty()||beneficiariocuenta.getApellidomaterno().trim().isEmpty()){
				appellidoMaterno= false;
			}
			if(beneficiariocuenta.getNombres()==null||beneficiariocuenta.getNombres().isEmpty()||beneficiariocuenta.getNombres().trim().isEmpty()){
				nombres= false;
			}
					
			if(!(appellidoPaterno&&appellidoMaterno&&nombres)){
				iterator.remove();
			}
			
		}
		
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
