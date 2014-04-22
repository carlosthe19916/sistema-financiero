package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;

import org.ventura.entity.schema.seguridad.Usuario;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncaja;

	@Column(nullable = false)
	private Integer numerooperacion;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date hora;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja", nullable = false)
	private Historialcaja historialcaja;

	@ManyToOne
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	public Transaccioncaja() {
	}

	public Integer getIdtransaccioncaja() {
		return this.idtransaccioncaja;
	}

	public void setIdtransaccioncaja(Integer idtransaccioncaja) {
		this.idtransaccioncaja = idtransaccioncaja;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

	public Integer getNumerooperacion() {
		return numerooperacion;
	}

	public void setNumerooperacion(Integer numerooperacion) {
		this.numerooperacion = numerooperacion;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Transaccioncaja)) {
			return false;
		}
		final Transaccioncaja other = (Transaccioncaja) obj;
		return other.getIdtransaccioncaja().equals(this.idtransaccioncaja) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtransaccioncaja;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}