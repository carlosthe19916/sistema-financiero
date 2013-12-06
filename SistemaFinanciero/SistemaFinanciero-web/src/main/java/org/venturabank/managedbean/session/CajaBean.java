package org.venturabank.managedbean.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.caja.Caja;

@Named
@SessionScoped
public class CajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Caja caja;

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	@PostConstruct
	private void init() {
		caja.setDenominacion("Agencia Quinuapata");
		caja.setIdcaja(1);
	}
	

}
