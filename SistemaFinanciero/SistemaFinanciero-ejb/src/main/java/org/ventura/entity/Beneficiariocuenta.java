package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the beneficiariocuenta database table.
 * 
 */
@Entity
@Table(name = "beneficiariocuenta", schema = "cuentapersonal")
@NamedQuery(name = "Beneficiariocuenta.findAll", query = "SELECT b FROM Beneficiariocuenta b")
public class Beneficiariocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idbeneficiariocuenta;

	@Column(nullable = false, length = 8)
	private String dni;

	@Column(nullable = false)
	private double porcentajebeneficio;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "numerocuentaahorro", nullable = false)
	private Cuentaahorro cuentaahorro;

	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "numerocuentacorriente")
	private Cuentacorriente cuentacorriente;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "numerocuentaplazofijo")
	private Cuentaplazofijo cuentaplazofijo;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;

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
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public Cuentaplazofijo getCuentaplazofijo() {
		return this.cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

}