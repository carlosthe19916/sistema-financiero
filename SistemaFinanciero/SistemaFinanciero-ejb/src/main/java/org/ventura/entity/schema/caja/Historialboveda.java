package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the historialboveda database table.
 * 
 */
@Entity
@Table(name="historialboveda",schema="caja")
@NamedQuery(name="Historialboveda.findAll", query="SELECT h FROM Historialboveda h")
public class Historialboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idhistorialboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechacierre;

	@Column(nullable=false)
	private Timestamp horaapertura;

	@Column(nullable=false)
	private Timestamp horacierre;

	@Column(nullable=false)
	private Integer saldofinal;

	@Column(nullable=false)
	private double saldoinicial;

	//bi-directional many-to-one association to Detallehistorialboveda
	@OneToMany(mappedBy="historialboveda")
	private List<Detallehistorialboveda> detallehistorialbovedas;

	//bi-directional many-to-one association to Boveda
	@ManyToOne
	@JoinColumn(name="idboveda", nullable=false)
	private Boveda boveda;

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

	public Timestamp getHoraapertura() {
		return this.horaapertura;
	}

	public void setHoraapertura(Timestamp horaapertura) {
		this.horaapertura = horaapertura;
	}

	public Timestamp getHoracierre() {
		return this.horacierre;
	}

	public void setHoracierre(Timestamp horacierre) {
		this.horacierre = horacierre;
	}

	public Integer getSaldofinal() {
		return this.saldofinal;
	}

	public void setSaldofinal(Integer saldofinal) {
		this.saldofinal = saldofinal;
	}

	public double getSaldoinicial() {
		return this.saldoinicial;
	}

	public void setSaldoinicial(double saldoinicial) {
		this.saldoinicial = saldoinicial;
	}

	public List<Detallehistorialboveda> getDetallehistorialbovedas() {
		return this.detallehistorialbovedas;
	}

	public void setDetallehistorialbovedas(List<Detallehistorialboveda> detallehistorialbovedas) {
		this.detallehistorialbovedas = detallehistorialbovedas;
	}

	public Detallehistorialboveda addDetallehistorialboveda(Detallehistorialboveda detallehistorialboveda) {
		getDetallehistorialbovedas().add(detallehistorialboveda);
		detallehistorialboveda.setHistorialboveda(this);

		return detallehistorialboveda;
	}

	public Detallehistorialboveda removeDetallehistorialboveda(Detallehistorialboveda detallehistorialboveda) {
		getDetallehistorialbovedas().remove(detallehistorialboveda);
		detallehistorialboveda.setHistorialboveda(null);

		return detallehistorialboveda;
	}

	public Boveda getBoveda() {
		return this.boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

}