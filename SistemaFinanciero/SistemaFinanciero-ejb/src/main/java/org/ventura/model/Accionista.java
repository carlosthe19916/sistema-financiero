package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accionista database table.
 * 
 */
@Entity
@Table(name="accionista", schema="persona")
@NamedQuery(name="Accionista.findAll", query="SELECT a FROM Accionista a")
public class Accionista implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccionistaPK id;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false)
	private double porcentajeparticipacion;

	//bi-directional many-to-one association to Personajuridica
	@ManyToOne
	@JoinColumn(name="ruc", nullable=false, insertable=false, updatable=false)
	private Personajuridica personajuridica;

	//bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name="dni", nullable=false, insertable=false, updatable=false)
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