package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the beneficiariocuenta database table.
 * 
 */
@Entity
@NamedQuery(name="Beneficiariocuenta.findAll", query="SELECT b FROM Beneficiariocuenta b")
public class Beneficiariocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dni;

	private Integer idbeneficiariocuenta;

	private double porcentajebeneficio;

	//bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name="numerocuentaahorro")
	private Cuentaahorro cuentaahorro;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente")
	private Cuentacorriente cuentacorriente;

	//bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name="numerocuentaplazofijo")
	private Cuentaplazofijo cuentaplazofijo;

	public Beneficiariocuenta() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getIdbeneficiariocuenta() {
		return this.idbeneficiariocuenta;
	}

	public void setIdbeneficiariocuenta(Integer idbeneficiariocuenta) {
		this.idbeneficiariocuenta = idbeneficiariocuenta;
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

}