package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cheque database table.
 * 
 */
@Entity
@Table(name = "cheque", schema = "cuentapersonal")
@NamedQuery(name="Cheque.findAll", query="SELECT c FROM Cheque c")
public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequePK id;

	//bi-directional many-to-one association to Chequeestado
	@ManyToOne
	@JoinColumn(name="idestadocheque")
	private Chequeestado chequeestado;

	//bi-directional many-to-one association to Chequera
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="numerochequeraporcliente", referencedColumnName="numerocuentacorriente"),
		@JoinColumn(name="numerocuentacorriente", referencedColumnName="numerochequeraporcliente")
		})
	private Chequera chequera;

	public Cheque() {
	}

	public ChequePK getId() {
		return this.id;
	}

	public void setId(ChequePK id) {
		this.id = id;
	}

	public Chequeestado getChequeestado() {
		return this.chequeestado;
	}

	public void setChequeestado(Chequeestado chequeestado) {
		this.chequeestado = chequeestado;
	}

	public Chequera getChequera() {
		return this.chequera;
	}

	public void setChequera(Chequera chequera) {
		this.chequera = chequera;
	}

}