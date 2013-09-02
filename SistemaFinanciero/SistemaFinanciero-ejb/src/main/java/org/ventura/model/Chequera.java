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
@Table(name = "chequera", schema = "cuentapersonal")
@NamedQuery(name="Chequera.findAll", query="SELECT c FROM Chequera c")
public class Chequera implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequeraPK id;

	private Integer cantidad;

	@Temporal(TemporalType.DATE)
	private Date fechaentrega;

	private Integer idsucursal;

	private Integer numerofin;

	private Integer numeroinicio;

	//bi-directional many-to-one association to Cheque
	@OneToMany(mappedBy="chequera")
	private List<Cheque> cheques;

	//bi-directional many-to-one association to Chequeraestado
	@ManyToOne
	@JoinColumn(name="idchequeraestado")
	private Chequeraestado chequeraestado;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente")
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

	public List<Cheque> getCheques() {
		return this.cheques;
	}

	public void setCheques(List<Cheque> cheques) {
		this.cheques = cheques;
	}

	public Cheque addCheque(Cheque cheque) {
		getCheques().add(cheque);
		cheque.setChequera(this);

		return cheque;
	}

	public Cheque removeCheque(Cheque cheque) {
		getCheques().remove(cheque);
		cheque.setChequera(null);

		return cheque;
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