package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalletransaccionboveda database table.
 * 
 */
@Entity
@Table(name="detalletransaccionboveda",schema="caja")
@NamedQuery(name="Detalletransaccionboveda.findAll", query="SELECT d FROM Detalletransaccionboveda d")
public class Detalletransaccionboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer iddetalletransaccionboveda;

	@Column(nullable=false)
	private Integer cantidad;

	@Column(nullable=false)
	private double total;

	//bi-directional many-to-one association to Denominacionmoneda
	@ManyToOne
	@JoinColumn(name="iddenominacionmoneda", nullable=false)
	private Denominacionmoneda denominacionmoneda;

	//bi-directional many-to-one association to Transaccionboveda
	@ManyToOne
	@JoinColumn(name="idtransaccionboveda", nullable=false)
	private Transaccionboveda transaccionboveda;

	public Detalletransaccionboveda() {
	}

	public Integer getIddetalletransaccionboveda() {
		return this.iddetalletransaccionboveda;
	}

	public void setIddetalletransaccionboveda(Integer iddetalletransaccionboveda) {
		this.iddetalletransaccionboveda = iddetalletransaccionboveda;
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

	public Transaccionboveda getTransaccionboveda() {
		return this.transaccionboveda;
	}

	public void setTransaccionboveda(Transaccionboveda transaccionboveda) {
		this.transaccionboveda = transaccionboveda;
	}

}