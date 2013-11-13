package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadoapertura database table.
 * 
 */
@Entity
@Table(name="estadoapertura",schema="caja")
@NamedQuery(name="Estadoapertura.findAll", query="SELECT e FROM Estadoapertura e")
public class Estadoapertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idestadoapertura;

	@Column(length=10)
	private String abreviatura;

	@Column(nullable=false, length=150)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Estadomovimiento
	@OneToMany(mappedBy="estadoapertura")
	private List<Estadomovimiento> estadomovimientos;

	public Estadoapertura() {
	}

	public Integer getIdestadoapertura() {
		return this.idestadoapertura;
	}

	public void setIdestadoapertura(Integer idestadoapertura) {
		this.idestadoapertura = idestadoapertura;
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

	public List<Estadomovimiento> getEstadomovimientos() {
		return this.estadomovimientos;
	}

	public void setEstadomovimientos(List<Estadomovimiento> estadomovimientos) {
		this.estadomovimientos = estadomovimientos;
	}

	public Estadomovimiento addEstadomovimiento(Estadomovimiento estadomovimiento) {
		getEstadomovimientos().add(estadomovimiento);
		estadomovimiento.setEstadoapertura(this);

		return estadomovimiento;
	}

	public Estadomovimiento removeEstadomovimiento(Estadomovimiento estadomovimiento) {
		getEstadomovimientos().remove(estadomovimiento);
		estadomovimiento.setEstadoapertura(null);

		return estadomovimiento;
	}

}