package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the historialboveda database table.
 * 
 */
@Entity
@Table(name = "historialboveda", schema = "caja")
@NamedQuery(name = "Historialboveda.findAll", query = "SELECT h FROM Historialboveda h")
@NamedQueries({ @NamedQuery(name = Historialboveda.findHistorialActive, query = "SELECT h FROM Historialboveda h WHERE h.estado = true AND h.boveda.idboveda = :idboveda") })
public class Historialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findHistorialActive = "org.ventura.entity.schema.caja.findHistorialActive";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idhistorialboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column
	private Date fechacierre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date horaapertura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date horacierre;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idboveda", nullable = false)
	private Boveda boveda;

	public Historialboveda() {
	}

	public Integer getIdhistorialboveda() {
		return this.idhistorialboveda;
	}

	public void setIdhistorialboveda(Integer idhistorialboveda) {
		this.idhistorialboveda = idhistorialboveda;
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Date getFechacierre() {
		return this.fechacierre;
	}

	public void setFechacierre(Date fechacierre) {
		this.fechacierre = fechacierre;
	}

	public Date getHoraapertura() {
		return this.horaapertura;
	}

	public void setHoraapertura(Date horaapertura) {
		this.horaapertura = horaapertura;
	}

	public Date getHoracierre() {
		return this.horacierre;
	}

	public void setHoracierre(Date horacierre) {
		this.horacierre = horacierre;
	}

	public Boveda getBoveda() {
		return this.boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void refreshSaldos() {
		/*
		 * this.saldoinicial = new Double(0); this.saldofinal = new Double(0);
		 * Detallehistorialboveda detallehistorialbovedainicial =
		 * getDetallehistorialbovedainicial(); Detallehistorialboveda
		 * detallehistorialbovedafinal = getDetallehistorialbovedafinal(); if
		 * (detallehistorialbovedainicial != null) { this.saldoinicial =
		 * detallehistorialbovedainicial.getTotal(); } if
		 * (detallehistorialbovedafinal != null) { this.saldofinal =
		 * detallehistorialbovedafinal.getTotal(); }
		 */
	}

}