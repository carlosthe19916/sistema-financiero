package org.ventura.entity.schema.maestro;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ubigeo database table.
 * 
 */
@Entity
@Table(name = "ubigeo", schema = "maestro")
@NamedQuery(name = "Ubigeo.findAll", query = "SELECT u FROM Ubigeo u")
@NamedQueries({
		@NamedQuery(name = Ubigeo.f_departamentos, query = "SELECT u FROM Ubigeo u ORDER BY u.departamento"),
		@NamedQuery(name = Ubigeo.f_provincias, query = "SELECT u FROM Ubigeo u WHERE SUBSTRING(u.idubigeo,1,2) = :iddepartamento ORDER BY u.provincia"),
		@NamedQuery(name = Ubigeo.f_distritos, query = "SELECT u FROM Ubigeo u WHERE SUBSTRING(u.idubigeo,1,4) = :idprovincia ORDER BY u.distrito") })
public class Ubigeo implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String f_departamentos = "org.ventura.entity.schema.maestro.Ubigeo.f_departamentos";
	public final static String f_provincias = "org.ventura.entity.schema.maestro.Ubigeo.f_provincias";
	public final static String f_distritos = "org.ventura.entity.schema.maestro.Ubigeo.f_distritos";

	@Id
	@Column(unique = true, nullable = false, length = 6)
	private String idubigeo;

	@Column(length = 70)
	private String departamento;

	@Column(nullable = false, length = 70)
	private String distrito;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 70)
	private String provincia;

	public Ubigeo() {
	}

	public String getIdubigeo() {
		return this.idubigeo;
	}

	public void setIdubigeo(String idubigeo) {
		this.idubigeo = idubigeo;
	}

	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Ubigeo)) {
			return false;
		}
		final Ubigeo other = (Ubigeo) obj;
		return other.getIdubigeo().equals(this.idubigeo) ? true : false;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(idubigeo);
	}
}