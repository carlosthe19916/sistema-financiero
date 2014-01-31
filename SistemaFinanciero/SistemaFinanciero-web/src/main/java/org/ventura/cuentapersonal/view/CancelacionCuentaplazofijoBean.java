package org.ventura.cuentapersonal.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CancelacionCuentaplazofijoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean success;
	private boolean failure;
	
	private BigDecimal totalCuenta;
	private BigDecimal interesCuenta;
	private Integer periodoCuenta;
	private BigDecimal treaCuenta;
	private BigDecimal teaCuenta;
	private CuentabancariaView cuentabancariaView;
	private Cuentabancaria cuentabancaria;
	private Date fechaCancelacion;
	private Transaccioncuentabancaria transaccioncuentabancaria;
	private VouchercajaView vouchercajaView;

	private boolean isDialogOpen;
	private String valorBusqueda;
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private TablaBean<CuentabancariaView> tablaCuentabancaria;
	
	@Inject CajaBean cajaBean;
	@Inject Caja caja;
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	
	public CancelacionCuentaplazofijoBean() {
		Calendar calendar = Calendar.getInstance();
		fechaCancelacion = calendar.getTime();
		
		success = false;
		failure = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception {
		try {
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);	
			
			caja = cajaBean.getCaja();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void cancelarCuentaplazofijo(){
		failure = false;
		if(success == false){
			try {
				Cuentabancaria cuentabancaria =  new Cuentabancaria();
				cuentabancaria.setIdcuentabancaria(cuentabancariaView.getIdCuentabancaria());
				
				transaccioncuentabancaria = cuentabancariaServiceLocal.cancelarCuentaplazofijo(caja, cuentabancaria, fechaCancelacion);
				success = true;
				failure = false;
				loadVoucher();
			} catch (Exception e) {
				failure = true;
				JsfUtil.addErrorMessage(e.getMessage());
			}
		} else {
			loadVoucher();
		}
	}
	
	public void loadVoucher(){
		try {
			this.vouchercajaView = transaccionCajaServiceLocal.getVoucherTransaccionBancaria(transaccioncuentabancaria);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			failure = true;
		}
	}
	
	public void buscarCuentabancaria(){
		List<CuentabancariaView> cuentabancariaViews;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			if(tipodocumento != null){
				cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(tipodocumento, valorBusqueda);				
			} else {
				cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(valorBusqueda);
			}		
			if(cuentabancariaViews != null){
				Tipocuentabancaria tipocuentabancariaPF = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
				for (Iterator<CuentabancariaView> iterator = cuentabancariaViews.iterator(); iterator.hasNext();) {
					CuentabancariaView cuentabancariaView = (CuentabancariaView) iterator.next();
					int idCuentabancaria = cuentabancariaView.getIdTipocuentabancaria();
					if(idCuentabancaria != tipocuentabancariaPF.getIdtipocuentabancaria()){
						iterator.remove();
					}
				}								
			}
			tablaCuentabancaria.setRows(cuentabancariaViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());			
		}
	}
	
	public void setCuentabancariaSelected(){
		if(cuentabancariaView != null){
			try {
				this.cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaView.getIdCuentabancaria());
				
				interesCuenta = cuentabancariaServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaView.getIdCuentabancaria());
				totalCuenta = interesCuenta.add(cuentabancariaView.getSaldoCuentabancaria());
				
				Calendar calStart = Calendar.getInstance();
				Calendar calEnd = Calendar.getInstance();
				calStart.setTime(cuentabancariaView.getFechaaperturaCuentabancaria());
				calEnd.setTime(cuentabancariaView.getFechacierreCuentabancaria());
				
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
				
				//cargar las tasas de interes para la cuenta
				this.teaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TEA, cuentabancariaView.getIdCuentabancaria());
				this.treaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TREA, cuentabancariaView.getIdCuentabancaria());
				
				teaCuenta = teaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				treaCuenta = treaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
			} catch (Exception e) {
				JsfUtil.addErrorMessage(e, e.getMessage());			
			}
		}	
		setDialogOpen(false);
	}
	
	public String getStringTime(Date date) {
		if(date != null){
			SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
		    return ft.format(date);
		} else {
			return null;
		}	  
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

	public CuentabancariaView getCuentabancariaView() {
		return cuentabancariaView;
	}

	public void setCuentabancariaView(CuentabancariaView cuentabancariaView) {
		this.cuentabancariaView = cuentabancariaView;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
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

	public boolean isDialogOpen() {
		return isDialogOpen;
	}

	public void setDialogOpen(boolean isDialogOpen) {
		this.isDialogOpen = isDialogOpen;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
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

	public BigDecimal getTreaCuenta() {
		return treaCuenta;
	}

	public void setTreaCuenta(BigDecimal treaCuenta) {
		this.treaCuenta = treaCuenta;
	}

	public BigDecimal getTeaCuenta() {
		return teaCuenta;
	}

	public void setTeaCuenta(BigDecimal teaCuenta) {
		this.teaCuenta = teaCuenta;
	}

	public BigDecimal getInteresCuenta() {
		return interesCuenta;
	}

	public void setInteresCuenta(BigDecimal interesCuenta) {
		this.interesCuenta = interesCuenta;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}
	
	
}
