package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the cuentabancaria_tipotasa database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria_tipotasa", schema = "cuentapersonal")
@NamedQuery(name = "CuentabancariaTipotasa.findAll", query = "SELECT c FROM CuentabancariaTipotasa c")
public class CuentabancariaTipotasa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CuentabancariaTipotasaPK id;

	@Column
	private BigDecimal tasainteres;

	public CuentabancariaTipotasa() {
	}

	public CuentabancariaTipotasaPK getId() {
		return id;
	}

	public void setId(CuentabancariaTipotasaPK id) {
		this.id = id;
	}

	public BigDecimal getTasainteres() {
		return tasainteres;
	}

	public void setTasainteres(BigDecimal tasainteres) {
		this.tasainteres = tasainteres;
	}

}