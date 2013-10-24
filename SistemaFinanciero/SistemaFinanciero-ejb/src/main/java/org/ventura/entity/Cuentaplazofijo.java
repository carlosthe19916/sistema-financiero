package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cuentaplazofijo database table.
 * 
 */
@Entity
@Table(name="cuentaplazofijo",schema="cuentapersonal")
@NamedQuery(name="Cuentaplazofijo.findAll", query="SELECT c FROM Cuentaplazofijo c")
public class Cuentaplazofijo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=14)
	private String numerocuentaplazofijo;

	@Column
	private Integer codigosocio;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechavencimiento;

	@Column(nullable=false)
	private Integer idtipomoneda;
	
	@Column(nullable=false)
	private Integer idestadocuenta;
	
	@Column(nullable=false)
	private Integer idretirointeres;
	
	@Column(nullable=false)
	private Integer idfrecuenciacapitalizacion;
	
	@Column(nullable=false)
	private Integer plazo;
	
	@Column(nullable=false)
	private boolean confirmacionsaldos;
	
	@Column(nullable=false)
	private double itf;

	@Column(nullable=false)
	private double monto;

	@Column(nullable=false)
	private double montointerespagado;

	private double tasainteres;

	@Column(nullable=false)
	private double ticeaf;

	@Column(length=20)
	private String tiporetiro;

	@Column(nullable=false)
	private double trea;
	
	@ManyToOne
	@JoinColumn(name = "codigosocio", insertable = false, updatable = false)
	private Socio socio;
	
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;
	

	//bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy="cuentaplazofijo")
	private List<Beneficiariocuenta> beneficiariocuentas;

	//bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name="idestadocuenta", nullable=false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	//bi-directional many-to-one association to Frecuenciacapitalizacion
	@ManyToOne
	@JoinColumn(name="idfrecuenciacapitalizacion", nullable = false, insertable = false, updatable = false)
	private Frecuenciacapitalizacion frecuenciacapitalizacion;

	//bi-directional many-to-one association to Retirointere
	@ManyToOne
	@JoinColumn(name="idretirointeres", nullable = false, insertable = false, updatable = false)
	private Retirointeres retirointeres;

	//bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy="cuentaplazofijo")
	private List<Titularcuenta> titularcuentas;

	public Cuentaplazofijo() {
	}

	public String getNumerocuentaplazofijo() {
		return this.numerocuentaplazofijo;
	}

	public void setNumerocuentaplazofijo(String numerocuentaplazofijo) {
		this.numerocuentaplazofijo = numerocuentaplazofijo;
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Date getFechavencimiento() {
		return this.fechavencimiento;
	}

	public void setFechavencimiento(Date fechavencimiento) {
		this.fechavencimiento = fechavencimiento;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getItf() {
		return this.itf;
	}

	public void setItf(double itf) {
		this.itf = itf;
	}

	public double getMonto() {
		return this.monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getMontointerespagado() {
		return this.montointerespagado;
	}

	public void setMontointerespagado(double montointerespagado) {
		this.montointerespagado = montointerespagado;
	}

	public double getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(double tasainteres) {
		this.tasainteres = tasainteres;
	}

	public double getTiceaf() {
		return this.ticeaf;
	}

	public void setTiceaf(double ticeaf) {
		this.ticeaf = ticeaf;
	}

	public String getTiporetiro() {
		return this.tiporetiro;
	}

	public void setTiporetiro(String tiporetiro) {
		this.tiporetiro = tiporetiro;
	}

	public double getTrea() {
		return this.trea;
	}

	public void setTrea(double trea) {
		this.trea = trea;
	}

	public List<Beneficiariocuenta> getBeneficiariocuentas() {
		return this.beneficiariocuentas;
	}

	public void setBeneficiariocuentas(List<Beneficiariocuenta> beneficiariocuentas) {
		this.beneficiariocuentas = beneficiariocuentas;
	}

	public Beneficiariocuenta addBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().add(beneficiariocuenta);
		beneficiariocuenta.setCuentaplazofijo(this);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().remove(beneficiariocuenta);
		beneficiariocuenta.setCuentaplazofijo(null);

		return beneficiariocuenta;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
	}

	public Frecuenciacapitalizacion getFrecuenciacapitalizacion() {
		return this.frecuenciacapitalizacion;
	}

	public void setFrecuenciacapitalizacion(Frecuenciacapitalizacion frecuenciacapitalizacion) {
		this.frecuenciacapitalizacion = frecuenciacapitalizacion;
	}

	public List<Titularcuenta> getTitularcuentas() {
		return this.titularcuentas;
	}

	public void setTitularcuentas(List<Titularcuenta> titularcuentas) {
		this.titularcuentas = titularcuentas;
	}

	public Titularcuenta addTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().add(titularcuenta);
		titularcuenta.setCuentaplazofijo(this);

		return titularcuenta;
	}

	public Titularcuenta removeTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().remove(titularcuenta);
		titularcuenta.setCuentaplazofijo(null);

		return titularcuenta;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Integer getCodigosocio() {
		return codigosocio;
	}

	public void setCodigosocio(Integer codigosocio) {
		this.codigosocio = codigosocio;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Integer getPlazo() {
		return plazo;
	}

	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public boolean isConfirmacionsaldos() {
		return confirmacionsaldos;
	}

	public void setConfirmacionsaldos(boolean confirmacionsaldos) {
		this.confirmacionsaldos = confirmacionsaldos;
	}

	public Integer getIdretirointeres() {
		return idretirointeres;
	}

	public void setIdretirointeres(Integer idretirointeres) {
		this.idretirointeres = idretirointeres;
	}

	public Integer getIdfrecuenciacapitalizacion() {
		return idfrecuenciacapitalizacion;
	}

	public void setIdfrecuenciacapitalizacion(Integer idfrecuenciacapitalizacion) {
		this.idfrecuenciacapitalizacion = idfrecuenciacapitalizacion;
	}

	public Retirointeres getRetirointeres() {
		return retirointeres;
	}

	public void setRetirointeres(Retirointeres retirointeres) {
		this.retirointeres = retirointeres;
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

}