package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the detallehistorialboveda database table.
 * 
 */
@Entity
@Table(name = "detallehistorialboveda", schema = "caja")
@NamedQuery(name = "Detallehistorialboveda.findAll", query = "SELECT d FROM Detallehistorialboveda d")
@NamedQueries({ @NamedQuery(name = Detallehistorialboveda.LAST_ACTIVE_FOR_BOVEDA, query = "SELECT d FROM Detallehistorialboveda d INNER JOIN d.historialboveda h INNER JOIN h.boveda b WHERE b.idboveda = :idboveda AND h.fechacierre = (SELECT MAX(h.fechacierre) FROM Historialboveda hh INNER JOIN hh.boveda bb WHERE bb.idboveda = b.idboveda)") })


public class Detallehistorialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String LAST_ACTIVE_FOR_BOVEDA = "org.ventura.entity.schema.caja.Detallehistorialboveda";

	@Id
	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private double total;

	// bi-directional many-to-one association to Denominacionmoneda
	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false)
	private Denominacionmoneda denominacionmoneda;

	// bi-directional many-to-one association to Historialboveda
	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	public Detallehistorialboveda() {
	}

	public Integer getIddetallehistorialboveda() {
		return this.iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return this.denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
	}

	public Historialboveda getHistorialboveda() {
		return this.historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}

}