package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPN;
import org.ventura.managedbean.session.CajaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCompraVentaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TransaccionCajaServiceLocal transaccionCompraVentaServiceLocal;
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@Inject
	private CajaBean cajaBean;
	@Inject
	private Caja caja;
	@Inject
	private Estadomovimiento estadomovimientoCaja;
	@Inject
	private Estadoapertura estadoaperturaCaja;
	
	@Inject
	private ComboBean<Tipotransaccioncompraventa> comboTipotransaccion;
	@Inject
	private Tipotransaccioncompraventa tipotransaccioncompraventa;
		
	@Inject
	private ComboBean<Tipomoneda> comboTipomonedaEntregado;
	@Inject
	private Tipomoneda tipomonedaEntregado;
	@Inject 
	private ComboBean<Tipomoneda> comboTipomonedaRecibido;
	@Inject
	private Tipomoneda tipomonedaRecibido;	
	@Inject
	private Moneda montoRecibido;
	@Inject
	private Moneda montoEntregado;
	@Inject
	private CalculadoraBean calculadoraBeanRecibido;
	@Inject
	private CalculadoraBean calculadoraBeanEntregado;
	
	private boolean isValidBean;
	private BigDecimal tipoCambio;
	private String referencia;
	
	public TransaccionCompraVentaCajaBean(){
		isValidBean = true;
		tipoCambio = BigDecimal.ZERO;
	}
	
	@PostConstruct
	private void initialize(){
		try {
			this.setCaja(cajaBean.getCaja());
			this.setEstadoaperturaCaja(getCaja().getEstadoapertura());
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(getCaja());
			if (historialcaja != null) {
				this.setEstadomovimientoCaja(historialcaja.getEstadomovimiento());
			} else {
				throw new Exception(
						"Caja no fue abierta correctamente, no tiene un historial activo");
			}
			validateBean();

			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccioncompraventa.ALL_ACTIVE);
				System.out.println("id CAjaBean: "+ cajaBean.getCaja().getIdcaja());
			cargarCombosTipoMoneda();
				System.out.println("Caja0001: "+ caja.getIdcaja());
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e,
					"El usuario o caja no tiene permitido realizar transacciones");
		}
	}
	
	public void cargarCombosTipoMoneda(){
			System.out.println("Caja0002: "+ caja.getIdcaja());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcaja", cajaBean.getCaja().getIdcaja());
		comboTipomonedaEntregado.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE_BY_CAJA, parameters);
		comboTipomonedaRecibido.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE_BY_CAJA, parameters);
	}
	
	public void calculateMontoEntregado(){
		Moneda monto = new Moneda(tipoCambio);
		Moneda money = monto.multiply(montoRecibido);
		setMontoEntregado(money);
	}
	
	public void validateBean() {
		if (getCaja() == null) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("El usuario o caja no tiene permitido realizar transacciones");
		}
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
		if (!this.getEstadoaperturaCaja().equals(estadoapertura)) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("La caja debe de estar ABIERTA para realizar transacciones");
		}
		Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
		if (!this.getEstadomovimientoCaja().equals(estadomovimiento)) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("La caja debe de estar DESCONGELADA para realizar transacciones");
		}	
	}
	
	public String createTransaccioncaja() {
		validateBean();
		if (isValidBean) {
			if (validateCompraVenta()) {
				Transaccioncompraventa transaccionCompraVenta = new Transaccioncompraventa();

				transaccionCompraVenta.setTipotransaccioncompraventa(tipotransaccioncompraventa);
				transaccionCompraVenta.setReferencia(referencia);
				transaccionCompraVenta.setTasacambio(tipoCambio);
				transaccionCompraVenta.setMontoentregado(montoEntregado);
				transaccionCompraVenta.setMontorecibido(montoRecibido);
				transaccionCompraVenta.setTipomonedaEntregado(tipomonedaEntregado);
				transaccionCompraVenta.setTipomonedaRecibido(tipomonedaRecibido);
				try {
					//transaccionCompraVenta = transaccionCompraVentaServiceLocal.createTransaccionCompraVenta(cajaBean.getCaja(),transaccionCompraVenta);
					//VouchercajaView vouchercajaView = transaccionCompraVentaServiceLocal.getVoucherTransaccionBancaria(transaccionCompraVenta);
					//imprimirVoucher(vouchercajaView);
				} catch (Exception e) {
					JsfUtil.addErrorMessage(e, "Error al realizar la transacción");
					return "failure";
				}
				JsfUtil.addSuccessMessage("La transaccion se realizó correctamente");
				return "success";
			}else {
				JsfUtil.addErrorMessage("Error, Ud. no puede compar o vender el Nuevo Sol y verifique que los tipos de monedas sean diferentes.");
				return null;
			}
		}else{
			JsfUtil.addErrorMessage("La caja no tiene permitido realizar transacciones");
			return null;
		}
	}

	public boolean validateCompraVenta() {
		boolean validate = true;
		if (comboTipotransaccion.getItemSelected() == 1) {
			if (comboTipomonedaRecibido.getItemSelected() == comboTipomonedaEntregado.getItemSelected()) {
				validate = false;
			}
			if (comboTipomonedaRecibido.getItemSelected() == 1) {
				validate = false;
			}
		}
		if (comboTipotransaccion.getItemSelected() == 2) {
			if (comboTipomonedaRecibido.getItemSelected() == comboTipomonedaEntregado.getItemSelected()) {
				validate = false;
			}
			if (comboTipomonedaEntregado.getItemSelected() == 1) {
				validate = false;
			}
		}
		return validate;
	}

	public void changeTipoTransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccioncompraventa tipotransaccionCompraVentaSelected = comboTipotransaccion.getObjectItemSelected(key);
		this.tipotransaccioncompraventa = tipotransaccionCompraVentaSelected;
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

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
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

	public boolean isValidBean() {
		return isValidBean;
	}

	public void setValidBean(boolean isValidBean) {
		this.isValidBean = isValidBean;
	}
	
	public void setBeanInvalid() {
		this.isValidBean = false;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Estadoapertura getEstadoaperturaCaja() {
		return estadoaperturaCaja;
	}

	public void setEstadoaperturaCaja(Estadoapertura estadoaperturaCaja) {
		this.estadoaperturaCaja = estadoaperturaCaja;
	}

	public Estadomovimiento getEstadomovimientoCaja() {
		return estadomovimientoCaja;
	}

	public void setEstadomovimientoCaja(Estadomovimiento estadomovimientoCaja) {
		this.estadomovimientoCaja = estadomovimientoCaja;
	}
}
