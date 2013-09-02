package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the targetadebitoasignadocuentacorriente database table.
 * 
 */
@Entity
@Table(name="targetadebitoasignadocuentacorriente",schema="cuentapersonal")
@NamedQuery(name="Targetadebitoasignadocuentacorriente.findAll", query="SELECT t FROM Targetadebitoasignadocuentacorriente t")
public class Targetadebitoasignadocuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TargetadebitoasignadocuentacorrientePK id;

	private Boolean estado;

	private Integer fechaactiva;

	private Integer fechainactiva;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente")
	private Cuentacorriente cuentacorriente;

	//bi-directional many-to-one association to Tarjetadebito
	@ManyToOne
	@JoinColumn(name="numerotargeta")
	private Tarjetadebito tarjetadebito;

	public Targetadebitoasignadocuentacorriente() {
	}

	public TargetadebitoasignadocuentacorrientePK getId() {
		return this.id;
	}

	public void setId(TargetadebitoasignadocuentacorrientePK id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getFechaactiva() {
		return this.fechaactiva;
	}

	public void setFechaactiva(Integer fechaactiva) {
		this.fechaactiva = fechaactiva;
	}

	public Integer getFechainactiva() {
		return this.fechainactiva;
	}

	public void setFechainactiva(Integer fechainactiva) {
		this.fechainactiva = fechainactiva;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public Tarjetadebito getTarjetadebito() {
		return this.tarjetadebito;
	}

	public void setTarjetadebito(Tarjetadebito tarjetadebito) {
		this.tarjetadebito = tarjetadebito;
	}

}