package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cuentaahorrohistorial database table.
 * 
 */
@Entity
@Table(name = "cuentaahorrohistorial", schema = "cuentapersonal")
@NamedQuery(name="Cuentaahorrohistorial.findAll", query="SELECT c FROM Cuentaahorrohistorial c")
public class Cuentaahorrohistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idcuentaahorrohistorial;

	private Integer cantidadretirantes;

	private Boolean estado;

	private double tasainteres;

	//bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name="numerocuentaahorro")
	private Cuentaahorro cuentaahorro;

	public Cuentaahorrohistorial() {
	}

	public Integer getIdcuentaahorrohistorial() {
		return this.idcuentaahorrohistorial;
	}

	public void setIdcuentaahorrohistorial(Integer idcuentaahorrohistorial) {
		this.idcuentaahorrohistorial = idcuentaahorrohistorial;
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

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

}