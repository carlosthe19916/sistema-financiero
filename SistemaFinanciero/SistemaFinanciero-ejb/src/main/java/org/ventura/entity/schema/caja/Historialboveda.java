package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the historialboveda database table.
 * 
 */
@Entity
@Table(name = "historialboveda", schema = "caja")
@NamedQuery(name = "Historialboveda.findAll", query = "SELECT h FROM Historialboveda h")
@NamedQueries({ @NamedQuery(name = Historialboveda.findHistorialActive, query = "SELECT h FROM Historialboveda h WHERE h.estado = true AND h.idboveda = :idboveda") })
public class Historialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findHistorialActive = "org.ventura.entity.schema.caja.findHistorialActive";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idhistorialboveda;

	@Column(nullable = false)
	private Integer idboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column
	private Date fechacierre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date horaapertura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date horacierre;

	@Column
	private Double saldofinal;

	@Column(nullable = false)
	private Double saldoinicial;

	@Column
	private Integer iddetallehistorialbovedainicial;

	@Column
	private Integer iddetallehistorialbovedafinal;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idboveda", nullable = false, insertable = false, updatable = false)
	private Boveda boveda;

	@ManyToOne
	@JoinColumn(name = "iddetallehistorialbovedainicial", nullable = false, insertable = false, updatable = false)
	private Detallehistorialboveda detallehistorialbovedainicial;

	@ManyToOne
	@JoinColumn(name = "iddetallehistorialbovedafinal", insertable = false, updatable = false)
	private Detallehistorialboveda detallehistorialbovedafinal;

	public Historialboveda() {
	}

	public Integer getIdhistorialboveda() {
		return this.idhistorialboveda;
	}

	public void setIdhistorialboveda(Integer idhistorialboveda) {
		this.idhistorialboveda = idhistorialboveda;
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Date getFechacierre() {
		return this.fechacierre;
	}

	public void setFechacierre(Date fechacierre) {
		this.fechacierre = fechacierre;
	}

	public Date getHoraapertura() {
		return this.horaapertura;
	}

	public void setHoraapertura(Date horaapertura) {
		this.horaapertura = horaapertura;
	}

	public Date getHoracierre() {
		return this.horacierre;
	}

	public void setHoracierre(Date horacierre) {
		this.horacierre = horacierre;
	}

	public Double getSaldofinal() {
		return this.saldofinal;
	}

	public void setSaldofinal(Double saldofinal) {
		this.saldofinal = saldofinal;
	}

	public Double getSaldoinicial() {
		return this.saldoinicial;
	}

	public void setSaldoinicial(Double saldoinicial) {
		this.saldoinicial = saldoinicial;
	}

	public Boveda getBoveda() {
		return this.boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
		this.idboveda = (boveda == null) ? null : boveda.getIdboveda();
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

	public Integer getIddetallehistorialbovedainicial() {
		return iddetallehistorialbovedainicial;
	}

	public void setIddetallehistorialbovedainicial(Integer iddetallehistorialbovedainicial) {
		this.iddetallehistorialbovedainicial = iddetallehistorialbovedainicial;
	}

	public Integer getIddetallehistorialbovedafinal() {
		return iddetallehistorialbovedafinal;
	}

	public void setIddetallehistorialbovedafinal(Integer iddetallehistorialbovedafinal) {
		this.iddetallehistorialbovedafinal = iddetallehistorialbovedafinal;
	}

	public Detallehistorialboveda getDetallehistorialbovedainicial() {
		return detallehistorialbovedainicial;
	}

	public void setDetallehistorialbovedainicial(Detallehistorialboveda detallehistorialbovedainicial) {
		this.detallehistorialbovedainicial = detallehistorialbovedainicial;
		this.iddetallehistorialbovedainicial = (detallehistorialbovedainicial == null) ? null : detallehistorialbovedainicial.getIddetallehistorialboveda();
		this.refreshSaldos();
	}

	public Detallehistorialboveda getDetallehistorialbovedafinal() {
		return detallehistorialbovedafinal;
	}

	public void setDetallehistorialbovedafinal(Detallehistorialboveda detallehistorialbovedafinal) {
		this.detallehistorialbovedafinal = detallehistorialbovedafinal;
		this.iddetallehistorialbovedafinal = (detallehistorialbovedafinal == null) ? null : detallehistorialbovedafinal.getIddetallehistorialboveda();
		this.refreshSaldos();
	}
	
	public void refreshSaldos() {
		this.saldoinicial = new Double(0);
		this.saldofinal = new Double(0);	
		Detallehistorialboveda detallehistorialbovedainicial = getDetallehistorialbovedainicial();
		Detallehistorialboveda detallehistorialbovedafinal = getDetallehistorialbovedafinal();	
		if (detallehistorialbovedainicial != null) {
			this.saldoinicial = detallehistorialbovedainicial.getTotal();
		}
		if (detallehistorialbovedafinal != null) {
			this.saldofinal = detallehistorialbovedafinal.getTotal();
		}
	}

}