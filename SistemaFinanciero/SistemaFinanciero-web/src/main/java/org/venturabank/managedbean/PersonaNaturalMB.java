package org.venturabank.managedbean;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.ventura.dao.impl.SexoDAO;
import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Sexo;

@ManagedBean
@SessionScoped
public class PersonaNaturalMB {

	
	@EJB
	SexoFacadeLocal facade;
	
	private String prueba;
	
	public PersonaNaturalMB(){
		int a = 4;
		probarBD();
	}
	
	public void probarBD(){
		Sexo prueba = new Sexo();
		prueba.setIdsexo(1);
		prueba.setDenominacion("prueba");
		prueba.setAbreviatura("p");
		prueba.setEstado(true);
		
		//SexoDAO a = new SexoDAO();
		
		a.create(prueba);
		
		this.prueba = prueba.toString();
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
}
