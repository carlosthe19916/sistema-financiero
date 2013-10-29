package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the estadocuenta database table.
 * 
 */
@Entity
@Table(name="estadocuenta",schema="cuentapersonal")
@NamedQuery(name="Estadocuenta.findAll", query="SELECT e FROM Estadocuenta e")
public class Estadocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idestadocuenta;

	@Column(length=3)
	private String abreviatura;

	@Column(nullable=false, length=20)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	public Estadocuenta() {
	}

	public Integer getIdestadocuenta() {
		return this.idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
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

}