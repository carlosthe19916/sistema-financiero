package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cheque database table.
 * 
 */
@Entity
@Table(name = "cheque", schema = "cuentapersonal")
@NamedQuery(name = "Cheque.findAll", query = "SELECT c FROM Cheque c")
public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequePK id;

	@Column(nullable = false)
	private Integer idestadocheque;

	@Column(nullable = false, length = 14)
	private String numerocuentacorriente;

	@ManyToOne
	@JoinColumn(name = "idestadocheque", nullable = false, insertable = false, updatable = false)
	private Chequeestado chequeestado;

	public Cheque() {
	}

	public ChequePK getId() {
		return this.id;
	}

	public void setId(ChequePK id) {
		this.id = id;
	}

	public Integer getIdestadocheque() {
		return this.idestadocheque;
	}

	public void setIdestadocheque(Integer idestadocheque) {
		this.idestadocheque = idestadocheque;
	}

	public String getNumerocuentacorriente() {
		return this.numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}

	public Chequeestado getChequeestado() {
		return chequeestado;
	}

	public void setChequeestado(Chequeestado chequeestado) {
		this.chequeestado = chequeestado;
	}

}