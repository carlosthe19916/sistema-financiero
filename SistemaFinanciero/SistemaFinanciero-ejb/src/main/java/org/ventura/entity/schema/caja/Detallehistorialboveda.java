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
	private Double total;

	public Detallehistorialboveda() {
	}

	public Integer getIddetallehistorialboveda() {
		return iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
}