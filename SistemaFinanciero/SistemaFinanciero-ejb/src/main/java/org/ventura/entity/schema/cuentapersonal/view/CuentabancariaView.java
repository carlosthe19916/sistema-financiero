package org.ventura.entity.schema.cuentapersonal.view;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cuentabancaria_view database table.
 * 
 */
@Entity
@Table(name="cuentabancaria_view" , schema ="cuentapersonal")
@NamedQuery(name="CuentabancariaView.findAll", query="SELECT c FROM CuentabancariaView c")
public class CuentabancariaView implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cuentabancaria")
	private Integer idCuentabancaria;
	
	@Column(name="abreviatura_estadocuenta", length=3)
	private String abreviaturaEstadocuenta;

	@Column(name="abreviatura_tipocuentabancaria", length=30)
	private String abreviaturaTipocuentabancaria;

	@Column(name="abreviatura_tipomomeda", length=3)
	private String abreviaturaTipomomeda;

	@Column(name="denominacion_estadocuenta", length=20)
	private String denominacionEstadocuenta;

	@Column(name="denominacion_tipocuentabancaria", length=100)
	private String denominacionTipocuentabancaria;

	@Column(name="denominacion_tipomoneda", length=35)
	private String denominacionTipomoneda;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaapertura_cuentabancaria")
	private Date fechaaperturaCuentabancaria;

	@Temporal(TemporalType.DATE)
	@Column(name="fechacierre_cuentabancaria")
	private Date fechacierreCuentabancaria;

	@Column(name="id_estadocuenta")
	private Integer idEstadocuenta;

	@Column(name="id_tipocuentabancaria")
	private Integer idTipocuentabancaria;

	@Column(name="id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name="numero_documento", length=2147483647)
	private String numeroDocumento;

	@Column(length=14)
	private String numerocuenta;

	@Column(name="saldo_cuentabancaria", precision=18, scale=2)
	private BigDecimal saldoCuentabancaria;

	@Column(length=2147483647)
	private String titular;

	public CuentabancariaView() {
	}

	public String getAbreviaturaEstadocuenta() {
		return this.abreviaturaEstadocuenta;
	}

	public void setAbreviaturaEstadocuenta(String abreviaturaEstadocuenta) {
		this.abreviaturaEstadocuenta = abreviaturaEstadocuenta;
	}

	public String getAbreviaturaTipocuentabancaria() {
		return this.abreviaturaTipocuentabancaria;
	}

	public void setAbreviaturaTipocuentabancaria(String abreviaturaTipocuentabancaria) {
		this.abreviaturaTipocuentabancaria = abreviaturaTipocuentabancaria;
	}

	public String getAbreviaturaTipomomeda() {
		return this.abreviaturaTipomomeda;
	}

	public void setAbreviaturaTipomomeda(String abreviaturaTipomomeda) {
		this.abreviaturaTipomomeda = abreviaturaTipomomeda;
	}

	public String getDenominacionEstadocuenta() {
		return this.denominacionEstadocuenta;
	}

	public void setDenominacionEstadocuenta(String denominacionEstadocuenta) {
		this.denominacionEstadocuenta = denominacionEstadocuenta;
	}

	public String getDenominacionTipocuentabancaria() {
		return this.denominacionTipocuentabancaria;
	}

	public void setDenominacionTipocuentabancaria(String denominacionTipocuentabancaria) {
		this.denominacionTipocuentabancaria = denominacionTipocuentabancaria;
	}

	public String getDenominacionTipomoneda() {
		return this.denominacionTipomoneda;
	}

	public void setDenominacionTipomoneda(String denominacionTipomoneda) {
		this.denominacionTipomoneda = denominacionTipomoneda;
	}

	public Date getFechaaperturaCuentabancaria() {
		return this.fechaaperturaCuentabancaria;
	}

	public void setFechaaperturaCuentabancaria(Date fechaaperturaCuentabancaria) {
		this.fechaaperturaCuentabancaria = fechaaperturaCuentabancaria;
	}

	public Date getFechacierreCuentabancaria() {
		return this.fechacierreCuentabancaria;
	}

	public void setFechacierreCuentabancaria(Date fechacierreCuentabancaria) {
		this.fechacierreCuentabancaria = fechacierreCuentabancaria;
	}

	public Integer getIdCuentabancaria() {
		return this.idCuentabancaria;
	}

	public void setIdCuentabancaria(Integer idCuentabancaria) {
		this.idCuentabancaria = idCuentabancaria;
	}

	public Integer getIdEstadocuenta() {
		return this.idEstadocuenta;
	}

	public void setIdEstadocuenta(Integer idEstadocuenta) {
		this.idEstadocuenta = idEstadocuenta;
	}

	public Integer getIdTipocuentabancaria() {
		return this.idTipocuentabancaria;
	}

	public void setIdTipocuentabancaria(Integer idTipocuentabancaria) {
		this.idTipocuentabancaria = idTipocuentabancaria;
	}

	public Integer getIdTipomoneda() {
		return this.idTipomoneda;
	}

	public void setIdTipomoneda(Integer idTipomoneda) {
		this.idTipomoneda = idTipomoneda;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumerocuenta() {
		return this.numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public BigDecimal getSaldoCuentabancaria() {
		return this.saldoCuentabancaria;
	}

	public void setSaldoCuentabancaria(BigDecimal saldoCuentabancaria) {
		this.saldoCuentabancaria = saldoCuentabancaria;
	}

	public String getTitular() {
		return this.titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

}