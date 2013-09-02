package org.ventura.model;

// Generated 02-sep-2013 11:26:30 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tarjetadebito generated by hbm2java
 */
@Entity
@Table(name = "tarjetadebito", schema = "cuentapersonal")
public class Tarjetadebito implements java.io.Serializable {

	private String numerotargeta;
	private Estadotargeta estadotargeta;
	private Tipotarjetadebito tipotarjetadebito;
	private Set tarjetadebitoasignadocuentaahorros = new HashSet(0);
	private Set targetadebitoasignadocuentacorrientes = new HashSet(0);

	public Tarjetadebito() {
	}

	public Tarjetadebito(String numerotargeta, Estadotargeta estadotargeta,
			Tipotarjetadebito tipotarjetadebito) {
		this.numerotargeta = numerotargeta;
		this.estadotargeta = estadotargeta;
		this.tipotarjetadebito = tipotarjetadebito;
	}

	public Tarjetadebito(String numerotargeta, Estadotargeta estadotargeta,
			Tipotarjetadebito tipotarjetadebito,
			Set tarjetadebitoasignadocuentaahorros,
			Set targetadebitoasignadocuentacorrientes) {
		this.numerotargeta = numerotargeta;
		this.estadotargeta = estadotargeta;
		this.tipotarjetadebito = tipotarjetadebito;
		this.tarjetadebitoasignadocuentaahorros = tarjetadebitoasignadocuentaahorros;
		this.targetadebitoasignadocuentacorrientes = targetadebitoasignadocuentacorrientes;
	}

	@Id
	@Column(name = "numerotargeta", unique = true, nullable = false, length = 16)
	public String getNumerotargeta() {
		return this.numerotargeta;
	}

	public void setNumerotargeta(String numerotargeta) {
		this.numerotargeta = numerotargeta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestadotargeta", nullable = false)
	public Estadotargeta getEstadotargeta() {
		return this.estadotargeta;
	}

	public void setEstadotargeta(Estadotargeta estadotargeta) {
		this.estadotargeta = estadotargeta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtargetadebitotipo", nullable = false)
	public Tipotarjetadebito getTipotarjetadebito() {
		return this.tipotarjetadebito;
	}

	public void setTipotarjetadebito(Tipotarjetadebito tipotarjetadebito) {
		this.tipotarjetadebito = tipotarjetadebito;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tarjetadebito")
	public Set getTarjetadebitoasignadocuentaahorros() {
		return this.tarjetadebitoasignadocuentaahorros;
	}

	public void setTarjetadebitoasignadocuentaahorros(
			Set tarjetadebitoasignadocuentaahorros) {
		this.tarjetadebitoasignadocuentaahorros = tarjetadebitoasignadocuentaahorros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tarjetadebito")
	public Set getTargetadebitoasignadocuentacorrientes() {
		return this.targetadebitoasignadocuentacorrientes;
	}

	public void setTargetadebitoasignadocuentacorrientes(
			Set targetadebitoasignadocuentacorrientes) {
		this.targetadebitoasignadocuentacorrientes = targetadebitoasignadocuentacorrientes;
	}

}
