package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the chequera database table.
 * 
 */
@Embeddable
public class ChequeraPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private Integer numerochequeraporcliente;

	@Column(insertable = false, updatable = false, unique = true, nullable = false, length = 14)
	private String numerocuentacorriente;

	public ChequeraPK() {
	}

	public Integer getNumerochequeraporcliente() {
		return this.numerochequeraporcliente;
	}

	public void setNumerochequeraporcliente(Integer numerochequeraporcliente) {
		this.numerochequeraporcliente = numerochequeraporcliente;
	}

	public String getNumerocuentacorriente() {
		return this.numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChequeraPK)) {
			return false;
		}
		ChequeraPK castOther = (ChequeraPK) other;
		return this.numerochequeraporcliente
				.equals(castOther.numerochequeraporcliente)
				&& this.numerocuentacorriente
						.equals(castOther.numerocuentacorriente);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numerochequeraporcliente.hashCode();
		hash = hash * prime + this.numerocuentacorriente.hashCode();

		return hash;
	}
}