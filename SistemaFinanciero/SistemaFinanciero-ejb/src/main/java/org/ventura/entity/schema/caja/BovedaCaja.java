package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the boveda_caja database table.
 * 
 */
@Entity
@Table(name="boveda_caja")
@NamedQuery(name="BovedaCaja.findAll", query="SELECT b FROM BovedaCaja b")
public class BovedaCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BovedaCajaPK id;

	@Column(nullable = false)
	private BigDecimal saldototal;

	public BovedaCaja() {
	}
	
	@ManyToOne
	@JoinColumn(name = "idcaja", nullable = false, insertable = false, updatable = false)
	private Caja caja;
	
	@ManyToOne
	@JoinColumn(name = "idboveda", nullable = false, insertable = false, updatable = false)
	private Boveda boveda;
	
	public BovedaCajaPK getId() {
		return id;
	}

	public void setId(BovedaCajaPK id) {
		this.id = id;
	}

	public BigDecimal getSaldototal() {
		return this.saldototal;
	}

	public void setSaldototal(BigDecimal saldototal) {
		this.saldototal = saldototal;
	}
}