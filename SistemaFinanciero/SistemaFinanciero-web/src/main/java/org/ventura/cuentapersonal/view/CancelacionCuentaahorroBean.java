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

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CancelacionCuentaahorroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean success;
	private boolean failure;
	
	private CuentabancariaView cuentabancariaView;
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
	
	public CancelacionCuentaahorroBean() {
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
	
	public void cancelarCuentaahorro(){
		failure = false;
		if(success == false){
			try {
				Cuentabancaria cuentabancaria =  new Cuentabancaria();
				cuentabancaria.setIdcuentabancaria(cuentabancariaView.getIdCuentabancaria());
				
				transaccioncuentabancaria = cuentabancariaServiceLocal.cancelarCuentaahorro(caja, cuentabancaria, fechaCancelacion);
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
	
	public void buscarCuentaahorro(){
		List<CuentabancariaView> cuentabancariaViews = null;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			if(tipodocumento != null){
				cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(TipocuentabancariaType.CUENTA_AHORRO,tipodocumento, valorBusqueda);				
			} else {
				cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(TipocuentabancariaType.CUENTA_AHORRO, valorBusqueda);
			}		
			tablaCuentabancaria.setRows(cuentabancariaViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());			
		}
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

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
}
