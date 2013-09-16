package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario", schema = "seguridad")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idusuario;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idsucursal;

	@Column(nullable = false, length = 50)
	private String nombreusuario;

	@Column(nullable = false, length = 50)
	private String password;

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

	public String getNombreusuario() {
		return this.nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}