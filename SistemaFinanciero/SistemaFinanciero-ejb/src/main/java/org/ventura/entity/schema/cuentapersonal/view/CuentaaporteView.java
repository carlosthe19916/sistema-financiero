package org.ventura.entity.schema.cuentapersonal.view;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the cuentaaporte_view database table.
 * 
 */
@Entity
@Table(name = "cuentaaporte_view", schema = "cuentapersonal")
@NamedQuery(name = "CuentaaporteView.findAll", query = "SELECT c FROM CuentaaporteView c")
@NamedQueries({
		@NamedQuery(name = CuentaaporteView.findByNumerocuenta, query = "SELECT c FROM CuentaaporteView c WHERE c.numerocuenta = :numerocuenta"),
		@NamedQuery(name = CuentaaporteView.findByLikeDni, query = "SELECT c FROM CuentaaporteView c WHERE c.tipoPersona = 'PN' AND c.numeroDocumento LIKE :dni"),
		@NamedQuery(name = CuentaaporteView.findByLikeRuc, query = "SELECT c FROM CuentaaporteView c WHERE c.tipoPersona = 'PJ' AND c.numeroDocumento LIKE :ruc"),
		@NamedQuery(name = CuentaaporteView.findByLikeNombre, query = "SELECT c FROM CuentaaporteView c WHERE c.tipoPersona = 'PN' AND c.titular LIKE :nombre"),
		@NamedQuery(name = CuentaaporteView.findByLikeRazonsocial, query = "SELECT c FROM CuentaaporteView c WHERE c.tipoPersona = 'PJ' AND c.titular LIKE :razonsocial") })
public class CuentaaporteView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByNumerocuenta = "org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView.findByNumerocuenta";
	public final static String findByLikeDni = "org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView.findByLikeDni";
	public final static String findByLikeRuc = "org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView.findByLikeRuc";
	public final static String findByLikeNombre = "org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView.findByLikeNombre";
	public final static String findByLikeRazonsocial = "org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView.findByLikeRazonsocial";

	@Id
	@Column(name = "id_cuentaaporte")
	private Integer idCuentaaporte;

	@Column(name = "tipo_cuenta")
	private String tipoCuenta;
	
	@Column(name = "abreviatura_estadocuenta", length = 3)
	private String abreviaturaEstadocuenta;

	@Column(name = "abreviatura_tipomomeda", length = 3)
	private String abreviaturaTipomomeda;

	@Column(name = "denominacion_estadocuenta", length = 20)
	private String denominacionEstadocuenta;

	@Column(name = "denominacion_tipomoneda", length = 35)
	private String denominacionTipomoneda;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaapertura_cuentaaporte")
	private Date fechaaperturaCuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechacierre_cuentaaporte")
	private Date fechacierreCuentaaporte;

	@Column(name = "id_estadocuenta")
	private Integer idEstadocuenta;

	@Column(name = "id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name = "numero_documento", length = 2147483647)
	private String numeroDocumento;

	@Column(length = 14)
	private String numerocuenta;

	@Column(name = "saldo_cuentaaporte", precision = 18, scale = 2)
	private BigDecimal saldoCuentaaporte;

	@Column(name = "tipo_persona", length = 2147483647)
	private String tipoPersona;

	@Column(length = 2147483647)
	private String titular;

	public CuentaaporteView() {
	}

	public String getAbreviaturaEstadocuenta() {
		return this.abreviaturaEstadocuenta;
	}

	public void setAbreviaturaEstadocuenta(String abreviaturaEstadocuenta) {
		this.abreviaturaEstadocuenta = abreviaturaEstadocuenta;
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

	public String getDenominacionTipomoneda() {
		return this.denominacionTipomoneda;
	}

	public void setDenominacionTipomoneda(String denominacionTipomoneda) {
		this.denominacionTipomoneda = denominacionTipomoneda;
	}

	public Date getFechaaperturaCuentaaporte() {
		return this.fechaaperturaCuentaaporte;
	}

	public void setFechaaperturaCuentaaporte(Date fechaaperturaCuentaaporte) {
		this.fechaaperturaCuentaaporte = fechaaperturaCuentaaporte;
	}

	public Date getFechacierreCuentaaporte() {
		return this.fechacierreCuentaaporte;
	}

	public void setFechacierreCuentaaporte(Date fechacierreCuentaaporte) {
		this.fechacierreCuentaaporte = fechacierreCuentaaporte;
	}

	public Integer getIdCuentaaporte() {
		return this.idCuentaaporte;
	}

	public void setIdCuentaaporte(Integer idCuentaaporte) {
		this.idCuentaaporte = idCuentaaporte;
	}

	public Integer getIdEstadocuenta() {
		return this.idEstadocuenta;
	}

	public void setIdEstadocuenta(Integer idEstadocuenta) {
		this.idEstadocuenta = idEstadocuenta;
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

	public BigDecimal getSaldoCuentaaporte() {
		return this.saldoCuentaaporte;
	}

	public void setSaldoCuentaaporte(BigDecimal saldoCuentaaporte) {
		this.saldoCuentaaporte = saldoCuentaaporte;
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

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

}