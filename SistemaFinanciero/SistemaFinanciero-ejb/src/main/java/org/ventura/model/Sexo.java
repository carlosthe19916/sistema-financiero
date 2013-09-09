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
		@NamedQuery(name = "Sexo.findAllActive", query = "SELECT e FROM Student e where e.estado=true"),
		@NamedQuery(name = "Sexo.findAllInactive", query = "SELECT e FROM Student e where e.estado=false") })
public class Sexo implements Serializable {

	private static final long serialVersionUID = 1L;

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

}