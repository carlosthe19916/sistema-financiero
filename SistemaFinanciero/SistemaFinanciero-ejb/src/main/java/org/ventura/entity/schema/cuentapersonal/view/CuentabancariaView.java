package org.ventura.entity.schema.cuentapersonal.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the cuentabancaria_view database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria_view", schema = "cuentapersonal")
@NamedQuery(name = "CuentabancariaView.findAll", query = "SELECT c FROM CuentabancariaView c")
@NamedQueries({
		@NamedQuery(name = CuentabancariaView.findByNumerocuenta, query = "SELECT c FROM CuentabancariaView c WHERE c.numerocuenta = :numerocuenta"),
		@NamedQuery(name = CuentabancariaView.findByLikeDni, query = "SELECT c FROM CuentabancariaView c WHERE c.tipoPersona = 'PN' AND c.numeroDocumento LIKE :dni"),
		@NamedQuery(name = CuentabancariaView.findByLikeRuc, query = "SELECT c FROM CuentabancariaView c WHERE c.tipoPersona = 'PJ' AND c.numeroDocumento LIKE :ruc"),
		@NamedQuery(name = CuentabancariaView.findByLikeNombre, query = "SELECT c FROM CuentabancariaView c WHERE c.tipoPersona = 'PN' AND c.socio LIKE :nombre"),
		@NamedQuery(name = CuentabancariaView.findByLikeRazonsocial, query = "SELECT c FROM CuentabancariaView c WHERE c.tipoPersona = 'PJ' AND c.socio LIKE :razonsocial"),

		@NamedQuery(name = CuentabancariaView.f_tipocuentabancaria_tipodocumento_estado_searched, query = "SELECT c FROM CuentabancariaView c WHERE c.idTipocuentabancaria = :idtipocuentabancaria AND c.idTipodocumento = :idtipodocumento AND NOT (c.idEstadocuenta = :idestadocuenta) AND c.numeroDocumento LIKE :numerodocumento"),
		@NamedQuery(name = CuentabancariaView.f_tipocuentabancaria_estado_searched, query = "SELECT c FROM CuentabancariaView c WHERE c.idTipocuentabancaria = :idtipocuentabancaria AND NOT (c.idEstadocuenta = :idestadocuenta) AND UPPER(c.socio) LIKE :searched"),
		@NamedQuery(name = CuentabancariaView.f_tipodocumento_estado_searched, query = "SELECT c FROM CuentabancariaView c WHERE c.idTipodocumento = :idtipodocumento AND NOT (c.idEstadocuenta = :idestadocuenta) AND c.numeroDocumento LIKE :numerodocumento"),
		@NamedQuery(name = CuentabancariaView.f_estadocuenta_searched, query = "SELECT c FROM CuentabancariaView c WHERE NOT (c.idEstadocuenta = :idestadocuenta) AND (UPPER(c.socio) LIKE :searched OR c.numerocuenta LIKE :searched)") })
public class CuentabancariaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByNumerocuenta = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.findByNumerocuenta";
	public final static String findByLikeDni = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.findByLikeDni";
	public final static String findByLikeRuc = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.findByLikeRuc";
	public final static String findByLikeNombre = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.findByLikeNombre";
	public final static String findByLikeRazonsocial = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.findByLikeRazonsocial";

	public final static String f_tipocuentabancaria_tipodocumento_estado_searched = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.f_tipocuentabancaria_tipodocumento_searched";
	public final static String f_tipocuentabancaria_estado_searched = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.f_tipocuentabancaria_estado_searched";
	public final static String f_tipodocumento_estado_searched = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.f_tipodocumento_estado_searched";
	public final static String f_estadocuenta_searched = "org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView.f_estadocuenta_searched";

	@Id
	@Column(name = "id_cuentabancaria")
	private Integer idCuentabancaria;

	@Column(name = "abreviatura_estadocuenta", length = 3)
	private String abreviaturaEstadocuenta;

	@Column(name = "abreviatura_tipocuentabancaria", length = 30)
	private String abreviaturaTipocuentabancaria;

	@Column(name = "abreviatura_tipomomeda", length = 3)
	private String abreviaturaTipomomeda;

	@Column(name = "denominacion_estadocuenta", length = 20)
	private String denominacionEstadocuenta;

	@Column(name = "denominacion_tipocuentabancaria", length = 100)
	private String denominacionTipocuentabancaria;

	@Column(name = "denominacion_tipomoneda", length = 35)
	private String denominacionTipomoneda;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaapertura_cuentabancaria")
	private Date fechaaperturaCuentabancaria;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechacierre_cuentabancaria")
	private Date fechacierreCuentabancaria;

	@Column(name = "id_estadocuenta")
	private Integer idEstadocuenta;

	@Column(name = "id_tipocuentabancaria")
	private Integer idTipocuentabancaria;

	@Column(name = "id_tipomoneda")
	private Integer idTipomoneda;

	@Column(name = "numero_documento")
	private String numeroDocumento;

	@Column(name = "id_tipodocumento")
	private Integer idTipodocumento;

	@Column(name = "denominacion_tipodocumento")
	private String denominacionTipodocumento;

	@Column(name = "abreviatura_tipodocumento")
	private String abreviaturaTipodocumento;

	@Column(length = 14)
	private String numerocuenta;

	@Column(name = "saldo_cuentabancaria", precision = 18, scale = 2)
	private BigDecimal saldoCuentabancaria;

	@Column(length = 2147483647)
	private String socio;

	@Column(name = "tipo_persona")
	private String tipoPersona;

	@Column(name = "cantidadretirantes_cuentabancaria")
	private Integer cantidadretirantesCuentabancaria;
	
	@Column(name = "id_socio")
	private Integer idSocio;
	
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

	public void setAbreviaturaTipocuentabancaria(
			String abreviaturaTipocuentabancaria) {
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

	public void setDenominacionTipocuentabancaria(
			String denominacionTipocuentabancaria) {
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

	public String getSaldoCuentabancariaAsString() {
		if(this.saldoCuentabancaria!=null)
			return Moneda.getMonedaFormat(this.saldoCuentabancaria);
		else 
			return "";
	}
	
	public void setSaldoCuentabancaria(BigDecimal saldoCuentabancaria) {
		this.saldoCuentabancaria = saldoCuentabancaria;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Integer getIdTipodocumento() {
		return idTipodocumento;
	}

	public void setIdTipodocumento(Integer idTipodocumento) {
		this.idTipodocumento = idTipodocumento;
	}

	public String getDenominacionTipodocumento() {
		return denominacionTipodocumento;
	}

	public void setDenominacionTipodocumento(String denominacionTipodocumento) {
		this.denominacionTipodocumento = denominacionTipodocumento;
	}

	public String getAbreviaturaTipodocumento() {
		return abreviaturaTipodocumento;
	}

	public void setAbreviaturaTipodocumento(String abreviaturaTipodocumento) {
		this.abreviaturaTipodocumento = abreviaturaTipodocumento;
	}

	public Integer getCantidadretirantesCuentabancaria() {
		return cantidadretirantesCuentabancaria;
	}

	public void setCantidadretirantesCuentabancaria(
			Integer cantidadretirantesCuentabancaria) {
		this.cantidadretirantesCuentabancaria = cantidadretirantesCuentabancaria;
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

}