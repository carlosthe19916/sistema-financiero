package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadomovimiento database table.
 * 
 */
@Entity
@Table(name="estadomovimiento",schema="caja")
@NamedQuery(name="Estadomovimiento.findAll", query="SELECT e FROM Estadomovimiento e")
public class Estadomovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idestadomovimiento;

	@Column(nullable=false, length=10)
	private String abreviatura;

	@Column(nullable=false, length=150)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Boveda
	@OneToMany(mappedBy="estadomovimiento")
	private List<Boveda> bovedas;

	//bi-directional many-to-one association to Caja
	@OneToMany(mappedBy="estadomovimiento")
	private List<Caja> cajas;

	//bi-directional many-to-one association to Estadoapertura
	@ManyToOne
	@JoinColumn(name="idestadoapertura", nullable=false)
	private Estadoapertura estadoapertura;

	public Estadomovimiento() {
	}

	public Integer getIdestadomovimiento() {
		return this.idestadomovimiento;
	}

	public void setIdestadomovimiento(Integer idestadomovimiento) {
		this.idestadomovimiento = idestadomovimiento;
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

	public Boveda addBoveda(Boveda boveda) {
		getBovedas().add(boveda);
		boveda.setEstadomovimiento(this);

		return boveda;
	}

	public Boveda removeBoveda(Boveda boveda) {
		getBovedas().remove(boveda);
		boveda.setEstadomovimiento(null);

		return boveda;
	}

	public List<Caja> getCajas() {
		return this.cajas;
	}

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
	}

	public Caja addCaja(Caja caja) {
		getCajas().add(caja);
		caja.setEstadomovimiento(this);

		return caja;
	}

	public Caja removeCaja(Caja caja) {
		getCajas().remove(caja);
		caja.setEstadomovimiento(null);

		return caja;
	}

	public Estadoapertura getEstadoapertura() {
		return this.estadoapertura;
	}

	public void setEstadoapertura(Estadoapertura estadoapertura) {
		this.estadoapertura = estadoapertura;
	}

}