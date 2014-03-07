package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name = "rol", schema = "seguridad")
@NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")
@NamedQueries({
		@NamedQuery(name = Rol.f_idusuario, query = "SELECT r FROM Rol r INNER JOIN r.grupos g INNER JOIN g.usuarios u WHERE u.idusuario = :idusuario"),
		@NamedQuery(name = Rol.f_idgrupo, query = "SELECT r FROM Rol r INNER JOIN r.grupos g WHERE g.idgrupo = :idgrupo") })
public class Rol implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idusuario = "org.ventura.entity.schema.seguridad.Rol.f_idusuario";
	public final static String f_idgrupo = "org.ventura.entity.schema.seguridad.Rol.f_idgrupo";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idrol;

	@Column(length = 200)
	private String descripcion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 100)
	private String nombre;

	// bi-directional many-to-many association to Modulo
	@ManyToMany
	@JoinTable(name = "grupo_rol", schema = "seguridad", joinColumns = { @JoinColumn(name = "idrol", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idgrupo", nullable = false) })
	private List<Grupo> grupos;

	// bi-directional many-to-many association to Modulo
	@ManyToMany
	@JoinTable(name = "modulo_rol", schema = "seguridad", joinColumns = { @JoinColumn(name = "idrol", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idmodulo", nullable = false) })
	private List<Modulo> modulos;

	public Rol() {
	}

	public Integer getIdrol() {
		return this.idrol;
	}

	public void setIdrol(Integer idrol) {
		this.idrol = idrol;
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

	public List<Grupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Modulo> getModulos() {
		return this.modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Rol)) {
			return false;
		}
		// a room can be uniquely identified by it's number and the building it
		// belongs to
		final Rol other = (Rol) obj;
		return other.getIdrol().equals(idrol) ? true : false;
	}

	@Override
	public int hashCode() {
		return idrol;
	}

}