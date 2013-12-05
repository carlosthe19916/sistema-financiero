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
@Table(name = "transaccioncaja", schema = "caja")
@NamedQuery(name = "Transaccioncaja.findAll", query = "SELECT t FROM Transaccioncaja t")
public class Transaccioncaja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Long idtransaccioncaja;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Column(nullable = false)
	private Timestamp hora;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja", nullable = false)
	private Historialcaja historialcaja;

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

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

}