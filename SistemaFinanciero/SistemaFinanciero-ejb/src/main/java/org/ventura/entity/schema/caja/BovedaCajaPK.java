package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BovedaCajaPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(unique = false, nullable = false)
	private Integer idboveda;

	@Column(unique = false, nullable = false)
	private Integer idcaja;

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

	public Integer getIdcaja() {
		return idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BovedaCajaPK)) {
			return false;
		}
		BovedaCajaPK castOther = (BovedaCajaPK) other;
		return 
			this.idcaja.equals(castOther.idcaja)
			&& this.idboveda.equals(castOther.idboveda);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idcaja.hashCode();
		hash = hash * prime + this.idboveda.hashCode();
		
		return hash;
	}
}
