package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the boveda_transacciones_historialactivo_view
 * database table.
 * 
 */
@Entity
@Table(name = "boveda_transacciones_historialactivo_view", schema = "caja")
@NamedQuery(name = "BovedaTransaccionesHistorialactivoView.findAll", query = "SELECT b FROM BovedaTransaccionesHistorialactivoView b")
@NamedQueries({ @NamedQuery(name = BovedaTransaccionesHistorialactivoView.f_idagencia, query = "SELECT b FROM BovedaTransaccionesHistorialactivoView b WHERE b.idAgencia = :idagencia ORDER BY b.idTransaccionboveda") })
public class BovedaTransaccionesHistorialactivoView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idagencia = "org.ventura.entity.schema.caja.view.BovedaTransaccionesHistorialactivoView.f_idagencia";

	@Id
	@Column(name = "id_transaccionboveda")
	private Integer idTransaccionboveda;

	@Column(name = "abreviatura_entidad", length = 2147483647)
	private String abreviaturaEntidad;

	@Column(name = "abreviatura_tipomoneda", length = 3)
	private String abreviaturaTipomoneda;

	@Column(name = "denominacion_boveda", length = 150)
	private String denominacionBoveda;

	@Column(name = "denominacion_entidad", length = 2147483647)
	private String denominacionEntidad;

	@Column(name = "denominacion_tipoentidad", length = 2147483647)
	private String denominacionTipoentidad;

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

	@Column(name = "id_entidad")
	private Integer idEntidad;

	@Column(name = "id_historialboveda")
	private Integer idHistorialboveda;

	@Column(name = "id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name = "id_tipotransaccion")
	private Integer idTipotransaccion;

	@Column(name = "idcreacion_historialboveda")
	private Integer idcreacionHistorialboveda;

	@Column(precision = 131089)
	private BigDecimal total;

	public BovedaTransaccionesHistorialactivoView() {
	}

	public String getAbreviaturaEntidad() {
		return this.abreviaturaEntidad;
	}

	public void setAbreviaturaEntidad(String abreviaturaEntidad) {
		this.abreviaturaEntidad = abreviaturaEntidad;
	}

	public String getAbreviaturaTipomoneda() {
		return this.abreviaturaTipomoneda;
	}

	public void setAbreviaturaTipomoneda(String abreviaturaTipomoneda) {
		this.abreviaturaTipomoneda = abreviaturaTipomoneda;
	}

	public String getDenominacionBoveda() {
		return this.denominacionBoveda;
	}

	public void setDenominacionBoveda(String denominacionBoveda) {
		this.denominacionBoveda = denominacionBoveda;
	}

	public String getDenominacionEntidad() {
		return this.denominacionEntidad;
	}

	public void setDenominacionEntidad(String denominacionEntidad) {
		this.denominacionEntidad = denominacionEntidad;
	}

	public String getDenominacionTipoentidad() {
		return this.denominacionTipoentidad;
	}

	public void setDenominacionTipoentidad(String denominacionTipoentidad) {
		this.denominacionTipoentidad = denominacionTipoentidad;
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

	public Integer getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdBoveda() {
		return this.idBoveda;
	}

	public void setIdBoveda(Integer idBoveda) {
		this.idBoveda = idBoveda;
	}

	public Integer getIdEntidad() {
		return this.idEntidad;
	}

	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}

	public Integer getIdHistorialboveda() {
		return this.idHistorialboveda;
	}

	public void setIdHistorialboveda(Integer idHistorialboveda) {
		this.idHistorialboveda = idHistorialboveda;
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

	public Integer getIdcreacionHistorialboveda() {
		return this.idcreacionHistorialboveda;
	}

	public void setIdcreacionHistorialboveda(Integer idcreacionHistorialboveda) {
		this.idcreacionHistorialboveda = idcreacionHistorialboveda;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}