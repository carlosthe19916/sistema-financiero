package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

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

	@OneToMany(mappedBy = "transaccionboveda")
	private List<Detalletransaccionboveda> detalletransaccionbovedas;

	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	@ManyToOne
	@JoinColumn(name = "idcaja", nullable = false)
	private Caja caja;

	@ManyToOne
	@JoinColumn(name = "identidadfinanciera", nullable = false)
	private Entidadfinanciera entidadfinanciera;

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

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public List<Detalletransaccionboveda> getDetalletransaccionbovedas() {
		return this.detalletransaccionbovedas;
	}

	public void setDetalletransaccionbovedas(
			List<Detalletransaccionboveda> detalletransaccionbovedas) {
		this.detalletransaccionbovedas = detalletransaccionbovedas;
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

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
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
/*
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Transaccionboveda)) {
			return false;
		}
		final Transaccionboveda other = (Transaccionboveda) obj;
		return other.getIdtransaccionboveda() == idtransaccionboveda ? true
				: false;
	}

	@Override
	public int hashCode() {
		return idtransaccionboveda;
	}*/
}