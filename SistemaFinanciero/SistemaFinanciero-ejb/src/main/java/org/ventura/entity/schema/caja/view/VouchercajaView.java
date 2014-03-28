package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the vouchercaja_view database table.
 * 
 */
@Entity
@Table(name = "vouchercaja_view", schema = "caja")
@NamedQuery(name = "VouchercajaView.findAll", query = "SELECT v FROM VouchercajaView v")
@NamedQueries({ @NamedQuery(name = VouchercajaView.FindByIdTransaccioncuentabancaria, query = "SELECT v FROM VouchercajaView v WHERE v.idTransaccioncuentabancaria = :idtransaccioncuentabancaria") })
public class VouchercajaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String FindByIdTransaccioncuentabancaria = "org.ventura.entity.schema.caja.view.VouchercajaView.FindByIdTransaccioncuentabancaria";

	@Id
	@Column(name = "id_transaccioncuentabancaria")
	private Integer idTransaccioncuentabancaria;

	@Column(name = "id_socio")
	private Integer idSocio;
	
	@Column(name = "id_transaccioncaja")
	private Integer idTransaccioncaja;

	@Column(name = "abreviatura_agencia", length = 10)
	private String abreviaturaAgencia;

	@Column(name = "abreviatura_caja", length = 30)
	private String abreviaturaCaja;

	@Column(name = "abreviatura_moneda", length = 3)
	private String abreviaturaMoneda;

	@Column(name = "abreviatura_tipotransaccion", length = 10)
	private String abreviaturaTipotransaccion;

	@Column(name = "codigo_agencia", length = 3)
	private String codigoAgencia;

	@Column(name = "denominacion_agencia", length = 150)
	private String denominacionAgencia;

	@Column(name = "denominacion_caja", length = 100)
	private String denominacionCaja;

	@Column(name = "denominacion_moneda", length = 35)
	private String denominacionMoneda;

	@Column(name = "denominacion_tipocuentabancaria", length = 100)
	private String denominacionTipocuentabancaria;

	@Column(name = "denominacion_tipotransaccion", length = 150)
	private String denominacionTipotransaccion;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Timestamp hora;

	@Column(name = "id_cuentabancaria")
	private Integer idCuentabancaria;

	@Column(precision = 18, scale = 2)
	private BigDecimal monto;

	@Column(name = "numero_cuenta", length = 14)
	private String numeroCuenta;

	@Column(name = "numero_operacion")
	private Integer numeroOperacion;

	@Column(length = 250)
	private String referencia;

	@Column(precision = 18, scale = 2)
	private BigDecimal saldo;

	@Column(name = "tipo_persona", length = 2147483647)
	private String tipoPersona;

	@Column(length = 2147483647)
	private String titular;

	public VouchercajaView() {
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

	public String getAbreviaturaMoneda() {
		return this.abreviaturaMoneda;
	}

	public void setAbreviaturaMoneda(String abreviaturaMoneda) {
		this.abreviaturaMoneda = abreviaturaMoneda;
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

	public String getDenominacionMoneda() {
		return this.denominacionMoneda;
	}

	public void setDenominacionMoneda(String denominacionMoneda) {
		this.denominacionMoneda = denominacionMoneda;
	}

	public String getDenominacionTipocuentabancaria() {
		return this.denominacionTipocuentabancaria;
	}

	public void setDenominacionTipocuentabancaria(
			String denominacionTipocuentabancaria) {
		this.denominacionTipocuentabancaria = denominacionTipocuentabancaria;
	}

	public String getDenominacionTipotransaccion() {
		return this.denominacionTipotransaccion;
	}

	public void setDenominacionTipotransaccion(
			String denominacionTipotransaccion) {
		this.denominacionTipotransaccion = denominacionTipotransaccion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getHora() {
		return this.hora;
	}

	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

	public Integer getIdCuentabancaria() {
		return this.idCuentabancaria;
	}

	public void setIdCuentabancaria(Integer idCuentabancaria) {
		this.idCuentabancaria = idCuentabancaria;
	}

	public Integer getIdTransaccioncaja() {
		return this.idTransaccioncaja;
	}

	public void setIdTransaccioncaja(Integer idTransaccioncaja) {
		this.idTransaccioncaja = idTransaccioncaja;
	}

	public Integer getIdTransaccioncuentabancaria() {
		return this.idTransaccioncuentabancaria;
	}

	public void setIdTransaccioncuentabancaria(
			Integer idTransaccioncuentabancaria) {
		this.idTransaccioncuentabancaria = idTransaccioncuentabancaria;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}
	
	public String getMontoAsString() {
		return Moneda.getMonedaFormat(this.monto);
	}
	
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
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

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public String getSaldoAsString() {
		return Moneda.getMonedaFormat(this.saldo);
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTitular() {
		return this.titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

}