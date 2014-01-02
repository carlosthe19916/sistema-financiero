package org.ventura.entity.schema.persona;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the personajuridica database table.
 * 
 */
@Entity
@Table(name = "personajuridica", schema = "persona")
@NamedQuery(name = "Personajuridica.findAll", query = "SELECT p FROM Personajuridica p")
public class Personajuridica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idpersonajuridica;

	@Column(length = 50)
	private String actividadprincipal;

	@Column(length = 30)
	private String celular;

	@Column(length = 200)
	private String direccion;

	@Column(length = 50)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaconstitucion;

	private Boolean finsocial;

	@Column(length = 50)
	private String nombrecomercial;

	@Column(nullable = false, length = 50)
	private String razonsocial;

	@Column(length = 100)
	private String referencia;

	@Column(length = 20)
	private String telefono;

	// bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy = "personajuridica")
	private List<Accionista> accionistas;

	// bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name = "idrepresentantelegal", nullable = false)
	private Personanatural personanatural;

	// bi-directional many-to-one association to Tipoempresa
	@ManyToOne
	@JoinColumn(name = "idtipoempresa", nullable = false)
	private Tipoempresa tipoempresa;

	// bi-directional many-to-one association to Tipodocumento
	@ManyToOne
	@JoinColumn(name = "idtipodocumento", nullable = false)
	private Tipodocumento tipodocumento;

	public Personajuridica() {
	}

	public Integer getIdpersonajuridica() {
		return this.idpersonajuridica;
	}

	public void setIdpersonajuridica(Integer idpersonajuridica) {
		this.idpersonajuridica = idpersonajuridica;
	}

	public String getActividadprincipal() {
		return this.actividadprincipal;
	}

	public void setActividadprincipal(String actividadprincipal) {
		this.actividadprincipal = actividadprincipal;
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

	public Date getFechaconstitucion() {
		return this.fechaconstitucion;
	}

	public void setFechaconstitucion(Date fechaconstitucion) {
		this.fechaconstitucion = fechaconstitucion;
	}

	public Boolean getFinsocial() {
		return this.finsocial;
	}

	public void setFinsocial(Boolean finsocial) {
		this.finsocial = finsocial;
	}

	public String getNombrecomercial() {
		return this.nombrecomercial;
	}

	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}

	public String getRazonsocial() {
		return this.razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
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
		accionista.setPersonajuridica(this);

		return accionista;
	}

	public Accionista removeAccionista(Accionista accionista) {
		getAccionistas().remove(accionista);
		accionista.setPersonajuridica(null);

		return accionista;
	}

	public Personanatural getPersonanatural() {
		return this.personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Tipoempresa getTipoempresa() {
		return this.tipoempresa;
	}

	public void setTipoempresa(Tipoempresa tipoempresa) {
		this.tipoempresa = tipoempresa;
	}

	public Tipodocumento getTipodocumento() {
		return this.tipodocumento;
	}

	public void setTipodocumento(Tipodocumento tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

}