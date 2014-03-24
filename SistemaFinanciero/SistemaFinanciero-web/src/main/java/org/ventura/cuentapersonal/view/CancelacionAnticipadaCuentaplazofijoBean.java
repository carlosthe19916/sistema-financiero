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
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;
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
	private Cuentabancaria cuentabancaria;
	
	private BigDecimal interesCuenta;
	private BigDecimal totalCuenta;
	private Integer periodoCuenta;
	//private BigDecimal treaCuenta;
	private BigDecimal teaCuenta;

	//datos de entrada
	private BigDecimal interesRecalculado;
	private BigDecimal totalRecalculado;
	private BigDecimal teaRecalculo;
	//private BigDecimal treaRecalculo;
	private Date fechaRecalculo;
	private Integer periodoRecalculo;
	
	private Cuentabancaria cuentaPlazofijoCreado;
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private TasainteresServiceLocal tasainteresServiceLocal;
	
	public CancelacionAnticipadaCuentaplazofijoBean() {
		cuentaValida = true;
		cuentaCreada = false;
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
				
				Cuentabancaria cuentabancaria = new Cuentabancaria();
				cuentabancaria.setIdcuentabancaria(cuentabancariaViewSelected.getIdCuentabancaria());
				BigDecimal teaReal = teaRecalculo.divide(new BigDecimal(100));
				//BigDecimal treaReal = treaRecalculo.divide(new BigDecimal(100));
				
				cuentaPlazofijoCreado = cuentabancariaServiceLocal.recalculoCuentaplazofijo(cuentabancaria, fechaRecalculo, teaReal);
				cuentaCreada = true;
			}
		} catch (Exception e) {
			this.cuentaValida = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void recalcularInteres(){
		BigDecimal result = BigDecimal.ZERO;
		try {
			if(cuentabancariaViewSelected != null){			
				if (teaRecalculo != null) {
					Calendar calStart = Calendar.getInstance();
					Calendar calEnd = Calendar.getInstance();
					calStart.setTime(cuentabancariaViewSelected.getFechaaperturaCuentabancaria());
					calEnd.setTime(fechaRecalculo);
					
					long milis1 = calStart.getTimeInMillis();
					long milis2 = calEnd.getTimeInMillis();	
					long diff = milis2 - milis1;
					
					this.periodoRecalculo = (int) (diff / (24 * 60 * 60 * 1000));
								
					BigDecimal teaReal = teaRecalculo.divide(new BigDecimal(100));
					result = tasainteresServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaViewSelected.getSaldoCuentabancaria(), periodoRecalculo, teaReal);
					
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
				this.cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaViewSelected.getIdCuentabancaria());
								
				interesCuenta = cuentabancariaServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaViewSelected.getIdCuentabancaria());
				totalCuenta = interesCuenta.add(cuentabancariaViewSelected.getSaldoCuentabancaria());
				totalRecalculado = new BigDecimal(totalCuenta.toString());
				
				interesRecalculado = new BigDecimal(interesCuenta.toString());
				totalRecalculado = new BigDecimal(totalRecalculado.toString());
				
				fechaRecalculo = cuentabancariaViewSelected.getFechacierreCuentabancaria();
				
				Calendar calStart = Calendar.getInstance();
				Calendar calEnd = Calendar.getInstance();
				calStart.setTime(cuentabancariaViewSelected.getFechaaperturaCuentabancaria());
				calEnd.setTime(cuentabancariaViewSelected.getFechacierreCuentabancaria());
				
				calStart.set(Calendar.HOUR, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				calStart.set(Calendar.MILLISECOND, 0);		
				calEnd.set(Calendar.HOUR, 0);
				calEnd.set(Calendar.MINUTE, 0);
				calEnd.set(Calendar.SECOND, 0);
				calEnd.set(Calendar.MILLISECOND, 0);
				
				long milis1 = calStart.getTimeInMillis();
				long milis2 = calEnd.getTimeInMillis();	
				long diff = milis2 - milis1;
				
				periodoCuenta = (int) (diff / (24 * 60 * 60 * 1000));
				periodoRecalculo = new Integer(periodoCuenta);
				
				//cargar las tasas de interes para la cuenta
				this.teaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TEA, cuentabancariaViewSelected.getIdCuentabancaria());
				//this.treaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TREA, cuentabancariaViewSelected.getIdCuentabancaria());
				
				teaCuenta = teaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				//treaCuenta = treaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				
				teaCuenta = teaCuenta.setScale(2);
				//treaCuenta = treaCuenta.setScale(2);
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

	public boolean isCuentaValida() {
		return cuentaValida;
	}

	public void setCuentaValida(boolean cuentaValida) {
		this.cuentaValida = cuentaValida;
	}

	public boolean isCuentaCreada() {
		return cuentaCreada;
	}

	public void setCuentaCreada(boolean cuentaCreada) {
		this.cuentaCreada = cuentaCreada;
	}

	public boolean isDlgBusquedaOpen() {
		return dlgBusquedaOpen;
	}

	public void setDlgBusquedaOpen(boolean dlgBusquedaOpen) {
		this.dlgBusquedaOpen = dlgBusquedaOpen;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(
			TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public BigDecimal getInteresCuenta() {
		return interesCuenta;
	}

	public void setInteresCuenta(BigDecimal interesCuenta) {
		this.interesCuenta = interesCuenta;
	}

	public BigDecimal getTotalCuenta() {
		return totalCuenta;
	}

	public void setTotalCuenta(BigDecimal totalCuenta) {
		this.totalCuenta = totalCuenta;
	}

	public Integer getPeriodoCuenta() {
		return periodoCuenta;
	}

	public void setPeriodoCuenta(Integer periodoCuenta) {
		this.periodoCuenta = periodoCuenta;
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

	public BigDecimal getTeaRecalculo() {
		return teaRecalculo;
	}

	public void setTeaRecalculo(BigDecimal teaRecalculo) {
		this.teaRecalculo = teaRecalculo;
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

	public Cuentabancaria getCuentaPlazofijoCreado() {
		return cuentaPlazofijoCreado;
	}

	public void setCuentaPlazofijoCreado(Cuentabancaria cuentaPlazofijoCreado) {
		this.cuentaPlazofijoCreado = cuentaPlazofijoCreado;
	}

	public CuentabancariaView getCuentabancariaViewSelected() {
		return cuentabancariaViewSelected;
	}

	public void setCuentabancariaViewSelected(
			CuentabancariaView cuentabancariaViewSelected) {
		this.cuentabancariaViewSelected = cuentabancariaViewSelected;
	}

	public BigDecimal getTeaCuenta() {
		return teaCuenta;
	}

	public void setTeaCuenta(BigDecimal teaCuenta) {
		this.teaCuenta = teaCuenta;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}
	


}
