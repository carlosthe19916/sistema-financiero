package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the cuentabancaria database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria", schema = "cuentapersonal")
@NamedQuery(name = "Cuentabancaria.findAll", query = "SELECT c FROM Cuentabancaria c")
public class Cuentabancaria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idcuentabancaria;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	private Integer fechacierre;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@Column(nullable = false)
	private Integer idsocio;

	@Column(nullable = false)
	private Integer idtipocuentabancaria;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false, length = 14)
	private String numerocuenta;

	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal saldo;

	public Cuentabancaria() {
	}

	public Integer getIdcuentabancaria() {
		return this.idcuentabancaria;
	}

	public void setIdcuentabancaria(Integer idcuentabancaria) {
		this.idcuentabancaria = idcuentabancaria;
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Integer getFechacierre() {
		return this.fechacierre;
	}

	public void setFechacierre(Integer fechacierre) {
		this.fechacierre = fechacierre;
	}

	public Integer getIdestadocuenta() {
		return this.idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public Integer getIdsocio() {
		return this.idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Integer getIdtipocuentabancaria() {
		return this.idtipocuentabancaria;
	}

	public void setIdtipocuentabancaria(Integer idtipocuentabancaria) {
		this.idtipocuentabancaria = idtipocuentabancaria;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public String getNumerocuenta() {
		return this.numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}