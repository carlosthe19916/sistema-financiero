package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the personanatural database table.
 * 
 */
@Entity
@Table(name = "personanatural", schema = "persona")
@NamedQuery(name = "Personanatural.findAll", query = "SELECT p FROM Personanatural p")
public class Personanatural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 8)
	private String dni;

	@Column(nullable = false, length = 40)
	private String apellidomaterno;

	@Column(nullable = false, length = 40)
	private String apellidopaterno;

	@Column(nullable = false, length = 50)
	private String nombres;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechanacimiento;

	@Column(length = 30)
	private String telefono;

	@Column(length = 30)
	private String celular;

	@Column(length = 200)
	private String direccion;

	@Column(length = 100)
	private String referencia;

	@Column(length = 50)
	private String ocupacion;

	@Column(length = 50)
	private String email;

	@Column(length = 200)
	private String firma;

	@Column
	private Integer idestadocivil;

	@Column(nullable = false)
	private Integer idsexo;

	@ManyToOne
	@JoinColumn(name = "idsexo", insertable = false, updatable = false)
	private Sexo sexo;

	@ManyToOne
	@JoinColumn(name = "idestadocivil", insertable = false, updatable = false)
	private Estadocivil estadocivil;

	public Personanatural() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidomaterno() {
		return this.apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getApellidopaterno() {
		return this.apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Integer getIdestadocivil() {
		return this.idestadocivil;
	}

	public void setIdestadocivil(Integer idestadocivil) {
		this.idestadocivil = idestadocivil;
	}

	public Integer getIdsexo() {
		return this.idsexo;
	}

	public void setIdsexo(Integer idsexo) {
		this.idsexo = idsexo;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getOcupacion() {
		return this.ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
		if (sexo != null) {
			this.idsexo = sexo.getIdsexo();
		} else {
			this.idsexo = null;
		}
	}

	public Estadocivil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(Estadocivil estadocivil) {
		this.estadocivil = estadocivil;
		if (estadocivil != null) {
			this.idestadocivil = estadocivil.getIdestadocivil();
		} else {
			this.idestadocivil = null;
		}
	}

	public String getNombreCompleto() {
		String apellidoPaterno = getApellidopaterno();
		String apellidoMaterno = getApellidomaterno();
		String nombres = getNombres();

		if (this.getApellidopaterno() == null) {
			apellidoPaterno = "";
		}
		if (this.getApellidomaterno() == null) {
			apellidoMaterno = "";
		}
		if (this.getNombres() == null) {
			nombres = "";
		}

		return apellidoPaterno + " " + apellidoMaterno + " " + nombres;
	}

	public boolean isValid() {
		boolean result = true;

		if (dni == null || dni.isEmpty() || dni.trim().isEmpty()
				|| dni.length() != 8) {
			result = false;
		}
		if (apellidopaterno == null || apellidopaterno.isEmpty()
				|| apellidopaterno.trim().isEmpty()) {
			result = false;
		}
		if (apellidomaterno == null || apellidomaterno.isEmpty()
				|| apellidomaterno.trim().isEmpty()) {
			result = false;
		}
		if (nombres == null || nombres.isEmpty() || nombres.trim().isEmpty()) {
			result = false;
		}
		if (fechanacimiento == null) {
			result = false;
		}
		if (idsexo == null) {
			result = false;
		}
		return result;
	}
}