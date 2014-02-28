package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.caja.Boveda;
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
		@NamedQuery(name = Usuario.FIND_BYID, query = "Select u From Usuario u LEFT JOIN u.trabajador t LEFT JOIN t.personanatural p LEFT JOIN u.grupos g WHERE u.idusuario = :idusuario"),
		@NamedQuery(name = Usuario.FIND_USER, query = "Select u From Usuario u LEFT OUTER JOIN u.trabajador t LEFT OUTER JOIN t.personanatural p LEFT OUTER JOIN t.agencia s WHERE u.estado = true AND u.username = :username"),
		@NamedQuery(name = Usuario.find_byusername_active, query = "SELECT u FROM Usuario u WHERE u.username = :username AND u.estado = TRUE"),
		@NamedQuery(name = Usuario.fadministrador_idagencia_usuario_password, query = "Select u From Usuario u INNER JOIN u.trabajador t INNER JOIN t.agencia a WHERE a.idagencia = :idagencia AND u.username = :usuario AND u.password = :password AND u.estado = TRUE"),
		@NamedQuery(name = Usuario.f_idagencia, query = "SELECT u FROM Usuario u INNER JOIN u.trabajador t INNER JOIN t.agencia a LEFT JOIN u.grupos g LEFT JOIN g.rols r WHERE a.idagencia= :idagencia AND u.estado = TRUE ORDER BY u.idusuario"),
		@NamedQuery(name = Usuario.f_idrol_idagencia, query = "SELECT u FROM Usuario u INNER JOIN u.trabajador t INNER JOIN t.agencia a INNER JOIN u.grupos g INNER JOIN g.rols r WHERE a.idagencia= :idagencia AND r.idrol = :idrol AND u.estado = TRUE ORDER BY u.idusuario"),
		@NamedQuery(name = Usuario.f_idgrupo_idagencia, query = "SELECT u FROM Usuario u INNER JOIN u.trabajador t INNER JOIN t.agencia a INNER JOIN u.grupos g INNER JOIN g.rols r WHERE a.idagencia= :idagencia AND g.idgrupo = :idgrupo AND u.estado = TRUE ORDER BY u.idusuario") })
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Usuario.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Usuario.ALL_ACTIVE";
	public final static String FIND_BYID = "org.ventura.model.Usuario.FIND_BYID";
	public final static String FIND_USER = "org.ventura.model.Usuario.FIND_USER";
	public final static String find_byusername_active = "org.ventura.model.Usuario.find_byusername";

	public final static String fadministrador_idagencia_usuario_password = "org.ventura.entity.schema.seguridad.Usuario.fadministrador_idagencia_usuario_password";
	public final static String f_idagencia = "org.ventura.entity.schema.seguridad.Usuario.f_idagencia";
	public final static String f_idrol_idagencia = "org.ventura.entity.schema.seguridad.Usuario.f_idrol_iagencia";
	public final static String f_idgrupo_idagencia = "org.ventura.entity.schema.seguridad.Usuario.f_idgrupo_idagencia";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idusuario;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 500)
	private String password;

	@Column(nullable = false, length = 100)
	private String username;

	@ManyToOne
	@JoinColumn(name = "idtrabajador", nullable = true)
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

	@Override
	public String toString() {
		return this.username;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Usuario)) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		return other.getIdusuario().equals(this.idusuario) ? true : false;
	}

	@Override
	public int hashCode() {
		return idusuario;
	}
}