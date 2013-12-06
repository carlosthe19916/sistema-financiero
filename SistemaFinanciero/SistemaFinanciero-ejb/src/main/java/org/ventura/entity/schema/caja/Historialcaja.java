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
@NamedQueries({
	@NamedQuery(name = Historialcaja.findHistorialActive, query = "select h from Historialcaja h where h.caja = :caja and h.idcreacion = (select max(hc.idcreacion) from Historialcaja hc where hc.caja = h.caja)")})
	//@NamedQuery(name = Historialboveda.findLastHistorialNoActive, query = "SELECT h FROM Historialboveda h WHERE h.boveda = :boveda and h.idcreacion = (SELECT (MAX(hh.idcreacion) - 1) FROM Historialboveda hh WHERE hh.boveda = h.boveda)") })
public class Historialcaja implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String findHistorialActive = "org.ventura.entity.schema.caja.Historialcaja.findHistorialActive";
	//public final static String findLastHistorialNoActive = "org.ventura.entity.schema.caja.Historialboveda.findLastHistorialNoActive";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idhistorialcaja;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	private Date fechacierre;

	private Date horaapertura;

	private Timestamp horacierre;

	private Integer idcreacion;

	
	
	// bi-directional many-to-one association to Estadomovimiento
	@ManyToOne
	@JoinColumn(name = "idcaja", nullable = false)
	private Caja caja;

	// bi-directional many-to-one association to Detallehistorialcaja
	@OneToMany(mappedBy = "historialcaja")
	private List<Detallehistorialcaja> detallehistorialcajas;
	
	@ManyToOne
	@JoinColumn(name = "idestadomovimiento", nullable = false)
	private Estadomovimiento estadomovimiento;
	
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

	public Date getHoraapertura() {
		return horaapertura;
	}

	public void setHoraapertura(Date horaapertura) {
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

	public Estadomovimiento getEstadomovimiento() {
		return estadomovimiento;
	}

	public void setEstadomovimiento(Estadomovimiento estadomovimiento) {
		this.estadomovimiento = estadomovimiento;
	}
}