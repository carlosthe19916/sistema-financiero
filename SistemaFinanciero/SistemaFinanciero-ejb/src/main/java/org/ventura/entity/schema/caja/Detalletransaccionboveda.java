package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the detalletransaccionboveda database table.
 * 
 */
@Entity
@Table(name = "detalletransaccionboveda", schema = "caja")
@NamedQuery(name = "Detalletransaccionboveda.findAll", query = "SELECT d FROM Detalletransaccionboveda d")
public class Detalletransaccionboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer iddetalletransaccionboveda;

	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private Double total;

	@Column(nullable = false)
	private Integer idtransaccionboveda;

	@Column(nullable = false)
	private Integer iddenominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false, insertable = false, updatable = false)
	private Denominacionmoneda denominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "idtransaccionboveda", nullable = false, insertable = false, updatable = false)
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
		//refreshTotal();
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return this.denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
		if (denominacionmoneda != null) {
			this.iddenominacionmoneda = denominacionmoneda.getIddenominacionmoneda();
		} else {
			this.iddenominacionmoneda = null;
		}
		//refreshTotal();
	}

	public Transaccionboveda getTransaccionboveda() {
		return this.transaccionboveda;
	}

	public void setTransaccionboveda(Transaccionboveda transaccionboveda) {
		this.transaccionboveda = transaccionboveda;
	}

	public Integer getIdtransaccionboveda() {
		return idtransaccionboveda;
	}

	public void setIdtransaccionboveda(Integer idtransaccionboveda) {
		this.idtransaccionboveda = idtransaccionboveda;
	}

	public Integer getIddenominacionmoneda() {
		return iddenominacionmoneda;
	}

	public void setIddenominacionmoneda(Integer iddenominacionmoneda) {
		this.iddenominacionmoneda = iddenominacionmoneda;
	}
	
	/*public void refreshTotal(){
		if (denominacionmoneda != null) {
			this.cantidad = (cantidad == null) ? 0 : this.cantidad;
			this.total = denominacionmoneda.getValor().multiply(new Can);
		} else {
			this.total = null;
		}
	}*/

}