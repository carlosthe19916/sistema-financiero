package org.ventura.caja.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.TabableView;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Transaccionbovedacaja;
import org.ventura.entity.schema.caja.Transaccioncajacaja;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.session.UsuarioMB;
import org.ventura.tipodato.Moneda;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionBovedaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject private TablaBean<Transaccionbovedacaja> tablaTransasccionesEnviadas;
	@Inject private TablaBean<Transaccionbovedacaja> tablaTransasccionesSolicitadas;
	
	private boolean success = false;
	private boolean failure = false;
	
	
	@Inject Transaccionbovedacaja transaccionbovedacaja;
	private boolean dlgCrearTransasccion;
	private boolean successCrearTransaccion;
	@Inject ComboBean<Boveda> comboBovedas;
	@Inject private TablaBean<Detalletransaccionboveda> tablaDetalletransaccion;
	
	@Inject private AgenciaBean agenciaBean;
	@Inject private CajaBean cajaBean;
	@Inject private UsuarioMB usuarioMB;
	
	@EJB private BovedaServiceLocal bovedaServiceLocal;
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	public TransaccionBovedaCajaBean(){
		success = false;
		failure = false;
		
		dlgCrearTransasccion = false;
		successCrearTransaccion = false;
	}

	@PostConstruct
	private void initialize() {
		try {
			List<Transaccionbovedacaja> transasccionesEnviadas = bovedaServiceLocal.getTransaccionesEnviadasCaja(cajaBean.getCaja());
			List<Transaccionbovedacaja> transasccionesSolicitadas = bovedaServiceLocal.getTransaccionesPorConfirmarCaja(cajaBean.getCaja());
			tablaTransasccionesEnviadas.setRows(transasccionesEnviadas);
			tablaTransasccionesSolicitadas.setRows(transasccionesSolicitadas);
			
			List<Boveda> listBovedas = cajaServiceLocal.getBovedas(cajaBean.getCaja());
			comboBovedas.setItems(listBovedas);
			
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}		
	}
	
	public void createTransaccion(){
		try {
			if(successCrearTransaccion != true){
				Transaccionbovedacaja transaccionbovedacaja = new Transaccionbovedacaja();
				transaccionbovedacaja.setDetalleTransaccionboveda(tablaDetalletransaccion.getRows());
				transaccionbovedacaja.setMonto(getTotalTransaccion().getValue());
				transaccionbovedacaja.setOrigen("CAJA");
					
				this.transaccionbovedacaja = bovedaServiceLocal.crearTransaccionBovedacaja(transaccionbovedacaja, cajaBean.getCaja(), comboBovedas.getObjectItemSelected(), usuarioMB.getUsuario());
				
				successCrearTransaccion = true;
				dlgCrearTransasccion = false;
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void confirmarTransaccion(Transaccionbovedacaja transaccionbovedacaja){
		try {
					
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cancelarTransaccion(Transaccionbovedacaja transaccionbovedacaja){
		try {
			bovedaServiceLocal.cancelarTransaccionbovedacaja(transaccionbovedacaja);
			
			List<Transaccionbovedacaja> transasccionesEnviadas = bovedaServiceLocal.getTransaccionesEnviadasCaja(cajaBean.getCaja());			
			tablaTransasccionesEnviadas.setRows(transasccionesEnviadas);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void loadDetalleTransaccionboveda() {
		try {
			Boveda bovedaSelected = comboBovedas.getObjectItemSelected();
			List<Detalletransaccionboveda> detalletransaccionbovedas = bovedaServiceLocal.getDetalletransaccionboveda(bovedaSelected);
						
			tablaDetalletransaccion.setRows(detalletransaccionbovedas);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void changeBoveda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Boveda bovedaSelected = comboBovedas.getObjectItemSelected(key);
		if(bovedaSelected != null){
			comboBovedas.setItemSelected(bovedaSelected);
			this.loadDetalleTransaccionboveda();
		}		
	}
	
	public Moneda getTotalTransaccion() {
		Moneda result = new Moneda();
		for (Detalletransaccionboveda e : tablaDetalletransaccion.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}
	
	public String getEstadoTransaccion(boolean estadoSolicitud, boolean estadoConfirmacion) {
	    String result = "";
		if(estadoSolicitud == true && estadoConfirmacion == true)
	    	result = "Confirmado";
		if(estadoSolicitud == true && estadoConfirmacion == false)
			result = "Por confirmar";
		if(estadoSolicitud == false && estadoConfirmacion == true)
			result = "No determinado F/V";
		if(estadoSolicitud == false && estadoConfirmacion == false)
			result = "cancelado";
		return result;
	}
	
	public String getStringDate(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	    return ft.format(date);
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public TablaBean<Transaccionbovedacaja> getTablaTransasccionesEnviadas() {
		return tablaTransasccionesEnviadas;
	}

	public void setTablaTransasccionesEnviadas(
			TablaBean<Transaccionbovedacaja> tablaTransasccionesEnviadas) {
		this.tablaTransasccionesEnviadas = tablaTransasccionesEnviadas;
	}

	public TablaBean<Transaccionbovedacaja> getTablaTransasccionesSolicitadas() {
		return tablaTransasccionesSolicitadas;
	}

	public void setTablaTransasccionesSolicitadas(
			TablaBean<Transaccionbovedacaja> tablaTransasccionesSolicitadas) {
		this.tablaTransasccionesSolicitadas = tablaTransasccionesSolicitadas;
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

	public boolean isDlgCrearTransasccion() {
		return dlgCrearTransasccion;
	}

	public void setDlgCrearTransasccion(boolean dlgCrearTransasccion) {
		this.dlgCrearTransasccion = dlgCrearTransasccion;
	}

	public ComboBean<Boveda> getComboBovedas() {
		return comboBovedas;
	}

	public void setComboBovedas(ComboBean<Boveda> comboBovedas) {
		this.comboBovedas = comboBovedas;
	}

	public TablaBean<Detalletransaccionboveda> getTablaDetalletransaccion() {
		return tablaDetalletransaccion;
	}

	public void setTablaDetalletransaccion(
			TablaBean<Detalletransaccionboveda> tablaDetalletransaccion) {
		this.tablaDetalletransaccion = tablaDetalletransaccion;
	}

	public boolean isSuccessCrearTransaccion() {
		return successCrearTransaccion;
	}

	public void setSuccessCrearTransaccion(boolean successCrearTransaccion) {
		this.successCrearTransaccion = successCrearTransaccion;
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public CajaBean getCajaBean() {
		return cajaBean;
	}

	public void setCajaBean(CajaBean cajaBean) {
		this.cajaBean = cajaBean;
	}

	public Transaccionbovedacaja getTransaccionbovedacaja() {
		return transaccionbovedacaja;
	}

	public void setTransaccionbovedacaja(Transaccionbovedacaja transaccionbovedacaja) {
		this.transaccionbovedacaja = transaccionbovedacaja;
	}

	

}
