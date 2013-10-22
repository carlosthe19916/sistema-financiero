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
	private Integer idagencia;
	
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;
	
	@ManyToOne
	@JoinColumn(name = "idagencia", insertable = false, updatable = false)
	private Agencia agencia;
	
	public Trabajador() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}