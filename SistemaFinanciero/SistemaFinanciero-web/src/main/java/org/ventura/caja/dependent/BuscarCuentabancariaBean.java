package org.ventura.caja.dependent;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;

@Named
@Dependent
public class BuscarCuentabancariaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valorBusqueda;

	@Inject
	private TablaBean<CuentabancariaView> tablaCuentabancaria;

	@Inject
	private Cuentabancaria cuentabancariaSeleccionada;

	public BuscarCuentabancariaBean() {
		// TODO Auto-generated constructor stub
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(
			TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public Cuentabancaria getCuentabancariaSeleccionada() {
		return cuentabancariaSeleccionada;
	}

	public void setCuentabancariaSeleccionada(
			Cuentabancaria cuentabancariaSeleccionada) {
		this.cuentabancariaSeleccionada = cuentabancariaSeleccionada;
	}

	

}
