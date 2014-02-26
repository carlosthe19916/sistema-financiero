package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the estadocuenta_view database table.
 * 
 */
@Entity
@Table(name="estadocuenta_view", schema = "caja")
@NamedQuery(name="EstadocuentaView.findAll", query="SELECT e FROM EstadocuentaView e")

@NamedQueries({
	@NamedQuery(name = EstadocuentaView.f_transacciones_estadocuenta, query = "select ecv from EstadocuentaView ecv where ecv.numeroCuenta=:numerocuenta and ecv.fechaTransaccioncaja between :fechaInicio and :fechaFin order by ecv.fechaTransaccioncaja, ecv.horaTransaccioncaja")})
public class EstadocuentaView implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String f_transacciones_estadocuenta = "org.ventura.entity.schema.caja.view.EstadocuentaView.f_transacciones_estadocuenta";

	@Id
	@Column(name="id_transaccioncaja")
	private Integer idTransaccioncaja;
	
	@Column(name="abreviatura_transaccion")
	private String abreviaturaTransaccion;

	@Column(name="denominacion_transaccion")
	private String denominacionTransaccion;

	@Column(name="estado_transaccion")
	private Boolean estadoTransaccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_transaccioncaja")
	private Date fechaTransaccioncaja;

	@Column(name="hora_transaccioncaja")
	private Timestamp horaTransaccioncaja;

	@Column(name="monto_transaccon")
	private BigDecimal montoTransaccon;

	@Column(name="numero_cuenta")
	private String numeroCuenta;

	@Column(name="numero_operacion")
	private Integer numeroOperacion;

	@Column(name="referecia_transaccion")
	private String refereciaTransaccion;

	private BigDecimal saldodisponible;

	@Column(name="tipo_transaccion")
	private Integer tipoTransaccion;

	public EstadocuentaView() {
	}

	public String getAbreviaturaTransaccion() {
		return this.abreviaturaTransaccion;
	}

	public void setAbreviaturaTransaccion(String abreviaturaTransaccion) {
		this.abreviaturaTransaccion = abreviaturaTransaccion;
	}

	public String getDenominacionTransaccion() {
		return this.denominacionTransaccion;
	}

	public void setDenominacionTransaccion(String denominacionTransaccion) {
		this.denominacionTransaccion = denominacionTransaccion;
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

	public Integer getIdTransaccioncaja() {
		return this.idTransaccioncaja;
	}

	public void setIdTransaccioncaja(Integer idTransaccioncaja) {
		this.idTransaccioncaja = idTransaccioncaja;
	}

	public BigDecimal getMontoTransaccon() {
		return this.montoTransaccon;
	}

	public void setMontoTransaccon(BigDecimal montoTransaccon) {
		this.montoTransaccon = montoTransaccon;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Integer getNumeroOperacion() {
		return this.numeroOperacion;
	}

	public void setNumeroOperacion(Integer numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public String getRefereciaTransaccion() {
		return this.refereciaTransaccion;
	}

	public void setRefereciaTransaccion(String refereciaTransaccion) {
		this.refereciaTransaccion = refereciaTransaccion;
	}

	public BigDecimal getSaldodisponible() {
		return this.saldodisponible;
	}

	public void setSaldodisponible(BigDecimal saldodisponible) {
		this.saldodisponible = saldodisponible;
	}

	public Integer getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public void setTipoTransaccion(Integer tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

}