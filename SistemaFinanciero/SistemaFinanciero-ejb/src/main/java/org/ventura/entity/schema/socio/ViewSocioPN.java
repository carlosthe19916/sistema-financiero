package org.ventura.entity.schema.socio;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.sucursal.Agencia;

import java.util.Date;

/**
 * The persistent class for the ViewSocio database table.
 * 
 */
//@Entity
//@Table(name = "viewsociopn", schema = "socio")
public class ViewSocioPN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private String idsocio;

	@Column(length = 8, nullable = true)
	private String dni;

	@Column(nullable = true)
	private String nombrecompleto;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaasociado;

	@Column(nullable = false)
	private Boolean estado;
	
	@Column(nullable=false)
	private Integer idagencia;
	
	@Column(nullable=false, insertable=false, updatable = false)
	private Integer idcuentaaporte;
	
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;
	
	@ManyToOne
	@JoinColumn(name = "idagencia", insertable = false, updatable = false)
	private Agencia agencia;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idcuentaaporte", insertable = false, nullable = false)
	private Cuentaaporte cuentaaporte;
	
	
	public String getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(String idsocio) {
		this.idsocio = idsocio;
	}
	
	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	public Date getFechaasociado() {
		return this.fechaasociado;
	}

	public void setFechaasociado(Date fechaasociado) {
		this.fechaasociado = fechaasociado;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}
	
	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	public Integer getIdcuentaaporte() {
		return idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}
}
