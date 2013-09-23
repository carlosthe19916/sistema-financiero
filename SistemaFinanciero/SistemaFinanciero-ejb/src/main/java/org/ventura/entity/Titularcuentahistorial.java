package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.TitularcuentahistorialListener;

import java.util.Date;

/**
 * The persistent class for the titularcuentahistorial database table.
 * 
 */
@Entity
@Table(name = "titularcuentahistorial", schema = "cuentapersonal")
@EntityListeners( { TitularcuentahistorialListener.class })
@NamedQuery(name = "Titularcuentahistorial.findAll", query = "SELECT t FROM Titularcuentahistorial t")
public class Titularcuentahistorial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtitularcuentahistorial;

	@Column(nullable = false)
	private Boolean estado;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaactiva;

	@Temporal(TemporalType.DATE)
	@Column
	private Date fechainactiva;

	@Column(nullable = false)
	private Integer idtitularcuenta;
	
	// bi-directional many-to-one association to Titularcuenta
	@ManyToOne
	@JoinColumn(name = "idtitularcuenta", nullable = false,insertable=false,updatable=false)
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

	public Integer getIdtitularcuenta() {
		return idtitularcuenta;
	}

	public void setIdtitularcuenta(Integer idtitularcuenta) {
		this.idtitularcuenta = idtitularcuenta;
	}

}