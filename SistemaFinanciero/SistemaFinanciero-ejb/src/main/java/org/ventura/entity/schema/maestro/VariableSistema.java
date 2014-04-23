package org.ventura.entity.schema.maestro;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the variable_sistema database table.
 * 
 */
@Entity
@Table(name = "variable_sistema", schema = "maestro")
@NamedQuery(name = "VariableSistema.findAll", query = "SELECT v FROM VariableSistema v")
public class VariableSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idvariablesistema;

	@Column(nullable = false, length = 100)
	private String denominacion;

	@Column(length = 200)
	private String descripcion;

	private Integer idtipomoneda;

	@Column(nullable = false, precision = 18, scale = 3)
	private BigDecimal valor;

	public VariableSistema() {
	}

	public Integer getIdvariablesistema() {
		return this.idvariablesistema;
	}

	public void setIdvariablesistema(Integer idvariablesistema) {
		this.idvariablesistema = idvariablesistema;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

}