package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipotransaccion database table.
 * 
 */
@Entity
@Table(name="tipotransaccion",schema="caja")
@NamedQuery(name="Tipotransaccion.findAll", query="SELECT t FROM Tipotransaccion t")
public class Tipotransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idtipotransaccion;

	@Column(nullable=false, length=10)
	private String abreviatura;

	@Column(nullable=false, length=150)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Transaccionboveda
	@OneToMany(mappedBy="tipotransaccion")
	private List<Transaccionboveda> transaccionbovedas;

	//bi-directional many-to-one association to Transaccioncaja
	@OneToMany(mappedBy="tipotransaccion")
	private List<Transaccioncaja> transaccioncajas;

	public Tipotransaccion() {
	}

	public Integer getIdtipotransaccion() {
		return this.idtipotransaccion;
	}

	public void setIdtipotransaccion(Integer idtipotransaccion) {
		this.idtipotransaccion = idtipotransaccion;
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

	public List<Transaccionboveda> getTransaccionbovedas() {
		return this.transaccionbovedas;
	}

	public void setTransaccionbovedas(List<Transaccionboveda> transaccionbovedas) {
		this.transaccionbovedas = transaccionbovedas;
	}

	public Transaccionboveda addTransaccionboveda(Transaccionboveda transaccionboveda) {
		getTransaccionbovedas().add(transaccionboveda);
		transaccionboveda.setTipotransaccion(this);

		return transaccionboveda;
	}

	public Transaccionboveda removeTransaccionboveda(Transaccionboveda transaccionboveda) {
		getTransaccionbovedas().remove(transaccionboveda);
		transaccionboveda.setTipotransaccion(null);

		return transaccionboveda;
	}

	public List<Transaccioncaja> getTransaccioncajas() {
		return this.transaccioncajas;
	}

	public void setTransaccioncajas(List<Transaccioncaja> transaccioncajas) {
		this.transaccioncajas = transaccioncajas;
	}

	public Transaccioncaja addTransaccioncaja(Transaccioncaja transaccioncaja) {
		getTransaccioncajas().add(transaccioncaja);
		transaccioncaja.setTipotransaccion(this);

		return transaccioncaja;
	}

	public Transaccioncaja removeTransaccioncaja(Transaccioncaja transaccioncaja) {
		getTransaccioncajas().remove(transaccioncaja);
		transaccioncaja.setTipotransaccion(null);

		return transaccioncaja;
	}

}