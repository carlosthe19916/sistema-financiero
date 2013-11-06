package org.ventura.dependent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.util.validate.Validator;

@Named
@Dependent
public class BeneficiariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Beneficiariocuenta> tablaBeneficiarios;

	public boolean isValid(){
		List<Beneficiariocuenta> beneficiarios = tablaBeneficiarios.getRows();
		return Validator.validateBeneficiarios(beneficiarios);
	}
	
	public void addBeneficiario() {
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();
		beneficiariocuenta.setEstado(true);
		this.tablaBeneficiarios.addRow(beneficiariocuenta);
	}
	
	public void validarBeneficiarios(){
		String mensaje= "Porcentaje de beneficio invalido";
		List<Beneficiariocuenta> beneficiarios = tablaBeneficiarios.getRows();
		Double porcentaje_total = new Double(0.0);
		for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiariocuenta beneficiariocuenta = (Beneficiariocuenta) iterator.next();
			
			boolean appellidoPaterno = true;
			boolean appellidoMaterno = true;
			boolean nombres= true;
			porcentaje_total+=beneficiariocuenta.getPorcentajebeneficio();
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
		if(porcentaje_total!=100.0){
			System.out.println("error::");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error",mensaje);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}

	public void removeBeneficiario() {
		this.tablaBeneficiarios.removeSelectedRow();
	}
	
	public void editBeneficiario() {
		
	}

	public void setTablaBeneficiarios(TablaBean<Beneficiariocuenta> tablaBeneficiarios) {
		this.tablaBeneficiarios = tablaBeneficiarios;
	}

	public TablaBean<Beneficiariocuenta> getTablaBeneficiarios() {
		return tablaBeneficiarios;
	}
	
	public List<Beneficiariocuenta> getListBeneficiarios(){
		return tablaBeneficiarios.getRows();
	}

}
