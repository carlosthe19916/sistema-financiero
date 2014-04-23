package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;

/**
 * The persistent class for the transaccioncompraventa database table.
 * 
 */
@Entity
@Table(name = "transaccioncompraventa", schema = "caja")
@NamedQuery(name = "Transaccioncompraventa.findAll", query = "SELECT t FROM Transaccioncompraventa t")
@NamedQueries({ @NamedQuery(name = Transaccioncompraventa.f_idtransaccioncaja, query = "SELECT t FROM Transaccioncompraventa t INNER JOIN t.transaccioncaja tc WHERE tc.idtransaccioncaja = :idtransaccioncaja"),
@NamedQuery(name = Transaccioncompraventa.f_count_compraventa, query = "SELECT tcv FROM Transaccioncompraventa tcv INNER JOIN tcv.transaccioncaja tc INNER JOIN tc.historialcaja hc INNER JOIN hc.caja c WHERE c.idcaja = :idcaja AND tcv.tipotransaccioncompraventa.idtipotransaccioncompraventa = :idtipotransaccioncompraventa AND hc.idcreacion = :idcreacion"),
@NamedQuery(name = Transaccioncompraventa.f_count_mayor_cuantia_compra, query = "SELECT tcv FROM Transaccioncompraventa tcv INNER JOIN tcv.transaccioncaja tc INNER JOIN tc.historialcaja hc INNER JOIN hc.caja c WHERE c.idcaja = :idcaja AND tcv.tipotransaccioncompraventa.idtipotransaccioncompraventa = :idtipotransaccioncompraventa AND hc.idcreacion = :idcreacion AND (tcv.montorecibido) >= :monto"),
@NamedQuery(name = Transaccioncompraventa.f_count_mayor_cuantia_venta, query = "SELECT tcv FROM Transaccioncompraventa tcv INNER JOIN tcv.transaccioncaja tc INNER JOIN tc.historialcaja hc INNER JOIN hc.caja c WHERE c.idcaja = :idcaja AND tcv.tipotransaccioncompraventa.idtipotransaccioncompraventa = :idtipotransaccioncompraventa AND hc.idcreacion = :idcreacion AND (tcv.montoentregado) >= :monto")})
public class Transaccioncompraventa implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idtransaccioncaja = "org.ventura.entity.schema.caja.Transaccioncompraventa.f_idtransaccioncaja";
	public final static String f_count_compraventa = "org.ventura.entity.schema.caja.Transaccioncompraventa.f_count_compraventa";
	public final static String f_count_mayor_cuantia_compra = "org.ventura.entity.schema.caja.Transaccioncompraventa.f_count_mayor_cuantia_compra";
	public final static String f_count_mayor_cuantia_venta = "org.ventura.entity.schema.caja.Transaccioncompraventa.f_count_mayor_cuantia_venta";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncompraventa;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montoentregado")) })
	private Moneda montoentregado;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montorecibido")) })
	private Moneda montorecibido;

	@Column(length = 11, name = "dniruc")
	private String dniRuc;

	@Column(length = 200, name = "nombresrazonsocial")
	private String nombresRazonSocial;

	@Column(name = "estado")
	private boolean estado;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "tipocambio")) })
	private TasaCambio tipocambio;

	@ManyToOne
	@JoinColumn(name = "idtransaccioncaja", nullable = false)
	private Transaccioncaja transaccioncaja;

	@ManyToOne
	@JoinColumn(name = "idtipomonedarecibido", nullable = false)
	private Tipomoneda tipomonedaRecibido;

	@ManyToOne
	@JoinColumn(name = "idtipomonedaentregado", nullable = false)
	private Tipomoneda tipomonedaEntregado;

	@ManyToOne
	@JoinColumn(name = "idtipotransaccioncompraventa", nullable = false)
	private Tipotransaccioncompraventa tipotransaccioncompraventa;

	public Transaccioncompraventa() {
	}

	public Integer getIdtransaccioncompraventa() {
		return this.idtransaccioncompraventa;
	}

	public void setIdtransaccioncompraventa(Integer idtransaccioncompraventa) {
		this.idtransaccioncompraventa = idtransaccioncompraventa;
	}

	public Tipotransaccioncompraventa getTipotransaccioncompraventa() {
		return this.tipotransaccioncompraventa;
	}

	public void setTipotransaccioncompraventa(
			Tipotransaccioncompraventa tipotransaccioncompraventa) {
		this.tipotransaccioncompraventa = tipotransaccioncompraventa;
	}

	public Moneda getMontoentregado() {
		return montoentregado;
	}

	public void setMontoentregado(Moneda montoentregado) {
		this.montoentregado = montoentregado;
	}

	public Moneda getMontorecibido() {
		return montorecibido;
	}

	public void setMontorecibido(Moneda montorecibido) {
		this.montorecibido = montorecibido;
	}

	public Tipomoneda getTipomonedaRecibido() {
		return tipomonedaRecibido;
	}

	public void setTipomonedaRecibido(Tipomoneda tipomonedaRecibido) {
		this.tipomonedaRecibido = tipomonedaRecibido;
	}

	public Tipomoneda getTipomonedaEntregado() {
		return tipomonedaEntregado;
	}

	public void setTipomonedaEntregado(Tipomoneda tipomonedaEntregado) {
		this.tipomonedaEntregado = tipomonedaEntregado;
	}

	public Transaccioncaja getTransaccioncaja() {
		return transaccioncaja;
	}

	public void setTransaccioncaja(Transaccioncaja transaccioncaja) {
		this.transaccioncaja = transaccioncaja;
	}

	public String getDniRuc() {
		return dniRuc;
	}

	public void setDniRuc(String dniRuc) {
		this.dniRuc = dniRuc;
	}

	public String getNombresRazonSocial() {
		return nombresRazonSocial;
	}

	public void setNombresRazonSocial(String nombresRazonSocial) {
		this.nombresRazonSocial = nombresRazonSocial;
	}

	public TasaCambio getTipocambio() {
		return tipocambio;
	}

	public void setTipocambio(TasaCambio tipocambio) {
		this.tipocambio = tipocambio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Transaccioncompraventa)) {
			return false;
		}
		final Transaccioncompraventa other = (Transaccioncompraventa) obj;
		return other.getIdtransaccioncompraventa().equals(this.idtransaccioncompraventa) ? true : false;
	}

	@Override
	public int hashCode() {
		return idtransaccioncompraventa;
	}
}