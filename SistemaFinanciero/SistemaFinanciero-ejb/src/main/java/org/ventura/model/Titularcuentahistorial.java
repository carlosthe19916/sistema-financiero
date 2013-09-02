package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the titularcuentahistorial database table.
 * 
 */
@Entity
@NamedQuery(name="Titularcuentahistorial.findAll", query="SELECT t FROM Titularcuentahistorial t")
public class Titularcuentahistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idtitularcuentahistorial;

	private Boolean estado;

	@Temporal(TemporalType.DATE)
	private Date fechaactiva;

	@Temporal(TemporalType.DATE)
	private Date fechainactiva;

	//bi-directional many-to-one association to Titularcuenta
	@ManyToOne
	@JoinColumn(name="idtitularcuenta")
	private Titularcuenta titularcuenta;

	public Titularcuentahistorial() {
	}

	public Integer getIdtitularcuentahistorial() {
		return this.idtitularcuentahistorial;
	}

	public void setIdtitularcuentahistorial(Integer idtitularcuentahistorial) {
		this.idtitularcuentahistorial = idtitularcuentahistorial;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechaactiva() {
		return this.fechaactiva;
	}

	public void setFechaactiva(Date fechaactiva) {
		this.fechaactiva = fechaactiva;
	}

	public Date getFechainactiva() {
		return this.fechainactiva;
	}

	public void setFechainactiva(Date fechainactiva) {
		this.fechainactiva = fechainactiva;
	}

	public Titularcuenta getTitularcuenta() {
		return this.titularcuenta;
	}

	public void setTitularcuenta(Titularcuenta titularcuenta) {
		this.titularcuenta = titularcuenta;
	}

}