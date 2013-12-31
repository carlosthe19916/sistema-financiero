package org.ventura.sistema.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ventura.boundary.local.SistemaServiceLocal;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CloseSistemaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private SistemaServiceLocal sistemaServiceLocal;

	private Date fechaCalculo;

	private boolean operacionTerminada;

	public CloseSistemaBean() {
		operacionTerminada = false;
	}

	@PostConstruct
	private void initialize() {
	}

	public void generarIntereses() {
		try {
			sistemaServiceLocal.closeSistema(fechaCalculo);
			operacionTerminada = true;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}

	public Date getFechaCalculo() {
		return fechaCalculo;
	}

	public void setFechaCalculo(Date fechaCalculo) {
		this.fechaCalculo = fechaCalculo;
	}

	public boolean isOperacionTerminada() {
		return operacionTerminada;
	}

	public void setOperacionTerminada(boolean operacionTerminada) {
		this.operacionTerminada = operacionTerminada;
	}
}
