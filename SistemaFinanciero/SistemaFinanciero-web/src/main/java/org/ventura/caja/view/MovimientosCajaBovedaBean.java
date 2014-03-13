package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Transaccionboveda;
import org.ventura.entity.schema.caja.view.CajaTransaccionesBovedaView;
import org.ventura.session.CajaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class MovimientosCajaBovedaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean dlgDetalleTransaccion;
	
	@Inject private TablaBean<CajaTransaccionesBovedaView> tablaTransacciones;
	@Inject private TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	@EJB private BovedaServiceLocal bovedaServiceLocal;
	
	private boolean failure;
	
	public MovimientosCajaBovedaBean() {
		dlgDetalleTransaccion = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{
		try {
			caja = cajaBean.getCaja();
			
			List<CajaTransaccionesBovedaView> list = cajaServiceLocal.getTransaccionesDelDia(caja);
			tablaTransacciones.setRows(list);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void verDetalle(CajaTransaccionesBovedaView transaccionesBovedaView){
		try {
			Integer idTransaccion = transaccionesBovedaView.getIdTransaccionboveda();
			Transaccionboveda transaccionboveda = new Transaccionboveda();
			transaccionboveda.setIdtransaccionboveda(idTransaccion);
			
			List<Detalletransaccionboveda> list = bovedaServiceLocal.getDetalleTransaccionBoveda(transaccionboveda);
			tablaDetalletransaccionboveda.setRows(list);
			
			dlgDetalleTransaccion = true;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public BigDecimal totalTransaccion(){
		return null;
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public TablaBean<CajaTransaccionesBovedaView> getTablaTransacciones() {
		return tablaTransacciones;
	}

	public void setTablaTransacciones(
			TablaBean<CajaTransaccionesBovedaView> tablaTransacciones) {
		this.tablaTransacciones = tablaTransacciones;
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

	public TablaBean<Detalletransaccionboveda> getTablaDetalletransaccionboveda() {
		return tablaDetalletransaccionboveda;
	}

	public void setTablaDetalletransaccionboveda(
			TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda) {
		this.tablaDetalletransaccionboveda = tablaDetalletransaccionboveda;
	}

	public boolean isDlgDetalleTransaccion() {
		return dlgDetalleTransaccion;
	}

	public void setDlgDetalleTransaccion(boolean dlgDetalleTransaccion) {
		this.dlgDetalleTransaccion = dlgDetalleTransaccion;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

}
