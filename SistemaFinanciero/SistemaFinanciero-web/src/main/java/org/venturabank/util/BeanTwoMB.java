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
@SessionScoped
public class BeanTwoMB<E> {

	String atribute2;

	public String getAtribute2() {
		return atribute2;
	}

	public void setAtribute2(String atribute2) {
		this.atribute2 = atribute2;
	}
	
	public void imprimir(){
		System.out.println(atribute2);
	}

	
}
