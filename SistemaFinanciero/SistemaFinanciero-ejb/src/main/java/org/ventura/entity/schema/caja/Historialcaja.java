package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the historialcaja database table.
 * 
 */
@Entity
@NamedQuery(name = "Historialcaja.findAll", query = "SELECT h FROM Historialcaja h")
public class Historialcaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idhistorialcaja;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	private Date fechacierre;

	private Timestamp horaapertura;

	private Timestamp horacierre;

	private Integer idcreacion;

	private Integer idestadomovimiento;
	
	// bi-directional many-to-one association to Estadomovimiento
	@ManyToOne
	@JoinColumn(name = "idcaja", nullable = false)
	private Caja caja;

	// bi-directional many-to-one association to Detallehistorialcaja
	@OneToMany(mappedBy = "historialcaja")
	private List<Detallehistorialcaja> detallehistorialcajas;

	public Historialcaja() {
	}

	public Integer getIdhistorialcaja() {
		return this.idhistorialcaja;
	}

	public void setIdhistorialcaja(Integer idhistorialcaja) {
		this.idhistorialcaja = idhistorialcaja;
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

	public Integer getIdcreacion() {
		return this.idcreacion;
	}

	public void setIdcreacion(Integer idcreacion) {
		this.idcreacion = idcreacion;
	}

	public Integer getIdestadomovimiento() {
		return this.idestadomovimiento;
	}

	public void setIdestadomovimiento(Integer idestadomovimiento) {
		this.idestadomovimiento = idestadomovimiento;
	}

	public List<Detallehistorialcaja> getDetallehistorialcajas() {
		return this.detallehistorialcajas;
	}

	public void setDetallehistorialcajas(
			List<Detallehistorialcaja> detallehistorialcajas) {
		this.detallehistorialcajas = detallehistorialcajas;
	}

	public Detallehistorialcaja addDetallehistorialcaja(
			Detallehistorialcaja detallehistorialcaja) {
		getDetallehistorialcajas().add(detallehistorialcaja);
		detallehistorialcaja.setHistorialcaja(this);

		return detallehistorialcaja;
	}

	public Detallehistorialcaja removeDetallehistorialcaja(
			Detallehistorialcaja detallehistorialcaja) {
		getDetallehistorialcajas().remove(detallehistorialcaja);
		detallehistorialcaja.setHistorialcaja(null);

		return detallehistorialcaja;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

}