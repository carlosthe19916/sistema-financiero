package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tarjetadebitoasignadocuentaahorro database table.
 * 
 */
@Embeddable
public class TarjetadebitoasignadocuentaahorroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=14)
	private String numerocuentaahorro;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=16)
	private String numerotargeta;

	public TarjetadebitoasignadocuentaahorroPK() {
	}
	public String getNumerocuentaahorro() {
		return this.numerocuentaahorro;
	}
	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
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
		if (!(other instanceof TarjetadebitoasignadocuentaahorroPK)) {
			return false;
		}
		TarjetadebitoasignadocuentaahorroPK castOther = (TarjetadebitoasignadocuentaahorroPK)other;
		return 
			this.numerocuentaahorro.equals(castOther.numerocuentaahorro)
			&& this.numerotargeta.equals(castOther.numerotargeta);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numerocuentaahorro.hashCode();
		hash = hash * prime + this.numerotargeta.hashCode();
		
		return hash;
	}
}