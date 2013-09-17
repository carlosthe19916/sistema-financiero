package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the accionista database table.
 * 
 */
@Embeddable
public class AccionistaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String dni;

	@Column(insertable=false, updatable=false, unique=true, nullable=false, length=11)
	private String ruc;

	public AccionistaPK() {
	}
	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getRuc() {
		return this.ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccionistaPK)) {
			return false;
		}
		AccionistaPK castOther = (AccionistaPK)other;
		return 
			this.dni.equals(castOther.dni)
			&& this.ruc.equals(castOther.ruc);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dni.hashCode();
		hash = hash * prime + this.ruc.hashCode();
		
		return hash;
	}
}