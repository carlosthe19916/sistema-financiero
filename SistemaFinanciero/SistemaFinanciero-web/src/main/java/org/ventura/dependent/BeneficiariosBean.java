package org.ventura.dependent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.util.validate.Validator;

@Named
@Dependent
public class BeneficiariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Beneficiario> tablaBeneficiarios;

	public boolean isValid(){
		List<Beneficiario> beneficiarios = tablaBeneficiarios.getRows();
		//return Validator.validateBeneficiarios(beneficiarios);
		return true;
	}
	
	public void addBeneficiario() {
		Beneficiario beneficiariocuenta = new Beneficiario();
		beneficiariocuenta.setEstado(true);
		this.tablaBeneficiarios.addRow(beneficiariocuenta);
	}
	
	public void validarBeneficiarios(){
		List<Beneficiario> beneficiarios = tablaBeneficiarios.getRows();
		Double porcentaje_total = new Double(0.0);
		for (Iterator<Beneficiario> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiario beneficiariocuenta = (Beneficiario) iterator.next();
			
			boolean appellidoPaterno = true;
			boolean appellidoMaterno = true;
			boolean nombres= true;
			
			porcentaje_total = (beneficiariocuenta.getPorcentajebeneficio() == null) ? porcentaje_total : porcentaje_total + beneficiariocuenta.getPorcentajebeneficio();
			if(beneficiariocuenta.getPorcentajebeneficio() == null){
				//beneficiariocuenta.setPorcentajebeneficio(new Double(0));
			}
			
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
		if(porcentaje_total != 100.0){
			FacesContext context = FacesContext.getCurrentInstance();			
			context.validationFailed();	
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suma Incorrecta (%)", "Porcentaje de Beneficios no suma 100%");
			context.addMessage(null, message);
		}	
	}

	public void removeBeneficiario() {
		this.tablaBeneficiarios.removeSelectedRow();
	}
	
	public void editBeneficiario() {
		
	}

	public void setTablaBeneficiarios(TablaBean<Beneficiario> tablaBeneficiarios) {
		this.tablaBeneficiarios = tablaBeneficiarios;
	}

	public TablaBean<Beneficiario> getTablaBeneficiarios() {
		return tablaBeneficiarios;
	}
	
	public List<Beneficiario> getListBeneficiarios(){
		return tablaBeneficiarios.getRows();
	}

}
