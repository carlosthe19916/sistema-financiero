package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the tipotransaccioncompraventa database table.
 * 
 */
@Entity
@Table(name = "tipotransaccioncompraventa", schema = "caja")
@NamedQuery(name = "Tipotransaccioncompraventa.findAll", query = "SELECT t FROM Tipotransaccioncompraventa t")
@NamedQueries({ @NamedQuery(name = Tipotransaccioncompraventa.ALL_ACTIVE, query = "SELECT t FROM Tipotransaccioncompraventa t WHERE t.estado = true") })
public class Tipotransaccioncompraventa implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE = "org.ventura.entity.schema.caja.Tipotransaccioncompraventa.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipotransaccioncompraventa;

	@Column(length = 30)
	private String abreviatura;

	@Column(nullable = false, length = 200)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Tipotransaccioncompraventa() {
	}

	public Integer getIdtipotransaccioncompraventa() {
		return this.idtipotransaccioncompraventa;
	}

	public void setIdtipotransaccioncompraventa(
			Integer idtipotransaccioncompraventa) {
		this.idtipotransaccioncompraventa = idtipotransaccioncompraventa;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tipotransaccioncompraventa)) {
			return false;
		}
		final Tipotransaccioncompraventa other = (Tipotransaccioncompraventa) obj;
		return other.getIdtipotransaccioncompraventa() == this.idtipotransaccioncompraventa? true : false;
	}

	@Override
	public int hashCode() {
		return idtipotransaccioncompraventa;
	}

}