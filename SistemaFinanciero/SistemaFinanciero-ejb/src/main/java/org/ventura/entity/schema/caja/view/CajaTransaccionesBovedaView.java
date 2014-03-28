package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the caja_transacciones_boveda_view database table.
 * 
 */
@Entity
@Table(name = "caja_transacciones_boveda_view", schema = "caja")
@NamedQuery(name = "CajaTransaccionesBovedaView.findAll", query = "SELECT c FROM CajaTransaccionesBovedaView c")
@NamedQueries({ @NamedQuery(name = CajaTransaccionesBovedaView.f_idcaja_idhistorialcaja, query = "SELECT c FROM CajaTransaccionesBovedaView c WHERE c.idCaja = :idcaja AND c.idHistorialcaja = :idhistorialcaja ORDER BY c.idTransaccionboveda") })
public class CajaTransaccionesBovedaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idcaja_idhistorialcaja = "org.ventura.entity.schema.caja.view.CajaTransaccionesBovedaView.f_idcaja_idhistorialcaja";

	@Id
	@Column(name = "id_transaccionboveda")
	private Integer idTransaccionboveda;

	@Column(name = "abreviatura_caja", length = 30)
	private String abreviaturaCaja;

	@Column(name = "abreviatura_tipomoneda", length = 3)
	private String abreviaturaTipomoneda;

	@Column(name = "denominacion_caja", length = 100)
	private String denominacionCaja;

	@Column(name = "denominacion_tipomoneda", length = 35)
	private String denominacionTipomoneda;

	@Column(name = "denominacion_tipotransaccion", length = 150)
	private String denominacionTipotransaccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_transaccionboveda")
	private Date fechaTransaccionboveda;

	@Column(name = "hora_transaccionboveda")
	private Timestamp horaTransaccionboveda;

	@Column(name = "id_caja")
	private Integer idCaja;

	@Column(name = "id_historialcaja")
	private Integer idHistorialcaja;

	@Column(name = "id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name = "id_tipotransaccion")
	private Integer idTipotransaccion;

	@Column(precision = 131089)
	private BigDecimal total;

	public CajaTransaccionesBovedaView() {
	}

	public String getAbreviaturaCaja() {
		return this.abreviaturaCaja;
	}

	public void setAbreviaturaCaja(String abreviaturaCaja) {
		this.abreviaturaCaja = abreviaturaCaja;
	}

	public String getAbreviaturaTipomoneda() {
		return this.abreviaturaTipomoneda;
	}

	public void setAbreviaturaTipomoneda(String abreviaturaTipomoneda) {
		this.abreviaturaTipomoneda = abreviaturaTipomoneda;
	}

	public String getDenominacionCaja() {
		return this.denominacionCaja;
	}

	public void setDenominacionCaja(String denominacionCaja) {
		this.denominacionCaja = denominacionCaja;
	}

	public String getDenominacionTipomoneda() {
		return this.denominacionTipomoneda;
	}

	public void setDenominacionTipomoneda(String denominacionTipomoneda) {
		this.denominacionTipomoneda = denominacionTipomoneda;
	}

	public String getDenominacionTipotransaccion() {
		return this.denominacionTipotransaccion;
	}

	public void setDenominacionTipotransaccion(
			String denominacionTipotransaccion) {
		this.denominacionTipotransaccion = denominacionTipotransaccion;
	}

	public Date getFechaTransaccionboveda() {
		return this.fechaTransaccionboveda;
	}

	public void setFechaTransaccionboveda(Date fechaTransaccionboveda) {
		this.fechaTransaccionboveda = fechaTransaccionboveda;
	}

	public Timestamp getHoraTransaccionboveda() {
		return this.horaTransaccionboveda;
	}

	public void setHoraTransaccionboveda(Timestamp horaTransaccionboveda) {
		this.horaTransaccionboveda = horaTransaccionboveda;
	}

	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	public Integer getIdHistorialcaja() {
		return this.idHistorialcaja;
	}

	public void setIdHistorialcaja(Integer idHistorialcaja) {
		this.idHistorialcaja = idHistorialcaja;
	}

	public Integer getIdTipomoneda() {
		return this.idTipomoneda;
	}

	public void setIdTipomoneda(Integer idTipomoneda) {
		this.idTipomoneda = idTipomoneda;
	}

	public Integer getIdTipotransaccion() {
		return this.idTipotransaccion;
	}

	public void setIdTipotransaccion(Integer idTipotransaccion) {
		this.idTipotransaccion = idTipotransaccion;
	}

	public Integer getIdTransaccionboveda() {
		return this.idTransaccionboveda;
	}

	public void setIdTransaccionboveda(Integer idTransaccionboveda) {
		this.idTransaccionboveda = idTransaccionboveda;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public String getTotalAsString() {
		System.out.println(this.total);
		return Moneda.getMonedaFormat(this.total);
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}