package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accionista database table.
 * 
 */
@Entity
@NamedQuery(name="Accionista.findAll", query="SELECT a FROM Accionista a")
public class Accionista implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccionistaPK id;

	private Boolean estado;

	private double porcentajeparticipacion;

	//bi-directional many-to-one association to Personajuridica
	@ManyToOne
	@JoinColumn(name="ruc")
	private Personajuridica personajuridica;

	//bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name="dni")
	private Personanatural personanatural;

	public Accionista() {
	}

	public AccionistaPK getId() {
		return this.id;
	}

	public void setId(AccionistaPK id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public double getPorcentajeparticipacion() {
		return this.porcentajeparticipacion;
	}

	public void setPorcentajeparticipacion(double porcentajeparticipacion) {
		this.porcentajeparticipacion = porcentajeparticipacion;
	}

	public Personajuridica getPersonajuridica() {
		return this.personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

	public Personanatural getPersonanatural() {
		return this.personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

}