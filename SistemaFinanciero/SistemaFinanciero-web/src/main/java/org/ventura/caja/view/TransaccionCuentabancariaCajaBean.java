package org.ventura.caja.view;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.view.JasperViewer;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.caja.dependent.BuscarCuentabancariaBean;
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
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.managedbean.session.CajaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB
	private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
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
	private TablaBean<CuentabancariaView> tablaCuentabancaria;
	@Inject
	private ComboBean<String> comboTipobusqueda;
	private String valorBusqueda;
	@Inject
	private CuentabancariaView cuentabancariaViewSearched;
	
	@Inject
	private CuentabancariaView cuentabancariaView;

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
				tipomoneda.setIdtipomoneda(cuentabancariaView.getIdTipomoneda());
				if (this.tipomoneda.equals(tipomoneda)) {
					Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();

					Cuentabancaria cuentabancaria = new Cuentabancaria();
					cuentabancaria.setNumerocuenta(numeroCuentabancaria);

					transaccioncuentabancaria.setTipotransaccion(tipotransaccion);
					transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
					transaccioncuentabancaria.setMonto(monto);
					transaccioncuentabancaria.setReferencia(referencia);
					transaccioncuentabancaria.setTipomoneda(tipomoneda);
	
					try {
						transaccioncuentabancaria = transaccionCajaServiceLocal.createTransaccionCuentabancaria(cajaBean.getCaja(),transaccioncuentabancaria);
						VouchercajaView vouchercajaView = transaccionCajaServiceLocal.getVoucherTransaccionBancaria(transaccioncuentabancaria);
						imprimirVoucher(vouchercajaView);
					} catch (Exception e) {
						JsfUtil.addErrorMessage(e, "Error al actualizar Caja");
						return "failure";
					}
					JsfUtil.addSuccessMessage("Caja Actualizada");
					return "success";

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
		JasperReport jr = null;
        String archivo = "C:\\Users\\TOSHIBA\\git\\sistema-financiero\\SistemaFinanciero\\SistemaFinanciero-web\\src\\main\\java\\org\\ventura\\reports\\Voucher.jasper";
       
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("numeroOperacion", voucher.getNumeroOperacion());
        parameters.put("fecha", voucher.getFecha());
        parameters.put("hora", voucher.getHora());
        parameters.put("title", voucher.getDenominacionTipotransaccion() + "EN CTA BANCARIA");
        parameters.put("tipoCuenta", voucher.getDenominacionTipocuentabancaria());
        parameters.put("numeroCuenta", voucher.getNumeroCuenta());
        parameters.put("titular", voucher.getTitular());
        parameters.put("referencia", voucher.getReferencia());
        parameters.put("moneda", voucher.getDenominacionMoneda());
        parameters.put("importe", voucher.getMonto().toString());
        parameters.put("itf", "todavia");
        parameters.put("codigoAgenciaCaja", voucher.getCodigoAgencia()+"|"+ voucher.getAbreviaturaCaja());
             
        try {	
            jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
            //JasperReport jr = (JasperReport)JRLoader.loadObject(new File("filename.jasper"));
            
            JasperPrint jp = JasperFillManager.fillReport(jr,parameters);
            //JasperPrintManager.printReport("archivo", false);

            //JasperViewer.viewReport(jp);
            /*PrinterJob job = PrinterJob.getPrinterJob();
            int selectedService = 0;
            selectedService = 0;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
            printRequestAttributeSet.add(MediaSizeName.ISO_A4); 
            MediaSizeName mediaSizeName = MediaSize.findMedia(64,25,MediaPrintableArea.MM);
            printRequestAttributeSet.add(mediaSizeName);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
            exporter.exportReport();
           
            job.print(printRequestAttributeSet);
			*/
            JasperPrint jasperPrint = getJasperPrint(voucher);
            JRSaver.saveObject(jasperPrint, "C:\\Users\\TOSHIBA\\git\\sistema-financiero\\SistemaFinanciero\\SistemaFinanciero-web\\src\\main\\java\\org\\ventura\\reports\\PrintServiceReport.jrprint");
            
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.ISO_A4);

            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
            //printServiceAttributeSet.add(new PrinterName("Epson Stylus 820 ESC/P 2", null));
            //printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6", null));
            //printServiceAttributeSet.add(new PrinterName("PDFCreator", null));
            
            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
            
            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, "C:\\Users\\TOSHIBA\\git\\sistema-financiero\\SistemaFinanciero\\SistemaFinanciero-web\\src\\main\\java\\org\\ventura\\reports\\PrintServiceReport.jrprint");
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
            
            exporter.exportReport();
    	    
        } catch (JRException ex) {
        	// TODO Auto-generated catch block
			ex.printStackTrace();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
	}
	
	private static JasperPrint getJasperPrint(VouchercajaView voucher) throws JRException
	  {
	   JasperPrint jp = null;
	   
	   JasperReport jr = null;
       String archivo = "C:\\Users\\TOSHIBA\\git\\sistema-financiero\\SistemaFinanciero\\SistemaFinanciero-web\\src\\main\\java\\org\\ventura\\reports\\Voucher.jasper";
      
       Map<String, Object> parameters = new HashMap<String, Object>();
       
       parameters.put("numeroOperacion", voucher.getNumeroOperacion());
       parameters.put("fecha", voucher.getFecha());
       parameters.put("hora", voucher.getHora());
       parameters.put("title", voucher.getDenominacionTipotransaccion() + "EN CTA BANCARIA");
       parameters.put("tipoCuenta", voucher.getDenominacionTipocuentabancaria());
       parameters.put("numeroCuenta", voucher.getNumeroCuenta());
       parameters.put("titular", voucher.getTitular());
       parameters.put("referencia", voucher.getReferencia());
       parameters.put("moneda", voucher.getDenominacionMoneda());
       parameters.put("importe", voucher.getMonto().toString());
       parameters.put("itf", "todavia");
       parameters.put("codigoAgenciaCaja", voucher.getCodigoAgencia()+"|"+ voucher.getAbreviaturaCaja());
            
       try {	
			jr = (JasperReport) JRLoader.loadObjectFromFile(archivo);
			jp = JasperFillManager.fillReport(jr, parameters);
			return jp;
		} catch (Exception e) {

		}
	return jp;
	}
	
	public void print(){
		long start = System.currentTimeMillis();
	    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	    printRequestAttributeSet.add(MediaSizeName.ISO_A4);

	    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
	    //printServiceAttributeSet.add(new PrinterName("Epson Stylus 820 ESC/P 2", null));
	    //printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6", null));
	    //printServiceAttributeSet.add(new PrinterName("PDFCreator", null));
	    
	    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
	    
	    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, "build/reports/PrintServiceReport.jrprint");
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
	    
	    try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}

	public void searchCuentabancaria() {
		List<CuentabancariaView> cuentabancarias;
		Integer value = comboTipobusqueda.getItemSelected();
		try {
			switch (value) {
				case 1:
					cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaViewByDni(valorBusqueda);
					break;
				 case 2:
					 cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaViewByRuc(valorBusqueda);
					 break;
				 case 3:
					 cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaViewByNombre(valorBusqueda);
					 break;
				 case 4:
					 cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaViewByRazonsocial(valorBusqueda);
					 break;
				 default: throw new Exception("Tipo de Busqueda no valida");
			}
			
			if (cuentabancarias != null) {
				this.tablaCuentabancaria.setRows(cuentabancarias);
			} else {
				this.tablaCuentabancaria.clean();
				JsfUtil.addErrorMessage("No se encontro resultados");
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Error al buscar Cuenta bancaria");
		}
	}

	public void findCuentabancariaByNumerocuenta() {
		CuentabancariaView cuentabancariaView;
		try {
			cuentabancariaView = cuentabancariaServiceLocal.findCuentabancariaViewByNumerocuenta(this.numeroCuentabancaria);
			if (cuentabancariaView != null) {
				this.cuentabancariaView = cuentabancariaView;
				setCuentabancariaValid();
			} else {
				this.cuentabancariaView = new CuentabancariaView();
				setCuentabancariaInvalid();

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuenta bancaria no encontrada","Cuenta bancaria no encontrada");
				FacesContext.getCurrentInstance().addMessage("msgBuscarCuentabancaria", facesMsg);
			}
		} catch (Exception e) {
			setCuentabancariaInvalid();
			JsfUtil.addErrorMessage("Error al buscar Cuenta bancaria");
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
			e.printStackTrace();
		}
	}

	public void setRowSelect() {
		CuentabancariaView cuentabancariaView;
		Object object = tablaCuentabancaria.getSelectedRow();
		if (object instanceof CuentabancariaView) {
			cuentabancariaView = (CuentabancariaView) object;
			this.cuentabancariaViewSearched = cuentabancariaView;
		} else {
			this.cuentabancariaViewSearched = null;
			JsfUtil.addErrorMessage("No se pudo seleccionar cuenta");
		}
	}
	
	public void setRowSelectToTransaccion() {
		if (cuentabancariaViewSearched != null) {
			this.cuentabancariaView = cuentabancariaViewSearched;
			this.numeroCuentabancaria = cuentabancariaViewSearched.getNumerocuenta();
		} else {
			this.cuentabancariaView = null;
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

	public CuentabancariaView getCuentabancariaView() {
		return cuentabancariaView;
	}

	public void setCuentabancariaView(CuentabancariaView cuentabancariaView) {
		this.cuentabancariaView = cuentabancariaView;
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

	public CuentabancariaView getCuentabancariaViewSearched() {
		return cuentabancariaViewSearched;
	}

	public void setCuentabancariaViewSearched(
			CuentabancariaView cuentabancariaViewSearched) {
		this.cuentabancariaViewSearched = cuentabancariaViewSearched;
	}

}
