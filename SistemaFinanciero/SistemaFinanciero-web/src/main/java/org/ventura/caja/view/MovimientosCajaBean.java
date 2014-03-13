package org.ventura.caja.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.view.CajaMovimientoView;
import org.ventura.session.CajaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class MovimientosCajaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private boolean failure;
	
	private Integer idTransaccioncaja;
	@Inject private TablaBean<CajaMovimientoView> tablaMovimientos;
	@Inject private CajaMovimientoView cajaMovimientoViewSelected;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	
	@Inject LoginBean loginBean;
	
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	
	public MovimientosCajaBean() {
		idTransaccioncaja = null;
		success = false;
		failure = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{
		try {
			caja = cajaBean.getCaja();
			
			List<CajaMovimientoView> list = transaccionCajaServiceLocal.getTransaccionesCajaWithHistorialActivo(caja);
			tablaMovimientos.setRows(list);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}
	}
	
	public void buscarTransaccioncaja(){
		try {
			List<CajaMovimientoView> list = transaccionCajaServiceLocal.buscarTransaccionCaja(caja, idTransaccioncaja);
			tablaMovimientos.setRows(list);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public void autorizarExtornacion(CajaMovimientoView cajaMovimientoView){
		cajaMovimientoViewSelected = cajaMovimientoView;
		loginBean.setDlgLogin(true);
	}
	
	public void extornarTransaccioncaja(){
		try {
			if(loginBean.isAutenticado() == true){
				transaccionCajaServiceLocal.extornarTransaccion(cajaMovimientoViewSelected);
				success = true;
				failure = false;
				JsfUtil.addSuccessMessage("Transaccion:" + cajaMovimientoViewSelected.getIdTransaccioncaja() + "extornada");
			}	
		} catch (Exception e) {
			failure = true;
			success = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}
	
	public Integer getIdTransaccioncaja() {
		return idTransaccioncaja;
	}

	public void setIdTransaccioncaja(Integer idTransaccioncaja) {
		this.idTransaccioncaja = idTransaccioncaja;
	}

	public TablaBean<CajaMovimientoView> getTablaMovimientos() {
		return tablaMovimientos;
	}

	public void setTablaMovimientos(TablaBean<CajaMovimientoView> tablaMovimientos) {
		this.tablaMovimientos = tablaMovimientos;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
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
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public CajaMovimientoView getCajaMovimientoViewSelected() {
		return cajaMovimientoViewSelected;
	}

	public void setCajaMovimientoViewSelected(
			CajaMovimientoView cajaMovimientoViewSelected) {
		this.cajaMovimientoViewSelected = cajaMovimientoViewSelected;
	}

}
