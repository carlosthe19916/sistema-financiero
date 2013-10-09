package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sistema database table.
 * 
 */
@Entity
@Table(name="sistema",schema="seguridad")
@NamedQuery(name="Sistema.findAll", query="SELECT s FROM Sistema s")
public class Sistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idsistema;

	@Column(nullable=false, length=200)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	public Sistema() {
	}

	public Integer getIdsistema() {
		return this.idsistema;
	}

	public void setIdsistema(Integer idsistema) {
		this.idsistema = idsistema;
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