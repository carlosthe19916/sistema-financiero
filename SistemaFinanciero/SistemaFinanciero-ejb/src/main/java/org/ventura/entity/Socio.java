package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name="socio")
@NamedQuery(name="Socio.findAll", query="SELECT s FROM Socio s")
public class Socio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=8)
	private String codigosocio;

	@Column(length=8)
	private String dni;
	
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaasociado;

	@Column(length=30)
	private String ruc;
	
	@ManyToOne
	@JoinColumn(name = "ruc", nullable = false, insertable = false, updatable = false)
	private Personajuridica personajuridica;

	public Socio() {
	}

	public String getCodigosocio() {
		return this.codigosocio;
	}

	public void setCodigosocio(String codigosocio) {
		this.codigosocio = codigosocio;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFechaasociado() {
		return this.fechaasociado;
	}

	public void setFechaasociado(Date fechaasociado) {
		this.fechaasociado = fechaasociado;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

}