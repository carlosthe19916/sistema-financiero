package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

/**
 * The persistent class for the boveda_caja database table.
 * 
 */
@Entity
@Table(name = "boveda_caja", schema = "caja")
@NamedQuery(name = "BovedaCaja.findAll", query = "SELECT b FROM BovedaCaja b")
public class BovedaCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BovedaCajaPK id;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldototal")) })
	private Moneda saldototal;

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

	public void setSaldototal(Moneda saldototal) {
		this.saldototal = saldototal;
	}

	public Moneda getSaldototal() {
		return saldototal;
	}
}
