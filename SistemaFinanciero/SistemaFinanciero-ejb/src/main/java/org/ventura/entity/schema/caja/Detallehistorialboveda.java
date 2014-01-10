package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

/**
 * The persistent class for the detalleaperturacierreboveda database table.
 * 
 */
@Entity
@Table(name = "detallehistorialboveda", schema = "caja")
@NamedQuery(name = "Detallehistorialboveda.findAll", query = "SELECT d FROM Detallehistorialboveda d")
public class Detallehistorialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false)
	private Denominacionmoneda denominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	public Detallehistorialboveda() {
		this.cantidad = 0;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
	}

	public Integer getIddetallehistorialboveda() {
		return iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Historialboveda getHistorialboveda() {
		return historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}
	
	public Moneda getSubtotal() {
		Moneda result = new Moneda();
		if (this.cantidad != null) {
			Moneda valor = this.denominacionmoneda.getValor();
			result = valor.multiply(cantidad);
		}
		return result;
	}

}