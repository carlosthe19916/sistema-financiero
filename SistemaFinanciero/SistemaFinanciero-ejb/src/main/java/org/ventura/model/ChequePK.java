package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cheque database table.
 * 
 */
@Embeddable
public class ChequePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private Integer numerocheque;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private Integer numerochequeraporcliente;

	public ChequePK() {
	}
	public Integer getNumerocheque() {
		return this.numerocheque;
	}
	public void setNumerocheque(Integer numerocheque) {
		this.numerocheque = numerocheque;
	}
	public Integer getNumerochequeraporcliente() {
		return this.numerochequeraporcliente;
	}
	public void setNumerochequeraporcliente(Integer numerochequeraporcliente) {
		this.numerochequeraporcliente = numerochequeraporcliente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChequePK)) {
			return false;
		}
		ChequePK castOther = (ChequePK)other;
		return 
			this.numerocheque.equals(castOther.numerocheque)
			&& this.numerochequeraporcliente.equals(castOther.numerochequeraporcliente);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numerocheque.hashCode();
		hash = hash * prime + this.numerochequeraporcliente.hashCode();
		
		return hash;
	}
}