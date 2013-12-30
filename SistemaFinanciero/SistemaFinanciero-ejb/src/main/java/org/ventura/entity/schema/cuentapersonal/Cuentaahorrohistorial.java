package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.CuentaahorrohistorialListener;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.TasaInteresTipoCambio;

/**
 * The persistent class for the cuentaahorrohistorial database table.
 * 
 */
@Entity
@Table(name = "cuentaahorrohistorial", schema = "cuentapersonal")
@EntityListeners( { CuentaahorrohistorialListener.class })
@NamedQuery(name = "Cuentaahorrohistorial.findAll", query = "SELECT c FROM Cuentaahorrohistorial c")
public class Cuentaahorrohistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentaahorrohistorial;

	@Column(nullable = false)
	private Integer cantidadretirantes;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idcuentaahorro;

	@Column
	private Double tasainteres;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "idcuentaahorro", nullable = false, insertable = false, updatable = false)
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

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
		if(cuentaahorro != null){
			this.idcuentaahorro = cuentaahorro.getIdcuentaahorro();
		} else {
			this.idcuentaahorro = null;
		}
	}

	public Integer getIdcuentaahorro() {
		return idcuentaahorro;
	}

	public void setIdcuentaahorro(Integer idcuentaahorro) {
		this.idcuentaahorro = idcuentaahorro;
	}

	public void setTasainteres(Double tasainteres) {
		this.tasainteres = tasainteres;
	}

	public Double getTasainteres() {
		return tasainteres;
	}

}