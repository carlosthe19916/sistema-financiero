package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;

import java.util.Date;

/**
 * The persistent class for the ViewSocio database table.
 * 
 */
@Entity
@Table(name = "viewsociopn", schema = "socio")
@NamedQueries({ @NamedQuery(name = ViewSocioPN.SOCIOSPN, query = "select s from ViewSocioPN s where s.idsocio like :datoIngresado or s.dni like :datoIngresado") })
public class ViewSocioPN implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String SOCIOSPN = "org.ventura.model.ViewSocioPN.SOCIOSPN";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private String idsocio;

	@Column(length = 8)
	private String dni;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaasociado;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 40)
	private String apellidopaterno;

	@Column(nullable = false, length = 40)
	private String apellidomaterno;

	@Column(nullable = false, length = 50)
	private String nombres;

	@Column(nullable = true, length = 50)
	private String nombrecompleto;

	@ManyToOne
	@JoinColumn(name = "idcuentaaporte", nullable = false, insertable = false, updatable = false)
	private Cuentaaporte cuentaaporte;

	public ViewSocioPN() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(String idsocio) {
		this.idsocio = idsocio;
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

	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

}
