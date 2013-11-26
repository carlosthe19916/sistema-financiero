package org.ventura.entity.schema.persona;

import java.io.Serializable;

import javax.inject.Inject;
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
	private Double porcentajeparticipacion;

	//bi-directional many-to-one association to Personajuridica
	@ManyToOne
	@JoinColumn(name="ruc", nullable=false, insertable=false, updatable=false)
	private Personajuridica personajuridica;

	//bi-directional many-to-one association to Personanatural
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="dni", nullable=false, insertable=false, updatable=false)
	private Personanatural personanatural;

	public Accionista() {
		id = new AccionistaPK();
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

	public Double getPorcentajeparticipacion() {
		return this.porcentajeparticipacion;
	}

	public void setPorcentajeparticipacion(Double porcentajeparticipacion) {
		this.porcentajeparticipacion = porcentajeparticipacion;
	}

	public Personajuridica getPersonajuridica() {
		return this.personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
		if(personajuridica != null){
			this.id.setRuc(personajuridica.getRuc());
		} else {
			this.id.setRuc(null);
		}
	}

	public Personanatural getPersonanatural() {
		return this.personanatural;
	}

	public void setPersonanatural(Personanatural oPersonaNatural) {
		this.personanatural = oPersonaNatural;
		if(personanatural != null){
			this.id.setDni(personanatural.getDni());
		} else {
			this.id.setDni(null);
		}
	}
}