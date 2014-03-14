package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.PendienteCaja;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.tipodato.Moneda;
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
	private boolean successPendiente;
	private BigDecimal montoPendiente;
	private Tipomoneda tipomonedaPendiente;
	private String observacionPendiente;
	private Date fechaPendiente;
	
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaApertura;
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre;
	
	private Map<Tipomoneda, BigDecimal> mapDiferenciaSaldos;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private Historialcaja historialcaja;
	@Inject private AgenciaBean agenciaBean;
	@Inject private Agencia agencia;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	public AbrirCajaBean() {
		success = false;
		failure = false;
		successPendiente = false;
		dlgVerificarSaldos = false;
		dlgCrearPendiente = false;
		mapDiferenciaSaldos = new HashMap<Tipomoneda, BigDecimal>();
	}
	
	@PostConstruct
	public void initialize() throws Exception{		
		try {
			caja = cajaBean.getCaja();
			agencia = agenciaBean.getAgencia();
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {								
				throw new Exception("Caja CERRADA, no se puede cerrar nuevamente");
			}
			
			historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			mapDetalleHistorialcajaApertura = cajaServiceLocal.getDetallehistorialcajaLastActive(caja);
			mapDetalleHistorialcajaCierre = cajaServiceLocal.getDetallehistorialcajaInZero(caja);
			
			for (Tipomoneda t : mapDetalleHistorialcajaCierre.keySet()) {
				mapDiferenciaSaldos.put(t, BigDecimal.ZERO);
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
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
			if(success == false){
				Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
				Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
				if (estadoapertura.equals(estadoapertura2)) {								
					this.cajaServiceLocal.closeCaja(caja, mapDetalleHistorialcajaCierre);
					success = true;
					setDlgVerificarSaldos(false);
				} else {
					failure = true;
					JsfUtil.addErrorMessage("Caja Cerrada, Imposible cerrar caja");			
				}
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void crearPendiente(){
		try {
			PendienteCaja pendienteCaja = new PendienteCaja();
			pendienteCaja.setMonto(new Moneda(montoPendiente));
			pendienteCaja.setObservacion(observacionPendiente);
			pendienteCaja.setTipomoneda(tipomonedaPendiente);
			pendienteCaja.setFecha(fechaPendiente);
			cajaServiceLocal.crearPendiente(caja, pendienteCaja);
			
			successPendiente = true;
			JsfUtil.addSuccessMessage("Pendiente creado");
			setDlgCrearPendiente(false);
			verificarSaldos();
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void congelar(){
		try {
			cajaServiceLocal.freezeCaja(caja);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void descongelar(){
		try {
			cajaServiceLocal.defrostCaja(caja);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cargarPendiente(Tipomoneda tipomoneda){
		dlgCrearPendiente = true;
		montoPendiente = mapDiferenciaSaldos.get(tipomoneda);
		tipomonedaPendiente = tipomoneda;
		observacionPendiente = "";
		fechaPendiente = Calendar.getInstance().getTime();
	}
	
	public void cancelarPendiente(){
		dlgCrearPendiente = false;
		montoPendiente = null;
		tipomonedaPendiente = null;
		observacionPendiente = "";
	}
	
	public String getMensajeSaldo(BigDecimal valor) {
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
	
	public String getMensajeTipoPendiente() {
		String msg = "";
		if(montoPendiente.compareTo(BigDecimal.ZERO) == 0){
			msg = "Cuadre de caja correcto";
		} else {
			if(montoPendiente.compareTo(BigDecimal.ZERO) == 1){
				msg = "SOBRANTE";
			} else {
				msg = "FALTANTE";
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

	public BigDecimal getMontoPendiente() {
		return montoPendiente;
	}

	public void setMontoPendiente(BigDecimal montoPendiente) {
		this.montoPendiente = montoPendiente;
	}

	public Tipomoneda getTipomonedaPendiente() {
		return tipomonedaPendiente;
	}

	public void setTipomonedaPendiente(Tipomoneda tipomonedaPendiente) {
		this.tipomonedaPendiente = tipomonedaPendiente;
	}

	public String getObservacionPendiente() {
		return observacionPendiente;
	}

	public void setObservacionPendiente(String observacionPendiente) {
		this.observacionPendiente = observacionPendiente;
	}

	public boolean isSuccessPendiente() {
		return successPendiente;
	}

	public void setSuccessPendiente(boolean successPendiente) {
		this.successPendiente = successPendiente;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

	public Date getFechaPendiente() {
		return fechaPendiente;
	}

	public void setFechaPendiente(Date fechaPendiente) {
		this.fechaPendiente = fechaPendiente;
	}
}
