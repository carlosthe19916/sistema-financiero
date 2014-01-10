package org.ventura.dependent;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.tipodato.Moneda;

@Named
@Dependent
public class CalculadoraRowBean {

	private Denominacionmoneda denominacionmoneda;
	private Integer cantidad;

	public CalculadoraRowBean() {
		this.cantidad = 0;
	}

	public CalculadoraRowBean(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
		this.cantidad = 0;
	}

	public CalculadoraRowBean(Denominacionmoneda denominacionmoneda,
			Integer cantidad) {
		this.denominacionmoneda = denominacionmoneda;
		this.cantidad = cantidad;
	}

	public Moneda getSubtotal() {
		Moneda result = new Moneda();
		result = denominacionmoneda.getValor().multiply(cantidad);
		return result;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
