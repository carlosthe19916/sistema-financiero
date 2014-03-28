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
	
	
	@Inject AgenciaBean agenciaBean;
	@Inject CajaBean cajaBean;
	
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
			System.out.println("Cajas11111: "+ cajas.size());
			cajas.remove(cajaBean.getCaja());
			System.out.println("Cajas2222: "+ cajas.size());
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
				transaccionCajaServiceLocal.crearTransaccioncajacaja(transaccioncajacaja, cajaBean.getCaja(), comboCaja.getObjectItemSelected());
				
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
			transaccionCajaServiceLocal.confirmarTransaccioncajacaja(transaccioncajacaja);		
						
			List<Transaccioncajacaja> transasccionesSolicitadas = transaccionCajaServiceLocal.getTransaccionesPorConfirmarCajaCaja(cajaBean.getCaja());		
			tablaTransasccionesSolicitadas.setRows(transasccionesSolicitadas);		
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

}
