package org.ventura.model;

// Generated 02-sep-2013 11:26:30 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tipotarjetadebito generated by hbm2java
 */
@Entity
@Table(name = "tipotarjetadebito", schema = "cuentapersonal")
public class Tipotarjetadebito implements java.io.Serializable {

	private int idtargetadebitotipo;
	private String denominacion;
	private boolean estado;
	private Set tarjetadebitos = new HashSet(0);

	public Tipotarjetadebito() {
	}

	public Tipotarjetadebito(int idtargetadebitotipo, String denominacion,
			boolean estado) {
		this.idtargetadebitotipo = idtargetadebitotipo;
		this.denominacion = denominacion;
		this.estado = estado;
	}

	public Tipotarjetadebito(int idtargetadebitotipo, String denominacion,
			boolean estado, Set tarjetadebitos) {
		this.idtargetadebitotipo = idtargetadebitotipo;
		this.denominacion = denominacion;
		this.estado = estado;
		this.tarjetadebitos = tarjetadebitos;
	}

	@Id
	@Column(name = "idtargetadebitotipo", unique = true, nullable = false)
	public int getIdtargetadebitotipo() {
		return this.idtargetadebitotipo;
	}

	public void setIdtargetadebitotipo(int idtargetadebitotipo) {
		this.idtargetadebitotipo = idtargetadebitotipo;
	}

	@Column(name = "denominacion", nullable = false, length = 50)
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@Column(name = "estado", nullable = false)
	public boolean isEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipotarjetadebito")
	public Set getTarjetadebitos() {
		return this.tarjetadebitos;
	}

	public void setTarjetadebitos(Set tarjetadebitos) {
		this.tarjetadebitos = tarjetadebitos;
	}

}
