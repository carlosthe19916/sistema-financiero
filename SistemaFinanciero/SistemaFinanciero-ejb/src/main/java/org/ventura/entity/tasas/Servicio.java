package org.ventura.entity.tasas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the servicio database table.
 * 
 */
@Entity
@Table(name="servicio", schema = "tasas")
@NamedQuery(name="Servicio.findAll", query="SELECT s FROM Servicio s")
public class Servicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idservicio;

	@Column(nullable=false, length=100)
	private String denominacion;

	@Column(length=300)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Tiposervicio
	@OneToMany(mappedBy="servicio")
	private List<Tiposervicio> tiposervicios;

	public Servicio() {
	}

	public Integer getIdservicio() {
		return this.idservicio;
	}

	public void setIdservicio(Integer idservicio) {
		this.idservicio = idservicio;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Tiposervicio> getTiposervicios() {
		return this.tiposervicios;
	}

	public void setTiposervicios(List<Tiposervicio> tiposervicios) {
		this.tiposervicios = tiposervicios;
	}

	public Tiposervicio addTiposervicio(Tiposervicio tiposervicio) {
		getTiposervicios().add(tiposervicio);
		tiposervicio.setServicio(this);

		return tiposervicio;
	}

	public Tiposervicio removeTiposervicio(Tiposervicio tiposervicio) {
		getTiposervicios().remove(tiposervicio);
		tiposervicio.setServicio(null);

		return tiposervicio;
	}

}