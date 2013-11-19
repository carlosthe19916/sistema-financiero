package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the detalleaperturacierreboveda database table.
 * 
 */
@Entity
@Table(name = "detalleaperturacierreboveda")
@NamedQuery(name = "Detalleaperturacierreboveda.findAll", query = "SELECT d FROM Detalleaperturacierreboveda d")
@NamedQueries({ @NamedQuery(name = "Detallehistorialboveda.LAST_ACTIVE_FOR_BOVEDA", query = "SELECT d FROM Detallehistorialboveda d INNER JOIN d.historialboveda h INNER JOIN h.boveda b WHERE b.idboveda = :idboveda AND h.estado = true") })
public class Detalleaperturacierreboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer iddetalleaperturacierreboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private Integer iddenominacionmoneda;

	@Column(nullable = false)
	private Integer iddetallehistorialboveda;

	@Column(nullable = false)
	private double total;

	public Detalleaperturacierreboveda() {
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

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}