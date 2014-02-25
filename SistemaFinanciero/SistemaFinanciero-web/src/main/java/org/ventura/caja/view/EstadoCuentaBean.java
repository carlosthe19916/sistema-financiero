package org.ventura.caja.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.EstadoCuentaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.view.EstadocuentaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.DateUtil;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class EstadoCuentaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//datos generales
	private boolean isValidBean;
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private Estadoapertura estadoaperturaCaja;
	@Inject private Estadomovimiento estadomovimientoCaja;
	private boolean isCuentabancariaValid;
	
	//private boolean successCuentaAporte;
	private boolean esCuentaAporte;
	private boolean esCuentaBancaria;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean failure;
	
	// busqueda de cuenta
	private boolean dlgBusquedaCuentaAporteOpen;
	private boolean dlgBusquedaCuentaBancariaOpen;
	private String valorBusqueda;
	
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private TablaBean<CuentaaporteView> tablaCuentaaporte;
	@Inject private TablaBean<CuentabancariaView> tablaCuentabancaria;
	@Inject private CuentaaporteView cuentaaporteViewSelected;
	
	// agrupadores pagina principal
	@Inject
	private ComboBean<String> comboTipoCuenta;
	@Inject private TablaBean<EstadocuentaView> tablaEstadoCuenta;

	// Datos pagina principal
	private String numeroCuentaAporte;
	
	@Inject private CuentabancariaView cuentabancariaView;
	@Inject private	Cuentabancaria cuentabancaria;
	
	@EJB private EstadoCuentaServiceLocal estadocuentaServiceLocal;
	@EJB private CuentaaporteServiceLocal cuentaaporteServiceLocal;
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	public EstadoCuentaBean() {
		isValidBean = true;
		//successCuentaAporte = true;
		failure = false;
		dlgBusquedaCuentaAporteOpen = false;
		dlgBusquedaCuentaBancariaOpen = false;
		isCuentabancariaValid = true;
	}

	@PostConstruct
	private void initialize() {
		cargarDatosComboTipoCuenta();
		cargarDatosCaja();
		
		esCuentaAporte = true;
		esCuentaBancaria = false;
	}
	
	public void cargarDatosComboTipoCuenta(){
		this.getComboTipoCuenta().putItem(1, "CUENTA APORTE");
		this.getComboTipoCuenta().putItem(2, "CUENTA BANCARIA");
	}
	
	public void cargarDatosCaja(){
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
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);	
			Tipodocumento tipodocumento = ProduceObject.getTipodocumento(TipodocumentoType.DNI);
			comboTipodocumento.setItemSelected(tipodocumento);
		} catch (Exception e) {
			isValidBean = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void buscarTransaccionesEstadoCuenta() throws Exception{
		System.out.println("llegooo");
		try {
			List<EstadocuentaView> list = estadocuentaServiceLocal.getTransaccionesEstadoCuenta(cuentaaporteViewSelected.getNumerocuenta(), fechaInicio, fechaFin);
			tablaEstadoCuenta.setRows(list);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	public void searchCuentaAporte() {
		List<CuentaaporteView> cuentaaporteViews;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			if(tipodocumento != null){
				cuentaaporteViews = cuentaaporteServiceLocal.findCuentaaporteView(tipodocumento, valorBusqueda);				
			} else {
				cuentaaporteViews = cuentaaporteServiceLocal.findCuentaaporteView(valorBusqueda);
			}
			
			tablaCuentaaporte.setRows(cuentaaporteViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void findCuentaAporteByNumerocuenta() {
		CuentaaporteView cuentaaporteView;
		try {
			cuentaaporteView = cuentaaporteServiceLocal.findCuentaaporteViewByNumerocuenta(this.numeroCuentaAporte);
			if (cuentaaporteView != null) {
				this.cuentaaporteViewSelected = cuentaaporteView;	
			} else {
				this.cuentaaporteViewSelected = new CuentaaporteView();
				
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta aporte no encontrada","Cuenta aporte no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void searchCuentabancaria() {
		List<CuentabancariaView> cuentabancarias;
		try {
			Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
			if(tipodocumento != null){
				cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaView(comboTipodocumento.getObjectItemSelected(),valorBusqueda);				
			} else {
				cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaView(valorBusqueda);
			}
			
			if (cuentabancarias != null) {
				Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);					
				for (Iterator<CuentabancariaView> iterator = cuentabancarias.iterator(); iterator.hasNext();) {
					CuentabancariaView cuentabancariaView = (CuentabancariaView) iterator.next();
					int idTipocuentabancaria = cuentabancariaView.getIdTipocuentabancaria();
					if(tipocuentabancaria.getIdtipocuentabancaria() == idTipocuentabancaria){
						iterator.remove();
					}			
				}				
				this.getTablaCuentabancaria().setRows(cuentabancarias);				
			} else {
				this.getTablaCuentabancaria().clean();
				JsfUtil.addErrorMessage("No se encontro resultados");
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Error al buscar Cuenta bancaria");
		}
	}

	public void findCuentabancariaByNumerocuenta() {
		CuentabancariaView cuentabancariaView;
		try {
			cuentabancariaView = cuentabancariaServiceLocal.findCuentabancariaViewByNumerocuenta(getCuentabancaria().getNumerocuenta());
			if (cuentabancariaView != null) {
				this.setCuentabancariaView(cuentabancariaView);
				this.setCuentabancaria(cuentabancariaServiceLocal.find(cuentabancariaView.getIdCuentabancaria()));
				this.setCuentabancariaValid(true);
			} else {
				this.setCuentabancariaView(new CuentabancariaView());
				setCuentabancaria(new Cuentabancaria());
				this.setCuentabancariaValid(false);

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta bancaria no encontrada","Cuenta bancaria no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			this.setCuentabancariaValid(false);
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void tipoCuenta(){
		if (comboTipoCuenta.getItemSelected() == 1) {
			esCuentaAporte = true;
			esCuentaBancaria = false;
		}if (comboTipoCuenta.getItemSelected() == 2) {
			esCuentaBancaria = true;
			esCuentaAporte = false;
		}
	}
	
	public void setRowSelectToTransaccionCA() {	
		if (cuentaaporteViewSelected != null) {
			this.numeroCuentaAporte = cuentaaporteViewSelected.getNumerocuenta();
		} else {
			this.numeroCuentaAporte = "";
			failure = true;
			JsfUtil.addErrorMessage("No se pudo cargar la cuenta");
		}
		setDlgBusquedaCuentaAporteOpen(false);
	}
	
	public void setRowSelectToTransaccionCB() {	
		try {
			if (cuentabancariaView != null) {
				this.cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaView.getIdCuentabancaria());
				this.isCuentabancariaValid = true;
			} else {
				this.cuentabancariaView = new CuentabancariaView();
				cuentabancaria = new Cuentabancaria();
				this.isCuentabancariaValid = false;

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta bancaria no encontrada","Cuenta bancaria no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			this.isCuentabancariaValid = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
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
	
	public String getMonthName(Date date){
		return DateUtil.getMonthName(date);
	}
	
	public String getStringDate(Date date) {
		if (date != null) {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			return dt.format(date);
		} else {
			return "";
		}
	}
	
	public String returnNumOperacion(Integer numeroop){
		String numOperacion = null;
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

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
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

	public ComboBean<String> getComboTipoCuenta() {
		return comboTipoCuenta;
	}

	public void setComboTipoCuenta(ComboBean<String> comboTipoCuenta) {
		this.comboTipoCuenta = comboTipoCuenta;
	}

	/*public boolean isSuccessCuentaAporte() {
		return successCuentaAporte;
	}

	public void setSuccessCuentaAporte(boolean successCuentaAporte) {
		this.successCuentaAporte = successCuentaAporte;
	}*/

	public boolean isDlgBusquedaCuentaAporteOpen() {
		return dlgBusquedaCuentaAporteOpen;
	}

	public void setDlgBusquedaCuentaAporteOpen(boolean dlgBusquedaCuentaAporteOpen) {
		this.dlgBusquedaCuentaAporteOpen = dlgBusquedaCuentaAporteOpen;
	}

	public boolean isEsCuentaAporte() {
		return esCuentaAporte;
	}

	public void setEsCuentaAporte(boolean esCuentaAporte) {
		this.esCuentaAporte = esCuentaAporte;
	}

	public boolean isEsCuentaBancaria() {
		return esCuentaBancaria;
	}

	public void setEsCuentaBancaria(boolean esCuentaBancaria) {
		this.esCuentaBancaria = esCuentaBancaria;
	}

	public String getNumeroCuentaAporte() {
		return numeroCuentaAporte;
	}

	public void setNumeroCuentaAporte(String numeroCuentaAporte) {
		this.numeroCuentaAporte = numeroCuentaAporte;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TablaBean<EstadocuentaView> getTablaEstadoCuenta() {
		return tablaEstadoCuenta;
	}

	public void setTablaEstadoCuenta(TablaBean<EstadocuentaView> tablaEstadoCuenta) {
		this.tablaEstadoCuenta = tablaEstadoCuenta;
	}
	
	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public CuentabancariaView getCuentabancariaView() {
		return cuentabancariaView;
	}

	public void setCuentabancariaView(CuentabancariaView cuentabancariaView) {
		this.cuentabancariaView = cuentabancariaView;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	public boolean isCuentabancariaValid() {
		return isCuentabancariaValid;
	}

	public void setCuentabancariaValid(boolean isCuentabancariaValid) {
		this.isCuentabancariaValid = isCuentabancariaValid;
	}

	public boolean isDlgBusquedaCuentaBancariaOpen() {
		return dlgBusquedaCuentaBancariaOpen;
	}

	public void setDlgBusquedaCuentaBancariaOpen(
			boolean dlgBusquedaCuentaBancariaOpen) {
		this.dlgBusquedaCuentaBancariaOpen = dlgBusquedaCuentaBancariaOpen;
	}
}
