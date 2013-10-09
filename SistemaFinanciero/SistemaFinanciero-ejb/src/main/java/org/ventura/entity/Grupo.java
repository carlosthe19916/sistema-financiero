package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the grupo database table.
 * 
 */
@Entity
@Table(name="grupo",schema="seguridad")
@NamedQuery(name="Grupo.findAll", query="SELECT g FROM Grupo g")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idgrupo;

	@Column(length=200)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="grupo_rol"
		, joinColumns={
			@JoinColumn(name="idgrupo", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="idrol", nullable=false)
			}
		)
	private List<Rol> rols;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="grupos")
	private List<Usuario> usuarios;

	public Grupo() {
	}

	public Integer getIdgrupo() {
		return this.idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Rol> getRols() {
		return this.rols;
	}

	public void setRols(List<Rol> rols) {
		this.rols = rols;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}