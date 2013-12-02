package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the historialboveda database table.
 * 
 */
@Entity
@Table(name = "historialboveda", schema = "caja")
@NamedQuery(name = "Historialboveda.findAll", query = "SELECT h FROM Historialboveda h")
@NamedQueries({ @NamedQuery(name = Historialboveda.findHistorialActive, query = "SELECT h FROM Historialboveda h WHERE h.boveda.idboveda = :idboveda and h.idcreacion = (SELECT MAX(hh.idcreacion) FROM Historialboveda hh WHERE hh.boveda.idboveda = h.boveda.idboveda)") })
public class Historialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findHistorialActive = "org.ventura.entity.schema.caja.findHistorialActive";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idhistorialboveda;

	@Column(unique = true, nullable = false)
	private Integer idcreacion;

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

	@ManyToOne
	@JoinColumn(name = "idboveda", nullable = false)
	private Boveda boveda;

	@ManyToOne
	@JoinColumn(name = "idestadomovimiento", nullable = false)
	private Estadomovimiento estadomovimiento;

	@OneToMany(mappedBy = "historialboveda")
	private List<Detallehistorialboveda> detallehistorialbovedas;

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

	public List<Detallehistorialboveda> getDetallehistorialbovedas() {
		return detallehistorialbovedas;
	}

	public void setDetallehistorialbovedas(
			List<Detallehistorialboveda> detallehistorialbovedas) {
		this.detallehistorialbovedas = detallehistorialbovedas;
	}

	public Estadomovimiento getEstadomovimiento() {
		return estadomovimiento;
	}

	public void setEstadomovimiento(Estadomovimiento estadomovimiento) {
		this.estadomovimiento = estadomovimiento;
	}

	public Integer getIdcreacion() {
		return idcreacion;
	}

	public void setIdcreacion(Integer idcreacion) {
		this.idcreacion = idcreacion;
	}

}