package org.ventura.caja.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detalletransaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncaja;
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
	
	private boolean dlgDetalle;
	private List<Detalletransaccioncaja> listDetalleTransaccion;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	
	@Inject LoginBean loginBean;
	
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	
	public MovimientosCajaBean() {
		idTransaccioncaja = null;
		success = false;
		failure = false;
		
		dlgDetalle = false;
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
	
	public void verDetalleTransaccion(CajaMovimientoView cajaMovimientoView){
		try {
			Integer idTransaccioncaja = cajaMovimientoView.getIdTransaccioncaja();
			Transaccioncaja transaccioncaja = new Transaccioncaja();
			transaccioncaja.setIdtransaccioncaja(idTransaccioncaja);
			List<Detalletransaccioncaja> detalletransaccioncaja = transaccionCajaServiceLocal.getDetalleTransaccionCaja(transaccioncaja);
			
			Map<Integer, Detalletransaccioncaja> map = new TreeMap<Integer, Detalletransaccioncaja>();
			for (Detalletransaccioncaja d : detalletransaccioncaja) {
				if(d.getCantidad() != 0)
					map.put(d.getCantidad(), d);
			}
			detalletransaccioncaja = new ArrayList<Detalletransaccioncaja>(map.values());
			this.listDetalleTransaccion = detalletransaccioncaja;
			dlgDetalle = true;
		} catch (Exception e) {
			failure = true;			
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

	public boolean isDlgDetalle() {
		return dlgDetalle;
	}

	public void setDlgDetalle(boolean dlgDetalle) {
		this.dlgDetalle = dlgDetalle;
	}

	public List<Detalletransaccioncaja> getListDetalleTransaccion() {
		return listDetalleTransaccion;
	}

	public void setListDetalleTransaccion(
			List<Detalletransaccioncaja> listDetalleTransaccion) {
		this.listDetalleTransaccion = listDetalleTransaccion;
	}

}
