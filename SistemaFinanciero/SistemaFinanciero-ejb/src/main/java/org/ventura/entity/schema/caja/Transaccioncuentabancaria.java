package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import java.math.BigDecimal;

/**
 * The persistent class for the transaccioncuentabancaria database table.
 * 
 */
@Entity
@Table(name = "transaccioncuentabancaria")
@NamedQuery(name = "Transaccioncuentabancaria.findAll", query = "SELECT t FROM Transaccioncuentabancaria t")
public class Transaccioncuentabancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncuentabancaria;

	private Integer idcheque;

	@Column(nullable = false)
	private Integer idcuentabancaria;

	@Column(nullable = false)
	private Integer idtransaccioncaja;

	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal monto;

	@Column(length = 250)
	private String referencia;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

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

	public Integer getIdcuentabancaria() {
		return this.idcuentabancaria;
	}

	public void setIdcuentabancaria(Integer idcuentabancaria) {
		this.idcuentabancaria = idcuentabancaria;
	}

	public Integer getIdtransaccioncaja() {
		return this.idtransaccioncaja;
	}

	public void setIdtransaccioncaja(Integer idtransaccioncaja) {
		this.idtransaccioncaja = idtransaccioncaja;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
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

}