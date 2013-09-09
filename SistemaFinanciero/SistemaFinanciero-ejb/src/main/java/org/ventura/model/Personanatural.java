package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the personanatural database table.
 * 
 */
@Entity
@Table(name="personanatural",schema="persona")
@NamedQuery(name="Personanatural.findAll", query="SELECT p FROM Personanatural p")
public class Personanatural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=8)
	private String dni;

	@Column(nullable=false, length=40)
	private String apellidomaterno;

	@Column(nullable=false, length=40)
	private String apellidopaterno;

	@Column(length=30)
	private String celular;

	@Column(length=200)
	private String direccion;

	@Column(length=50)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechanacimiento;

	@Column(length=200)
	private String firma;

	private Integer idestadocivil;

	@Column(nullable=false)
	private Integer idsexo;

	@Column(nullable=false, length=50)
	private String nombres;

	@Column(length=50)
	private String ocupacion;

	@Column(length=100)
	private String referencia;

	@Column(length=30)
	private String telefono;

	//bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy="personanatural")
	private List<Accionista> accionistas;

	//bi-directional many-to-one association to Personajuridica
	@OneToMany(mappedBy="personanatural")
	private List<Personajuridica> personajuridicas;

	@ManyToOne
	@JoinColumn(name = "idsexo", insertable=false, updatable=false)
	private Sexo sexo;
	
	@ManyToOne
	@JoinColumn(name = "idestadocivil", insertable=false, updatable=false)
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

	public List<Accionista> getAccionistas() {
		return this.accionistas;
	}

	public void setAccionistas(List<Accionista> accionistas) {
		this.accionistas = accionistas;
	}

	public Accionista addAccionista(Accionista accionista) {
		getAccionistas().add(accionista);
		accionista.setPersonanatural(this);

		return accionista;
	}

	public Accionista removeAccionista(Accionista accionista) {
		getAccionistas().remove(accionista);
		accionista.setPersonanatural(null);

		return accionista;
	}

	public List<Personajuridica> getPersonajuridicas() {
		return this.personajuridicas;
	}

	public void setPersonajuridicas(List<Personajuridica> personajuridicas) {
		this.personajuridicas = personajuridicas;
	}

	public Personajuridica addPersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().add(personajuridica);
		personajuridica.setPersonanatural(this);

		return personajuridica;
	}

	public Personajuridica removePersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().remove(personajuridica);
		personajuridica.setPersonanatural(null);

		return personajuridica;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Estadocivil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(Estadocivil estadocivil) {
		this.estadocivil = estadocivil;
	}

}