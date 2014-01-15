package org.ventura.cuentapersonal.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CancelacionAnticipadaCuentaplazofijoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean cuentaValida;
	private boolean cuentaCreada;
	
	//buscar cuenta
	private boolean dlgBusquedaOpen;
	private String campoBusqueda;
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private TablaBean<CuentabancariaView> tablaCuentabancaria;
	private CuentabancariaView cuentabancariaViewSelected;
	
	private BigDecimal interesGenerado;
	private BigDecimal totalSaldo;

	//datos de entrada
	private BigDecimal interesRecalculado;
	private BigDecimal totalRecalculado;
	private BigDecimal tea;
	private BigDecimal trea;
	private Date fechaRecalculo;
	private Integer periodoRecalculo;
	
	private Cuentabancaria cuentaPlazofijoCreado;
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private TasainteresServiceLocal tasainteresServiceLocal;
	
	public CancelacionAnticipadaCuentaplazofijoBean() {
		dlgBusquedaOpen = false;
	}

	@PostConstruct
	private void initialize(){	
		try {
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}		
	}

	public void cancelarCuentaaporte(){
		try {
			if(cuentaCreada == false){	
				/*Cuentabancaria cuentabancaria = new Cuentabancaria();
				cuentabancaria.setIdcuentabancaria(cuentabancariaViewSelected.getIdCuentabancaria());
				BigDecimal teaReal = tea.divide(new BigDecimal(100));
				BigDecimal treaReal = trea.divide(new BigDecimal(100));
				
				cuentaPlazofijoCreado = cuentabancariaServiceLocal.renovarCuentaplazofijo(cuentabancaria,periodoDeposito, teaReal, treaReal);
				cuentaCreada = true;*/
			}
		} catch (Exception e) {
			this.cuentaValida = false;
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void recalcularInteres(){
		BigDecimal result = BigDecimal.ZERO;
		try {
			if(cuentabancariaViewSelected != null){			
				if (tea != null) {
					int periodoDeposito = 0;
					Calendar calStart = Calendar.getInstance();
					Calendar calEnd = Calendar.getInstance();
					calStart.setTime(cuentabancariaViewSelected.getFechaaperturaCuentabancaria());
					calEnd.setTime(fechaRecalculo);
					
					long milis1 = calStart.getTimeInMillis();
					long milis2 = calEnd.getTimeInMillis();	
					long diff = milis2 - milis1;
					
					periodoDeposito = (int) (diff / (24 * 60 * 60 * 1000));
					this.periodoRecalculo = periodoDeposito;
					
					BigDecimal teaReal = tea.divide(new BigDecimal(100));
					result = tasainteresServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaViewSelected.getSaldoCuentabancaria(), periodoDeposito, teaReal);
					
					this.interesRecalculado = result;
					this.totalRecalculado = interesRecalculado.add(cuentabancariaViewSelected.getSaldoCuentabancaria());
				}		
			}			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void buscarCuentabancaria(){
		List<CuentabancariaView> cuentabancariaViews;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(TipocuentabancariaType.CUENTA_PLAZO_FIJO, tipodocumento, campoBusqueda);
			tablaCuentabancaria.setRows(cuentabancariaViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setCuentabancariaSelected(){
		if(cuentabancariaViewSelected != null){
			try {
				interesGenerado = cuentabancariaServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaViewSelected.getIdCuentabancaria());
				totalSaldo = interesGenerado.add(cuentabancariaViewSelected.getSaldoCuentabancaria());
				totalRecalculado = new BigDecimal(totalSaldo.toString());
			} catch (Exception e) {
				JsfUtil.addErrorMessage(e, e.getMessage());
				e.printStackTrace();
			}
		}	
		setDlgBusquedaOpen(false);
	}
	
	
	
	public void changeTipodocumento(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
	}
	
	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(
			TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public boolean isDlgBusquedaOpen() {
		return dlgBusquedaOpen;
	}

	public void setDlgBusquedaOpen(boolean dlgBusquedaOpen) {
		this.dlgBusquedaOpen = dlgBusquedaOpen;
	}

	public CuentabancariaView getCuentabancariaViewSelected() {
		return cuentabancariaViewSelected;
	}

	public void setCuentabancariaViewSelected(
			CuentabancariaView cuentabancariaViewSelected) {
		this.cuentabancariaViewSelected = cuentabancariaViewSelected;
	}

	public BigDecimal getInteresGenerado() {
		return interesGenerado;
	}

	public void setInteresGenerado(BigDecimal interesGenerado) {
		this.interesGenerado = interesGenerado;
	}

	public BigDecimal getTotalSaldo() {
		return totalSaldo;
	}

	public void setTotalSaldo(BigDecimal totalSaldo) {
		this.totalSaldo = totalSaldo;
	}

	public BigDecimal getTea() {
		return tea;
	}

	public void setTea(BigDecimal tea) {
		this.tea = tea;
	}

	public BigDecimal getTrea() {
		return trea;
	}

	public void setTrea(BigDecimal trea) {
		this.trea = trea;
	}

	public boolean isCuentaCreada() {
		return cuentaCreada;
	}

	public void setCuentaCreada(boolean cuentaCreada) {
		this.cuentaCreada = cuentaCreada;
	}

	public boolean isCuentaValida() {
		return cuentaValida;
	}

	public void setCuentaValida(boolean cuentaValida) {
		this.cuentaValida = cuentaValida;
	}

	public Cuentabancaria getCuentaPlazofijoCreado() {
		return cuentaPlazofijoCreado;
	}

	public void setCuentaPlazofijoCreado(Cuentabancaria cuentaPlazofijoCreado) {
		this.cuentaPlazofijoCreado = cuentaPlazofijoCreado;
	}

	public BigDecimal getInteresRecalculado() {
		return interesRecalculado;
	}

	public void setInteresRecalculado(BigDecimal interesRecalculado) {
		this.interesRecalculado = interesRecalculado;
	}

	public BigDecimal getTotalRecalculado() {
		return totalRecalculado;
	}

	public void setTotalRecalculado(BigDecimal totalRecalculado) {
		this.totalRecalculado = totalRecalculado;
	}

	public Date getFechaRecalculo() {
		return fechaRecalculo;
	}

	public void setFechaRecalculo(Date fechaRecalculo) {
		this.fechaRecalculo = fechaRecalculo;
	}

	public Integer getPeriodoRecalculo() {
		return periodoRecalculo;
	}

	public void setPeriodoRecalculo(Integer periodoRecalculo) {
		this.periodoRecalculo = periodoRecalculo;
	}

}
