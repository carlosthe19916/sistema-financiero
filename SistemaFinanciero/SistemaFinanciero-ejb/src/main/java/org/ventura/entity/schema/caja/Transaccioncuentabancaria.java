package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;

/**
 * The persistent class for the transaccioncuentabancaria database table.
 * 
 */
@Entity
@Table(name = "transaccioncuentabancaria", schema = "caja")
@NamedQuery(name = "Transaccioncuentabancaria.findAll", query = "SELECT t FROM Transaccioncuentabancaria t")
public class Transaccioncuentabancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncuentabancaria;

	private Integer idcheque;

	@Column(nullable = false)
	private Integer idtransaccioncaja;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto")) })
	private Moneda monto;

	@Column(length = 250)
	private String referencia;

	@ManyToOne
	@JoinColumn(name = "idcuentabancaria", nullable = false)
	private Cuentabancaria cuentabancaria;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	public Transaccioncuentabancaria() {
	}

	public Integer getIdtransaccioncuentabancaria() {
		return this.idtransaccioncuentabancaria;
	}

	public void setIdtransaccioncuentabancaria(
			Integer idtransaccioncuentabancaria) {
		this.idtransaccioncuentabancaria = idtransaccioncuentabancaria;
	}

	public Integer getIdcheque() {
		return this.idcheque;
	}

	public void setIdcheque(Integer idcheque) {
		this.idcheque = idcheque;
	}

	public Integer getIdtransaccioncaja() {
		return this.idtransaccioncaja;
	}

	public void setIdtransaccioncaja(Integer idtransaccioncaja) {
		this.idtransaccioncaja = idtransaccioncaja;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
		this.monto = monto;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

}