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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer idaccionista;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal porcentajeparticipacion;

	@ManyToOne
	@JoinColumn(name = "idpersonajuridica", nullable = false)
	private Personajuridica personajuridica;

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

	public Integer getIdaccionista() {
		return idaccionista;
	}

	public void setIdaccionista(Integer idaccionista) {
		this.idaccionista = idaccionista;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Accionista)) {
			return false;
		}
		// a room can be uniquely identified by it's number and the building it
		// belongs to
		final Accionista other = (Accionista) obj;
		return other.getIdaccionista().equals(idaccionista) ? true : false;
	}

	@Override
	public int hashCode() {
		return idaccionista;
	}
	
}