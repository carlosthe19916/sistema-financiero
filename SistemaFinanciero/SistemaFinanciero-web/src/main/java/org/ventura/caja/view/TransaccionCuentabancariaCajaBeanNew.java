package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCuentabancariaCajaBeanNew implements Serializable {

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

	public TransaccionCuentabancariaCajaBeanNew() {
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
					try {
						Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();

						transaccioncuentabancaria.setTipotransaccion(comboTipotransaccion.getObjectItemSelected());
						transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
						transaccioncuentabancaria.setMonto(new Moneda(monto));
						transaccioncuentabancaria.setReferencia(referencia);
						transaccioncuentabancaria.setTipomoneda(comboTipomoneda.getObjectItemSelected());
		
						transaccioncuentabancaria = transaccionCajaServiceLocal.createTransaccionCuentabancaria(caja,transaccioncuentabancaria);
						success = true;
					} catch (Exception e) {
						JsfUtil.addErrorMessage(e.getMessage());
						failure = true;
					}
				} else {
					JsfUtil.addErrorMessage("Los tipos de moneda son incompatibles");
				}
			} else {
				JsfUtil.addErrorMessage("La cuenta bancaria no es valida");
			}
		} else {
			
		}
	}

	public void searchCuentabancaria() {
		List<CuentabancariaView> cuentabancarias;
		try {
			cuentabancarias = cuentabancariaServiceLocal.findCuentabancariaView(comboTipodocumento.getObjectItemSelected(),valorBusqueda);
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

	public void changeTipomoneda(ValueChangeEvent event) {	
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.monto = new BigDecimal("0.00");
		loadDenominacionmonedaCalculadora(tipomonedaSelected);
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

}
