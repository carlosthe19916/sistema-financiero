package org.ventura.caja.view;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.TipocambioServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.view.ViewvouchercompraventaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.session.CajaBean;
import org.ventura.session.UsuarioMB;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.ProduceObjectTipocambio;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCompraVentaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TransaccionCajaServiceLocal transaccionCompraVentaServiceLocal;
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB
	private TipocambioServiceLocal tipocambioServiceLocal;
	@EJB 
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB 
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	
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
	private TasaCambio tipoCambio;
	@Inject
	private LoginBean loginBean;
	
	private ViewvouchercompraventaView voucherCompraVenta;
	
	private boolean isValidBean;
	private String dniRuc;
	private String nombresRazonSocial;
	private boolean validateSaldoTotalCaja;
	private boolean pageVoucher;
	private boolean loginsuccess;
	
	@Inject
	private UsuarioMB usuarioMB;
	
	public TransaccionCompraVentaCajaBean(){
		isValidBean = true;
		setValidateSaldoTotalCaja(true);
	}
	
	@PostConstruct
	private void initialize(){
		loginsuccess = false;
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
			cargarCombosTipoMoneda();
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e,
					"El usuario o caja no tiene permitido realizar transacciones");
		}
	}
	
	public void cargarCombosTipoMoneda(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcaja", cajaBean.getCaja().getIdcaja());
		comboTipomonedaEntregado.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE_BY_CAJA, parameters);
		comboTipomonedaRecibido.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE_BY_CAJA, parameters);
	}
	
	public void calculateMontoTotal() {
		Moneda cero = new Moneda();
		if (montoRecibido.isGreaterThan(cero)) {
			Moneda money = montoRecibido.multiply(tipoCambio);
			setMontoEntregado(money);
		} else {
			montoRecibido = new Moneda();
			montoEntregado = new Moneda();
		}
		
		
		
	}
	
	/*public void calculateMontoRecibido(){
		Moneda cero = new Moneda();
		if (montoEntregado.isGreaterThan(cero)) {
			Moneda money = montoEntregado.multiply(tipoCambio);
			setMontoRecibido(money);
		}else {
			montoRecibido = new Moneda();
			montoEntregado = new Moneda();
		}
	}*/
	
	public void loadDenominacionmonedaCalculadora() {
		List<Denominacionmoneda> list;
		try {
			Tipomoneda tipomonedaRecibido = this.tipomonedaRecibido;
			if (tipomonedaRecibido != null) {
				list = denominacionmonedaServiceLocal.getDenominacionmonedasActive(tipomonedaRecibido);
			} else {
				list = new ArrayList<Denominacionmoneda>();
			}
			calculadoraBeanRecibido.setDenominaciones(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					transaccionCompraVenta.setDniRuc(dniRuc);
					transaccionCompraVenta.setNombresRazonSocial(nombresRazonSocial);
					transaccionCompraVenta.setTipocambio(tipoCambio);
					transaccionCompraVenta.setTipomonedaEntregado(tipomonedaEntregado);
					transaccionCompraVenta.setTipomonedaRecibido(tipomonedaRecibido);
					transaccionCompraVenta.setEstado(true);
					
					if (isCompra()) {
						transaccionCompraVenta.setMontorecibido(montoRecibido);
						transaccionCompraVenta.setMontoentregado(montoEntregado);
					} if (isVenta()) {
						transaccionCompraVenta.setMontorecibido(montoEntregado);
						transaccionCompraVenta.setMontoentregado(montoRecibido);
					}

					if (validateSaldoCaja(caja, transaccionCompraVenta)) {
						try {
							transaccionCompraVenta = transaccionCompraVentaServiceLocal.createTransaccionCompraVenta(cajaBean.getCaja(),transaccionCompraVenta, usuarioMB.getUsuario());
							ViewvouchercompraventaView vouchercompraventaView = transaccionCompraVentaServiceLocal.getVoucherTransaccionCompraVentaMoneda(transaccionCompraVenta);
							setVoucherCompraVenta(vouchercompraventaView);
						} catch (Exception e) {
							
						}
						pageVoucher = true;
						return null;
					}else {
						JsfUtil.addErrorMessage("La caja no tiene suficiente dinero para realizar la transacción");
						return null;
					}
			}else {
				JsfUtil.addErrorMessage("Error, Ud. no puede comprar o vender el Nuevo Sol y verifique que los tipos de monedas sean diferentes.");
				return null;
			}
		}else{
			JsfUtil.addErrorMessage("La caja no tiene permitido realizar transacciones");
			return null;
		}
	}

	public boolean validateSaldoCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) {
		boolean result = false;
		try {
			result = transaccionCompraVentaServiceLocal.validateSaldoBovedaCaja(caja, transaccioncompraventa);
		} catch (Exception e) {
		}
		setValidateSaldoTotalCaja(result);
		return result;
	}

	public void cagarTipoCambio(){
		Moneda monto = new Moneda();
		TasaCambio tasacambio = null;
		Tipotasa tipoTasa = null;
		
		try {
			if (comboTipotransaccion.getItemSelected() != -1 && comboTipomonedaRecibido.getItemSelected() != -1 && comboTipomonedaEntregado.getItemSelected() != -1) {
				if (comboTipotransaccion.getItemSelected() == 1) {
					tipoTasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
				}if (comboTipotransaccion.getItemSelected() == 2) {
					tipoTasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
				}
				tasacambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipoTasa, monto, tipomonedaRecibido, tipomonedaEntregado); 
				setTipoCambio(tasacambio);
			}
		} catch (Exception e) {}
	} 
	
	public String returnNumOperacion(){
		String numOperacion = null;
		Integer numeroop = voucherCompraVenta.getNumeroOperacion();
		if (numeroop > 0 && numeroop < 10) {
			numOperacion = "000" + numeroop;
		}if (numeroop >= 10 && numeroop < 100) {
			numOperacion = "00" + numeroop;
		}if (numeroop >= 100 && numeroop < 1000) {
			numOperacion = "0" + numeroop;
		}if (numeroop >= 1000) {
			numOperacion = "" + numeroop;
		}
		return numOperacion;
	}
	
	public String extornarTransaccionCompraVenta(){
		try {
			Transaccioncompraventa transaccioncompraventa;
			Caja cajas;
			transaccioncompraventa = transaccionCompraVentaServiceLocal.find(voucherCompraVenta.getIdTransaccioncompraventa());
			transaccioncompraventa.setEstado(false);
			cajas = cajaServiceLocal.find(voucherCompraVenta.getIdCaja());
			transaccionCompraVentaServiceLocal.extornarTransaccionCompraVenta(cajas, transaccioncompraventa);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al extornar la transacción");
			return "failure";
		}
		return null;
	}
	
	//recupera los nombres y apellidos o la razon social de la persona juridica
	public void retornarNombresRazonSocial() throws Exception{
		Integer dr = dniRuc.length();
		try {
			if(dr==8){
				Tipodocumento dni = ProduceObject.getTipodocumento(TipodocumentoType.DNI);
				Personanatural pn = personanaturalServiceLocal.find(dni, dniRuc);
				if(pn == null){
					setNombresRazonSocial("");
				}
				setNombresRazonSocial(pn.getApellidopaterno()+" "+pn.getApellidomaterno()+" "+pn.getNombres());
			}
			if(dr==11){
				Tipodocumento ruc = ProduceObject.getTipodocumento(TipodocumentoType.RUC);
				Personajuridica pj = personajuridicaServiceLocal.find(ruc, dniRuc);
				if(pj == null){
					setNombresRazonSocial("");
				}
				setNombresRazonSocial(pj.getRazonsocial());
			}
		} catch (Exception e) {
		}
	}
	
	//valida que la compra o venta no sea del nuevo sol o tipos de monedas iguales
	public boolean validateCompraVenta() {
		boolean validate = true;
		if (isCompra()) {
			if (comboTipomonedaRecibido.getItemSelected() == comboTipomonedaEntregado.getItemSelected()) {
				validate = false;
			}
			if (isRecibidaNuevoSol()) {
				validate = false;
			}
		}
		if (isVenta()) {
			if (comboTipomonedaRecibido.getItemSelected() == comboTipomonedaEntregado.getItemSelected()) {
				validate = false;
			}
			if (isEntregadoNuevoSol()) {
				validate = false;
			}
		}
		return validate;
	}
	
	public boolean isCompra(){
		boolean result = false;
		if (comboTipotransaccion.getItemSelected()==1) {
			result = true;
		}
		return result;
	}
	
	public boolean isVenta(){
		boolean result = false;
		if (comboTipotransaccion.getItemSelected()==2) {
			result = true;
		}
		return result;
	}
	
	public boolean isRecibidaNuevoSol(){
		boolean result = false;
		if (comboTipomonedaRecibido.getItemSelected() == 1) {
			result = true;
		}
		return result;
	}
	public boolean isEntregadoNuevoSol(){
		boolean result = false;
		if (comboTipomonedaEntregado.getItemSelected() == 1) {
			result = true;
		}
		return result;
	}
	
	public void changeTipoTransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccioncompraventa tipotransaccionCompraVentaSelected = comboTipotransaccion.getObjectItemSelected(key);
		this.tipotransaccioncompraventa = tipotransaccionCompraVentaSelected;
		this.montoRecibido = new Moneda();
		this.montoEntregado = new Moneda();
	}

	public void changeTipomonedaEntregado(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomonedaEntregado.getObjectItemSelected(key);
		this.tipomonedaEntregado = tipomonedaSelected;
		this.montoEntregado = new Moneda();
		this.montoRecibido = new Moneda();
	}
	
	public void changeTipomonedaRecibido(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomonedaRecibido.getObjectItemSelected(key);
		this.tipomonedaRecibido = tipomonedaSelected;
		this.montoRecibido = new Moneda();
		this.montoEntregado = new Moneda();
		loadDenominacionmonedaCalculadora();
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
	
	public void setMontoFromCalculadora() {
		Moneda result = this.calculadoraBeanRecibido.getTotal();
		this.montoRecibido = result;
	}

	public CalculadoraBean getCalculadoraBeanRecibido() {
		return calculadoraBeanRecibido;
	}

	public void setCalculadoraBeanRecibido(CalculadoraBean calculadoraBeanRecibido) {
		this.calculadoraBeanRecibido = calculadoraBeanRecibido;
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

	public String getDniRuc() {
		return dniRuc;
	}

	public void setDniRuc(String dniRuc) {
		this.dniRuc = dniRuc;
	}

	public String getNombresRazonSocial() {
		return nombresRazonSocial;
	}

	public void setNombresRazonSocial(String nombresRazonSocial) {
		this.nombresRazonSocial = nombresRazonSocial;
	}

	public TasaCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TasaCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public boolean isValidateSaldoTotalCaja() {
		return validateSaldoTotalCaja;
	}

	public void setValidateSaldoTotalCaja(boolean validateSaldoTotalCaja) {
		this.validateSaldoTotalCaja = validateSaldoTotalCaja;
	}

	public boolean isPageVoucher() {
		return pageVoucher;
	}

	public void setPageVoucher(boolean pageVoucher) {
		this.pageVoucher = pageVoucher;
	}

	public ViewvouchercompraventaView getVoucherCompraVenta() {
		return voucherCompraVenta;
	}

	public void setVoucherCompraVenta(ViewvouchercompraventaView voucherCompraVenta) {
		this.voucherCompraVenta = voucherCompraVenta;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean isLoginsuccess() {
		return loginsuccess;
	}

	public void setLoginsuccess(boolean loginsuccess) {
		this.loginsuccess = loginsuccess;
	}
}
