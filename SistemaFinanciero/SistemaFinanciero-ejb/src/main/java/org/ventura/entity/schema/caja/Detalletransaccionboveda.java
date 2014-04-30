package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

/**
 * The persistent class for the detalletransaccionboveda database table.
 * 
 */
@Entity
@Table(name = "detalletransaccionboveda", schema = "caja")
@NamedQuery(name = "Detalletransaccionboveda.findAll", query = "SELECT d FROM Detalletransaccionboveda d")
//@NamedQueries({ @NamedQuery(name = Detalletransaccionboveda.ALL_ACTIVE_BY_TRANSACCION, query = "SELECT d FROM Detalletransaccionboveda d WHERE d.transaccionboveda = :transaccionboveda") })
public class Detalletransaccionboveda implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE_BY_TRANSACCION = "org.ventura.entity.schema.caja.Detalletransaccionboveda.ALL_ACTIVE_BY_TRANSACCION";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetalletransaccionboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false)
	private Denominacionmoneda denominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "idtransaccionboveda")
	private Transaccionboveda transaccionboveda;
	
	public Transaccionboveda getTransaccionboveda() {
		return transaccionboveda;
	}

	public void setTransaccionboveda(Transaccionboveda transaccionboveda) {
		this.transaccionboveda = transaccionboveda;
	}

	@ManyToOne
	@JoinColumn(name = "idtransaccionbovedacaja")
	private Transaccionbovedacaja transaccionbovedacaja;
	
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

	public Denominacionmoneda getDenominacionmoneda() {
		return this.denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
	}

	public Moneda getSubtotal() {
		Moneda result = new Moneda();
		if (this.cantidad != null) {
			Moneda valor = this.denominacionmoneda.getValor();
			result = valor.multiply(cantidad);
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Detalletransaccionboveda)) {
			return false;
		}
		final Detalletransaccionboveda other = (Detalletransaccionboveda) obj;
		return other.getIddetalletransaccionboveda().equals(iddetalletransaccionboveda) ? true : false;
	}

	@Override
	public int hashCode() {
		return iddetalletransaccionboveda;
	}

	public Transaccionbovedacaja getTransaccionbovedacaja() {
		return transaccionbovedacaja;
	}

	public void setTransaccionbovedacaja(Transaccionbovedacaja transaccionbovedacaja) {
		this.transaccionbovedacaja = transaccionbovedacaja;
	}

}