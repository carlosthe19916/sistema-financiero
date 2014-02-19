package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.rrhh.Trabajador;

import java.util.List;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario", schema = "seguridad")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
@NamedQueries({
		@NamedQuery(name = Usuario.ALL, query = "SELECT u FROM Usuario u"),
		@NamedQuery(name = Usuario.ALL_ACTIVE, query = "SELECT u FROM Usuario u WHERE u.estado=true"),
		@NamedQuery(name = Usuario.FIND_USER, query = "Select u From Usuario u LEFT OUTER JOIN u.trabajador t LEFT OUTER JOIN t.personanatural p LEFT OUTER JOIN t.agencia s WHERE u.estado = true AND u.username = :username"),
		@NamedQuery(name = Usuario.fadministrador_idagencia_usuario_password, query = "Select u From Usuario u INNER JOIN u.trabajador t INNER JOIN t.agencia a WHERE a.idagencia = :idagencia AND u.username = :usuario AND u.password = :password AND u.estado = TRUE") })
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Usuario.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Usuario.ALL_ACTIVE";
	public final static String FIND_USER = "org.ventura.model.Usuario.FIND_USER";

	public final static String fadministrador_idagencia_usuario_password = "org.ventura.entity.schema.seguridad.Usuario.fadministrador_idagencia_usuario_password";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idusuario;

	@Column
	private Integer idtrabajador;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 500)
	private String password;

	@Column(nullable = false, length = 100)
	private String username;

	@ManyToOne
	@JoinColumn(name = "idtrabajador", insertable = false, updatable = false)
	private Trabajador trabajador;

	// bi-directional many-to-many association to Grupo
	@ManyToMany
	@JoinTable(name = "usuario_grupo", schema = "seguridad", joinColumns = { @JoinColumn(name = "idusuario", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idgrupo", nullable = false) })
	private List<Grupo> grupos;

	@ManyToMany
	@JoinTable(name = "caja_usuario", schema = "caja", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = { @JoinColumn(name = "idcaja") })
	private List<Caja> cajas;

	public Usuario() {
	}

	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Grupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public List<Caja> getCajas() {
		return cajas;
	}

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
	}

	public Integer getIdtrabajador() {
		return idtrabajador;
	}

	public void setIdtrabajador(Integer idtrabajador) {
		this.idtrabajador = idtrabajador;
	}

}