package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;

import java.util.Date;

/**
 * The persistent class for the transaccioncuentaaporte database table.
 * 
 */
@Entity
@Table(name = "transaccioncuentaaporte", schema = "caja")
@NamedQuery(name = "Transaccioncuentaaporte.findAll", query = "SELECT t FROM Transaccioncuentaaporte t")
@NamedQueries({
		@NamedQuery(name = Transaccioncuentaaporte.f_idtransaccioncaja, query = "SELECT t FROM Transaccioncuentaaporte t INNER JOIN t.transaccioncaja tc WHERE tc.idtransaccioncaja = :idtransaccioncaja"),
		@NamedQuery(name = Transaccioncuentaaporte.f_get_begindate_idcuentaaporte, query = "SELECT t FROM Transaccioncuentaaporte t INNER JOIN t.cuentaaporte ca INNER JOIN t.transaccioncaja tc WHERE ca.idcuentaaporte = :idcuentaaporte AND tc.hora > :begindate") })
public class Transaccioncuentaaporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idtransaccioncaja = "org.ventura.entity.schema.caja.Transaccioncuentaaporte.f_idtransaccioncaja";
	public final static String f_get_begindate_idcuentaaporte = "org.ventura.entity.schema.caja.Transaccioncuentaaporte.f_get_begindate_idcuentaaporte";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncuentaaporte;

	@Column(nullable = false)
	private BigDecimal saldodisponible;

	@ManyToOne
	@JoinColumn(name = "idcuentaaporte", nullable = false)
	private Cuentaaporte cuentaaporte;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccion", nullable = false)
	private Tipotransaccion tipotransaccion;

	@ManyToOne
	@JoinColumn(name = "idtransaccioncaja", nullable = false)
	private Transaccioncaja transaccioncaja;

	@Temporal(TemporalType.DATE)
	@Column
	private Date mesafecta;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto")) })
	private Moneda monto;

	@Column(length = 200)
	private String referencia;

	private boolean estado;

	public Transaccioncuentaaporte() {
	}

	public Integer getIdtransaccioncuentaaporte() {
		return this.idtransaccioncuentaaporte;
	}

	public void setIdtransaccioncuentaaporte(Integer idtransaccioncuentaaporte) {
		this.idtransaccioncuentaaporte = idtransaccioncuentaaporte;
	}

	public Date getMesafecta() {
		return this.mesafecta;
	}

	public void setMesafecta(Date mesafecta) {
		this.mesafecta = mesafecta;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public Transaccioncaja getTransaccioncaja() {
		return transaccioncaja;
	}

	public void setTransaccioncaja(Transaccioncaja transaccioncaja) {
		this.transaccioncaja = transaccioncaja;
	}

	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
		this.monto = monto;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public BigDecimal getSaldodisponible() {
		return saldodisponible;
	}

	public void setSaldodisponible(BigDecimal saldodisponible) {
		this.saldodisponible = saldodisponible;
	}

}