package org.ventura.entity.tasas;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tiposervicio database table.
 * 
 */
@Entity
@Table(name="tiposervicio", schema = "tasas")
@NamedQuery(name="Tiposervicio.findAll", query="SELECT t FROM Tiposervicio t")
public class Tiposervicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idtiposervicio;

	@Column(nullable=false, length=100)
	private String denominacion;

	@Column(length=300)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Tasaintere
	@OneToMany(mappedBy="tiposervicio")
	private List<Tasainteres> tasainteres;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="idservicio", nullable=false)
	private Servicio servicio;
	
	//bi-directional many-to-one association to Tipocambio
	@OneToMany(mappedBy="tiposervicio")
	private List<Tipocambio> tipocambio;

	public Tiposervicio() {
	}

	public Integer getIdtiposervicio() {
		return this.idtiposervicio;
	}

	public void setIdtiposervicio(Integer idtiposervicio) {
		this.idtiposervicio = idtiposervicio;
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

	public List<Tasainteres> getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(List<Tasainteres> tasainteres) {
		this.tasainteres = tasainteres;
	}

	public Tasainteres addTasaintere(Tasainteres tasaintere) {
		getTasainteres().add(tasaintere);
		tasaintere.setTiposervicio(this);

		return tasaintere;
	}

	public Tasainteres removeTasaintere(Tasainteres tasaintere) {
		getTasainteres().remove(tasaintere);
		tasaintere.setTiposervicio(null);

		return tasaintere;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

}