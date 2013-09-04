package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the cuentacorriente database table.
 * 
 */
@Entity
@Table(name="cuentacorriente", schema ="cuentapersonal")
@NamedQuery(name="Cuentacorriente.findAll", query="SELECT c FROM Cuentacorriente c")
public class Cuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=14)
	private String numerocuentacorriente;

	@Column(length=8)
	private String dni;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaapertura;

	@Column(nullable=false)
	private Integer idestadocuenta;

	@Column(nullable=false)
	private Integer idtipomoneda;

	@Column(length=11)
	private String ruc;

	public Cuentacorriente() {
	}

	public String getNumerocuentacorriente() {
		return this.numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
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

	public Integer getIdestadocuenta() {
		return this.idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

}