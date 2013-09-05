package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the estadotarjeta database table.
 * 
 */
@Entity
@Table(name="estadotarjeta",schema="cuentapersonal")
@NamedQuery(name="Estadotarjeta.findAll", query="SELECT e FROM Estadotarjeta e")
public class Estadotarjeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer idestadotargeta;

	@Column(length=50)
	private String abreviatura;

	@Column(nullable=false, length=50)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	public Estadotarjeta() {
	}

	public Integer getIdestadotargeta() {
		return this.idestadotargeta;
	}

	public void setIdestadotargeta(Integer idestadotargeta) {
		this.idestadotargeta = idestadotargeta;
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