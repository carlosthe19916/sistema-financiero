package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class Detalleaperturacierreboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetalleaperturacierreboveda;

	@Column(nullable = false)
	private Integer cantidad;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "subtotal")) })
	private Moneda subtotal;

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

	

	public Denominacionmoneda getDenominacionmoneda() {
		return denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
		if (denominacionmoneda != null) {
			this.iddenominacionmoneda = denominacionmoneda.getIddenominacionmoneda();
			this.cantidad = (cantidad == null) ? 0 : this.cantidad;
					
			Moneda denominacionmonedaValor = denominacionmoneda.getValor();
			BigDecimal result=denominacionmonedaValor.multiply(cantidad);
			this.getSubtotal().setValue(result);
		} else {
			this.iddenominacionmoneda = null;
			this.setSubtotal(null);
		}
	}

	public Detallehistorialboveda getDetallehistorialboveda() {
		return detallehistorialboveda;
	}

	public void setDetallehistorialboveda(Detallehistorialboveda detallehistorialboveda) {
		this.detallehistorialboveda = detallehistorialboveda;
		this.iddetallehistorialboveda = (detallehistorialboveda == null) ? null : detallehistorialboveda.getIddetallehistorialboveda();
	}
	
	public void refreshSubtotal(){
		Integer cantidad = this.cantidad;
		Moneda denominacionMonedaValor = this.denominacionmoneda.getValor();
		BigDecimal result = denominacionMonedaValor.multiply(cantidad);
		this.getSubtotal().setValue(result);
	}

	public Moneda getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Moneda subtotal) {
		this.subtotal = subtotal;
	}

}