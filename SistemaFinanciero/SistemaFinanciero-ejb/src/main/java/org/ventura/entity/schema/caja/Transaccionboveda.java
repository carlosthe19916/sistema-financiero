package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The persistent class for the transaccionboveda database table.
 * 
 */
@Entity
@Table(name = "transaccionboveda", schema = "caja")
@NamedQuery(name = "Transaccionboveda.findAll", query = "SELECT t FROM Transaccionboveda t")
public class Transaccionboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtransaccionboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Column(nullable = false)
	private Timestamp hora;

	@Column(nullable = false)
	private Double monto;

	@OneToMany(mappedBy = "transaccionboveda")
	private List<Detalletransaccionboveda> detalletransaccionbovedas;

	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	public Transaccionboveda() {
	}

	public Integer getIdtransaccionboveda() {
		return this.idtransaccionboveda;
	}

	public void setIdtransaccionboveda(Integer idtransaccionboveda) {
		this.idtransaccionboveda = idtransaccionboveda;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Timestamp getHora() {
		return this.hora;
	}

	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public List<Detalletransaccionboveda> getDetalletransaccionbovedas() {
		return this.detalletransaccionbovedas;
	}

	public void setDetalletransaccionbovedas(List<Detalletransaccionboveda> detalletransaccionbovedas) {
		this.detalletransaccionbovedas = detalletransaccionbovedas;
		refreshTotal();
	}

	public Detalletransaccionboveda addDetalletransaccionboveda(Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().add(detalletransaccionboveda);
		detalletransaccionboveda.setTransaccionboveda(this);
		refreshTotal();
		return detalletransaccionboveda;
	}

	public Detalletransaccionboveda removeDetalletransaccionboveda(Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().remove(detalletransaccionboveda);
		detalletransaccionboveda.setTransaccionboveda(null);
		refreshTotal();
		return detalletransaccionboveda;
	}

	public Tipotransaccion getTipotransaccion() {
		return this.tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Historialboveda getHistorialboveda() {
		return historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}
	
	public void refreshTotal() {
		List<Detalletransaccionboveda> detalleaperturacierrebovedas = getDetalletransaccionbovedas();
		this.monto = new Double(0);
		if (detalleaperturacierrebovedas != null) {
			for (Iterator<Detalletransaccionboveda> iterator = detalleaperturacierrebovedas.iterator(); iterator.hasNext();) {
				Detalletransaccionboveda detalleaperturacierreboveda = iterator.next();
				this.monto = monto + detalleaperturacierreboveda.getTotal();
			}
		}
	}

}