package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the cheque database table.
 * 
 */
@Embeddable
public class ViewBovedadetallePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private Integer idboveda;

	@Column(unique = true, nullable = false)
	private Integer idhistorialboveda;

	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialboveda;

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

	public Integer getIddetallehistorialboveda() {
		return iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Integer getIdhistorialboveda() {
		return idhistorialboveda;
	}

	public void setIdhistorialboveda(Integer idhistorialboveda) {
		this.idhistorialboveda = idhistorialboveda;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ViewBovedadetallePK)) {
			return false;
		}
		ViewBovedadetallePK castOther = (ViewBovedadetallePK) other;
		return this.idboveda.equals(castOther.idboveda)
				&& this.idhistorialboveda.equals(castOther.idhistorialboveda)
				&& this.iddetallehistorialboveda
						.equals(castOther.iddetallehistorialboveda);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idboveda.hashCode();
		hash = hash * prime + this.idhistorialboveda.hashCode();
		hash = hash * prime + this.iddetallehistorialboveda.hashCode();

		return hash;
	}
}