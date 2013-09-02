package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the targetadebitoasignadocuentacorriente database table.
 * 
 */
@Embeddable
public class TargetadebitoasignadocuentacorrientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String numerocuentacorriente;

	@Column(insertable=false, updatable=false)
	private String numerotargeta;

	public TargetadebitoasignadocuentacorrientePK() {
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
		if (!(other instanceof TargetadebitoasignadocuentacorrientePK)) {
			return false;
		}
		TargetadebitoasignadocuentacorrientePK castOther = (TargetadebitoasignadocuentacorrientePK)other;
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