package org.venturabank.managedbean;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;

import org.ventura.dao.CrudService;
import org.ventura.model.Estadocivil;

@ManagedBean
@SessionScoped
public class PersonaNaturalMB {

	@EJB
	CrudService crudService;
	
	private String prueba;
	
	public PersonaNaturalMB(){
		int a = 4;
		tablaEstadoCivil();
	}
	
	public void tablaEstadoCivil(){
		//crudService.findWithNamedQuery(Estadocivil.findAll);
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
}
