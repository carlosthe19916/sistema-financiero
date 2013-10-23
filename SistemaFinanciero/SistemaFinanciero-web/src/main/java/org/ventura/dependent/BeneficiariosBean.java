package org.ventura.dependent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.Beneficiariocuenta;

@Named
@Dependent
public class BeneficiariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Beneficiariocuenta> tablaBeneficiarios;

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

	public void setTablaBeneficiarios(
			TablaBean<Beneficiariocuenta> tablaBeneficiarios) {
		this.tablaBeneficiarios = tablaBeneficiarios;
	}

	public TablaBean<Beneficiariocuenta> getTablaBeneficiarios() {
		return tablaBeneficiarios;
	}
	
	public List<Beneficiariocuenta> getListBeneficiarios(){
		return tablaBeneficiarios.getRows();
	}

}
