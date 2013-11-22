package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.DetalleaerturacierrebovedaListener;

/**
 * The persistent class for the detalleaperturacierreboveda database table.
 * 
 */
@Entity
@Table(name = "detalleaperturacierreboveda", schema = "caja")
@EntityListeners(DetalleaerturacierrebovedaListener.class)
@NamedQuery(name = "Detalleaperturacierreboveda.findAll", query = "SELECT d FROM Detalleaperturacierreboveda d")
// @NamedQueries({ @NamedQuery(name =
// "Detallehistorialboveda.LAST_ACTIVE_FOR_BOVEDA", query =
// "SELECT d FROM Detallehistorialboveda d INNER JOIN d.historialboveda h INNER JOIN h.boveda b WHERE b.idboveda = :idboveda AND h.estado = true")
// })
public class Detalleaperturacierreboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetalleaperturacierreboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private Double subtotal;

	@Column(nullable = false)
	private Integer iddenominacionmoneda;

	@Column(nullable = false)
	private Integer iddetallehistorialboveda;

	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false, insertable = false, updatable = false)
	private Denominacionmoneda denominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "iddetallehistorialboveda", nullable = false, insertable = false, updatable = false)
	private Detallehistorialboveda detallehistorialboveda;

	public Detalleaperturacierreboveda() {
		this.cantidad = 0;
	}

	public Integer getIddetalleaperturacierreboveda() {
		return this.iddetalleaperturacierreboveda;
	}

	public void setIddetalleaperturacierreboveda(
			Integer iddetalleaperturacierreboveda) {
		this.iddetalleaperturacierreboveda = iddetalleaperturacierreboveda;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIddenominacionmoneda() {
		return this.iddenominacionmoneda;
	}

	public void setIddenominacionmoneda(Integer iddenominacionmoneda) {
		this.iddenominacionmoneda = iddenominacionmoneda;
	}

	public Integer getIddetallehistorialboveda() {
		return this.iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
		if (denominacionmoneda != null) {
			this.iddenominacionmoneda = denominacionmoneda
					.getIddenominacionmoneda();
			this.cantidad = (cantidad == null) ? 0 : this.cantidad;
			//this.subtotal = cantidad * denominacionmoneda.getValor();
		} else {
			this.iddenominacionmoneda = null;
			this.subtotal = null;
		}
	}

	public Detallehistorialboveda getDetallehistorialboveda() {
		return detallehistorialboveda;
	}

	public void setDetallehistorialboveda(Detallehistorialboveda detallehistorialboveda) {
		this.detallehistorialboveda = detallehistorialboveda;
		this.iddetallehistorialboveda = (detallehistorialboveda == null) ? null : detallehistorialboveda.getIddetallehistorialboveda();
	}

}