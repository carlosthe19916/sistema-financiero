package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idusuario;

	@Column(nullable=false)
	private Boolean estado;

	private Integer idsucursal;

	@Column(nullable=false, length=500)
	private String password;

	@Column(nullable=false, length=100)
	private String username;

	//bi-directional many-to-many association to Grupo
	@ManyToMany
	@JoinTable(
		name="usuario_grupo"
		, joinColumns={
			@JoinColumn(name="idusuario", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="idgrupo", nullable=false)
			}
		)
	private List<Grupo> grupos;

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

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
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

}