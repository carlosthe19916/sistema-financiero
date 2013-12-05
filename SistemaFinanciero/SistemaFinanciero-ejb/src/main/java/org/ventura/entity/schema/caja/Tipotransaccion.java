package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the tipotransaccion database table.
 * 
 */
@Entity
@Table(name = "tipotransaccion", schema = "caja")
@NamedQuery(name = "Tipotransaccion.findAll", query = "SELECT t FROM Tipotransaccion t")
@NamedQueries({ @NamedQuery(name = Tipotransaccion.ALL_ACTIVE, query = "SELECT t FROM Tipotransaccion t WHERE t.estado = true") })
public class Tipotransaccion implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE = "org.ventura.entity.schema.caja.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipotransaccion;

	@Column(nullable = false, length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Tipotransaccion() {
	}

	public Integer getIdtipotransaccion() {
		return this.idtipotransaccion;
	}

	public void setIdtipotransaccion(Integer idtipotransaccion) {
		this.idtipotransaccion = idtipotransaccion;
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