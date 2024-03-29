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

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncajacaja;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.session.UsuarioMB;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TransaccionCajaCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean dlgCrearTransasccion;
	private BigDecimal monto;
	@Inject private ComboBean<Caja> comboCaja;
	@Inject private ComboBean<Tipomoneda> comboTipomoneda;
	
	
	@Inject private TablaBean<Transaccioncajacaja> tablaTransasccionesEnviadas;
	@Inject private TablaBean<Transaccioncajacaja> tablaTransasccionesSolicitadas;
	
	private boolean failure;
	private boolean successCrearTransaccion;
	
	private Transaccioncajacaja transaccioncajacaja;
	
	@Inject AgenciaBean agenciaBean;
	@Inject CajaBean cajaBean;
	@Inject UsuarioMB usuarioMB;
	
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB  private CajaServiceLocal cajaServiceLocal;
	@EJB private SucursalServiceLocal sucursalServiceLocal;
	
	public TransaccionCajaCajaBean(){
		successCrearTransaccion = false;
		failure = false;
		
		dlgCrearTransasccion = false;
	}

	@PostConstruct
	private void initialize() {
		try {
			List<Caja> cajas = sucursalServiceLocal.getCajas(agenciaBean.getAgencia());
			cajas.remove(cajaBean.getCaja());
			comboCaja.setItems(cajas);
			
			List<Boveda> bovedas = cajaServiceLocal.getBovedas(cajaBean.getCaja());
			for (Boveda boveda : bovedas) {
				comboTipomoneda.addItem(boveda.getTipomoneda());
			}
			
			List<Transaccioncajacaja> transasccionesEnviadas = transaccionCajaServiceLocal.getTransaccionesEnviadasCajaCaja(cajaBean.getCaja());
			List<Transaccioncajacaja> transasccionesSolicitadas = transaccionCajaServiceLocal.getTransaccionesPorConfirmarCajaCaja(cajaBean.getCaja());
			tablaTransasccionesEnviadas.setRows(transasccionesEnviadas);
			tablaTransasccionesSolicitadas.setRows(transasccionesSolicitadas);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}		
	}

	public void createTransaccion(){
		try {
			if(successCrearTransaccion != true){
				Transaccioncajacaja transaccioncajacaja = new Transaccioncajacaja();;
				transaccioncajacaja.setMonto(monto);
				transaccioncajacaja.setTipomoneda(comboTipomoneda.getObjectItemSelected());			
				this.transaccioncajacaja = transaccionCajaServiceLocal.crearTransaccioncajacaja(transaccioncajacaja, cajaBean.getCaja(), comboCaja.getObjectItemSelected(), usuarioMB.getUsuario());
				
				successCrearTransaccion = true;
				dlgCrearTransasccion = false;
			}
			
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void confirmarTransaccion(Transaccioncajacaja transaccioncajacaja){
		try {
			if(successCrearTransaccion != true){
				this.transaccioncajacaja =  transaccionCajaServiceLocal.confirmarTransaccioncajacaja(transaccioncajacaja,usuarioMB.getUsuario());		
				
				List<Transaccioncajacaja> transasccionesSolicitadas = transaccionCajaServiceLocal.getTransaccionesPorConfirmarCajaCaja(cajaBean.getCaja());		
				tablaTransasccionesSolicitadas.setRows(transasccionesSolicitadas);	
				
				successCrearTransaccion = true;
			}					
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cancelarTransaccion(Transaccioncajacaja transaccioncajacaja){
		try {
			transaccionCajaServiceLocal.cancelarTransaccioncajacaja(transaccioncajacaja);
			
			List<Transaccioncajacaja> transasccionesEnviadas = transaccionCajaServiceLocal.getTransaccionesEnviadasCajaCaja(cajaBean.getCaja());			
			tablaTransasccionesEnviadas.setRows(transasccionesEnviadas);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
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
	
	public boolean isDlgCrearTransasccion() {
		return dlgCrearTransasccion;
	}

	public void setDlgCrearTransasccion(boolean dlgCrearTransasccion) {
		this.dlgCrearTransasccion = dlgCrearTransasccion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public ComboBean<Caja> getComboCaja() {
		return comboCaja;
	}

	public void setComboCaja(ComboBean<Caja> comboCaja) {
		this.comboCaja = comboCaja;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public TablaBean<Transaccioncajacaja> getTablaTransasccionesEnviadas() {
		return tablaTransasccionesEnviadas;
	}

	public void setTablaTransasccionesEnviadas(
			TablaBean<Transaccioncajacaja> tablaTransasccionesEnviadas) {
		this.tablaTransasccionesEnviadas = tablaTransasccionesEnviadas;
	}

	public TablaBean<Transaccioncajacaja> getTablaTransasccionesSolicitadas() {
		return tablaTransasccionesSolicitadas;
	}

	public void setTablaTransasccionesSolicitadas(
			TablaBean<Transaccioncajacaja> tablaTransasccionesSolicitadas) {
		this.tablaTransasccionesSolicitadas = tablaTransasccionesSolicitadas;
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

	public Transaccioncajacaja getTransaccioncajacaja() {
		return transaccioncajacaja;
	}

	public void setTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja) {
		this.transaccioncajacaja = transaccioncajacaja;
	}

}
