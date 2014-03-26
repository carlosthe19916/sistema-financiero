package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the caja_movimientos database table.
 * 
 */
@Entity
@Table(name = "caja_movimientos_view", schema = "caja")
@NamedQuery(name = "CajaMovimiento.findAll", query = "SELECT c FROM CajaMovimientoView c")
@NamedQueries({
		@NamedQuery(name = CajaMovimientoView.f_idcaja_idhistorialcaja, query = "SELECT c FROM CajaMovimientoView c WHERE c.idCaja = :idcaja AND c.idhistorialcaja = :idhistorialcaja ORDER BY c.numerooperacionTransaccioncaja DESC"),
		@NamedQuery(name = CajaMovimientoView.f_idtransaccioncaja_idcaja_idhistorialcaja, query = "SELECT c FROM CajaMovimientoView c WHERE c.idTransaccioncaja = :idtransaccioncaja AND c.idCaja = :idcaja AND c.idhistorialcaja = :idhistorialcaja") })
public class CajaMovimientoView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idcaja_idhistorialcaja = "org.ventura.entity.schema.caja.view.CajaMovimientoView.f_idcaja_idhistorialcaja";
	public final static String f_idtransaccioncaja_idcaja_idhistorialcaja = "org.ventura.entity.schema.caja.view.CajaMovimientoView.f_idcaja_idtransaccioncaja";

	@Id
	@Column(name = "id_transaccioncaja")
	private Integer idTransaccioncaja;

	@Column(name = "abreviatura_caja", length = 30)
	private String abreviaturaCaja;

	@Column(name = "abreviatura_tipomoneda", length = 2147483647)
	private String abreviaturaTipomoneda;

	@Column(name = "denominacion_caja", length = 100)
	private String denominacionCaja;

	@Column(name = "denominacion_tipomoneda", length = 2147483647)
	private String denominacionTipomoneda;

	@Column(name = "denominacion_tipotransaccion", length = 2147483647)
	private String denominacionTipotransaccion;

	@Column(name = "estado_transaccion")
	private Boolean estadoTransaccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_transaccioncaja")
	private Date fechaTransaccioncaja;

	@Column(name = "hora_transaccioncaja")
	private Timestamp horaTransaccioncaja;

	@Column(name = "id_caja")
	private Integer idCaja;

	@Column(name = "id_tipotransaccion")
	private Integer idTipotransaccion;

	private Integer idhistorialcaja;

	@Column(name = "monto_transaccion", length = 2147483647)
	private String montoTransaccion;

	@Column(name = "numerooperacion_transaccioncaja")
	private Integer numerooperacionTransaccioncaja;

	@Column(length = 2147483647)
	private String tipotransaccion;

	public CajaMovimientoView() {
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

	public Boolean getEstadoTransaccion() {
		return this.estadoTransaccion;
	}

	public void setEstadoTransaccion(Boolean estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public Date getFechaTransaccioncaja() {
		return this.fechaTransaccioncaja;
	}

	public void setFechaTransaccioncaja(Date fechaTransaccioncaja) {
		this.fechaTransaccioncaja = fechaTransaccioncaja;
	}

	public Timestamp getHoraTransaccioncaja() {
		return this.horaTransaccioncaja;
	}

	public void setHoraTransaccioncaja(Timestamp horaTransaccioncaja) {
		this.horaTransaccioncaja = horaTransaccioncaja;
	}

	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	public Integer getIdTipotransaccion() {
		return this.idTipotransaccion;
	}

	public void setIdTipotransaccion(Integer idTipotransaccion) {
		this.idTipotransaccion = idTipotransaccion;
	}

	public Integer getIdTransaccioncaja() {
		return this.idTransaccioncaja;
	}

	public void setIdTransaccioncaja(Integer idTransaccioncaja) {
		this.idTransaccioncaja = idTransaccioncaja;
	}

	public Integer getIdhistorialcaja() {
		return this.idhistorialcaja;
	}

	public void setIdhistorialcaja(Integer idhistorialcaja) {
		this.idhistorialcaja = idhistorialcaja;
	}

	public String getMontoTransaccion() {
		return this.montoTransaccion;
	}

	public void setMontoTransaccion(String montoTransaccion) {
		this.montoTransaccion = montoTransaccion;
	}

	public Integer getNumerooperacionTransaccioncaja() {
		return this.numerooperacionTransaccioncaja;
	}

	public void setNumerooperacionTransaccioncaja(
			Integer numerooperacionTransaccioncaja) {
		this.numerooperacionTransaccioncaja = numerooperacionTransaccioncaja;
	}

	public String getTipotransaccion() {
		return this.tipotransaccion;
	}

	public void setTipotransaccion(String tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

}