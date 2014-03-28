package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the vouchercaja_cuentaaporte_view database table.
 * 
 */
@Entity
@Table(name = "vouchercaja_cuentaaporte_view", schema = "caja")
@NamedQuery(name = "VouchercajaCuentaaporteView.findAll", query = "SELECT v FROM VouchercajaCuentaaporteView v")
@NamedQueries({ @NamedQuery(name = VouchercajaCuentaaporteView.FindByIdTransaccioncuentaaporte, query = "SELECT v FROM VouchercajaCuentaaporteView v WHERE v.idTransaccioncuentaaporte = :idtransaccioncuentaaporte") })
public class VouchercajaCuentaaporteView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String FindByIdTransaccioncuentaaporte = "org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView.FindByIdTransaccioncuentaaporte";

	@Id
	@Column(name = "id_transaccioncuentaaporte")
	private Integer idTransaccioncuentaaporte;

	@Column(name = "abreviatura_agencia", length = 10)
	private String abreviaturaAgencia;

	@Column(name = "abreviatura_caja", length = 30)
	private String abreviaturaCaja;

	@Column(name = "abreviatura_tipomoneda_transaccion", length = 3)
	private String abreviaturaTipomonedaTransaccion;

	@Column(name = "abreviatura_tipotransaccion", length = 10)
	private String abreviaturaTipotransaccion;

	@Column(name = "codigo_agencia", length = 3)
	private String codigoAgencia;

	@Column(name = "denominacion_agencia", length = 150)
	private String denominacionAgencia;

	@Column(name = "denominacion_caja", length = 100)
	private String denominacionCaja;

	@Column(name = "denominacion_tipomoneda_transaccion", length = 35)
	private String denominacionTipomonedaTransaccion;

	@Column(name = "denominacion_tipotransaccion", length = 150)
	private String denominacionTipotransaccion;

	@Column(name = "estado_transaccioncuentaaporte")
	private Boolean estadoTransaccioncuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_transaccioncaja")
	private Date fechaTransaccioncaja;

	@Column(name = "hora_transaccioncaja")
	private Timestamp horaTransaccioncaja;

	@Column(name = "id_agencia")
	private Integer idAgencia;

	@Column(name = "id_caja")
	private Integer idCaja;

	@Column(name = "id_cuentaaporte")
	private Integer idCuentaaporte;

	@Column(name = "id_persona")
	private Integer idPersona;

	@Column(name = "id_socio")
	private Integer idSocio;

	@Column(name = "id_tipomoneda_transaccion")
	private Integer idTipomonedaTransaccion;

	@Column(name = "id_tipotransaccion")
	private Integer idTipotransaccion;

	@Column(name = "id_transaccioncaja")
	private Integer idTransaccioncaja;

	@Temporal(TemporalType.DATE)
	@Column(name = "mesafecta_transaccioncuentaaporte")
	private Date mesafectaTransaccioncuentaaporte;

	@Column(name = "monto_transaccioncuentaaporte", precision = 18, scale = 2)
	private BigDecimal montoTransaccioncuentaaporte;

	@Column(name = "numero_cuentaaporte", length = 14)
	private String numeroCuentaaporte;

	@Column(name = "numerooperacion_transaccioncaja")
	private Integer numerooperacionTransaccioncaja;

	@Column(length = 2147483647)
	private String persona;

	@Column(name = "referencia_transaccioncuentaaporte", length = 200)
	private String referenciaTransaccioncuentaaporte;

	@Column(name = "saldodissponible_transaccioncuentaaporte", precision = 18, scale = 2)
	private BigDecimal saldodissponibleTransaccioncuentaaporte;

	@Column(name = "tipo_persona", length = 2147483647)
	private String tipoPersona;

	public VouchercajaCuentaaporteView() {
	}

	public String getAbreviaturaAgencia() {
		return this.abreviaturaAgencia;
	}

	public void setAbreviaturaAgencia(String abreviaturaAgencia) {
		this.abreviaturaAgencia = abreviaturaAgencia;
	}

	public String getAbreviaturaCaja() {
		return this.abreviaturaCaja;
	}

	public void setAbreviaturaCaja(String abreviaturaCaja) {
		this.abreviaturaCaja = abreviaturaCaja;
	}

	public String getAbreviaturaTipomonedaTransaccion() {
		return this.abreviaturaTipomonedaTransaccion;
	}

	public void setAbreviaturaTipomonedaTransaccion(
			String abreviaturaTipomonedaTransaccion) {
		this.abreviaturaTipomonedaTransaccion = abreviaturaTipomonedaTransaccion;
	}

	public String getAbreviaturaTipotransaccion() {
		return this.abreviaturaTipotransaccion;
	}

	public void setAbreviaturaTipotransaccion(String abreviaturaTipotransaccion) {
		this.abreviaturaTipotransaccion = abreviaturaTipotransaccion;
	}

	public String getCodigoAgencia() {
		return this.codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getDenominacionAgencia() {
		return this.denominacionAgencia;
	}

	public void setDenominacionAgencia(String denominacionAgencia) {
		this.denominacionAgencia = denominacionAgencia;
	}

	public String getDenominacionCaja() {
		return this.denominacionCaja;
	}

	public void setDenominacionCaja(String denominacionCaja) {
		this.denominacionCaja = denominacionCaja;
	}

	public String getDenominacionTipomonedaTransaccion() {
		return this.denominacionTipomonedaTransaccion;
	}

	public void setDenominacionTipomonedaTransaccion(
			String denominacionTipomonedaTransaccion) {
		this.denominacionTipomonedaTransaccion = denominacionTipomonedaTransaccion;
	}

	public String getDenominacionTipotransaccion() {
		return this.denominacionTipotransaccion;
	}

	public void setDenominacionTipotransaccion(
			String denominacionTipotransaccion) {
		this.denominacionTipotransaccion = denominacionTipotransaccion;
	}

	public Boolean getEstadoTransaccioncuentaaporte() {
		return this.estadoTransaccioncuentaaporte;
	}

	public void setEstadoTransaccioncuentaaporte(
			Boolean estadoTransaccioncuentaaporte) {
		this.estadoTransaccioncuentaaporte = estadoTransaccioncuentaaporte;
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

	public Integer getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	public Integer getIdCuentaaporte() {
		return this.idCuentaaporte;
	}

	public void setIdCuentaaporte(Integer idCuentaaporte) {
		this.idCuentaaporte = idCuentaaporte;
	}

	public Integer getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getIdSocio() {
		return this.idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public Integer getIdTipomonedaTransaccion() {
		return this.idTipomonedaTransaccion;
	}

	public void setIdTipomonedaTransaccion(Integer idTipomonedaTransaccion) {
		this.idTipomonedaTransaccion = idTipomonedaTransaccion;
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

	public Integer getIdTransaccioncuentaaporte() {
		return this.idTransaccioncuentaaporte;
	}

	public void setIdTransaccioncuentaaporte(Integer idTransaccioncuentaaporte) {
		this.idTransaccioncuentaaporte = idTransaccioncuentaaporte;
	}

	public Date getMesafectaTransaccioncuentaaporte() {
		return this.mesafectaTransaccioncuentaaporte;
	}

	public void setMesafectaTransaccioncuentaaporte(
			Date mesafectaTransaccioncuentaaporte) {
		this.mesafectaTransaccioncuentaaporte = mesafectaTransaccioncuentaaporte;
	}

	public BigDecimal getMontoTransaccioncuentaaporte() {
		return this.montoTransaccioncuentaaporte;
	}
	
	public String getMontoTransaccioncuentaaporteAsString() {
		return Moneda.getMonedaFormat(this.montoTransaccioncuentaaporte);
	}
	
	public void setMontoTransaccioncuentaaporte(
			BigDecimal montoTransaccioncuentaaporte) {
		this.montoTransaccioncuentaaporte = montoTransaccioncuentaaporte;
	}

	public String getNumeroCuentaaporte() {
		return this.numeroCuentaaporte;
	}

	public void setNumeroCuentaaporte(String numeroCuentaaporte) {
		this.numeroCuentaaporte = numeroCuentaaporte;
	}

	public Integer getNumerooperacionTransaccioncaja() {
		return this.numerooperacionTransaccioncaja;
	}

	public void setNumerooperacionTransaccioncaja(
			Integer numerooperacionTransaccioncaja) {
		this.numerooperacionTransaccioncaja = numerooperacionTransaccioncaja;
	}

	public String getPersona() {
		return this.persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getReferenciaTransaccioncuentaaporte() {
		return this.referenciaTransaccioncuentaaporte;
	}

	public void setReferenciaTransaccioncuentaaporte(
			String referenciaTransaccioncuentaaporte) {
		this.referenciaTransaccioncuentaaporte = referenciaTransaccioncuentaaporte;
	}

	public BigDecimal getSaldodissponibleTransaccioncuentaaporte() {
		return this.saldodissponibleTransaccioncuentaaporte;
	}

	public String getSaldodissponibleTransaccioncuentaaporteAsString() {
		return Moneda.getMonedaFormat(this.saldodissponibleTransaccioncuentaaporte);
	}
	
	public void setSaldodissponibleTransaccioncuentaaporte(
			BigDecimal saldodissponibleTransaccioncuentaaporte) {
		this.saldodissponibleTransaccioncuentaaporte = saldodissponibleTransaccioncuentaaporte;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

}