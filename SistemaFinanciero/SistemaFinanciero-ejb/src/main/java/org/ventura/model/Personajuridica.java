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
@NamedQuery(name="Personajuridica.findAll", query="SELECT p FROM Personajuridica p")
public class Personajuridica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String ruc;

	private String actividadprincipal;

	private String celular;

	private String direccion;

	private String email;

	@Temporal(TemporalType.DATE)
	private Date fechaconstitucion;

	private Boolean finsocial;

	private String nombrecomercial;

	private String razonsocial;

	private String referencia;

	private String telefono;

	//bi-directional many-to-one association to Accionista
	@OneToMany(mappedBy="personajuridica")
	private List<Accionista> accionistas;

	//bi-directional many-to-one association to Personanatural
	@ManyToOne
	@JoinColumn(name="dnirepresentantelegal")
	private Personanatural personanatural;

	//bi-directional many-to-one association to Tipoempresa
	@ManyToOne
	@JoinColumn(name="idtipoempresa")
	private Tipoempresa tipoempresa;

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

}