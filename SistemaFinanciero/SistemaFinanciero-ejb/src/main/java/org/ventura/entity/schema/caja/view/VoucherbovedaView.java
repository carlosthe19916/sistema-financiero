package org.ventura.entity.schema.caja.view;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the voucherboveda_view database table.
 * 
 */
@Entity
@Table(name = "voucherboveda_view", schema = "caja")
@NamedQuery(name = "VoucherbovedaView.findAll", query = "SELECT v FROM VoucherbovedaView v")
public class VoucherbovedaView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "id_transaccionboveda")
	private Integer idTransaccionboveda;

	@Column(name = "abreviatura_agencia", length = 10)
	private String abreviaturaAgencia;

	@Column(name = "abreviatura_tipomoneda", length = 3)
	private String abreviaturaTipomoneda;

	@Column(name = "abreviatura_tipotransaccion", length = 10)
	private String abreviaturaTipotransaccion;

	@Column(name = "codigo_agencia", length = 3)
	private String codigoAgencia;

	@Column(name = "denominacion_agencia", length = 150)
	private String denominacionAgencia;

	@Column(name = "denominacion_boveda", length = 150)
	private String denominacionBoveda;

	@Column(name = "denominacion_tipomoneda", length = 35)
	private String denominacionTipomoneda;

	@Column(name = "denominacion_tipotransaccion", length = 150)
	private String denominacionTipotransaccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_transaccionboveda")
	private Date fechaTransaccionboveda;

	@Column(name = "hora_transaccionboveda")
	private Timestamp horaTransaccionboveda;

	@Column(name = "id_agencia")
	private Integer idAgencia;

	@Column(name = "id_boveda")
	private Integer idBoveda;
	
	@Column(name = "tipoentidad_transaccionboveda", length = 2147483647)
	private String tipoentidadTransaccionboveda;
	
	@Column(name = "id_entidad")
	private Integer idEntidad;

	@Column(name = "abreviatura_entidad", length = 20)
	private String abreviaturaEntidad;
	
	@Column(name = "denominacion_entidad", length = 150)
	private String denominacionEntidad;
	
	
	@Column(name = "id_historialboveda")
	private Integer idHistorialboveda;

	@Column(name = "id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name = "id_tipotransaccion")
	private Integer idTipotransaccion;

	@Column(name = "saldodisponible_transaccionboveda", precision = 18, scale = 2)
	private BigDecimal saldodisponibleTransaccionboveda;


	@Column(name = "total_transaccion", precision = 131089)
	private BigDecimal totalTransaccion;

	public VoucherbovedaView() {
	}

	public Integer getIdTransaccionboveda() {
		return idTransaccionboveda;
	}

	public void setIdTransaccionboveda(Integer idTransaccionboveda) {
		this.idTransaccionboveda = idTransaccionboveda;
	}

	public String getAbreviaturaAgencia() {
		return abreviaturaAgencia;
	}

	public void setAbreviaturaAgencia(String abreviaturaAgencia) {
		this.abreviaturaAgencia = abreviaturaAgencia;
	}

	public String getAbreviaturaTipomoneda() {
		return abreviaturaTipomoneda;
	}

	public void setAbreviaturaTipomoneda(String abreviaturaTipomoneda) {
		this.abreviaturaTipomoneda = abreviaturaTipomoneda;
	}

	public String getAbreviaturaTipotransaccion() {
		return abreviaturaTipotransaccion;
	}

	public void setAbreviaturaTipotransaccion(String abreviaturaTipotransaccion) {
		this.abreviaturaTipotransaccion = abreviaturaTipotransaccion;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getDenominacionAgencia() {
		return denominacionAgencia;
	}

	public void setDenominacionAgencia(String denominacionAgencia) {
		this.denominacionAgencia = denominacionAgencia;
	}

	public String getDenominacionBoveda() {
		return denominacionBoveda;
	}

	public void setDenominacionBoveda(String denominacionBoveda) {
		this.denominacionBoveda = denominacionBoveda;
	}

	public String getDenominacionTipomoneda() {
		return denominacionTipomoneda;
	}

	public void setDenominacionTipomoneda(String denominacionTipomoneda) {
		this.denominacionTipomoneda = denominacionTipomoneda;
	}

	public String getDenominacionTipotransaccion() {
		return denominacionTipotransaccion;
	}

	public void setDenominacionTipotransaccion(String denominacionTipotransaccion) {
		this.denominacionTipotransaccion = denominacionTipotransaccion;
	}

	public Date getFechaTransaccionboveda() {
		return fechaTransaccionboveda;
	}

	public void setFechaTransaccionboveda(Date fechaTransaccionboveda) {
		this.fechaTransaccionboveda = fechaTransaccionboveda;
	}

	public Timestamp getHoraTransaccionboveda() {
		return horaTransaccionboveda;
	}

	public void setHoraTransaccionboveda(Timestamp horaTransaccionboveda) {
		this.horaTransaccionboveda = horaTransaccionboveda;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Integer idBoveda) {
		this.idBoveda = idBoveda;
	}

	public String getTipoentidadTransaccionboveda() {
		return tipoentidadTransaccionboveda;
	}

	public void setTipoentidadTransaccionboveda(String tipoentidadTransaccionboveda) {
		this.tipoentidadTransaccionboveda = tipoentidadTransaccionboveda;
	}

	public Integer getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getAbreviaturaEntidad() {
		return abreviaturaEntidad;
	}

	public void setAbreviaturaEntidad(String abreviaturaEntidad) {
		this.abreviaturaEntidad = abreviaturaEntidad;
	}

	public String getDenominacionEntidad() {
		return denominacionEntidad;
	}

	public void setDenominacionEntidad(String denominacionEntidad) {
		this.denominacionEntidad = denominacionEntidad;
	}

	public Integer getIdHistorialboveda() {
		return idHistorialboveda;
	}

	public void setIdHistorialboveda(Integer idHistorialboveda) {
		this.idHistorialboveda = idHistorialboveda;
	}

	public Integer getIdTipomoneda() {
		return idTipomoneda;
	}

	public void setIdTipomoneda(Integer idTipomoneda) {
		this.idTipomoneda = idTipomoneda;
	}

	public Integer getIdTipotransaccion() {
		return idTipotransaccion;
	}

	public void setIdTipotransaccion(Integer idTipotransaccion) {
		this.idTipotransaccion = idTipotransaccion;
	}

	public BigDecimal getSaldodisponibleTransaccionboveda() {
		return saldodisponibleTransaccionboveda;
	}

	public void setSaldodisponibleTransaccionboveda(
			BigDecimal saldodisponibleTransaccionboveda) {
		this.saldodisponibleTransaccionboveda = saldodisponibleTransaccionboveda;
	}

	public BigDecimal getTotalTransaccion() {
		return totalTransaccion;
	}

	public void setTotalTransaccion(BigDecimal totalTransaccion) {
		this.totalTransaccion = totalTransaccion;
	}

	

}