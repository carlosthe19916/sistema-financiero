package org.ventura.dependent;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.tipodato.Moneda;

@Named
@Dependent
public class CalculadoraBean {

	private List<CalculadoraRowBean> list;

	public CalculadoraBean() {
		list = new ArrayList<CalculadoraRowBean>();
	}

	public CalculadoraBean(List<Denominacionmoneda> denominacionmonedas) {
		list = new ArrayList<CalculadoraRowBean>();
		for (Denominacionmoneda e : denominacionmonedas) {
			CalculadoraRowBean row = new CalculadoraRowBean(e);
			this.list.add(row);
		}
	}

	public void setDenominaciones(List<Denominacionmoneda> list) {
		this.list.clear();
		for (Denominacionmoneda denominacionmoneda : list) {
			CalculadoraRowBean row = new CalculadoraRowBean(denominacionmoneda);
			this.list.add(row);
		}
	}

	public Moneda getTotal() {
		Moneda result = new Moneda();
		for (CalculadoraRowBean e : list) {
			result = result.add(e.getSubtotal());
		}
		return result;
	}

	public List<CalculadoraRowBean> getList() {
		return list;
	}

}
