package org.ventura.entity.schema.sucursal;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sucursal database table.
 * 
 */
@Entity
@Table(name="sucursal",schema="sucursal")
@NamedQuery(name="Sucursal.findAll", query="SELECT s FROM Sucursal s")
public class Sucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idsucursal;

	@Column(length=10)
	private String abreviatura;

	@Column(nullable=false, length=150)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false, length=6)
	private String idubigeo;

	public Sucursal() {
	}

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
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

	public String getIdubigeo() {
		return this.idubigeo;
	}

	public void setIdubigeo(String idubigeo) {
		this.idubigeo = idubigeo;
	}

}