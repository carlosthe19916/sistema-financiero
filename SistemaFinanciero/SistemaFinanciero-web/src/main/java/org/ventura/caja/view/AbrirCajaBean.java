package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AbrirCajaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private boolean failure;
	
	private boolean dlgVerificarSaldos;
	private boolean dlgCrearPendiente;
	
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaApertura;
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre;
	
	private Map<Tipomoneda, BigDecimal> mapDiferenciaSaldos;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private AgenciaBean agenciaBean;
	@Inject private Agencia agencia;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	public AbrirCajaBean() {
		success = false;
		failure = false;
		dlgVerificarSaldos = false;
		dlgCrearPendiente = false;
		mapDiferenciaSaldos = new HashMap<Tipomoneda, BigDecimal>();
	}
	
	@PostConstruct
	public void initialize() throws Exception{		
		try {
			caja = cajaBean.getCaja();
			agencia = agenciaBean.getAgencia();
			
			mapDetalleHistorialcajaApertura = cajaServiceLocal.getDetallehistorialcajaLastActive(caja);
			mapDetalleHistorialcajaCierre = cajaServiceLocal.getDetallehistorialcajaInZero(caja);
			
			for (Tipomoneda t : mapDetalleHistorialcajaCierre.keySet()) {
				mapDiferenciaSaldos.put(t, BigDecimal.ZERO);
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	
	public List<Detallehistorialcaja> retornarDetalle(Tipomoneda tipomoneda){
		return mapDetalleHistorialcajaCierre.get(tipomoneda);
	}
	
	public void verificarSaldos(){		
		try {								
			mapDiferenciaSaldos = cajaServiceLocal.verificarSaldosCaja(caja, mapDetalleHistorialcajaCierre);		
			setDlgVerificarSaldos(true);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void closeCaja() throws Exception {
		Caja caja = this.caja;	
		try {
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {
								
				this.cajaServiceLocal.closeCaja(caja, mapDetalleHistorialcajaCierre);
				
				
			} else {
				failure = true;
				JsfUtil.addErrorMessage("Caja Cerrada, Imposible cerrar caja");			
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String getMensajeSaldo(BigDecimal valor){
		String msg = "";
		if(valor.compareTo(BigDecimal.ZERO) == 0){
			msg = "Cuadre de caja correcto";
		} else {
			if(valor.compareTo(BigDecimal.ZERO) == 1){
				msg = "Saldo de caja sobrante";
			} else {
				msg = "Saldo de caja faltante";
			}
		}
		return msg;
	}
	
	public int getCompareBigdecimal(BigDecimal valor){
		return valor.compareTo(BigDecimal.ZERO);
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Map<Tipomoneda, List<Detallehistorialcaja>> getMapDetalleHistorialcajaApertura() {
		return mapDetalleHistorialcajaApertura;
	}

	public void setMapDetalleHistorialcajaApertura(
			Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaApertura) {
		this.mapDetalleHistorialcajaApertura = mapDetalleHistorialcajaApertura;
	}

	public Map<Tipomoneda, List<Detallehistorialcaja>> getMapDetalleHistorialcajaCierre() {
		return mapDetalleHistorialcajaCierre;
	}

	public void setMapDetalleHistorialcajaCierre(
			Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre) {
		this.mapDetalleHistorialcajaCierre = mapDetalleHistorialcajaCierre;
	}

	public boolean isDlgVerificarSaldos() {
		return dlgVerificarSaldos;
	}

	public void setDlgVerificarSaldos(boolean dlgVerificarSaldos) {
		this.dlgVerificarSaldos = dlgVerificarSaldos;
	}

	public Map<Tipomoneda, BigDecimal> getMapDiferenciaSaldos() {
		return mapDiferenciaSaldos;
	}

	public void setMapDiferenciaSaldos(
			Map<Tipomoneda, BigDecimal> mapDiferenciaSaldos) {
		this.mapDiferenciaSaldos = mapDiferenciaSaldos;
	}

	public boolean isDlgCrearPendiente() {
		return dlgCrearPendiente;
	}

	public void setDlgCrearPendiente(boolean dlgCrearPendiente) {
		this.dlgCrearPendiente = dlgCrearPendiente;
	}

}
