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
@NamedQuery(name="Personanatural.findAll", query="SELECT p FROM Personanatural p")
public class Personanatural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String dni;

	private String apellidomaterno;

	private String apellidopaterno;

	private String celular;

	private String direccion;

	private String email;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	private String firma;

	private Integer idestadocivil;

	private Integer idsexo;

	private String nombres;

	private String ocupacion;

	private String referencia;

	private String telefono;

	//bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy="personanatural")
	private List<Accionista> accionistas;

	//bi-directional many-to-one association to Personajuridica
	@OneToMany(mappedBy="personanatural")
	private List<Personajuridica> personajuridicas;

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

}