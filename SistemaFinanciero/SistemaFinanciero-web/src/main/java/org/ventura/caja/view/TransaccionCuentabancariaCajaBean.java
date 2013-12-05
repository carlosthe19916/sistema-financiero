package org.ventura.caja.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.caja.dependent.BuscarCuentabancariaBean;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@Inject
	private Transaccioncuentabancaria transaccioncuentabancaria;
	
	//agrupadores pagina principal
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	private CalculadoraBean calculadoraBean;

	//Datos pagina principal
	@Inject
	private Tipotransaccion tipotransaccion;	
	@Inject
	private Tipomoneda tipomoneda;
	@Inject
	private Moneda monto;
	private String numeroCuenta;
	private String referencia;

	@Inject
	private BuscarCuentabancariaBean buscarCuentabancariaBean;
	
	public TransaccionCuentabancariaCajaBean() {

	}

	@PostConstruct
	private void initialize() {
		try {
			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccion.ALL_ACTIVE);
			comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createTransaccioncaja(){
		//transaccioncuentabancaria.set
	}
	
	public void loadDenominacionmonedaCalculadora() {
		List<Denominacionmoneda> list;
		try {
			Tipomoneda tipomoneda = this.tipomoneda;
			if (tipomoneda != null) {
				list = denominacionmonedaServiceLocal.getDenominacionmonedasActive(tipomoneda);
			} else {
				list = new ArrayList<Denominacionmoneda>();
			}
			calculadoraBean.setDenominaciones(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeTipotransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccion tipotransaccionSelected = comboTipotransaccion.getObjectItemSelected(key);
		this.tipotransaccion = tipotransaccionSelected;
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.tipomoneda = tipomonedaSelected;
		this.monto = new Moneda();
		loadDenominacionmonedaCalculadora();
	}

	public DenominacionmonedaServiceLocal getDenominacionmonedaServiceLocal() {
		return denominacionmonedaServiceLocal;
	}

	public void setDenominacionmonedaServiceLocal(DenominacionmonedaServiceLocal denominacionmonedaServiceLocal) {
		this.denominacionmonedaServiceLocal = denominacionmonedaServiceLocal;
	}

	public ComboBean<Tipotransaccion> getComboTipotransaccion() {
		return comboTipotransaccion;
	}

	public void setComboTipotransaccion(ComboBean<Tipotransaccion> comboTipotransaccion) {
		this.comboTipotransaccion = comboTipotransaccion;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public CalculadoraBean getCalculadoraBean() {
		return calculadoraBean;
	}

	public void setCalculadoraBean(CalculadoraBean calculadoraBean) {
		this.calculadoraBean = calculadoraBean;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
		Moneda result = new Moneda(monto);
		this.monto = result;
	}

	public void setMontoFromCalculadora() {
		Moneda result = this.calculadoraBean.getTotal();
		this.monto = result;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BuscarCuentabancariaBean getBuscarCuentabancariaBean() {
		return buscarCuentabancariaBean;
	}

	public void setBuscarCuentabancariaBean(BuscarCuentabancariaBean buscarCuentabancariaBean) {
		this.buscarCuentabancariaBean = buscarCuentabancariaBean;
	}
}
