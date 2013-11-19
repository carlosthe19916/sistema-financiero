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

public class Detallehistorialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String LAST_ACTIVE_FOR_BOVEDA = "org.ventura.entity.schema.caja.Detallehistorialboveda";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialboveda;

	@Column(nullable = false)
	private Double saldototal;

	@Column(nullable = false)
	private Integer idhistorialboveda;
	
	// bi-directional many-to-one association to Historialboveda
	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false, insertable = false, updatable = false)
	private Historialboveda historialboveda;

	public Detallehistorialboveda() {
	}

	public Integer getIddetallehistorialboveda() {
		return iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Double getSaldototal() {
		return saldototal;
	}

	public void setSaldototal(Double saldototal) {
		this.saldototal = saldototal;
	}

	public Integer getIdhistorialboveda() {
		return idhistorialboveda;
	}

	public void setIdhistorialboveda(Integer idhistorialboveda) {
		this.idhistorialboveda = idhistorialboveda;
	}

	public Historialboveda getHistorialboveda() {
		return historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}

	
}