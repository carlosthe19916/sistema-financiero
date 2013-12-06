package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.venturabank.managedbean.session.CajaBean;

@Named
@ViewScoped
public class TransaccionCompraVentaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TransaccionCajaServiceLocal cajaServiceLocal;
	
	@Inject
	private CajaBean cajaBean;
	
	@Inject
	private ComboBean<Tipotransaccioncompraventa> comboTipotransaccion;
	@Inject
	private Tipotransaccioncompraventa tipotransaccioncompraventa;
	private String referencia;	
	@Inject
	private ComboBean<Tipomoneda> comboTipomonedaEntregado;
	@Inject
	private Tipomoneda tipomonedaEntregado;
	@Inject 
	private ComboBean<Tipomoneda> comboTipomonedaRecibido;
	@Inject
	private Tipomoneda tipomonedaRecibido;
	private BigDecimal tasaCambio;
	@Inject
	private Moneda montoRecibido;
	@Inject
	private Moneda montoEntregado;	
	@Inject
	private CalculadoraBean calculadoraBeanRecibido;
	@Inject
	private CalculadoraBean calculadoraBeanEntregado;

	@PostConstruct
	private void initialize(){
		comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccioncompraventa.ALL_ACTIVE);
		comboTipomonedaEntregado.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		comboTipomonedaRecibido.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void createTransaccioncaja() {
		Transaccioncompraventa transaccioncompraventa = new Transaccioncompraventa();

		transaccioncompraventa.setTipotransaccioncompraventa(tipotransaccioncompraventa);
		transaccioncompraventa.setReferencia(referencia);
		transaccioncompraventa.setMontoentregado(montoEntregado);
		transaccioncompraventa.setMontorecibido(montoRecibido);
		transaccioncompraventa.setTipomonedaEntregado(tipomonedaEntregado);
		transaccioncompraventa.setTipomonedaRecibido(tipomonedaRecibido);

		try {
			cajaServiceLocal.createTransaccionCompraVenta(cajaBean.getCaja(), transaccioncompraventa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeTipotransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccioncompraventa tipotransaccionSelected = comboTipotransaccion.getObjectItemSelected(key);
		this.tipotransaccioncompraventa = tipotransaccionSelected;
	}

	public void changeTipomonedaEntregado(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomonedaEntregado.getObjectItemSelected(key);
		this.tipomonedaEntregado = tipomonedaSelected;
		this.montoEntregado = new Moneda();
		//loadDenominacionmonedaCalculadora();
	}
	
	public void changeTipomonedaRecibido(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomonedaRecibido.getObjectItemSelected(key);
		this.tipomonedaRecibido = tipomonedaSelected;
		this.montoRecibido = new Moneda();
		//loadDenominacionmonedaCalculadora();
	}

	public ComboBean<Tipotransaccioncompraventa> getComboTipotransaccion() {
		return comboTipotransaccion;
	}

	public void setComboTipotransaccion(
			ComboBean<Tipotransaccioncompraventa> comboTipotransaccion) {
		this.comboTipotransaccion = comboTipotransaccion;
	}

	public Tipotransaccioncompraventa getTipotransaccioncompraventa() {
		return tipotransaccioncompraventa;
	}

	public void setTipotransaccioncompraventa(
			Tipotransaccioncompraventa tipotransaccioncompraventa) {
		this.tipotransaccioncompraventa = tipotransaccioncompraventa;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public ComboBean<Tipomoneda> getComboTipomonedaEntregado() {
		return comboTipomonedaEntregado;
	}

	public void setComboTipomonedaEntregado(
			ComboBean<Tipomoneda> comboTipomonedaEntregado) {
		this.comboTipomonedaEntregado = comboTipomonedaEntregado;
	}

	public Tipomoneda getTipomonedaEntregado() {
		return tipomonedaEntregado;
	}

	public void setTipomonedaEntregado(Tipomoneda tipomonedaEntregado) {
		this.tipomonedaEntregado = tipomonedaEntregado;
	}

	public ComboBean<Tipomoneda> getComboTipomonedaRecibido() {
		return comboTipomonedaRecibido;
	}

	public void setComboTipomonedaRecibido(
			ComboBean<Tipomoneda> comboTipomonedaRecibido) {
		this.comboTipomonedaRecibido = comboTipomonedaRecibido;
	}

	public Tipomoneda getTipomonedaRecibido() {
		return tipomonedaRecibido;
	}

	public void setTipomonedaRecibido(Tipomoneda tipomonedaRecibido) {
		this.tipomonedaRecibido = tipomonedaRecibido;
	}

	public BigDecimal getTasaCambio() {
		return tasaCambio;
	}

	public void setTasaCambio(BigDecimal tasaCambio) {
		this.tasaCambio = tasaCambio;
	}

	public Moneda getMontoRecibido() {
		return montoRecibido;
	}

	public void setMontoRecibido(Moneda montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public Moneda getMontoEntregado() {
		return montoEntregado;
	}

	public void setMontoEntregado(Moneda montoEntregado) {
		this.montoEntregado = montoEntregado;
	}

	public CalculadoraBean getCalculadoraBeanRecibido() {
		return calculadoraBeanRecibido;
	}

	public void setCalculadoraBeanRecibido(CalculadoraBean calculadoraBeanRecibido) {
		this.calculadoraBeanRecibido = calculadoraBeanRecibido;
	}

	public CalculadoraBean getCalculadoraBeanEntregado() {
		return calculadoraBeanEntregado;
	}

	public void setCalculadoraBeanEntregado(CalculadoraBean calculadoraBeanEntregado) {
		this.calculadoraBeanEntregado = calculadoraBeanEntregado;
	}
	

}
