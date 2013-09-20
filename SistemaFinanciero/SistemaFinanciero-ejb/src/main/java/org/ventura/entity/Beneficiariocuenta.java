package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.BeneficiariocuentaListener;

/**
 * The persistent class for the beneficiariocuenta database table.
 * 
 */
@Entity
@Table(name = "beneficiariocuenta", schema = "cuentapersonal")
@EntityListeners( { BeneficiariocuentaListener.class})
@NamedQuery(name = "Beneficiariocuenta.findAll", query = "SELECT b FROM Beneficiariocuenta b")
public class Beneficiariocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idbeneficiariocuenta;

	@Column(length = 8)
	private String dni;

	@Column(nullable = false, length = 50)
	private String apellidopaterno;

	@Column(nullable = false, length = 50)
	private String apellidomaterno;

	@Column(nullable = false, length = 60)
	private String nombres;

	@Column(nullable = false)
	private double porcentajebeneficio;

	@Column(nullable = false)
	private Boolean estado;

	@Column(length = 14)
	private String numerocuentaahorro;

	@Column(length = 14)
	private String numerocuentacorriente;

	@Column(length = 14)
	private String numerocuentaplazofijo;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "numerocuentaahorro", insertable = false, updatable = false)
	private Cuentaahorro cuentaahorro;

	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "numerocuentacorriente", insertable = false, updatable = false)
	private Cuentacorriente cuentacorriente;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "numerocuentaplazofijo", insertable = false, updatable = false)
	private Cuentaplazofijo cuentaplazofijo;

	public Beneficiariocuenta() {
	}

	public Integer getIdbeneficiariocuenta() {
		return this.idbeneficiariocuenta;
	}

	public void setIdbeneficiariocuenta(Integer idbeneficiariocuenta) {
		this.idbeneficiariocuenta = idbeneficiariocuenta;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public double getPorcentajebeneficio() {
		return this.porcentajebeneficio;
	}

	public void setPorcentajebeneficio(double porcentajebeneficio) {
		this.porcentajebeneficio = porcentajebeneficio;
	}

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
		this.numerocuentaahorro = cuentaahorro.getNumerocuentaahorro();
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
		this.numerocuentacorriente = cuentacorriente.getNumerocuentacorriente();
	}

	public Cuentaplazofijo getCuentaplazofijo() {
		return this.cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
		this.numerocuentaplazofijo = cuentaplazofijo.getNumerocuentaplazofijo();
	}

	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getNumerocuentaahorro() {
		return numerocuentaahorro;
	}

	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
	}

	public String getNumerocuentacorriente() {
		return numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}

	public String getNumerocuentaplazofijo() {
		return numerocuentaplazofijo;
	}

	public void setNumerocuentaplazofijo(String numerocuentaplazofijo) {
		this.numerocuentaplazofijo = numerocuentaplazofijo;
	}

}