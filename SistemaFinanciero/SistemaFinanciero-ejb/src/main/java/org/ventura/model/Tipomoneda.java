package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipomoneda database table.
 * 
 */
@Entity
@NamedQuery(name="Tipomoneda.findAll", query="SELECT t FROM Tipomoneda t")
public class Tipomoneda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idtipomoneda;

	private String abreviatura;

	private String denominacion;

	private Boolean estado;

	public Tipomoneda() {
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
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