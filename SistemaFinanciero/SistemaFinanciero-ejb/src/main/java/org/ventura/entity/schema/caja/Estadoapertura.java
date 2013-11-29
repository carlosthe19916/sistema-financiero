package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the estadoapertura database table.
 * 
 */
@Entity
@Table(name = "estadoapertura", schema = "caja")
@NamedQuery(name = "Estadoapertura.findAll", query = "SELECT e FROM Estadoapertura e")
public class Estadoapertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idestadoapertura;

	@Column(length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Estadoapertura() {
	}

	public Integer getIdestadoapertura() {
		return this.idestadoapertura;
	}

	public void setIdestadoapertura(Integer idestadoapertura) {
		this.idestadoapertura = idestadoapertura;
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
		if ((obj == null) || !(obj instanceof Estadoapertura)) {
			return false;
		}
		final Estadoapertura other = (Estadoapertura) obj;
		return other.getIdestadoapertura() == this.idestadoapertura ? true : false;
	}

	@Override
	public int hashCode() {
		return idestadoapertura;
	}

}