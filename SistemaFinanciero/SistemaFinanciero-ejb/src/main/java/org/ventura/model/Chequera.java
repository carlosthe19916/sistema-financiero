package org.ventura.model;

// Generated 02-sep-2013 11:26:30 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Chequera generated by hbm2java
 */
@Entity
@Table(name = "chequera", schema = "cuentapersonal")
public class Chequera implements java.io.Serializable {

	private ChequeraId id;
	private Sucursal sucursal;
	private Chequeraestado chequeraestado;
	private Cuentacorriente cuentacorriente;
	private Date fechaentrega;
	private int cantidad;
	private int numeroinicio;
	private int numerofin;
	private Set cheques = new HashSet(0);

	public Chequera() {
	}

	public Chequera(ChequeraId id, Sucursal sucursal,
			Chequeraestado chequeraestado, Cuentacorriente cuentacorriente,
			int cantidad, int numeroinicio, int numerofin) {
		this.id = id;
		this.sucursal = sucursal;
		this.chequeraestado = chequeraestado;
		this.cuentacorriente = cuentacorriente;
		this.cantidad = cantidad;
		this.numeroinicio = numeroinicio;
		this.numerofin = numerofin;
	}

	public Chequera(ChequeraId id, Sucursal sucursal,
			Chequeraestado chequeraestado, Cuentacorriente cuentacorriente,
			Date fechaentrega, int cantidad, int numeroinicio, int numerofin,
			Set cheques) {
		this.id = id;
		this.sucursal = sucursal;
		this.chequeraestado = chequeraestado;
		this.cuentacorriente = cuentacorriente;
		this.fechaentrega = fechaentrega;
		this.cantidad = cantidad;
		this.numeroinicio = numeroinicio;
		this.numerofin = numerofin;
		this.cheques = cheques;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "numerochequeraporcliente", column = @Column(name = "numerochequeraporcliente", nullable = false)),
			@AttributeOverride(name = "numerocuentacorriente", column = @Column(name = "numerocuentacorriente", nullable = false, length = 14)) })
	public ChequeraId getId() {
		return this.id;
	}

	public void setId(ChequeraId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsucursal", nullable = false)
	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idchequeraestado", nullable = false)
	public Chequeraestado getChequeraestado() {
		return this.chequeraestado;
	}

	public void setChequeraestado(Chequeraestado chequeraestado) {
		this.chequeraestado = chequeraestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numerocuentacorriente", nullable = false, insertable = false, updatable = false)
	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaentrega", length = 13)
	public Date getFechaentrega() {
		return this.fechaentrega;
	}

	public void setFechaentrega(Date fechaentrega) {
		this.fechaentrega = fechaentrega;
	}

	@Column(name = "cantidad", nullable = false)
	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "numeroinicio", nullable = false)
	public int getNumeroinicio() {
		return this.numeroinicio;
	}

	public void setNumeroinicio(int numeroinicio) {
		this.numeroinicio = numeroinicio;
	}

	@Column(name = "numerofin", nullable = false)
	public int getNumerofin() {
		return this.numerofin;
	}

	public void setNumerofin(int numerofin) {
		this.numerofin = numerofin;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chequera")
	public Set getCheques() {
		return this.cheques;
	}

	public void setCheques(Set cheques) {
		this.cheques = cheques;
	}

}
