package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the grupo database table.
 * 
 */
@Entity
@Table(name = "grupo", schema = "seguridad")
@NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
@NamedQueries({
		@NamedQuery(name = Grupo.all_active, query = "SELECT g FROM Grupo g WHERE g.estado = TRUE"),
		@NamedQuery(name = Grupo.f_idusuario, query = "SELECT g FROM Grupo g INNER JOIN g.rols r INNER JOIN g.usuarios u INNER JOIN u.trabajador t INNER JOIN t.agencia a WHERE u.idusuario = :idusuario") })
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String all_active = "org.ventura.entity.schema.seguridad.Grupo.all_active";
	public final static String f_idusuario = "org.ventura.entity.schema.seguridad.Grupo.f_idusuario";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idgrupo;

	@Column(length = 200)
	private String descripcion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 100)
	private String nombre;

	// bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(name = "grupo_rol", schema = "seguridad", joinColumns = { @JoinColumn(name = "idgrupo", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idrol", nullable = false) })
	private List<Rol> rols;

	// bi-directional many-to-many association to Usuario
	@ManyToMany
	@JoinTable(name = "usuario_grupo", schema = "seguridad", joinColumns = { @JoinColumn(name = "idgrupo", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idusuario", nullable = false) })
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

	@Override
	public String toString(){
		return this.nombre;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Grupo)) {
			return false;
		}
		// a room can be uniquely identified by it's number and the building it
		// belongs to
		final Grupo other = (Grupo) obj;
		return other.getIdgrupo() == idgrupo ? true : false;
	}

	@Override
	public int hashCode() {
		return idgrupo;
	}

}