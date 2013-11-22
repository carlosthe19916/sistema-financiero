package org.venturabank.util;

import java.math.BigDecimal;

import org.ventura.util.maestro.Moneda;

public class DetalleTransaccionBean {

	private Moneda valor;
	private int cantidad;
	private Moneda total;

	public DetalleTransaccionBean() {
		valor = new Moneda();
		total = new Moneda();
		cantidad = 0;
	}

	public Moneda getValor() {
		return valor;
	}

	public void setValor(Moneda valor) {
		this.valor = valor;
		BigDecimal result = valor.multiply(cantidad);
		this.total.setValue(result);
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
		BigDecimal result = valor.multiply(cantidad);
		this.total.setValue(result);
	}

	public Moneda getTotal() {
		return total;
	}

	public void setTotal(Moneda total) {
		this.total = total;
	}
}
