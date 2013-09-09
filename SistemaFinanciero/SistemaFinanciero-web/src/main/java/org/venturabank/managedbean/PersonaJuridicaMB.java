package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.model.Personajuridica;

@ManagedBean
@NoneScoped
public class PersonaJuridicaMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Personajuridica oPersonajuridica;

	public PersonaJuridicaMB(){
		oPersonajuridica = new Personajuridica();
		
	}
	public Personajuridica getoPersonajuridica() {
		return oPersonajuridica;
	}

	public void setoPersonajuridica(Personajuridica oPersonajuridica) {
		this.oPersonajuridica = oPersonajuridica;
	}

}
