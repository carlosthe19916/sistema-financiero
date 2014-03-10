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
@NamedQueries({
		@NamedQuery(name = Trabajador.f_idagencia, query = "SELECT t FROM Trabajador t INNER JOIN t.agencia a WHERE a.idagencia = :idagencia"),
		@NamedQuery(name = Trabajador.f_idagencia_searched, query = "SELECT t FROM Trabajador t INNER JOIN t.personanatural p INNER JOIN t.agencia a WHERE a.idagencia = :idagencia AND UPPER(CONCAT(p.apellidopaterno,' ', p.apellidomaterno,' ', p.nombres)) LIKE :searched"),
		@NamedQuery(name = Trabajador.f_idagencia_idtipodocumento_numerodocumento, query = "SELECT t FROM Trabajador t INNER JOIN t.personanatural p INNER JOIN t.agencia a WHERE a.idagencia = :idagencia AND p.tipodocumento.idtipodocumento = :idtipodocumento AND p.numerodocumento = :numerodocumento") })
public class Trabajador implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String f_idagencia = "org.ventura.entity.schema.rrhh.Trabajador.f_idusuario";
	public final static String f_idagencia_searched = "org.ventura.entity.schema.rrhh.Trabajador.f_searched";
	public final static String f_idagencia_idtipodocumento_numerodocumento = "org.ventura.entity.schema.rrhh.Trabajador.f_idtipodocumento_numerodocumento";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtrabajador;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idpersonanatural")
	private Personanatural personanatural;

	@ManyToOne
	@JoinColumn(name = "idagencia")
	private Agencia agencia;

	public Trabajador() {
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
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
		return other.getIdtrabajador().equals(this.idtrabajador) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtrabajador;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}