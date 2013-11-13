package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the transaccionboveda database table.
 * 
 */
@Entity
@Table(name="transaccionboveda",schema="caja")
@NamedQuery(name="Transaccionboveda.findAll", query="SELECT t FROM Transaccionboveda t")
public class Transaccionboveda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idtransaccionboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;

	@Column(nullable=false)
	private Timestamp hora;

	@Column(nullable=false)
	private double monto;

	//bi-directional many-to-one association to Detalletransaccionboveda
	@OneToMany(mappedBy="transaccionboveda")
	private List<Detalletransaccionboveda> detalletransaccionbovedas;

	//bi-directional many-to-one association to Boveda
	@ManyToOne
	@JoinColumn(name="idboveda", nullable=false)
	private Boveda boveda;

	//bi-directional many-to-one association to Tipotransaccion
	@ManyToOne
	@JoinColumn(name="idtipotransaccion", nullable=false)
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

	public double getMonto() {
		return this.monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public List<Detalletransaccionboveda> getDetalletransaccionbovedas() {
		return this.detalletransaccionbovedas;
	}

	public void setDetalletransaccionbovedas(List<Detalletransaccionboveda> detalletransaccionbovedas) {
		this.detalletransaccionbovedas = detalletransaccionbovedas;
	}

	public Detalletransaccionboveda addDetalletransaccionboveda(Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().add(detalletransaccionboveda);
		detalletransaccionboveda.setTransaccionboveda(this);

		return detalletransaccionboveda;
	}

	public Detalletransaccionboveda removeDetalletransaccionboveda(Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().remove(detalletransaccionboveda);
		detalletransaccionboveda.setTransaccionboveda(null);

		return detalletransaccionboveda;
	}

	public Boveda getBoveda() {
		return this.boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public Tipotransaccion getTipotransaccion() {
		return this.tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

}