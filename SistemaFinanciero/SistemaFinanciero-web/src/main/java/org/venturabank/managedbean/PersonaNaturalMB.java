package org.venturabank.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;

import org.ventura.dao.CrudService;
import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Estadocivil;
import org.ventura.model.Sexo;

@ManagedBean
@SessionScoped
public class PersonaNaturalMB {

	
	@EJB
	SexoFacadeLocal facade;
	
	private String prueba;
	
	public PersonaNaturalMB(){
		int a = 4;
		tablaEstadoCivil();
	}
	
	public void tablaEstadoCivil(){
		Sexo prueba = new Sexo();
		prueba.setIdsexo(1);
		prueba.setDenominacion("prueba");
		prueba.setAbreviatura("p");
		prueba.setEstado(true);
		
		facade.create(prueba);
		
		this.prueba = prueba.toString();
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
}
