package org.ventura.entity.schema.rrhh;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.sucursal.Agencia;

/**
 * The persistent class for the trabajador database table.
 * 
 */
@Entity
@Table(name = "trabajador", schema = "rrhh")
@NamedQuery(name = "Trabajador.findAll", query = "SELECT t FROM Trabajador t")
public class Trabajador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtrabajador;

	@Column(nullable = false)
	private Integer idagencia;

	@ManyToOne
	@JoinColumn(name = "idpersonanatural")
	private Personanatural personanatural;

	@ManyToOne
	@JoinColumn(name = "idagencia", insertable = false, updatable = false)
	private Agencia agencia;

	public Trabajador() {
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

	public Integer getIdtrabajador() {
		return idtrabajador;
	}

	public void setIdtrabajador(Integer idtrabajador) {
		this.idtrabajador = idtrabajador;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Trabajador)) {
			return false;
		}
		final Trabajador other = (Trabajador) obj;
		return other.getIdtrabajador() == this.idtrabajador ? true : false;
	}

	@Override
	public int hashCode() {
		return idtrabajador;
	}

}