package org.ventura.model;

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
	@Column(unique = true, nullable = false, length = 11)
	private String ruc;

	@Column(nullable = false, length = 50)
	private String razonsocial;

	@Column(length = 50)
	private String nombrecomercial;

	@Column(length = 50)
	private String actividadprincipal;

	private Boolean finsocial;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaconstitucion;

	@Column(length = 30)
	private String celular;

	@Column(length = 200)
	private String direccion;

	@Column(length = 100)
	private String referencia;

	@Column(length = 20)
	private String telefono;

	@Column(length = 50)
	private String email;

	@Column(length = 8, nullable = false)
	private String dnirepresentantelegal;

	@Column(nullable = false)
	private Integer idtipoempresa;

	// bi-directional many-to-one association to Tipoempresa
	@ManyToOne
	@JoinColumn(name = "idtipoempresa", nullable = false, insertable = false, updatable = false)
	private Tipoempresa tipoempresa;

	// bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name = "dnirepresentantelegal", nullable = false, insertable = false, updatable = false)
	private Personanatural personanatural;

	// bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy = "personajuridica")
	private List<Accionista> listAccionista;

	// bi-directional many-to-one association to Accionista

	@OneToMany(mappedBy = "personajuridica")
	private List<Personajuridicacliente> listPersonajuridicacliente;

	public Personajuridica() {
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public Accionista addAccionista(Accionista accionista) {
		getListAccionista().add(accionista);
		accionista.setPersonajuridica(this);

		return accionista;
	}

	public Accionista removeAccionista(Accionista accionista) {
		getListAccionista().remove(accionista);
		accionista.setPersonajuridica(null);

		return accionista;
	}

	public Tipoempresa getTipoempresa() {
		return this.tipoempresa;
	}

	public void setTipoempresa(Tipoempresa tipoempresa) {
		this.tipoempresa = tipoempresa;
	}

	public String getDnirepresentantelegal() {
		return dnirepresentantelegal;
	}

	public void setDnirepresentantelegal(String dnirepresentantelegal) {
		this.dnirepresentantelegal = dnirepresentantelegal;
	}

	public Integer getIdtipoempresa() {
		return idtipoempresa;
	}

	public void setIdtipoempresa(Integer idtipoempresa) {
		this.idtipoempresa = idtipoempresa;
	}

	public List<Accionista> getListAccionista() {
		return listAccionista;
	}

	public void setListAccionista(List<Accionista> listAccionista) {
		this.listAccionista = listAccionista;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public List<Personajuridicacliente> getListPersonajuridicacliente() {
		return listPersonajuridicacliente;
	}

	public void setListPersonajuridicacliente(
			List<Personajuridicacliente> listPersonajuridicacliente) {
		this.listPersonajuridicacliente = listPersonajuridicacliente;
	}

}