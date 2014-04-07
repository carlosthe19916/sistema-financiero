package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarCuentabancaria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean failure;
	
	private String searched;
	@Inject private TablaBean<CuentabancariaView> tablaCuentabancaria;
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	
	public AdministrarCuentabancaria() {
		failure = false;
	}
	
	@PostConstruct
	private void initialize() {
		  
	}
	
	public void buscarCuentabancaria(){
		try {
			List<CuentabancariaView> list = cuentabancariaServiceLocal.findCuentabancariaView(searched);
			tablaCuentabancaria.setRows(list);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public String getSearched() {
		return searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

}
