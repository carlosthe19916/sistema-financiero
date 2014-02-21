package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the entidadfinanciera database table.
 * 
 */
@Entity
@Table(name = "entidadfinanciera", schema = "caja")
@NamedQuery(name = "Entidadfinanciera.findAll", query = "SELECT e FROM Entidadfinanciera e")
@NamedQueries({ @NamedQuery(name = Entidadfinanciera.ALL_ACTIVE, query = "SELECT e FROM Entidadfinanciera e WHERE e.estado = true") })
public class Entidadfinanciera implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE = "org.ventura.entity.schema.Entidadfinanciera.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer identidadfinanciera;

	@Column(nullable = false, length = 20)
	private String abreviatura;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(length = 30)
	private String numerocuenta;

	public Entidadfinanciera() {
	}

	public Integer getIdentidadfinanciera() {
		return this.identidadfinanciera;
	}

	public void setIdentidadfinanciera(Integer identidadfinanciera) {
		this.identidadfinanciera = identidadfinanciera;
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

	public String getNumerocuenta() {
		return this.numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Entidadfinanciera)) {
			return false;
		}
		final Entidadfinanciera other = (Entidadfinanciera) obj;
		return other.getIdentidadfinanciera().equals(identidadfinanciera) ? true : false;
	}

	@Override
	public int hashCode() {
		return identidadfinanciera;
	}

}