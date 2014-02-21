package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the transaccionboveda database table.
 * 
 */
@Entity
@Table(name = "transaccionboveda", schema = "caja")
@NamedQuery(name = "Transaccionboveda.findAll", query = "SELECT t FROM Transaccionboveda t")
@NamedQueries({ @NamedQuery(name = Transaccionboveda.FIND, query = "SELECT t FROM Transaccionboveda t WHERE t.idtransaccionboveda = :idtransaccionboveda") })
public class Transaccionboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String FIND = "org.ventura.entity.schema.caja.Transaccionboveda.FIND";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccionboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date hora;
	
	@Column(nullable = false)
	private BigDecimal saldodisponible;

	@OneToMany(mappedBy = "transaccionboveda")
	private List<Detalletransaccionboveda> detalletransaccionbovedas;

	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja")
	private Historialcaja historialcaja;

	@ManyToOne
	@JoinColumn(name = "identidadfinanciera")
	private Entidadfinanciera entidadfinanciera;

	public Transaccionboveda() {
	}

	public Integer getIdtransaccionboveda() {
		return idtransaccionboveda;
	}

	public void setIdtransaccionboveda(Integer idtransaccionboveda) {
		this.idtransaccionboveda = idtransaccionboveda;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public List<Detalletransaccionboveda> getDetalletransaccionbovedas() {
		return detalletransaccionbovedas;
	}

	public void setDetalletransaccionbovedas(
			List<Detalletransaccionboveda> detalletransaccionbovedas) {
		this.detalletransaccionbovedas = detalletransaccionbovedas;
	}

	public Historialboveda getHistorialboveda() {
		return historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Entidadfinanciera getEntidadfinanciera() {
		return entidadfinanciera;
	}

	public void setEntidadfinanciera(Entidadfinanciera entidadfinanciera) {
		this.entidadfinanciera = entidadfinanciera;
	}

	public Moneda getTotal() {
		Moneda result = new Moneda();
		for (Detalletransaccionboveda e : detalletransaccionbovedas) {
			result = result.add(e.getSubtotal());
		}
		return result;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

	public BigDecimal getSaldodisponible() {
		return saldodisponible;
	}

	public void setSaldodisponible(BigDecimal saldodisponible) {
		this.saldodisponible = saldodisponible;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Transaccionboveda)) {
			return false;
		}
		final Transaccionboveda other = (Transaccionboveda) obj;
		return other.getIdtransaccionboveda().equals(this.idtransaccionboveda) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtransaccionboveda;
	}
}