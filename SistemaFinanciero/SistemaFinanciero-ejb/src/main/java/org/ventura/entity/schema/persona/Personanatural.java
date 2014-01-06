package org.ventura.entity.schema.persona;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the personanatural database table.
 * 
 */
@Entity
@Table(name = "personanatural", schema = "persona")
@NamedQuery(name = "Personanatural.findAll", query = "SELECT p FROM Personanatural p")
@NamedQueries({ @NamedQuery(name = Personanatural.FindByTipodocumentoNumerodocumento, query = "SELECT p FROM Personanatural p WHERE p.tipodocumento = :tipodocumento AND p.numerodocumento = :numerodocumento") })
public class Personanatural implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String FindByTipodocumentoNumerodocumento = "org.ventura.entity.schema.persona.personanatural.FindByDni";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idpersonanatural;

	@Column(length = 15, nullable = false)
	private String numerodocumento;

	@Column(nullable = false, length = 50)
	private String apellidomaterno;

	@Column(nullable = false, length = 50)
	private String apellidopaterno;

	@Column(length = 30)
	private String celular;

	@Column(length = 200)
	private String direccion;

	@Column(length = 50)
	private String email;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	@Column(length = 200)
	private String firma;

	@Column(nullable = false, length = 60)
	private String nombres;

	@Column(length = 70)
	private String ocupacion;

	@Column(length = 100)
	private String referencia;

	@Column(length = 30)
	private String telefono;

	// bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy = "personanatural")
	private List<Accionista> accionistas;

	// bi-directional many-to-one association to Tipodocumento
	@ManyToOne
	@JoinColumn(name = "idtipodocumento", nullable = false)
	private Tipodocumento tipodocumento;

	@ManyToOne
	@JoinColumn(name = "idsexo")
	private Sexo sexo;

	@ManyToOne
	@JoinColumn(name = "idestadocivil")
	private Estadocivil estadocivil;

	public Personanatural() {
	}

	public Integer getIdpersonanatural() {
		return this.idpersonanatural;
	}

	public void setIdpersonanatural(Integer idpersonanatural) {
		this.idpersonanatural = idpersonanatural;
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

	public Tipodocumento getTipodocumento() {
		return this.tipodocumento;
	}

	public void setTipodocumento(Tipodocumento tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getNombreCompleto() {
		return this.nombres;
	}

	public String getNumerodocumento() {
		return numerodocumento;
	}

	public void setNumerodocumento(String numerodocumento) {
		this.numerodocumento = numerodocumento;
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