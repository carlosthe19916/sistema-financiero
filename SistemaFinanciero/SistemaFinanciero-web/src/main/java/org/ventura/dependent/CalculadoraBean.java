package org.ventura.dependent;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Moneda;

@Named
@Dependent
public class CalculadoraBean {

	private List<Row> list;

	public CalculadoraBean() {
		list = new ArrayList<CalculadoraBean.Row>();
	}

	public CalculadoraBean(List<Denominacionmoneda> denominacionmonedas) {
		list = new ArrayList<CalculadoraBean.Row>();
		for (Denominacionmoneda e : denominacionmonedas) {
			Row row = new Row(e);
			this.list.add(row);
		}
	}

	public void setDenominaciones(List<Denominacionmoneda> list) {
		list.clear();
		for (Denominacionmoneda denominacionmoneda : list) {
			Row row = new Row(denominacionmoneda);
			this.list.add(row);
		}
	}

	public Moneda getTotal() {
		Moneda result = new Moneda();
		for (Row e : list) {
			result = result.add(e.getSubtotal());
		}
		return result;
	}

	class Row {

		private Denominacionmoneda denominacionmoneda;
		private Integer cantidad;

		public Row() {
			this.cantidad = 0;
		}

		public Row(Denominacionmoneda denominacionmoneda) {
			this.denominacionmoneda = denominacionmoneda;
			this.cantidad = 0;
		}

		public Row(Denominacionmoneda denominacionmoneda, Integer cantidad) {
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

	public List<Row> getList() {
		return list;
	}

}
