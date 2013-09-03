package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tarjetadebitoasignadocuentaahorro database table.
 * 
 */
@Entity
@Table(name="tarjetadebitoasignadocuentaahorro",schema="cuentapersonal")
@NamedQuery(name="Tarjetadebitoasignadocuentaahorro.findAll", query="SELECT t FROM Tarjetadebitoasignadocuentaahorro t")
public class Tarjetadebitoasignadocuentaahorro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TarjetadebitoasignadocuentaahorroPK id;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false)
	private Integer fechaactiva;

	@Temporal(TemporalType.DATE)
	private Date fechainanctiva;

	//bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name="numerocuentaahorro", nullable=false, insertable=false, updatable=false)
	private Cuentaahorro cuentaahorro;

	//bi-directional many-to-one association to Tarjetadebito
	@ManyToOne
	@JoinColumn(name="numerotargeta", nullable=false, insertable=false, updatable=false)
	private Tarjetadebito tarjetadebito;

	public Tarjetadebitoasignadocuentaahorro() {
	}

	public TarjetadebitoasignadocuentaahorroPK getId() {
		return this.id;
	}

	public void setId(TarjetadebitoasignadocuentaahorroPK id) {
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

	public Date getFechainanctiva() {
		return this.fechainanctiva;
	}

	public void setFechainanctiva(Date fechainanctiva) {
		this.fechainanctiva = fechainanctiva;
	}

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public Tarjetadebito getTarjetadebito() {
		return this.tarjetadebito;
	}

	public void setTarjetadebito(Tarjetadebito tarjetadebito) {
		this.tarjetadebito = tarjetadebito;
	}

}