package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the cheque database table.
 * 
 */
@Embeddable
public class CuentabancariaTipotasaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Integer idcuentabancaria;

	@Column(nullable = false)
	private Integer idtipotasa;

	public CuentabancariaTipotasaPK() {
	}

	public Integer getIdcuentabancaria() {
		return idcuentabancaria;
	}

	public void setIdcuentabancaria(Integer idcuentabancaria) {
		this.idcuentabancaria = idcuentabancaria;
	}

	public Integer getIdtipotasa() {
		return idtipotasa;
	}

	public void setIdtipotasa(Integer idtipotasa) {
		this.idtipotasa = idtipotasa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CuentabancariaTipotasaPK)) {
			return false;
		}
		CuentabancariaTipotasaPK castOther = (CuentabancariaTipotasaPK) other;
		return this.idcuentabancaria.equals(castOther.idcuentabancaria)
				&& this.idtipotasa.equals(castOther.idtipotasa);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idcuentabancaria.hashCode();
		hash = hash * prime + this.idtipotasa.hashCode();

		return hash;
	}
}