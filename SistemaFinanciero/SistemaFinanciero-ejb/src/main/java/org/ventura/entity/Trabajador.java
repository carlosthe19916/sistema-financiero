package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the trabajador database table.
 * 
 */
@Entity
@Table(name="trabajador",schema="rrhh")
@NamedQuery(name="Trabajador.findAll", query="SELECT t FROM Trabajador t")
public class Trabajador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=8)
	private String dni;

	@Column(nullable=false)
	private Integer idsucursal;
	
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;
	
	@ManyToOne
	@JoinColumn(name = "idsucursal", insertable = false, updatable = false)
	private Sucursal sucursal;
	
	public Trabajador() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

}