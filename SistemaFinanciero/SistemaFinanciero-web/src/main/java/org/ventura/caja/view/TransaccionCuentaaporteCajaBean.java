package org.ventura.caja.view;

import java.awt.print.PrinterJob;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

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
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.DateUtil;
import org.venturabank.util.JsfUtil;
import org.venturabank.util.PrintUtil;

@Named
@ViewScoped
public class TransaccionCuentaaporteCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB
	private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB
	private CuentaaporteServiceLocal cuentaaporteServiceLocal;
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

	private boolean isValidBean;
	private boolean isCuentabancariaValid;

	// busqueda de cuentabancaria
	@Inject
	private TablaBean<CuentaaporteView> tablaCuentaaporte;
	@Inject
	private ComboBean<String> comboTipobusqueda;
	private String valorBusqueda;
	@Inject
	private CuentaaporteView cuentaaporteViewSearched;
	
	@Inject
	private CuentaaporteView cuentaaporteView;

	// agrupadores pagina principal
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	private CalculadoraBean calculadoraBean;
	@Inject
	private TablaBean<AportesCuentaaporteView> tablaAportes;

	// Datos pagina principal
	@Inject
	private Tipotransaccion tipotransaccion;
	@Inject
	private Tipomoneda tipomoneda;
	@Inject
	private Moneda monto;
	private String numeroCuentabancaria;
	private String referencia;

	public TransaccionCuentaaporteCajaBean() {
		isValidBean = true;
		isCuentabancariaValid = true;
	}

	@PostConstruct
	private void initialize() {
		try {
			this.caja = cajaBean.getCaja();
			this.estadoaperturaCaja = caja.getEstadoapertura();
			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			if (historialcaja != null) {
				this.estadomovimientoCaja = historialcaja.getEstadomovimiento();
			} else {
				throw new Exception(
						"Caja no fue abierta correctamente, no tiene un historial activo");
			}

			validateBean();

			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccion.ALL_ACTIVE);
			comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
			comboTipobusqueda.putItem(1, "Dni");
			comboTipobusqueda.putItem(2, "Ruc");
			comboTipobusqueda.putItem(3, "Apellidos o Nombres");
			comboTipobusqueda.putItem(4, "Razon social");
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e,
					"El usuario o caja no tiene permitido realizar transacciones");
		}
	}

	public void validateBean() {
		if (caja == null) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("El usuario o caja no tiene permitido realizar transacciones");
		}
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
		if (!this.estadoaperturaCaja.equals(estadoapertura)) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("La caja debe de estar ABIERTA para realizar transacciones");
		}
		Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
		if (!this.estadomovimientoCaja.equals(estadomovimiento)) {
			setBeanInvalid();
			JsfUtil.addErrorMessage("La caja debe de estar DESCONGELADA para realizar transacciones");
		}	
	}

	public String createTransaccioncaja() {
		if (isCuentabancariaValid == true) {
			validateBean();
			if (isValidBean()) {
			
				Tipomoneda tipomoneda = new Tipomoneda();
				tipomoneda.setIdtipomoneda(cuentaaporteView.getIdTipomoneda());
				if (this.tipomoneda.equals(tipomoneda)) {
					
					AportesCuentaaporteView aportesCuentaaporteViewSelected;
					Object object = tablaAportes.getSelectedRow();
					if (object instanceof AportesCuentaaporteView) {
						
						aportesCuentaaporteViewSelected = (AportesCuentaaporteView) object;
						
						Transaccioncuentaaporte transaccioncuentaaporte = new Transaccioncuentaaporte();

						Cuentaaporte cuentaaporte = new Cuentaaporte();
						cuentaaporte.setNumerocuentaaporte(numeroCuentabancaria);

						transaccioncuentaaporte.setTipotransaccion(tipotransaccion);
						transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
						transaccioncuentaaporte.setMonto(monto);
						transaccioncuentaaporte.setReferencia(referencia);
						transaccioncuentaaporte.setTipomoneda(tipomoneda);
						transaccioncuentaaporte.setMesafecta(aportesCuentaaporteViewSelected.getId().getMes());
						
						try {
							transaccioncuentaaporte = transaccionCajaServiceLocal.createTransaccionCuentaaporte(caja,transaccioncuentaaporte);
							//VouchercajaView vouchercajaView = transaccionCajaServiceLocal.getVoucherTransaccionBancaria(transaccioncuentabancaria);
							//imprimirVoucher(vouchercajaView);
						} catch (Exception e) {
							JsfUtil.addErrorMessage(e, "Error al actualizar Caja");
							return "failure";
						}
						JsfUtil.addSuccessMessage("Aporte realizado");
						return "successTransaccionCuentaaporte?faces-redirect=true";					
					} else {
						JsfUtil.addErrorMessage("No se selecciono el mes de pago");
						return null;
					}				
				} else {
					JsfUtil.addErrorMessage("Los tipos de moneda son incompatibles");
					return null;
				}
			} else {
				JsfUtil.addErrorMessage("La caja no tiene permitido realizar transacciones");
				return null;
			}
		} else {
			JsfUtil.addErrorMessage("La cuenta bancaria no es valida");
			return null;
		}		
	}
	
	public void imprimirVoucher(VouchercajaView voucher){         
        try {	
        	Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("numeroOperacion", voucher.getNumeroOperacion().toString());
            parameters.put("fecha", voucher.getFecha());
            parameters.put("hora", voucher.getHora());
            parameters.put("title", voucher.getDenominacionTipotransaccion()+" EN CUENTA BANCARIA");
            parameters.put("tipoCuenta", voucher.getDenominacionTipocuentabancaria());
            parameters.put("numeroCuenta", voucher.getNumeroCuenta());
            parameters.put("titular", voucher.getTitular());
            parameters.put("referencia", voucher.getReferencia());
            parameters.put("moneda", voucher.getDenominacionMoneda());
            parameters.put("importe", voucher.getMonto().toString());
            parameters.put("itf", "todavia");
            parameters.put("codigoAgenciaCaja", voucher.getCodigoAgencia() + "|" + voucher.getAbreviaturaCaja());
            
        	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();           
            String serverContextPath = servletContext.getContextPath();
            
            String url = servletContext.getRealPath("WEB-INF/reports/Voucher.jasper");
            JasperReport jasperReport = PrintUtil.getJasperReport(url);      
            JasperPrint jp = PrintUtil.getJasperPrint(jasperReport, parameters);      
            
            PrinterJob job = PrinterJob.getPrinterJob();
    				
            PrintRequestAttributeSet printRequestAttributeSet = PrintUtil.getPrintRequest();
            PrintServiceAttributeSet printServiceAttributeSet = PrintUtil.getPrintService();
            
            JRPrintServiceExporter exporter = new JRPrintServiceExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
    		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,printRequestAttributeSet);
    		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,printServiceAttributeSet);
    		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,Boolean.FALSE);
    		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,Boolean.TRUE);
     
    		JasperPrintManager.printReport(jp, false);
    		 
    		//exporter.exportReport();
    		
            //job.print(printRequestAttributeSet);       
    	    
        } catch (JRException ex) {
        	// TODO Auto-generated catch block
			ex.printStackTrace();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
		
	}

	public void searchCuentabancaria() {
		List<CuentaaporteView> cuentabancarias;
		Integer value = comboTipobusqueda.getItemSelected();
		try {
			switch (value) {
				case 1:
					cuentabancarias = cuentaaporteServiceLocal.findCuentaaporteViewByDni(valorBusqueda);
					break;
				 case 2:
					 cuentabancarias = cuentaaporteServiceLocal.findCuentaaporteViewByRuc(valorBusqueda);
					 break;
				 case 3:
					 cuentabancarias = cuentaaporteServiceLocal.findCuentaaporteViewByNombre(valorBusqueda);
					 break;
				 case 4:
					 cuentabancarias = cuentaaporteServiceLocal.findCuentaaporteViewByRazonsocial(valorBusqueda);
					 break;
				 default: throw new Exception("Tipo de Busqueda no valida");
			}
			
			if (cuentabancarias != null) {
				this.tablaCuentaaporte.setRows(cuentabancarias);
			} else {
				this.tablaCuentaaporte.clean();
				JsfUtil.addErrorMessage("No se encontro resultados");
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Error al buscar Cuenta bancaria");
		}
	}

	public void findCuentabancariaByNumerocuenta() {
		CuentaaporteView cuentaaporteView;
		try {
			cuentaaporteView = cuentaaporteServiceLocal.findCuentaaporteViewByNumerocuenta(this.numeroCuentabancaria);
			if (cuentaaporteView != null) {
				this.cuentaaporteView = cuentaaporteView;
				setCuentabancariaValid();	
				
				//probando list para eliminar linea
				loadDetalleAportesCuenta();
			} else {
				this.cuentaaporteView = new CuentaaporteView();
				setCuentabancariaInvalid();

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta aporte no encontrada","Cuenta aporte no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			setCuentabancariaInvalid();
			JsfUtil.addErrorMessage("Error al buscar Cuenta aporte");
		}
	}

	public void loadDetalleAportesCuenta() {
		List<AportesCuentaaporteView> list;
		try {
			if (cuentaaporteView != null) {
				Integer idCuentaaporte = cuentaaporteView.getIdCuentaaporte();
				
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
		CuentaaporteView cuentaaporteView;
		Object object = tablaCuentaaporte.getSelectedRow();
		if (object instanceof CuentaaporteView) {
			cuentaaporteView = (CuentaaporteView) object;
			this.cuentaaporteViewSearched = cuentaaporteView;
		} else {
			this.cuentaaporteViewSearched = null;
			JsfUtil.addErrorMessage("No se pudo seleccionar cuenta");
		}
		
		if (cuentaaporteViewSearched != null) {
			this.cuentaaporteView = cuentaaporteViewSearched;
			this.numeroCuentabancaria = cuentaaporteViewSearched.getNumerocuenta();
			this.loadDetalleAportesCuenta();
		} else {
			this.cuentaaporteView = null;
			this.numeroCuentabancaria = "";
			JsfUtil.addErrorMessage("No se pudo cargar la cuenta");
		}
	}
	
	public void changeTipobusqueda(ValueChangeEvent event) {
		// Integer key = (Integer) event.getNewValue();
		// Tipotransaccion tipotransaccionSelected =
		// comboTipotransaccion.getObjectItemSelected(key);
		// this.tipotransaccion = tipotransaccionSelected;
	}

	public void changeTipotransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccion tipotransaccionSelected = comboTipotransaccion
				.getObjectItemSelected(key);
		this.tipotransaccion = tipotransaccionSelected;
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.tipomoneda = tipomonedaSelected;
		this.monto = new Moneda();
		loadDenominacionmonedaCalculadora();
	}

	public void setBeanInvalid() {
		this.isValidBean = false;
	}

	public void setCuentabancariaInvalid() {
		this.isCuentabancariaValid = false;
	}
	
	public void setCuentabancariaValid() {
		this.isCuentabancariaValid = true;
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

	public ComboBean<String> getComboTipobusqueda() {
		return comboTipobusqueda;
	}

	public void setComboTipobusqueda(ComboBean<String> comboTipobusqueda) {
		this.comboTipobusqueda = comboTipobusqueda;
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
		return cuentaaporteViewSearched;
	}

	public void setCuentaaporteViewSearched(
			CuentaaporteView cuentaaporteViewSearched) {
		this.cuentaaporteViewSearched = cuentaaporteViewSearched;
	}

	public CuentaaporteView getCuentaaporteView() {
		return cuentaaporteView;
	}

	public void setCuentaaporteView(CuentaaporteView cuentaaporteView) {
		this.cuentaaporteView = cuentaaporteView;
	}

	public TablaBean<AportesCuentaaporteView> getTablaAportes() {
		return tablaAportes;
	}

	public void setTablaAportes(TablaBean<AportesCuentaaporteView> tablaAportes) {
		this.tablaAportes = tablaAportes;
	}

}
