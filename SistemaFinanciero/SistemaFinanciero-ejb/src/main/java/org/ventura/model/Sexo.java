package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the sexo database table.
 * 
 */
@Entity
@Table(name = "sexo", schema = "maestro")
@NamedQuery(name = "Sexo.findAll", query = "SELECT s FROM Sexo s")
@NamedQueries({
		@NamedQuery(name = Sexo.ALL, query = "Select b From Sexo b"),
		@NamedQuery(name = Sexo.ALL_ACTIVE, query = "Select s From Sexo s WHERE s.estado=true")})

public class Sexo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String findAll = "org.ventura.model.sexo.findAll";
	public final static String ALL = "org.ventura.model.Sexo.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Sexo.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idsexo;

	@Column(length = 1)
	private String abreviatura;

	@Column(length = 15)
	private String denominacion;

	private Boolean estado;

	public Sexo() {
	}

	public Integer getIdsexo() {
		return this.idsexo;
	}

	public void setIdsexo(Integer idsexo) {
		this.idsexo = idsexo;
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
	public int hashCode() {
		return idsexo;
	}

}