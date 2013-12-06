package org.venturabank.managedbean.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.seguridad.Menu;
import org.ventura.entity.schema.seguridad.Modulo;

@Named
@SessionScoped
public class PageViewBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Modulo moduloSelected;
	
	@Inject
	private Menu menu;
	
	@PostConstruct
	private void initialize(){
		
	}

	public Modulo getModuloSelected() {
		return moduloSelected;
	}

	public void setModuloSelected(Modulo moduloSelected) {
		this.moduloSelected = moduloSelected;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
