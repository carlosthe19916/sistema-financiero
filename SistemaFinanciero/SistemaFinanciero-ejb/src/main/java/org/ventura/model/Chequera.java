package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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
	private Integer idchequeraestado;

	@Column(nullable=false)
	private Integer idsucursal;

	@Column(nullable=false)
	private Integer numerofin;

	@Column(nullable=false)
	private Integer numeroinicio;

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

	public Integer getIdchequeraestado() {
		return this.idchequeraestado;
	}

	public void setIdchequeraestado(Integer idchequeraestado) {
		this.idchequeraestado = idchequeraestado;
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

}