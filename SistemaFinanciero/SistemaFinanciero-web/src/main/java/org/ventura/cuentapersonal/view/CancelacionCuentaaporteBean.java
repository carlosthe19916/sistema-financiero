package org.ventura.cuentapersonal.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CancelacionCuentaaporteBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean success;
	private boolean failure;
	
	private CuentaaporteView cuentaaporteView;
	private Date fechaCancelacion;
	private Transaccioncuentaaporte transaccioncuentaaporte;
	private VouchercajaCuentaaporteView vouchercajaCuentaaporteView;

	private boolean isDialogOpen;
	private String valorBusqueda;
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private TablaBean<CuentaaporteView> tablaCuentaaporte;
	
	@Inject CajaBean cajaBean;
	@Inject Caja caja;
	
	@EJB private CuentaaporteServiceLocal cuentaaporteServiceLocal;
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	
	public CancelacionCuentaaporteBean() {
		Calendar calendar = Calendar.getInstance();
		fechaCancelacion = calendar.getTime();
		
		success = false;
		failure = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception {
		try {
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);	
			
			Tipodocumento tipodocumento = ProduceObject.getTipodocumento(TipodocumentoType.DNI);
			comboTipodocumento.setItemSelected(tipodocumento);
			
			caja = cajaBean.getCaja();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void cancelarCuentaaporte(){
		failure = false;
		if(success == false){
			try {
				Cuentaaporte cuentaaporte =  new Cuentaaporte();
				cuentaaporte.setIdcuentaaporte(cuentaaporteView.getIdCuentaaporte());
				
				transaccioncuentaaporte = cuentaaporteServiceLocal.cancelarCuentaaporte(caja, cuentaaporte, fechaCancelacion);
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
			this.vouchercajaCuentaaporteView = transaccionCajaServiceLocal.getVoucherTransaccionCuentaaporte(transaccioncuentaaporte);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			failure = true;
		}
	}
	
	public void buscarCuentaaporte(){
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
		}
	}
	
	public String returnNumOperacion(){
		String numOperacion = null;
		Integer numeroop = vouchercajaCuentaaporteView.getNumerooperacionTransaccioncaja();
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
	
	public String getStringTime(Date date) {
		if(date != null){
			SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
		    return ft.format(date);
		} else {
			return null;
		}	  
	}
	
	public CuentaaporteView getCuentaaporteView() {
		return cuentaaporteView;
	}

	public void setCuentaaporteView(CuentaaporteView cuentaaporteView) {
		this.cuentaaporteView = cuentaaporteView;
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

	public TablaBean<CuentaaporteView> getTablaCuentaaporte() {
		return tablaCuentaaporte;
	}

	public void setTablaCuentaaporte(TablaBean<CuentaaporteView> tablaCuentaaporte) {
		this.tablaCuentaaporte = tablaCuentaaporte;
	}

	public boolean isDialogOpen() {
		return isDialogOpen;
	}

	public void setDialogOpen(boolean isDialogOpen) {
		this.isDialogOpen = isDialogOpen;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
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

	public VouchercajaCuentaaporteView getVouchercajaCuentaaporteView() {
		return vouchercajaCuentaaporteView;
	}

	public void setVouchercajaCuentaaporteView(
			VouchercajaCuentaaporteView vouchercajaCuentaaporteView) {
		this.vouchercajaCuentaaporteView = vouchercajaCuentaaporteView;
	}
}
