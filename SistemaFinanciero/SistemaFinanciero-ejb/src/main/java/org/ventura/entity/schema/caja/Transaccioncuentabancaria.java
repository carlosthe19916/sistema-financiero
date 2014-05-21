package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;

/**
 * The persistent class for the transaccioncuentabancaria database table.
 * 
 */
@Entity
@Table(name = "transaccioncuentabancaria", schema = "caja")
@NamedQuery(name = "Transaccioncuentabancaria.findAll", query = "SELECT t FROM Transaccioncuentabancaria t")
@NamedQueries({
		@NamedQuery(name = Transaccioncuentabancaria.f_idtransaccioncaja, query = "SELECT t FROM Transaccioncuentabancaria t INNER JOIN t.transaccioncaja tc WHERE tc.idtransaccioncaja = :idtransaccioncaja"),
		@NamedQuery(name = Transaccioncuentabancaria.f_get_begindate_idcuentaaporte, query = "SELECT t FROM Transaccioncuentabancaria t INNER JOIN t.cuentabancaria cb INNER JOIN t.transaccioncaja tc WHERE cb.idcuentabancaria = :idcuentabancaria AND tc.hora > :begindate"),
		@NamedQuery(name = Transaccioncuentabancaria.f_count_cuentabancaria, query = "SELECT tcb FROM Transaccioncuentabancaria tcb INNER JOIN tcb.cuentabancaria cb INNER JOIN tcb.transaccioncaja tc INNER JOIN tc.historialcaja hc INNER JOIN hc.caja c WHERE c.idcaja = :idcaja AND cb.tipocuentabancaria.idtipocuentabancaria = :idtipocuentabancaria AND tcb.tipotransaccion.idtipotransaccion = :idtipotransaccion AND hc.idcreacion = :idcreacion"),
		@NamedQuery(name = Transaccioncuentabancaria.f_count_mayor_cuantia_cuenta_bancaria, query = "SELECT tcb FROM Transaccioncuentabancaria tcb INNER JOIN tcb.transaccioncaja tc INNER JOIN tc.historialcaja hc INNER JOIN hc.caja c WHERE c.idcaja = :idcaja AND tcb.tipotransaccion.idtipotransaccion = :idtipotransaccion AND hc.idcreacion = :idcreacion AND (tcb.monto) >= :monto AND tcb.tipomoneda.idtipomoneda = :idtipomoneda"),
		@NamedQuery(name = Transaccioncuentabancaria.f_total_deposito_retiro, query = "SELECT tcb FROM Transaccioncuentabancaria tcb INNER JOIN tcb.transaccioncaja tc WHERE tc.historialcaja.idcreacion = :idcreacion AND tcb.tipotransaccion.idtipotransaccion = :idtipotransaccion AND tcb.tipomoneda.idtipomoneda = :idtipomoneda")}) 
public class Transaccioncuentabancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idtransaccioncaja = "org.ventura.entity.schema.caja.Transaccioncuentabancaria.f_idtransaccioncaja";
	public final static String f_get_begindate_idcuentaaporte = "org.ventura.entity.schema.caja.Transaccioncuentabancaria.f_get_begindate_idcuentaaporte";
	public final static String f_count_cuentabancaria = "org.ventura.entity.schema.caja.Transaccioncuentabancaria.f_count_cuentabancaria";
	public final static String f_count_mayor_cuantia_cuenta_bancaria = "org.ventura.entity.schema.caja.Transaccioncuentabancaria.f_count_mayor_cuantia_cuenta_bancaria";
	public final static String f_total_deposito_retiro = "org.ventura.entity.schema.caja.Transaccioncuentabancaria.f_total_deposito_retiro";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncuentabancaria;

	private Integer idcheque;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto")) })
	private Moneda monto;

	@Column(length = 250)
	private String referencia;

	@Column
	private boolean estado;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldodisponible")) })
	private Moneda saldodisponible;

	@ManyToOne
	@JoinColumn(name = "idtransaccioncaja", nullable = false)
	private Transaccioncaja transaccioncaja;

	@ManyToOne
	@JoinColumn(name = "idcuentabancaria", nullable = false)
	private Cuentabancaria cuentabancaria;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	public Transaccioncuentabancaria() {
	}

	public Transaccioncaja getTransaccioncaja() {
		return transaccioncaja;
	}

	public void setTransaccioncaja(Transaccioncaja transaccioncaja) {
		this.transaccioncaja = transaccioncaja;
	}

	public Integer getIdtransaccioncuentabancaria() {
		return this.idtransaccioncuentabancaria;
	}

	public void setIdtransaccioncuentabancaria(
			Integer idtransaccioncuentabancaria) {
		this.idtransaccioncuentabancaria = idtransaccioncuentabancaria;
	}

	public Integer getIdcheque() {
		return this.idcheque;
	}

	public void setIdcheque(Integer idcheque) {
		this.idcheque = idcheque;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
		this.monto = monto;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Moneda getSaldodisponible() {
		return saldodisponible;
	}

	public void setSaldodisponible(Moneda saldodisponible) {
		this.saldodisponible = saldodisponible;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Transaccioncuentabancaria)) {
			return false;
		}
		final Transaccioncuentabancaria other = (Transaccioncuentabancaria) obj;
		return other.getIdtransaccioncuentabancaria().equals(this.idtransaccioncuentabancaria) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtransaccioncuentabancaria;
	}
}