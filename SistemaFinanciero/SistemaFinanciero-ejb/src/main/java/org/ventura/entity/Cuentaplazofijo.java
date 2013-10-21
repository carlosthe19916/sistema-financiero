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

	@Column(length=8)
	private String dni;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechavencimiento;

	@Column(nullable=false)
	private Integer idtipomoneda;

	@Column(nullable=false)
	private double itf;

	@Column(nullable=false)
	private double monto;

	@Column(nullable=false)
	private double montointerespagado;

	@Column(length=11)
	private String ruc;

	private double tasainteres;

	@Column(nullable=false)
	private double ticeaf;

	@Column(length=20)
	private String tiporetiro;

	@Column(nullable=false)
	private double trea;
	
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanaturalcliente personanaturalcliente;

	@ManyToOne
	@JoinColumn(name = "ruc", insertable = false, updatable = false)
	private Personajuridicacliente personajuridicacliente;
	
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	//bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy="cuentaplazofijo")
	private List<Beneficiariocuenta> beneficiariocuentas;

	//bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name="idestadocuenta", nullable=false)
	private Estadocuenta estadocuenta;

	//bi-directional many-to-one association to Frecuenciacapitalizacion
	@ManyToOne
	@JoinColumn(name="idfrecuenciacapitalizacion", nullable=false)
	private Frecuenciacapitalizacion frecuenciacapitalizacion;

	//bi-directional many-to-one association to Retirointere
	@ManyToOne
	@JoinColumn(name="idretirointeres", nullable=false)
	private Retirointere retirointere;

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

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public Retirointere getRetirointere() {
		return this.retirointere;
	}

	public void setRetirointere(Retirointere retirointere) {
		this.retirointere = retirointere;
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

	public Personanaturalcliente getPersonanaturalcliente() {
		return personanaturalcliente;
	}

	public void setPersonanaturalcliente(Personanaturalcliente personanaturalcliente) {
		this.personanaturalcliente = personanaturalcliente;
	}

	public Personajuridicacliente getPersonajuridicacliente() {
		return personajuridicacliente;
	}

	public void setPersonajuridicacliente(Personajuridicacliente personajuridicacliente) {
		this.personajuridicacliente = personajuridicacliente;
	}

}