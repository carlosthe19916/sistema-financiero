package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.SocioListener;

import java.util.Date;

/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name = "socio", schema = "socio")
@EntityListeners( { SocioListener.class })
@NamedQuery(name = "Socio.findAll", query = "SELECT s FROM Socio s")
@NamedQueries({
		@NamedQuery(name = Socio.ALL, query = "Select s From Sexo s"),
		@NamedQuery(name = Socio.FindByDni, query = "Select s From Socio s WHERE s.estado=true AND s.dni=:dni"),
		@NamedQuery(name = Socio.FindByRuc, query = "Select s From Socio s WHERE s.estado=true AND s.ruc=:ruc"),
		//@NamedQuery(name = Socio.SOCIOSPN1, query = "Select s From Socio s where s.dni like :datoIngresado or s.personanatural.apellidopaterno like :datoIngresado or s.personanatural.apellidomaterno like :datoIngresado or s.personanatural.nombres like :datoIngresado"),
		@NamedQuery(name = Socio.SOCIOSPJ, query = "Select s From Socio s where s.ruc like :datoIngresado or s.personajuridica.razonsocial like :datoIngresado or s.personajuridica.nombrecomercial like :datoIngresado") })
public class Socio implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Socio.ALL";
	public final static String FindByDni = "org.ventura.model.Socio.FindByDni";
	public final static String FindByRuc = "org.ventura.model.Socio.FindByRuc";
	//public final static String SOCIOSPN1 = "org.ventura.model.Socio.SOCIOSPN1";
	public final static String SOCIOSPJ = "org.ventura.model.Socio.SOCIOSPJ";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idsocio;

	@Column(length = 8)
	private String dni;

	@Column(length = 30)
	private String ruc;

	@Column
	private Integer idcuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaasociado;
	
	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;

	@ManyToOne
	@JoinColumn(name = "ruc", nullable = false, insertable = false, updatable = false)
	private Personajuridica personajuridica;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@JoinColumn(name = "idcuentaaporte", nullable = false, insertable = false, updatable = false)
	private Cuentaaporte cuentaaporte;
	
	public Socio() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFechaasociado() {
		return this.fechaasociado;
	}

	public void setFechaasociado(Date fechaasociado) {
		this.fechaasociado = fechaasociado;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
		if (personanatural != null) {
			this.dni = personanatural.getDni();
		} else {
			this.dni = null;
		}
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
		if (personajuridica != null) {
			this.ruc = personajuridica.getRuc();
		} else {
			this.ruc = null;
		}
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Integer getIdcuentaaporte() {
		return idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
		if (cuentaaporte != null) {
			this.idcuentaaporte = cuentaaporte.getIdcuentaaporte();
		} else {
			this.cuentaaporte = null;
		}
	}
}
