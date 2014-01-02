package org.ventura.entity.schema.persona;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the accionista database table.
 * 
 */
@Entity
@Table(name = "accionista", schema = "persona")
@NamedQuery(name = "Accionista.findAll", query = "SELECT a FROM Accionista a")
public class Accionista implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, precision = 4, scale = 2)
	private BigDecimal porcentajeparticipacion;

	// bi-directional many-to-one association to Personajuridica
	@ManyToOne
	@JoinColumn(name = "idpersonajuridica", nullable = false)
	private Personajuridica personajuridica;

	// bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name = "idpersonanatural", nullable = false)
	private Personanatural personanatural;

	public Accionista() {
	}

	public BigDecimal getPorcentajeparticipacion() {
		return this.porcentajeparticipacion;
	}

	public void setPorcentajeparticipacion(BigDecimal porcentajeparticipacion) {
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