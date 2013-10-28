package org.ventura.entity.tasas;

import java.io.Serializable;

import javax.persistence.*;


import java.util.Date;

/**
 * The persistent class for the tasainteres database table.
 * 
 */
@Entity
@Table(name = "tasainteres", schema = "tasas")
@NamedQuery(name = "Tasainteres.findAll", query = "SELECT t FROM Tasainteres t")
@NamedQueries({ @NamedQuery(name = Tasainteres.FindByAbreviatura, query = "Select ti From Tasainteres ti INNER JOIN ti.tiposervicio ts INNER JOIN ts.servicio s INNER JOIN ti.tipotasa tt WHERE s.estado = TRUE and ts.estado = TRUE and ti.estado = TRUE and tt.estado = TRUE and tt.abreviatura=:tipotasa and ts.denominacion=:tiposervicio") })
public class Tasainteres implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String FindByAbreviatura = "org.ventura.entity.tasas.Tasainteres.FindByAbreviatura";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtasainteres;

	@Column(nullable = false)
	private Boolean estado;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechainicio;

	@Column(nullable = false)
	private double montomaximo;

	@Column(nullable = false)
	private double montominimo;

	@Column(nullable = false)
	private double tasa;

	// bi-directional many-to-one association to Tiposervicio
	@ManyToOne
	@JoinColumn(name = "idtiposervicio", nullable = false)
	private Tiposervicio tiposervicio;

	// bi-directional many-to-one association to Tipotasa
	@ManyToOne
	@JoinColumn(name = "idtipotasa", nullable = false)
	private Tipotasa tipotasa;

	public Tasainteres() {
	}

	public Integer getIdtasainteres() {
		return this.idtasainteres;
	}

	public void setIdtasainteres(Integer idtasainteres) {
		this.idtasainteres = idtasainteres;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public double getMontomaximo() {
		return this.montomaximo;
	}

	public void setMontomaximo(double montomaximo) {
		this.montomaximo = montomaximo;
	}

	public double getMontominimo() {
		return this.montominimo;
	}

	public void setMontominimo(double montominimo) {
		this.montominimo = montominimo;
	}

	public double getTasa() {
		return this.tasa;
	}

	public void setTasa(double tasa) {
		this.tasa = tasa;
	}

	public Tiposervicio getTiposervicio() {
		return this.tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public Tipotasa getTipotasa() {
		return this.tipotasa;
	}

	public void setTipotasa(Tipotasa tipotasa) {
		this.tipotasa = tipotasa;
	}

}