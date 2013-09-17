package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ubigeo database table.
 * 
 */
@Entity
@Table(name="ubigeo",schema="maestro")
@NamedQuery(name="Ubigeo.findAll", query="SELECT u FROM Ubigeo u")
public class Ubigeo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=6)
	private String idubigeo;

	@Column(length=70)
	private String departamento;

	@Column(nullable=false, length=70)
	private String distrito;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false, length=70)
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

}