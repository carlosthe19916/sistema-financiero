package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the caja database table.
 * 
 */
@Entity
@Table(name="caja" ,schema="caja")
@NamedQuery(name="Caja.findAll", query="SELECT c FROM Caja c")
public class Caja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idcaja;

	@Column(nullable=false, length=30)
	private String abreviatura;

	@Column(nullable=false, length=100)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-many association to Boveda
	@ManyToMany(mappedBy="cajas")
	private List<Boveda> bovedas;

	//bi-directional many-to-one association to Estadomovimiento
	@ManyToOne
	@JoinColumn(name="idestadomovimiento", nullable=false)
	private Estadomovimiento estadomovimiento;

	//bi-directional many-to-one association to Transaccioncaja
	@OneToMany(mappedBy="caja")
	private List<Transaccioncaja> transaccioncajas;

	public Caja() {
	}

	public Integer getIdcaja() {
		return this.idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
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

	public List<Boveda> getBovedas() {
		return this.bovedas;
	}

	public void setBovedas(List<Boveda> bovedas) {
		this.bovedas = bovedas;
	}

	public Estadomovimiento getEstadomovimiento() {
		return this.estadomovimiento;
	}

	public void setEstadomovimiento(Estadomovimiento estadomovimiento) {
		this.estadomovimiento = estadomovimiento;
	}

	public List<Transaccioncaja> getTransaccioncajas() {
		return this.transaccioncajas;
	}

	public void setTransaccioncajas(List<Transaccioncaja> transaccioncajas) {
		this.transaccioncajas = transaccioncajas;
	}

	public Transaccioncaja addTransaccioncaja(Transaccioncaja transaccioncaja) {
		getTransaccioncajas().add(transaccioncaja);
		transaccioncaja.setCaja(this);

		return transaccioncaja;
	}

	public Transaccioncaja removeTransaccioncaja(Transaccioncaja transaccioncaja) {
		getTransaccioncajas().remove(transaccioncaja);
		transaccioncaja.setCaja(null);

		return transaccioncaja;
	}

}