package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cuentaplazofijo database table.
 * 
 */
@Entity
@Table(name = "cuentaplazofijo", schema = "cuentapersonal")
@NamedQuery(name = "Cuentaplazofijo.findAll", query = "SELECT c FROM Cuentaplazofijo c")
public class Cuentaplazofijo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String numerocuentaplazofijo;

	private String dni;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	private Date fechavencimiento;

	private Integer idtipomoneda;

	private double itf;

	private double monto;

	private double montointerespagado;

	private String ruc;

	private BigDecimal tasainteres;

	private double ticeaf;

	private String tiporetiro;

	private double trea;

	// bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy = "cuentaplazofijo")
	private List<Beneficiariocuenta> beneficiariocuentas;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta")
	private Estadocuenta estadocuenta;

	// bi-directional many-to-one association to Frecuenciacapitalizacion
	@ManyToOne
	@JoinColumn(name = "idfrecuenciacapitalizacion")
	private Frecuenciacapitalizacion frecuenciacapitalizacion;

	// bi-directional many-to-one association to Retirointere
	@ManyToOne
	@JoinColumn(name = "idretirointeres")
	private Retirointeres retirointere;

	// bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy = "cuentaplazofijo")
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

	public BigDecimal getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(BigDecimal tasainteres) {
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

	public void setBeneficiariocuentas(
			List<Beneficiariocuenta> beneficiariocuentas) {
		this.beneficiariocuentas = beneficiariocuentas;
	}

	public Beneficiariocuenta addBeneficiariocuenta(
			Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().add(beneficiariocuenta);
		beneficiariocuenta.setCuentaplazofijo(this);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(
			Beneficiariocuenta beneficiariocuenta) {
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

	public void setFrecuenciacapitalizacion(
			Frecuenciacapitalizacion frecuenciacapitalizacion) {
		this.frecuenciacapitalizacion = frecuenciacapitalizacion;
	}

	public Retirointeres getRetirointere() {
		return this.retirointere;
	}

	public void setRetirointere(Retirointeres retirointere) {
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

}