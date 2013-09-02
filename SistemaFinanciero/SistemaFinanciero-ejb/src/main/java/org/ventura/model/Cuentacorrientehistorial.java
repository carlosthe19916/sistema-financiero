package org.ventura.model;

// Generated 02-sep-2013 11:26:30 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cuentacorrientehistorial generated by hbm2java
 */
@Entity
@Table(name = "cuentacorrientehistorial", schema = "cuentapersonal")
public class Cuentacorrientehistorial implements java.io.Serializable {

	private int idcuentacorrientehistorial;
	private Cuentacorriente cuentacorriente;
	private double tasainteres;
	private int cantidadretirantes;
	private boolean estado;

	public Cuentacorrientehistorial() {
	}

	public Cuentacorrientehistorial(int idcuentacorrientehistorial,
			Cuentacorriente cuentacorriente, double tasainteres,
			int cantidadretirantes, boolean estado) {
		this.idcuentacorrientehistorial = idcuentacorrientehistorial;
		this.cuentacorriente = cuentacorriente;
		this.tasainteres = tasainteres;
		this.cantidadretirantes = cantidadretirantes;
		this.estado = estado;
	}

	@Id
	@Column(name = "idcuentacorrientehistorial", unique = true, nullable = false)
	public int getIdcuentacorrientehistorial() {
		return this.idcuentacorrientehistorial;
	}

	public void setIdcuentacorrientehistorial(int idcuentacorrientehistorial) {
		this.idcuentacorrientehistorial = idcuentacorrientehistorial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numerocuentacorriente", nullable = false)
	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	@Column(name = "tasainteres", nullable = false, precision = 17, scale = 17)
	public double getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(double tasainteres) {
		this.tasainteres = tasainteres;
	}

	@Column(name = "cantidadretirantes", nullable = false)
	public int getCantidadretirantes() {
		return this.cantidadretirantes;
	}

	public void setCantidadretirantes(int cantidadretirantes) {
		this.cantidadretirantes = cantidadretirantes;
	}

	@Column(name = "estado", nullable = false)
	public boolean isEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
