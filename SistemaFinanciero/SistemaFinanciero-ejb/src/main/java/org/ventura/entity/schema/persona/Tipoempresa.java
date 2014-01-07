package org.ventura.entity.schema.persona;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;

import java.util.List;

/**
 * The persistent class for the tipoempresa database table.
 * 
 */
@Entity
@Table(name = "tipoempresa", schema = "persona")
@NamedQuery(name = "Tipoempresa.findAll", query = "SELECT t FROM Tipoempresa t")
@NamedQueries({
		@NamedQuery(name = Tipoempresa.ALL, query = "SELECT t FROM Tipoempresa t"),
		@NamedQuery(name = Tipoempresa.ALL_ACTIVE, query = "SELECT t FROM Tipoempresa t WHERE t.estado = TRUE") })
public class Tipoempresa implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.entity.schema.persona.tipoempresa.ALL";
	public final static String ALL_ACTIVE = "org.ventura.entity.schema.persona.tipoempresa.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipoempresa;

	@Column(nullable = false, length = 50)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Tipoempresa() {
	}

	public Integer getIdtipoempresa() {
		return this.idtipoempresa;
	}

	public void setIdtipoempresa(Integer idtipoempresa) {
		this.idtipoempresa = idtipoempresa;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tipoempresa)) {
			return false;
		}
		// a room can be uniquely identified by it's number and the building it
		// belongs to
		final Tipoempresa other = (Tipoempresa) obj;
		return other.getIdtipoempresa() == idtipoempresa ? true : false;
	}

	@Override
	public int hashCode() {
		return idtipoempresa;
	}

}