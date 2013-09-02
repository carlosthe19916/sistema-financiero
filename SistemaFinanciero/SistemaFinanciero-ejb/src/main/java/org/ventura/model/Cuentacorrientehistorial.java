package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cuentacorrientehistorial database table.
 * 
 */
@Entity
@NamedQuery(name="Cuentacorrientehistorial.findAll", query="SELECT c FROM Cuentacorrientehistorial c")
public class Cuentacorrientehistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idcuentacorrientehistorial;

	private Integer cantidadretirantes;

	private Boolean estado;

	private double tasainteres;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente")
	private Cuentacorriente cuentacorriente;

	public Cuentacorrientehistorial() {
	}

	public Integer getIdcuentacorrientehistorial() {
		return this.idcuentacorrientehistorial;
	}

	public void setIdcuentacorrientehistorial(Integer idcuentacorrientehistorial) {
		this.idcuentacorrientehistorial = idcuentacorrientehistorial;
	}

	public Integer getCantidadretirantes() {
		return this.cantidadretirantes;
	}

	public void setCantidadretirantes(Integer cantidadretirantes) {
		this.cantidadretirantes = cantidadretirantes;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public double getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(double tasainteres) {
		this.tasainteres = tasainteres;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

}