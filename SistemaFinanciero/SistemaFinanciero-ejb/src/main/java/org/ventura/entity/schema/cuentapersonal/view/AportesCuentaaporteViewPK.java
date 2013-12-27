package org.ventura.entity.schema.cuentapersonal.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class AportesCuentaaporteViewPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Integer idcuentaaporte;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mes;

	public Date getMes() {
		return mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public Integer getIdcuentaaporte() {
		return idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AportesCuentaaporteViewPK)) {
			return false;
		}
		AportesCuentaaporteViewPK castOther = (AportesCuentaaporteViewPK) other;
		return this.idcuentaaporte.equals(castOther.idcuentaaporte)
				&& this.mes.equals(castOther.mes);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idcuentaaporte.hashCode();
		hash = hash * prime + this.mes.hashCode();

		return hash;
	}
}
