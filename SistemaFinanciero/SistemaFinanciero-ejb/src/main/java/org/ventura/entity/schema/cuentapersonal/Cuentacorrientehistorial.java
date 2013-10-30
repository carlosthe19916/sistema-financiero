package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the cuentacorrientehistorial database table.
 * 
 */
@Entity
@Table(name = "cuentacorrientehistorial", schema = "cuentapersonal")
@NamedQuery(name = "Cuentacorrientehistorial.findAll", query = "SELECT c FROM Cuentacorrientehistorial c")
public class Cuentacorrientehistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idcuentacorrientehistorial;

	@Column(nullable = false)
	private Integer cantidadretirantes;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Double tasainteres;

	@Column(nullable = false)
	private Integer idcuentacorriente;

	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "idcuentacorriente", nullable = false, insertable = false, updatable = false)
	private Cuentacorriente cuentacorriente;

	public Cuentacorrientehistorial() {
	}

	public Integer getIdcuentacorrientehistorial() {
		return this.idcuentacorrientehistorial;
	}

	public void setIdcuentacorrientehistorial(Integer idcuentacorrientehistorial) {
		this.idcuentacorrientehistorial = idcuentacorrientehistorial;
	}

	public Integer getCantidadretirantes() {
		return this.cantidadretirantes;
	}

	public void setCantidadretirantes(Integer cantidadretirantes) {
		this.cantidadretirantes = cantidadretirantes;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Double getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(Double tasainteres) {
		this.tasainteres = tasainteres;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public Integer getIdcuentacorriente() {
		return idcuentacorriente;
	}

	public void setIdcuentacorriente(Integer idcuentacorriente) {
		this.idcuentacorriente = idcuentacorriente;
	}

}