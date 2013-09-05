package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cheque database table.
 * 
 */
@Entity
@Table(name="cheque",schema="cuentapersonal")
@NamedQuery(name="Cheque.findAll", query="SELECT c FROM Cheque c")
public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequePK id;

	@Column(nullable=false)
	private Integer idestadocheque;

	//bi-directional many-to-one association to Chequera
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="numerochequeraporcliente", referencedColumnName="numerocuentacorriente", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="numerocuentacorriente", referencedColumnName="numerochequeraporcliente", nullable=false)
		})
	private Chequera chequera1;

	//bi-directional many-to-one association to Chequera
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="numerochequeraporcliente", referencedColumnName="numerochequeraporcliente", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="numerocuentacorriente", referencedColumnName="numerocuentacorriente", nullable=false)
		})
	private Chequera chequera2;

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

	public Chequera getChequera1() {
		return this.chequera1;
	}

	public void setChequera1(Chequera chequera1) {
		this.chequera1 = chequera1;
	}

	public Chequera getChequera2() {
		return this.chequera2;
	}

	public void setChequera2(Chequera chequera2) {
		this.chequera2 = chequera2;
	}

}