package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.CalculadoraBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
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
import org.ventura.session.UsuarioMB;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipodocumentoType;
import org.ventura.util.maestro.VariableSistemaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioMB usuarioMB;
	@Inject
	private CajaBean cajaBean;
	@Inject
	private Caja caja;

	private boolean isCuentabancariaValid;
	private boolean success;
	private boolean failure;
	private boolean isTitular;

	private boolean isOperacionMayorCuantia;
	@Inject private OperacionMayorCuantiaBean operacionMayorCuantiaBean;
	
	// busqueda de cuentabancaria
	@Inject
	private TablaBean<CuentabancariaView> tablaCuentabancaria;
	@Inject
	private ComboBean<Tipodocumento> comboTipodocumento;
	private String valorBusqueda;
	private boolean dlgBusquedaCuentaOpen;

	// pagina principal
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	Cuentabancaria cuentabancaria;
	@Inject
	private CuentabancariaView cuentabancariaView;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	private Moneda monto;
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
	@EJB
	private MaestrosServiceLocal maestrosServiceLocal;
	
	public TransaccionCuentabancariaCajaBean() {
		isCuentabancariaValid = true;
		success = false;
		failure = false;
		monto = new Moneda();
		isTitular = false;
		dlgBusquedaCuentaOpen = false;
		
		isOperacionMayorCuantia = false;
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
			
			Tipodocumento tipodocumento = ProduceObject.getTipodocumento(TipodocumentoType.DNI);
			comboTipodocumento.setItemSelected(tipodocumento);
		} catch (Exception e) {
			failure = true;
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
						if(monto.getValue().compareTo(BigDecimal.ZERO) > 0){
							
							BigDecimal montoMaximoTransaccion = null;
							TipomonedaType tipomonedaType = ProduceObject.getTipomoneda(comboTipomoneda.getObjectItemSelected()) ;
							try {
								switch (tipomonedaType) {
								case NUEVO_SOL:
									montoMaximoTransaccion = maestrosServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_NUEVO_SOL).getValor();
									break;
								case DOLAR:
									montoMaximoTransaccion = maestrosServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_DOLAR).getValor();
									break;
								case EURO:
									montoMaximoTransaccion = maestrosServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_EURO).getValor();
									break;
								default:
									break;
								}
							} catch (Exception e) {
								JsfUtil.addErrorMessage(e.getMessage());
								failure = true;
							}
							
							//validar si la operacion es de mayor cuantia
							if(monto.isGreaterThanOrEqual(new Moneda(montoMaximoTransaccion)) && isOperacionMayorCuantia == false){
								isOperacionMayorCuantia = true;
								
								Tipotransaccion tipotransaccion = comboTipotransaccion.getObjectItemSelected();
								String numeroCuenta = cuentabancariaView.getNumerocuenta();
								Tipomoneda moneda = comboTipomoneda.getObjectItemSelected();
								BigDecimal importe = monto.getValue();
								
								this.operacionMayorCuantiaBean.setTipotransaccion(tipotransaccion.getDenominacion());
								this.operacionMayorCuantiaBean.setCuentaBeneficiario(numeroCuenta);
								this.operacionMayorCuantiaBean.setTipomoneda(moneda);
								this.operacionMayorCuantiaBean.setMonto(importe);
								
								//beneficiario
								Tipodocumento tipodocumentoBeneficiario = new Tipodocumento();
								tipodocumentoBeneficiario.setIdtipodocumento(cuentabancariaView.getIdTipodocumento());
								tipodocumentoBeneficiario.setDenominacion(cuentabancariaView.getDenominacionTipodocumento());
								tipodocumentoBeneficiario.setAbreviatura(cuentabancariaView.getAbreviaturaTipodocumento());
								this.operacionMayorCuantiaBean.getComboTipodocumentoBeneficiario().setItemSelected(tipodocumentoBeneficiario);
								this.operacionMayorCuantiaBean.setNumerodocumentoBeneficiario(cuentabancariaView.getNumeroDocumento());
								this.operacionMayorCuantiaBean.setApellidosnombresRazonsocialBeneficiario(cuentabancariaView.getSocio());
								this.operacionMayorCuantiaBean.setDireccionBeneficiario(cuentabancariaView.getDireccionSocio());
								this.operacionMayorCuantiaBean.setTelefonoBeneficiario(cuentabancariaView.getTelefonoSocio());
								this.operacionMayorCuantiaBean.setFechanacimientoConstitucionBeneficiario(cuentabancariaView.getFechaNacConstSocio());
								this.operacionMayorCuantiaBean.setOcupacionActividadEconomicaBeneficiario(cuentabancariaView.getOcupacionActividadSocio());
							} else {
								try {
									Map<Denominacionmoneda, Integer> detalleTranssaccion = calculadoraBean.getDenominaciones();
									
									Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();

									transaccioncuentabancaria.setTipotransaccion(comboTipotransaccion.getObjectItemSelected());
									transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
									transaccioncuentabancaria.setMonto(monto);
									transaccioncuentabancaria.setReferencia(referencia);
									transaccioncuentabancaria.setTipomoneda(comboTipomoneda.getObjectItemSelected());
					
									Tipotransaccion tipotransaccion = comboTipotransaccion.getObjectItemSelected();								
									TipoTransaccionType tipoTransaccionType = ProduceObject.getTipotransaccion(tipotransaccion);
									switch (tipoTransaccionType) {
									case DEPOSITO:
										this.transaccioncuentabancaria = transaccionCajaServiceLocal.deposito(caja, cuentabancaria, transaccioncuentabancaria,detalleTranssaccion, usuarioMB.getUsuario(), this.operacionMayorCuantiaBean.getTransaccionmayorcuantiaObject());
										break;
									case RETIRO:
										this.transaccioncuentabancaria = transaccionCajaServiceLocal.retiro(caja, cuentabancaria, transaccioncuentabancaria,detalleTranssaccion, usuarioMB.getUsuario());
										break;
									default:
										break;
									}								
									success = true;
									cargarVoucher();
								} catch (Exception e) {
									JsfUtil.addErrorMessage(e.getMessage());
									failure = true;
								}
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
				comboTipomoneda.setItemSelected(cuentabancaria.getTipomoneda());
				this.monto = new Moneda();
				loadDenominacionmonedaCalculadora(cuentabancaria.getTipomoneda());
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
		if (monto.isLessThan(new Moneda())) {
			monto = new Moneda();
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
		this.monto = new Moneda();
		loadDenominacionmonedaCalculadora(tipomonedaSelected);
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public void setMontoFromCalculadora() {
		Moneda result = this.calculadoraBean.getTotal();
		this.monto = result;
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

	public String getMontoAsString() {
		return monto.toString();
	}
	
	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
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

	public boolean isTitular() {
		return isTitular;
	}

	public void setTitular(boolean isTitular) {
		this.isTitular = isTitular;
	}

	public boolean isDlgBusquedaCuentaOpen() {
		return dlgBusquedaCuentaOpen;
	}

	public void setDlgBusquedaCuentaOpen(boolean dlgBusquedaCuentaOpen) {
		this.dlgBusquedaCuentaOpen = dlgBusquedaCuentaOpen;
	}

	public boolean isOperacionMayorCuantia() {
		return isOperacionMayorCuantia;
	}

	public void setOperacionMayorCuantia(boolean isOperacionMayorCuantia) {
		this.isOperacionMayorCuantia = isOperacionMayorCuantia;
	}

	public OperacionMayorCuantiaBean getOperacionMayorCuantiaBean() {
		return operacionMayorCuantiaBean;
	}

	public void setOperacionMayorCuantiaBean(
			OperacionMayorCuantiaBean operacionMayorCuantiaBean) {
		this.operacionMayorCuantiaBean = operacionMayorCuantiaBean;
	}
}
