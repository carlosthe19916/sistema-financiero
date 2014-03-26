package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the detalletransaccioncaja database table.
 * 
 */
@Entity
@Table(name = "detalletransaccioncaja", schema = "caja")
@NamedQuery(name = "Detalletransaccioncaja.findAll", query = "SELECT d FROM Detalletransaccioncaja d")
@NamedQueries({ @NamedQuery(name = Detalletransaccioncaja.f_idtransaccioncaja, query = "SELECT d FROM Detalletransaccioncaja d WHERE d.transaccioncaja.idtransaccioncaja = :idtransaccioncaja") })
public class Detalletransaccioncaja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idtransaccioncaja = "org.ventura.entity.schema.caja.Detalletransaccioncaja.f_idtransaccioncaja";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetalletransaccioncaja;

	@Column(nullable = false)
	private Integer cantidad;

	@ManyToOne
	@JoinColumn(name = "idtransaccioncaja", nullable = false)
	private Transaccioncaja transaccioncaja;

	@ManyToOne
	@JoinColumn(name = "iddenominacionmoneda", nullable = false)
	private Denominacionmoneda denominacionmoneda;

	public Detalletransaccioncaja() {
	}

	public Integer getIddetalletransaccioncaja() {
		return this.iddetalletransaccioncaja;
	}

	public void setIddetalletransaccioncaja(Integer iddetalletransaccioncaja) {
		this.iddetalletransaccioncaja = iddetalletransaccioncaja;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Transaccioncaja getTransaccioncaja() {
		return transaccioncaja;
	}

	public void setTransaccioncaja(Transaccioncaja transaccioncaja) {
		this.transaccioncaja = transaccioncaja;
	}

	public Denominacionmoneda getDenominacionmoneda() {
		return denominacionmoneda;
	}

	public void setDenominacionmoneda(Denominacionmoneda denominacionmoneda) {
		this.denominacionmoneda = denominacionmoneda;
	}
	
	public BigDecimal getSubtotal(){
		if(cantidad != null && denominacionmoneda != null){
			if(denominacionmoneda.getValor() != null){
				BigDecimal result = denominacionmoneda.getValor().multiply(cantidad).getValue();
				return result;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}