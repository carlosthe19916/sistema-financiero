package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the detallehistorialcaja database table.
 * 
 */
@Entity
@Table(name = "Detallehistorialcaja", schema = "caja")
@NamedQuery(name="Detallehistorialcaja.findAll", query="SELECT d FROM Detallehistorialcaja d")
@NamedQueries({
	@NamedQuery(name = Detallehistorialcaja.detalleHistorialCajaByTipoMoneda, query = "select dh from Detallehistorialcaja dh where dh.denominacionmoneda.idtipomoneda = :idtipomoneda and dh.historialcaja.idhistorialcaja = :idhistorialcaja")})
public class Detallehistorialcaja implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String detalleHistorialCajaByTipoMoneda = "org.ventura.entity.schema.caja.Detallehistorialcaja.detalleHistorialCajaByTipoMoneda";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialcaja;

	private Integer cantidad;

	public Detallehistorialcaja() {
	}
	
	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false)
	private Denominacionmoneda denominacionmoneda;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja", nullable = false)
	private Historialcaja historialcaja;

	public Integer getIddetallehistorialcaja() {
		return this.iddetallehistorialcaja;
	}

	public void setIddetallehistorialcaja(Integer iddetallehistorialcaja) {
		this.iddetallehistorialcaja = iddetallehistorialcaja;
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

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
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