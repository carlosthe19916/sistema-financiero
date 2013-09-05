package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tarjetadebitoasignadocuentacorriente database table.
 * 
 */
@Embeddable
public class TarjetadebitoasignadocuentacorrientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=14)
	private String numerocuentacorriente;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=16)
	private String numerotargeta;

	public TarjetadebitoasignadocuentacorrientePK() {
	}
	public String getNumerocuentacorriente() {
		return this.numerocuentacorriente;
	}
	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}
	public String getNumerotargeta() {
		return this.numerotargeta;
	}
	public void setNumerotargeta(String numerotargeta) {
		this.numerotargeta = numerotargeta;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TarjetadebitoasignadocuentacorrientePK)) {
			return false;
		}
		TarjetadebitoasignadocuentacorrientePK castOther = (TarjetadebitoasignadocuentacorrientePK)other;
		return 
			this.numerocuentacorriente.equals(castOther.numerocuentacorriente)
			&& this.numerotargeta.equals(castOther.numerotargeta);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numerocuentacorriente.hashCode();
		hash = hash * prime + this.numerotargeta.hashCode();
		
		return hash;
	}
}