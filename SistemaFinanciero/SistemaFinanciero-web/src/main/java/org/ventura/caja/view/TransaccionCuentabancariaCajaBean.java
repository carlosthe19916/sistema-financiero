package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.ventura.boundary.local.CuentabancariaServiceLocal;
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
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CajaBean cajaBean;
	@Inject
	private Caja caja;

	private boolean isValidBean;
	private boolean isCuentabancariaValid;
	private boolean success;
	private boolean failure;

	// busqueda de cuentabancaria
	@Inject
	private TablaBean<CuentabancariaView> tablaCuentabancaria;
	@Inject
	private ComboBean<Tipodocumento> comboTipodocumento;
	private String valorBusqueda;

	// pagina principal
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	Cuentabancaria cuentabancaria;
	@Inject
	private CuentabancariaView cuentabancariaView;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	BigDecimal monto;
	private String referencia;

	private Transaccioncuentabancaria transaccioncuentabancaria;
	private VouchercajaView vouchercajaView;
	
	// calculadora
	@Inject
	private CalculadoraBean calculadoraBean;

	// servicios
	@EJB
	private DenominacionmonedaServiceLocal denominacionmonedaServiceLocal;
	@EJB
	private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB
	private CajaServiceLocal cajaServiceLocal;

	public TransaccionCuentabancariaCajaBean() {
		isValidBean = true;
		isCuentabancariaValid = true;
		
		success = false;
		failure = false;

		monto = new BigDecimal("0.00");
	}

	@PostConstruct
	private void initialize() {
		try {
			this.caja = cajaBean.getCaja();
			if (caja != null) {
				Estadoapertura estadoaperturaView = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
				if (!caja.getEstadoapertura().equals(estadoaperturaView)) {
					throw new Exception("La caja debe de estar ABIERTA");
				}
			} else {
				throw new Exception("El usuario no tiene asignado cajas");
			}

			Historialcaja historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			if (historialcaja != null) {
				Estadomovimiento estadoaperturaDB = historialcaja.getEstadomovimiento();
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
				if (!estadoaperturaDB.equals(estadomovimiento)) {
					throw new Exception("La caja debe de estar DESCONGELADA");
				}
			} else {
				throw new Exception("Caja no fue abierta correctamente");
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
				Tipomoneda tipomoneda = new Tipomoneda();
				tipomoneda.setIdtipomoneda(cuentabancariaView.getIdTipomoneda());
				if (comboTipomoneda.getObjectItemSelected().equals(tipomoneda)) {
					Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
					if(!tipocuentabancaria.equals(cuentabancaria.getTipocuentabancaria())){
						if(monto.compareTo(BigDecimal.ZERO) > 0){
							try {
								Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();

								transaccioncuentabancaria.setTipotransaccion(comboTipotransaccion.getObjectItemSelected());
								transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
								transaccioncuentabancaria.setMonto(new Moneda(monto));
								transaccioncuentabancaria.setReferencia(referencia);
								transaccioncuentabancaria.setTipomoneda(comboTipomoneda.getObjectItemSelected());
				
								this.transaccioncuentabancaria = transaccionCajaServiceLocal.createTransaccionCuentabancaria(caja,transaccioncuentabancaria);
								success = true;
								cargarVoucher();
							} catch (Exception e) {
								JsfUtil.addErrorMessage(e.getMessage());
								failure = true;
							}
						} else {
							failure = true;
							JsfUtil.addErrorMessage("El monto:" + monto + " no es un monto válido");
						}					
					} else {
						failure = true;
						JsfUtil.addErrorMessage("No se pueden realizar transacciones en cuentas a PLAZO FIJO a travez de esta ventana");
					}				
				} else {
					failure = true;
					JsfUtil.addErrorMessage("Los tipos de moneda son incompatibles");
				}
			} else {
				failure = true;
				JsfUtil.addErrorMessage("La cuenta bancaria no es valida");
			}
		} else {
			cargarVoucher();
		}
	}
	
	public void cargarVoucher(){
		try {
			this.vouchercajaView = transaccionCajaServiceLocal.getVoucherTransaccionBancaria(transaccioncuentabancaria);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			failure = true;
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
			cuentabancariaView = cuentabancariaServiceLocal.findCuentabancariaViewByNumerocuenta(cuentabancaria.getNumerocuenta());
			if (cuentabancariaView != null) {
				this.cuentabancariaView = cuentabancariaView;
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

	public void loadDenominacionmonedaCalculadora(Tipomoneda tipomoneda) {	
		List<Denominacionmoneda> list;
		try {
			if (tipomoneda != null) {
				list = denominacionmonedaServiceLocal.getDenominacionmonedasActive(tipomoneda);
			} else {
				list = new ArrayList<Denominacionmoneda>();
			}
			calculadoraBean.setDenominaciones(list);
		} catch (Exception e) {
			 JsfUtil.addErrorMessage(e.getMessage());
		}	 
	}

	public void setRowSelect() {
		/*
		 * CuentabancariaView cuentabancariaView; Object object =
		 * tablaCuentabancaria.getSelectedRow(); if (object instanceof
		 * CuentabancariaView) { cuentabancariaView = (CuentabancariaView)
		 * object; this.cuentabancariaViewSearched = cuentabancariaView; } else
		 * { this.cuentabancariaViewSearched = null;
		 * JsfUtil.addErrorMessage("No se pudo seleccionar cuenta"); }
		 */
	}

	public void setRowSelectToTransaccion() {	
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
	
	public void validarMontoTransaccion(){
		if (monto.compareTo(BigDecimal.ZERO)<=0) {
			monto = new BigDecimal("0.00");
		}
	}
	
	public boolean isRetiro(){
		Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
		boolean result = false;
		if (retiro.equals(comboTipotransaccion.getObjectItemSelected())) {
			result = true;
		}
		return result;
	}
	
	public String returnNumOperacion(){
		String numOperacion = null;
		Integer numeroop = vouchercajaView.getNumeroOperacion();
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

	public void changeTipomoneda(ValueChangeEvent event) {	
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.monto = new BigDecimal("0.00");
		loadDenominacionmonedaCalculadora(tipomonedaSelected);
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public void setMontoFromCalculadora() {
		Moneda result = this.calculadoraBean.getTotal();
		this.monto = result.getValue();
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

	public CajaBean getCajaBean() {
		return cajaBean;
	}

	public void setCajaBean(CajaBean cajaBean) {
		this.cajaBean = cajaBean;
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

	public boolean isCuentabancariaValid() {
		return isCuentabancariaValid;
	}

	public void setCuentabancariaValid(boolean isCuentabancariaValid) {
		this.isCuentabancariaValid = isCuentabancariaValid;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(
			TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(
			ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	public CuentabancariaView getCuentabancariaView() {
		return cuentabancariaView;
	}

	public void setCuentabancariaView(CuentabancariaView cuentabancariaView) {
		this.cuentabancariaView = cuentabancariaView;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Transaccioncuentabancaria getTransaccioncuentabancaria() {
		return transaccioncuentabancaria;
	}

	public void setTransaccioncuentabancaria(
			Transaccioncuentabancaria transaccioncuentabancaria) {
		this.transaccioncuentabancaria = transaccioncuentabancaria;
	}

	public VouchercajaView getVouchercajaView() {
		return vouchercajaView;
	}

	public void setVouchercajaView(VouchercajaView vouchercajaView) {
		this.vouchercajaView = vouchercajaView;
	}

}
