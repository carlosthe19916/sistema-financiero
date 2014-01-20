package org.ventura.entity.schema.socio;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.SocioListener;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.sucursal.Agencia;

import java.util.Date;

/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name = "socio", schema = "socio")
@EntityListeners(SocioListener.class)
@NamedQuery(name = "Socio.findAll", query = "SELECT s FROM Socio s")
@NamedQueries({
		@NamedQuery(name = Socio.ALL, query = "SELECT s FROM Socio s"),
		@NamedQuery(name = Socio.fPN_tipodocumento_numerodocumento, query = "Select s From Socio s INNER JOIN s.personanatural p WHERE s.estado = TRUE AND p.tipodocumento = :tipodocumento AND p.numerodocumento = :numerodocumento"),
		@NamedQuery(name = Socio.fPJ_tipodocumento_numerodocumento, query = "Select s From Socio s INNER JOIN s.personajuridica p WHERE s.estado = TRUE AND p.tipodocumento = :tipodocumento AND p.numerodocumento = :numerodocumento")
/*
 * 
 * @NamedQuery(name = Socio.SOCIOSPN, query =
 * "select s from ViewSocioPN s where s.estado=true and (s.idsocio like :datoIngresado or s.dni like :datoIngresado or s.personanatural.apellidopaterno like :datoIngresado or s.personanatural.apellidomaterno like :datoIngresado or s.personanatural.nombres like :datoIngresado or s.nombrecompleto like :datoIngresado)"
 * ),
 * 
 * @NamedQuery(name = Socio.SOCIOSPJ, query =
 * "Select s From ViewSocioPJ s where s.estado=true and (s.idsocio like :datoIngresado or s.ruc like :datoIngresado or s.personajuridica.razonsocial like :datoIngresado or s.personajuridica.nombrecomercial like :datoIngresado)"
 * )
 */})
public class Socio implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Socio.ALL";
	public final static String SOCIOSPN = "org.ventura.model.Socio.SOCIOSPN";
	public final static String SOCIOSPJ = "org.ventura.model.Socio.SOCIOSPJ";

	public final static String fPN_tipodocumento_numerodocumento = "org.ventura.entity.schema.socio.socio.fPN_tipodocumento_numerodocumento";
	public final static String fPJ_tipodocumento_numerodocumento = "org.ventura.entity.schema.socio.socio.fPJ_tipodocumento_numerodocumento";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idsocio;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaasociado;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idpersonanatural")
	private Personanatural personanatural;

	@ManyToOne
	@JoinColumn(name = "idapoderado")
	private Personanatural apoderado;

	@ManyToOne
	@JoinColumn(name = "idagencia", nullable = false)
	private Agencia agencia;

	@ManyToOne
	@JoinColumn(name = "idpersonajuridica")
	private Personajuridica personajuridica;

	@ManyToOne
	@JoinColumn(name = "idcuentaaporte", nullable = false)
	private Cuentaaporte cuentaaporte;

	public Socio() {
	}

	public Date getFechaasociado() {
		return this.fechaasociado;
	}

	public void setFechaasociado(Date fechaasociado) {
		this.fechaasociado = fechaasociado;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
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

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Agencia)) {
			return false;
		}
		final Socio other = (Socio) obj;
		return other.getIdsocio() == this.idsocio ? true : false;
	}

	@Override
	public int hashCode() {
		return idsocio;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

	public Personanatural getApoderado() {
		return apoderado;
	}

	public void setApoderado(Personanatural apoderado) {
		this.apoderado = apoderado;
	}
}
