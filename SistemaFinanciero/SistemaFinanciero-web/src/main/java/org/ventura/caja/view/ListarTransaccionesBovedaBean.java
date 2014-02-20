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

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Transaccionboveda;
import org.ventura.entity.schema.caja.view.BovedaTransaccionesHistorialactivoView;
import org.ventura.entity.schema.caja.view.CajaTransaccionesBovedaView;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ListarTransaccionesBovedaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean dlgDetalleTransaccion;
	
	@Inject private TablaBean<BovedaTransaccionesHistorialactivoView> tablaTransacciones;
	@Inject private TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda;
	
	
	@Inject AgenciaBean agenciaBean;
	@Inject Agencia agencia;
	
	@EJB private BovedaServiceLocal bovedaServiceLocal;
	
	public ListarTransaccionesBovedaBean() {
		dlgDetalleTransaccion = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{
		try {			
			agencia = agenciaBean.getAgencia();
			
			List<BovedaTransaccionesHistorialactivoView> list = bovedaServiceLocal.getTransaccionesDelDia(agenciaBean.getAgencia());
			tablaTransacciones.setRows(list);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	public void verDetalle(BovedaTransaccionesHistorialactivoView view){
		try {
			Integer idTransaccion = view.getIdTransaccionboveda();
			Transaccionboveda transaccionboveda = new Transaccionboveda();
			transaccionboveda.setIdtransaccionboveda(idTransaccion);
			
			List<Detalletransaccionboveda> list = bovedaServiceLocal.getDetalleTransaccionBoveda(transaccionboveda);
			tablaDetalletransaccionboveda.setRows(list);
			
			dlgDetalleTransaccion = true;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public boolean isDlgDetalleTransaccion() {
		return dlgDetalleTransaccion;
	}

	public void setDlgDetalleTransaccion(boolean dlgDetalleTransaccion) {
		this.dlgDetalleTransaccion = dlgDetalleTransaccion;
	}

	public TablaBean<BovedaTransaccionesHistorialactivoView> getTablaTransacciones() {
		return tablaTransacciones;
	}

	public void setTablaTransacciones(
			TablaBean<BovedaTransaccionesHistorialactivoView> tablaTransacciones) {
		this.tablaTransacciones = tablaTransacciones;
	}

	public TablaBean<Detalletransaccionboveda> getTablaDetalletransaccionboveda() {
		return tablaDetalletransaccionboveda;
	}

	public void setTablaDetalletransaccionboveda(
			TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda) {
		this.tablaDetalletransaccionboveda = tablaDetalletransaccionboveda;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	

	

}
