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
	private Integer idchequeraestado;

	@Column(nullable=false)
	private Integer idsucursal;

	@Column(nullable=false)
	private Integer numerofin;

	@Column(nullable=false)
	private Integer numeroinicio;

	//bi-directional many-to-one association to Cheque
	@OneToMany(mappedBy="chequera1")
	private List<Cheque> cheques1;

	//bi-directional many-to-one association to Cheque
	@OneToMany(mappedBy="chequera2")
	private List<Cheque> cheques2;

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

	public List<Cheque> getCheques1() {
		return this.cheques1;
	}

	public void setCheques1(List<Cheque> cheques1) {
		this.cheques1 = cheques1;
	}

	public Cheque addCheques1(Cheque cheques1) {
		getCheques1().add(cheques1);
		cheques1.setChequera1(this);

		return cheques1;
	}

	public Cheque removeCheques1(Cheque cheques1) {
		getCheques1().remove(cheques1);
		cheques1.setChequera1(null);

		return cheques1;
	}

	public List<Cheque> getCheques2() {
		return this.cheques2;
	}

	public void setCheques2(List<Cheque> cheques2) {
		this.cheques2 = cheques2;
	}

	public Cheque addCheques2(Cheque cheques2) {
		getCheques2().add(cheques2);
		cheques2.setChequera2(this);

		return cheques2;
	}

	public Cheque removeCheques2(Cheque cheques2) {
		getCheques2().remove(cheques2);
		cheques2.setChequera2(null);

		return cheques2;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

}