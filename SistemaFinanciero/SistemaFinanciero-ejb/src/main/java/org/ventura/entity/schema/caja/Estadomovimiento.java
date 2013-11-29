package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the estadomovimiento database table.
 * 
 */
@Entity
@Table(name = "estadomovimiento", schema = "caja")
@NamedQuery(name = "Estadomovimiento.findAll", query = "SELECT e FROM Estadomovimiento e")
public class Estadomovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idestadomovimiento;

	@Column(nullable = false, length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Estadomovimiento() {
	}

	public Integer getIdestadomovimiento() {
		return this.idestadomovimiento;
	}

	public void setIdestadomovimiento(Integer idestadomovimiento) {
		this.idestadomovimiento = idestadomovimiento;
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
		if ((obj == null) || !(obj instanceof Estadomovimiento)) {
			return false;
		}
		final Estadomovimiento other = (Estadomovimiento) obj;
		return other.getIdestadomovimiento() == this.idestadomovimiento ? true : false;
	}

	@Override
	public int hashCode() {
		return idestadomovimiento;
	}
}