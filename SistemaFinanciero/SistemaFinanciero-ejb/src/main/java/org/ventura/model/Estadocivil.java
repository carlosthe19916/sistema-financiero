package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the estadocivil database table.
 * 
 */
@Entity
@Table(name = "estadocivil", schema = "maestro")
@NamedQuery(name = "Estadocivil.findAll", query = "SELECT e FROM Estadocivil e")
public class Estadocivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idestadocivil;

	@Column(length = 1)
	private String abreviatura;

	@Column(length = 15)
	private String denominacion;

	private Boolean estado;

	public Estadocivil() {
	}

	public Integer getIdestadocivil() {
		return this.idestadocivil;
	}

	public void setIdestadocivil(Integer idestadocivil) {
		this.idestadocivil = idestadocivil;
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