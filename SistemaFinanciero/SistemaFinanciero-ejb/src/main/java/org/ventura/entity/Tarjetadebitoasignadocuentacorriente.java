package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tarjetadebitoasignadocuentacorriente database table.
 * 
 */
@Entity
@Table(name="tarjetadebitoasignadocuentacorriente",schema="cuentapersonal")
@NamedQuery(name="Tarjetadebitoasignadocuentacorriente.findAll", query="SELECT t FROM Tarjetadebitoasignadocuentacorriente t")
public class Tarjetadebitoasignadocuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TarjetadebitoasignadocuentacorrientePK id;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false)
	private Integer fechaactiva;

	private Integer fechainactiva;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente", nullable=false, insertable=false, updatable=false)
	private Cuentacorriente cuentacorriente;

	//bi-directional many-to-one association to Tarjetadebito
	@ManyToOne
	@JoinColumn(name="numerotargeta", nullable=false, insertable=false, updatable=false)
	private Tarjetadebito tarjetadebito;

	public Tarjetadebitoasignadocuentacorriente() {
	}

	public TarjetadebitoasignadocuentacorrientePK getId() {
		return this.id;
	}

	public void setId(TarjetadebitoasignadocuentacorrientePK id) {
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