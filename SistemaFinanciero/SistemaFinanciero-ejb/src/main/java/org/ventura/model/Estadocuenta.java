package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadocuenta database table.
 * 
 */
@Entity
@Table(name="estadocuenta",schema="cuentapersonal")
@NamedQuery(name="Estadocuenta.findAll", query="SELECT e FROM Estadocuenta e")
public class Estadocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idestadocuenta;

	@Column(length=3)
	private String abreviatura;

	@Column(nullable=false, length=20)
	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Cuentaahorro
	@OneToMany(mappedBy="estadocuenta")
	private List<Cuentaahorro> cuentaahorros;

	//bi-directional many-to-one association to Cuentacorriente
	@OneToMany(mappedBy="estadocuenta")
	private List<Cuentacorriente> cuentacorrientes;

	//bi-directional many-to-one association to Cuentaplazofijo
	@OneToMany(mappedBy="estadocuenta")
	private List<Cuentaplazofijo> cuentaplazofijos;

	public Estadocuenta() {
	}

	public Integer getIdestadocuenta() {
		return this.idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Cuentaahorro> getCuentaahorros() {
		return this.cuentaahorros;
	}

	public void setCuentaahorros(List<Cuentaahorro> cuentaahorros) {
		this.cuentaahorros = cuentaahorros;
	}

	public Cuentaahorro addCuentaahorro(Cuentaahorro cuentaahorro) {
		getCuentaahorros().add(cuentaahorro);
		cuentaahorro.setEstadocuenta(this);

		return cuentaahorro;
	}

	public Cuentaahorro removeCuentaahorro(Cuentaahorro cuentaahorro) {
		getCuentaahorros().remove(cuentaahorro);
		cuentaahorro.setEstadocuenta(null);

		return cuentaahorro;
	}

	public List<Cuentacorriente> getCuentacorrientes() {
		return this.cuentacorrientes;
	}

	public void setCuentacorrientes(List<Cuentacorriente> cuentacorrientes) {
		this.cuentacorrientes = cuentacorrientes;
	}

	public Cuentacorriente addCuentacorriente(Cuentacorriente cuentacorriente) {
		getCuentacorrientes().add(cuentacorriente);
		cuentacorriente.setEstadocuenta(this);

		return cuentacorriente;
	}

	public Cuentacorriente removeCuentacorriente(Cuentacorriente cuentacorriente) {
		getCuentacorrientes().remove(cuentacorriente);
		cuentacorriente.setEstadocuenta(null);

		return cuentacorriente;
	}

	public List<Cuentaplazofijo> getCuentaplazofijos() {
		return this.cuentaplazofijos;
	}

	public void setCuentaplazofijos(List<Cuentaplazofijo> cuentaplazofijos) {
		this.cuentaplazofijos = cuentaplazofijos;
	}

	public Cuentaplazofijo addCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().add(cuentaplazofijo);
		cuentaplazofijo.setEstadocuenta(this);

		return cuentaplazofijo;
	}

	public Cuentaplazofijo removeCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().remove(cuentaplazofijo);
		cuentaplazofijo.setEstadocuenta(null);

		return cuentaplazofijo;
	}

}