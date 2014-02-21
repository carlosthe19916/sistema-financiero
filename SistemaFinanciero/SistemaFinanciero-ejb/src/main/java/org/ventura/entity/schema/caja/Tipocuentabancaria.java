package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the tipocuentabancaria database table.
 * 
 */
@Entity
@Table(name = "tipocuentabancaria", schema = "cuentapersonal")
@NamedQuery(name = "Tipocuentabancaria.findAll", query = "SELECT t FROM Tipocuentabancaria t")
public class Tipocuentabancaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipocuentabancaria;

	@Column(nullable = false, length = 30)
	private String abreviatura;

	@Column(nullable = false, length = 100)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Tipocuentabancaria() {
	}

	public Integer getIdtipocuentabancaria() {
		return this.idtipocuentabancaria;
	}

	public void setIdtipocuentabancaria(Integer idtipocuentabancaria) {
		this.idtipocuentabancaria = idtipocuentabancaria;
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
		if ((obj == null) || !(obj instanceof Tipocuentabancaria)) {
			return false;
		}
		final Tipocuentabancaria other = (Tipocuentabancaria) obj;
		return other.getIdtipocuentabancaria().equals(this.idtipocuentabancaria) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtipocuentabancaria;
	}

}