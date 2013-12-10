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

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.caja.dependent.BuscarCuentabancariaBean;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.venturabank.managedbean.session.CajaBean;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB
	private TransaccionCajaServiceLocal transaccioncuentabancariaServiceLocal;
	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	
	@Inject
	private CajaBean cajaBean;
	
	
	//busqueda de cuentabancaria
	@Inject
	private TablaBean<CuentabancariaView> tablaCuentabancaria;
	@Inject
	private ComboBean<String> comboTipobusqueda;
	private String valorBusqueda;

	// agrupadores pagina principal
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	private CalculadoraBean calculadoraBean;

	// Datos pagina principal
	@Inject
	private Tipotransaccion tipotransaccion;
	@Inject
	private Tipomoneda tipomoneda;
	@Inject
	private Moneda monto;
	private String numeroCuentabancaria;
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
			comboTipobusqueda.putItem(1, "Dni");
			comboTipobusqueda.putItem(2, "Ruc");
			comboTipobusqueda.putItem(3, "Apellidos o Nombres");
			comboTipobusqueda.putItem(4, "Razon social");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createTransaccioncaja() {
		Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();

		Cuentabancaria cuentabancaria = new Cuentabancaria();
		cuentabancaria.setNumerocuenta(numeroCuentabancaria);

		transaccioncuentabancaria.setTipotransaccion(tipotransaccion);
		transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
		transaccioncuentabancaria.setMonto(monto);
		transaccioncuentabancaria.setReferencia(referencia);
		transaccioncuentabancaria.setTipomoneda(tipomoneda);

		try {
			transaccioncuentabancariaServiceLocal.createTransaccionCuentabancaria(cajaBean.getCaja(), transaccioncuentabancaria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findCuentabancaria() {
		List<CuentabancariaView> cuentabancariaViews;
		try {
			cuentabancariaViews = cuentabancariaServiceLocal.findByDni(this.valorBusqueda);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void changeTipobusqueda(ValueChangeEvent event) {
		//Integer key = (Integer) event.getNewValue();
		//Tipotransaccion tipotransaccionSelected = comboTipotransaccion.getObjectItemSelected(key);
		//this.tipotransaccion = tipotransaccionSelected;
	}
	
	public void changeTipotransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccion tipotransaccionSelected = comboTipotransaccion
				.getObjectItemSelected(key);
		this.tipotransaccion = tipotransaccionSelected;
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda
				.getObjectItemSelected(key);
		this.tipomoneda = tipomonedaSelected;
		this.monto = new Moneda();
		loadDenominacionmonedaCalculadora();
	}

	public DenominacionmonedaServiceLocal getDenominacionmonedaServiceLocal() {
		return denominacionmonedaServiceLocal;
	}

	public void setDenominacionmonedaServiceLocal(
			DenominacionmonedaServiceLocal denominacionmonedaServiceLocal) {
		this.denominacionmonedaServiceLocal = denominacionmonedaServiceLocal;
	}

	public ComboBean<Tipotransaccion> getComboTipotransaccion() {
		return comboTipotransaccion;
	}

	public void setComboTipotransaccion(
			ComboBean<Tipotransaccion> comboTipotransaccion) {
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
		return numeroCuentabancaria;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuentabancaria = numeroCuenta;
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

	public void setBuscarCuentabancariaBean(
			BuscarCuentabancariaBean buscarCuentabancariaBean) {
		this.buscarCuentabancariaBean = buscarCuentabancariaBean;
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

	public String getNumeroCuentabancaria() {
		return numeroCuentabancaria;
	}

	public void setNumeroCuentabancaria(String numeroCuentabancaria) {
		this.numeroCuentabancaria = numeroCuentabancaria;
	}

	public ComboBean<String> getComboTipobusqueda() {
		return comboTipobusqueda;
	}

	public void setComboTipobusqueda(ComboBean<String> comboTipobusqueda) {
		this.comboTipobusqueda = comboTipobusqueda;
	}
}
