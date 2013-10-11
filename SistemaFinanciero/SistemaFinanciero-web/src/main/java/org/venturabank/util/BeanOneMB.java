package org.venturabank.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.ventura.dao.CrudService;

@ManagedBean
@ViewScoped
public class BeanOneMB<E> {

	String atribute1;

	public void imprimir(){
		System.out.println(atribute1);
	}
	
	public String getAtribute1() {
		return atribute1;
	}

	public void setAtribute1(String atribute1) {
		this.atribute1 = atribute1;
	}
	
}
