package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the chequeraestado database table.
 * 
 */
@Entity
@Table(name="chequeraestado",schema="cuentapersonal")
@NamedQuery(name="Chequeraestado.findAll", query="SELECT c FROM Chequeraestado c")
public class Chequeraestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer idchequeraestado;

	@Column(length=2)
	private String abreviatura;

	@Column(nullable=false)
	private Integer denominacion;

	@Column(nullable=false)
	private Boolean estado;

	public Chequeraestado() {
	}

	public Integer getIdchequeraestado() {
		return this.idchequeraestado;
	}

	public void setIdchequeraestado(Integer idchequeraestado) {
		this.idchequeraestado = idchequeraestado;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(Integer denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}