package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccionboveda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date hora;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto")) })
	private Moneda monto;

	@OneToMany(mappedBy = "transaccionboveda", cascade = CascadeType.ALL)
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
		this.monto = new Moneda();
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

	public void setDetalletransaccionbovedas(List<Detalletransaccionboveda> detalletransaccionbovedas) {
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

	public void setMonto(Moneda monto) {
		this.monto = monto;
	}

	public Moneda getMonto() {
		refreshMonto();
		return monto;
	}
	
	public void refreshMonto(){
		Moneda result = new Moneda();
		List<Detalletransaccionboveda> detalletransaccionbovedas = this. detalletransaccionbovedas;
		if(detalletransaccionbovedas != null){
			for (Iterator<Detalletransaccionboveda> iterator = detalletransaccionbovedas.iterator(); iterator.hasNext();) {
				Detalletransaccionboveda detalletransaccionboveda = (Detalletransaccionboveda) iterator.next();
				BigDecimal aux = result.add(detalletransaccionboveda.getSubtotal());
				result.setValue(aux);
			}
		}
		this.monto = result;
	}
}