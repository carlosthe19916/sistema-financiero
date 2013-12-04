package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the transaccioncaja database table.
 * 
 */
@Entity
@Table(name="transaccioncaja",schema="caja")
@NamedQuery(name="Transaccioncaja.findAll", query="SELECT t FROM Transaccioncaja t")
public class Transaccioncaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Long idtransaccioncaja;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;

	@Column(nullable=false)
	private Timestamp hora;

	@Column(nullable=false)
	private Integer idtipomoneda;

	@Column(nullable=false)
	private double monto;

	@Column(length=250)
	private String referencia;

	//bi-directional many-to-one association to Historialcaja
	@ManyToOne
	@JoinColumn(name="idhistorialcaja", nullable=false)
	private Historialcaja historialcaja;

	//bi-directional many-to-one association to Tipotransaccion
	@ManyToOne
	@JoinColumn(name="idtipotransaccion", nullable=false)
	private Tipotransaccion tipotransaccion;

	public Transaccioncaja() {
	}

	public Long getIdtransaccioncaja() {
		return this.idtransaccioncaja;
	}

	public void setIdtransaccioncaja(Long idtransaccioncaja) {
		this.idtransaccioncaja = idtransaccioncaja;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getHora() {
		return this.hora;
	}

	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getMonto() {
		return this.monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Tipotransaccion getTipotransaccion() {
		return this.tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

}