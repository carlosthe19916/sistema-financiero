package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;
import org.venturabank.util.DateUtil;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCuentaaporteCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private Estadomovimiento estadomovimientoCaja;
	@Inject private Estadoapertura estadoaperturaCaja;
	
	private boolean isValidBean;
	private boolean isCuentabancariaValid;
	private boolean success;
	private boolean failure;
	
	// busqueda de cuentabancaria
	private boolean dlgBusquedaCuentaOpen;
	@Inject private TablaBean<CuentaaporteView> tablaCuentaaporte;
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private CuentaaporteView cuentaaporteViewSelected;
	private String valorBusqueda;

	// agrupadores pagina principal
	@Inject private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject private CalculadoraBean calculadoraBean;
	@Inject private TablaBean<AportesCuentaaporteView> tablaAportes;

	// Datos pagina principal
	@Inject
	private Tipotransaccion tipotransaccion; 
	@Inject
	private Tipomoneda tipomoneda;
	@Inject private Moneda monto;
	private String numeroCuentabancaria;
	private String referencia;
	
	private Transaccioncuentaaporte transaccioncuentaaporte;
	private VouchercajaCuentaaporteView vouchercajaCuentaaporteView;
	
	@EJB private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB private CuentaaporteServiceLocal cuentaaporteServiceLocal;
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	public TransaccionCuentaaporteCajaBean() {
		isValidBean = true;
		isCuentabancariaValid = true;
		success = false;
		failure = false;
		dlgBusquedaCuentaOpen = false;
	}

	@PostConstruct
	private void initialize() {
		try {
			this.caja = cajaBean.getCaja();
			if (caja == null) {
				throw new Exception("El usuario o caja no tiene asinado cajas");
			}	
			this.estadoaperturaCaja = caja.getEstadoapertura();
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
			if (!this.estadoaperturaCaja.equals(estadoapertura)) {
				throw new Exception("La caja debe de estar ABIERTA para realizar transacciones");
			}
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			if (historialcaja != null) {
				this.estadomovimientoCaja = historialcaja.getEstadomovimiento();
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
				if (!this.estadomovimientoCaja.equals(estadomovimiento)) {
					throw new Exception("La caja debe de estar DESCONGELADA para realizar transacciones");
				}	
			} else {		
				throw new Exception("Caja no fue abierta correctamente, no tiene un historial activo");
			}
			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccion.ALL_ACTIVE);
			comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);	
		} catch (Exception e) {
			isValidBean = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public void createTransaccioncaja() {
		if (success == false) {
			if (isCuentabancariaValid == true) {
				Tipotransaccion tipotransaccion = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
				if(tipotransaccion.equals(comboTipotransaccion.getObjectItemSelected())){
					Tipomoneda tipomoneda = new Tipomoneda();
					tipomoneda.setIdtipomoneda(cuentaaporteViewSelected.getIdTipomoneda());
					if (this.tipomoneda.equals(tipomoneda)) {
						if(monto.isGreaterThan(new Moneda(BigDecimal.ZERO))){
							AportesCuentaaporteView aportesCuentaaporteViewSelected;
							Object object = tablaAportes.getSelectedRow();
							if (object instanceof AportesCuentaaporteView) {
								try {
									aportesCuentaaporteViewSelected = (AportesCuentaaporteView) object;
			
									Transaccioncuentaaporte transaccioncuentaaporte = new Transaccioncuentaaporte();
			
									Cuentaaporte cuentaaporte = new Cuentaaporte();
									cuentaaporte.setNumerocuentaaporte(numeroCuentabancaria);
			
									transaccioncuentaaporte.setTipotransaccion(comboTipotransaccion.getObjectItemSelected());
									transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
									transaccioncuentaaporte.setMonto(monto);
									transaccioncuentaaporte.setReferencia(referencia);
									transaccioncuentaaporte.setTipomoneda(tipomoneda);
									transaccioncuentaaporte.setMesafecta(aportesCuentaaporteViewSelected.getId().getMes());
					
									transaccioncuentaaporte = transaccionCajaServiceLocal.createTransaccionCuentaaporte(caja,transaccioncuentaaporte);
									
									this.transaccioncuentaaporte = transaccioncuentaaporte;
									success = true;
									cargarVoucher();
								} catch (Exception e) {
									JsfUtil.addErrorMessage(e.getMessage());
								}	
							} else {
								failure = true;
								JsfUtil.addErrorMessage("No se selecciono el mes de pago");
							}
						} else {
							failure = true;
							JsfUtil.addErrorMessage("El monto:" + monto + " no es un monto válido");
						}
					} else {
						failure = true;
						JsfUtil.addErrorMessage("Los tipos de moneda son incompatibles");
					}
				} else {
					failure = true;
					JsfUtil.addErrorMessage("No se permiten RETIROS de una cuenta de aportes. Debe de cancelar la cuenta para realizar la operación");
				}		
			} else {
				failure = true;
				JsfUtil.addErrorMessage("La cuenta de aoprtes no es valida");
			}
		} else {
			cargarVoucher();
		}
	}

	public void cargarVoucher(){
		try {
			this.vouchercajaCuentaaporteView = transaccionCajaServiceLocal.getVoucherTransaccionCuentaaporte(transaccioncuentaaporte);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			failure = true;
		}
	}
	
	public void searchCuentabancaria() {
		List<CuentaaporteView> cuentaaporteViews;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			cuentaaporteViews = cuentaaporteServiceLocal.findCuentaaporteView(tipodocumento, valorBusqueda);
			tablaCuentaaporte.setRows(cuentaaporteViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}

	public void findCuentabancariaByNumerocuenta() {
		CuentaaporteView cuentaaporteView;
		try {
			cuentaaporteView = cuentaaporteServiceLocal.findCuentaaporteViewByNumerocuenta(this.numeroCuentabancaria);
			if (cuentaaporteView != null) {
				this.cuentaaporteViewSelected = cuentaaporteView;
				isCuentabancariaValid = true;	
	
				//probando list para eliminar linea
				loadDetalleAportesCuenta();
			} else {
				this.cuentaaporteViewSelected = new CuentaaporteView();
				isCuentabancariaValid = false;

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta aporte no encontrada","Cuenta aporte no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			isCuentabancariaValid = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public void loadDetalleAportesCuenta() {
		List<AportesCuentaaporteView> list;
		try {
			if (cuentaaporteViewSelected != null) {
				Integer idCuentaaporte = cuentaaporteViewSelected.getIdCuentaaporte();
				
				Date startDate;			
				Date endDate;
				
				Calendar aux = Calendar.getInstance();
				aux.add(Calendar.YEAR,-1);
				startDate = aux.getTime();
				
				Calendar aux2 = Calendar.getInstance();
				aux2.add(Calendar.MONTH, 2);
				endDate = aux2.getTime();
				
				list = cuentaaporteServiceLocal.getTableAportesPorpagar(idCuentaaporte, startDate, endDate);
			} else {
				list = new ArrayList<AportesCuentaaporteView>();
			}
			tablaAportes.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMonthName(Date date){
		return DateUtil.getMonthName(date);
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
			e.printStackTrace();
		}
	}
	
	public void setRowSelectToTransaccion() {	
		if (cuentaaporteViewSelected != null) {
			this.numeroCuentabancaria = cuentaaporteViewSelected.getNumerocuenta();
			this.loadDetalleAportesCuenta();
		} else {
			this.numeroCuentabancaria = "";
			JsfUtil.addErrorMessage("No se pudo cargar la cuenta");
		}
		setDlgBusquedaCuentaOpen(false);
	}
	
	public void changeTipobusqueda(ValueChangeEvent event) {
		// Integer key = (Integer) event.getNewValue();
		// Tipotransaccion tipotransaccionSelected =
		// comboTipotransaccion.getObjectItemSelected(key);
		// this.tipotransaccion = tipotransaccionSelected;
	}

	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
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

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public String getNumeroCuentabancaria() {
		return numeroCuentabancaria;
	}

	public void setNumeroCuentabancaria(String numeroCuentabancaria) {
		this.numeroCuentabancaria = numeroCuentabancaria;
	}

	public ComboBean<Tipodocumento> getComboTipobusqueda() {
		return comboTipodocumento;
	}

	public void setComboTipobusqueda(ComboBean<Tipodocumento> comboTipobusqueda) {
		this.comboTipodocumento = comboTipobusqueda;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public boolean isValidBean() {
		return isValidBean;
	}

	public void setValidBean(boolean isValidBean) {
		this.isValidBean = isValidBean;
	}

	public Estadomovimiento getEstadomovimientoCaja() {
		return estadomovimientoCaja;
	}

	public void setEstadomovimientoCaja(Estadomovimiento estadomovimientoCaja) {
		this.estadomovimientoCaja = estadomovimientoCaja;
	}

	public Estadoapertura getEstadoaperturaCaja() {
		return estadoaperturaCaja;
	}

	public void setEstadoaperturaCaja(Estadoapertura estadoaperturaCaja) {
		this.estadoaperturaCaja = estadoaperturaCaja;
	}

	public boolean isCuentabancariaValid() {
		return isCuentabancariaValid;
	}

	public void setCuentabancariaValid(boolean isCuentabancariaValid) {
		this.isCuentabancariaValid = isCuentabancariaValid;
	}

	public TablaBean<CuentaaporteView> getTablaCuentaaporte() {
		return tablaCuentaaporte;
	}

	public void setTablaCuentaaporte(TablaBean<CuentaaporteView> tablaCuentaaporte) {
		this.tablaCuentaaporte = tablaCuentaaporte;
	}

	public CuentaaporteView getCuentaaporteViewSearched() {
		return cuentaaporteViewSelected;
	}

	public void setCuentaaporteViewSearched(
			CuentaaporteView cuentaaporteViewSearched) {
		this.cuentaaporteViewSelected = cuentaaporteViewSearched;
	}

	public TablaBean<AportesCuentaaporteView> getTablaAportes() {
		return tablaAportes;
	}

	public void setTablaAportes(TablaBean<AportesCuentaaporteView> tablaAportes) {
		this.tablaAportes = tablaAportes;
	}

	public boolean isDlgBusquedaCuentaOpen() {
		return dlgBusquedaCuentaOpen;
	}

	public void setDlgBusquedaCuentaOpen(boolean dlgBusquedaCuentaOpen) {
		this.dlgBusquedaCuentaOpen = dlgBusquedaCuentaOpen;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public CuentaaporteView getCuentaaporteViewSelected() {
		return cuentaaporteViewSelected;
	}

	public void setCuentaaporteViewSelected(
			CuentaaporteView cuentaaporteViewSelected) {
		this.cuentaaporteViewSelected = cuentaaporteViewSelected;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public Transaccioncuentaaporte getTransaccioncuentaaporte() {
		return transaccioncuentaaporte;
	}

	public void setTransaccioncuentaaporte(
			Transaccioncuentaaporte transaccioncuentaaporte) {
		this.transaccioncuentaaporte = transaccioncuentaaporte;
	}

	public VouchercajaCuentaaporteView getVouchercajaCuentaaporteView() {
		return vouchercajaCuentaaporteView;
	}

	public void setVouchercajaCuentaaporteView(
			VouchercajaCuentaaporteView vouchercajaCuentaaporteView) {
		this.vouchercajaCuentaaporteView = vouchercajaCuentaaporteView;
	}

}
