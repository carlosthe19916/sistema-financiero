package org.ventura.caja.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.dependent.CalculadoraBean;

@Named
@ViewScoped
public class TransaccionCompraVentaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CalculadoraBean calculadoraBeanRecibido;
	@Inject
	private CalculadoraBean calculadoraBeanEntregado;

	public CalculadoraBean getCalculadoraBeanRecibido() {
		return calculadoraBeanRecibido;
	}

	public void setCalculadoraBeanRecibido(
			CalculadoraBean calculadoraBeanRecibido) {
		this.calculadoraBeanRecibido = calculadoraBeanRecibido;
	}

	public CalculadoraBean getCalculadoraBeanEntregado() {
		return calculadoraBeanEntregado;
	}

	public void setCalculadoraBeanEntregado(
			CalculadoraBean calculadoraBeanEntregado) {
		this.calculadoraBeanEntregado = calculadoraBeanEntregado;
	}

}
