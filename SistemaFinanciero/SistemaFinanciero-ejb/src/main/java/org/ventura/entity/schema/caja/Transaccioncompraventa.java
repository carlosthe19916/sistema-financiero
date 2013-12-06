package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import java.math.BigDecimal;

/**
 * The persistent class for the transaccioncompraventa database table.
 * 
 */
@Entity
@Table(name = "transaccioncompraventa", schema = "caja")
@NamedQuery(name = "Transaccioncompraventa.findAll", query = "SELECT t FROM Transaccioncompraventa t")
public class Transaccioncompraventa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncompraventa;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montoentregado")) })
	private Moneda montoentregado;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montorecibido")) })
	private Moneda montorecibido;

	@Column(length = 200)
	private String referencia;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal tasacambio;

	@ManyToOne
	@JoinColumn(name = "idtransaccioncaja", nullable = false)
	private Transaccioncaja transaccioncaja;
	
	@ManyToOne
	@JoinColumn(name = "idtipomonedarecibido", nullable = false)
	private Tipomoneda tipomonedaRecibido;
	
	@ManyToOne
	@JoinColumn(name = "idtipomonedaentregado", nullable = false)
	private Tipomoneda tipomonedaEntregado;
	
	@ManyToOne
	@JoinColumn(name = "idtipotransaccioncompraventa", nullable = false)
	private Tipotransaccioncompraventa tipotransaccioncompraventa;

	public Transaccioncompraventa() {
	}

	public Integer getIdtransaccioncompraventa() {
		return this.idtransaccioncompraventa;
	}

	public void setIdtransaccioncompraventa(Integer idtransaccioncompraventa) {
		this.idtransaccioncompraventa = idtransaccioncompraventa;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getTasacambio() {
		return this.tasacambio;
	}

	public void setTasacambio(BigDecimal tasacambio) {
		this.tasacambio = tasacambio;
	}

	public Tipotransaccioncompraventa getTipotransaccioncompraventa() {
		return this.tipotransaccioncompraventa;
	}

	public void setTipotransaccioncompraventa(
			Tipotransaccioncompraventa tipotransaccioncompraventa) {
		this.tipotransaccioncompraventa = tipotransaccioncompraventa;
	}

	public Moneda getMontoentregado() {
		return montoentregado;
	}

	public void setMontoentregado(Moneda montoentregado) {
		this.montoentregado = montoentregado;
	}

	public Moneda getMontorecibido() {
		return montorecibido;
	}

	public void setMontorecibido(Moneda montorecibido) {
		this.montorecibido = montorecibido;
	}

	public Tipomoneda getTipomonedaRecibido() {
		return tipomonedaRecibido;
	}

	public void setTipomonedaRecibido(Tipomoneda tipomonedaRecibido) {
		this.tipomonedaRecibido = tipomonedaRecibido;
	}

	public Tipomoneda getTipomonedaEntregado() {
		return tipomonedaEntregado;
	}

	public void setTipomonedaEntregado(Tipomoneda tipomonedaEntregado) {
		this.tipomonedaEntregado = tipomonedaEntregado;
	}

	public Transaccioncaja getTransaccioncaja() {
		return transaccioncaja;
	}

	public void setTransaccioncaja(Transaccioncaja transaccioncaja) {
		this.transaccioncaja = transaccioncaja;
	}

}