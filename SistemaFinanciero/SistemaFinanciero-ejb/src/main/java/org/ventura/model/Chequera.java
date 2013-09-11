package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the chequera database table.
 * 
 */
@Entity
@Table(name="chequera",schema="cuentapersonal")
@NamedQuery(name="Chequera.findAll", query="SELECT c FROM Chequera c")
public class Chequera implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequeraPK id;

	@Column(nullable=false)
	private Integer cantidad;

	@Temporal(TemporalType.DATE)
	private Date fechaentrega;

	@Column(nullable=false)
	private Integer idsucursal;

	@Column(nullable=false)
	private Integer numerofin;

	@Column(nullable=false)
	private Integer numeroinicio;



	//bi-directional many-to-one association to Chequeraestado
	@ManyToOne
	@JoinColumn(name="idchequeraestado", nullable=false)
	private Chequeraestado chequeraestado;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente", nullable=false, insertable=false, updatable=false)
	private Cuentacorriente cuentacorriente;

	public Chequera() {
	}

	public ChequeraPK getId() {
		return this.id;
	}

	public void setId(ChequeraPK id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaentrega() {
		return this.fechaentrega;
	}

	public void setFechaentrega(Date fechaentrega) {
		this.fechaentrega = fechaentrega;
	}

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public Integer getNumerofin() {
		return this.numerofin;
	}

	public void setNumerofin(Integer numerofin) {
		this.numerofin = numerofin;
	}

	public Integer getNumeroinicio() {
		return this.numeroinicio;
	}

	public void setNumeroinicio(Integer numeroinicio) {
		this.numeroinicio = numeroinicio;
	}


	public Chequeraestado getChequeraestado() {
		return this.chequeraestado;
	}

	public void setChequeraestado(Chequeraestado chequeraestado) {
		this.chequeraestado = chequeraestado;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

}