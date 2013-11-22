package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the denominacionmoneda database table.
 * 
 */
@Entity
@Table(name = "denominacionmoneda", schema = "caja")
@NamedQuery(name = "Denominacionmoneda.findAll", query = "SELECT d FROM Denominacionmoneda d")
@NamedQueries({ @NamedQuery(name = Denominacionmoneda.findAllByTipoMoneda, query = "SELECT d FROM Denominacionmoneda d WHERE d.idtipomoneda = :idtipomoneda AND d.estado = true") })
public class Denominacionmoneda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAllByTipoMoneda = "org.ventura.entity.schema.caja.findAllByTipoMoneda";

	@Id
	@Column(unique = true, nullable = false)
	private Integer iddenominacionmoneda;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private BigDecimal valor;

	public Denominacionmoneda() {
	}

	public Integer getIddenominacionmoneda() {
		return this.iddenominacionmoneda;
	}

	public void setIddenominacionmoneda(Integer iddenominacionmoneda) {
		this.iddenominacionmoneda = iddenominacionmoneda;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Denominacionmoneda)) {
			return false;
		}
		final Denominacionmoneda other = (Denominacionmoneda) obj;
		return other.getIddenominacionmoneda() == iddenominacionmoneda ? true : false;
	}

	@Override
	public int hashCode() {
		return iddenominacionmoneda;
	}
}