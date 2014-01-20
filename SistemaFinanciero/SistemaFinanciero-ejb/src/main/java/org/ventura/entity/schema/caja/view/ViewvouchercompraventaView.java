package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the viewvouchercompraventa_view database table.
 * 
 */
@Entity
@Table(name="viewvouchercompraventa_view", schema = "caja")
@NamedQuery(name="ViewvouchercompraventaView.findAll", query="SELECT v FROM ViewvouchercompraventaView v")
@NamedQueries({@NamedQuery(name = ViewvouchercompraventaView.FindByIdTransaccioncompraventa, query = "SELECT v FROM ViewvouchercompraventaView v WHERE v.idTransaccioncompraventa = :idtransaccioncompraventa")})
public class ViewvouchercompraventaView implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String FindByIdTransaccioncompraventa = "org.ventura.entity.schema.caja.view.ViewvouchercompraventaView.FindByIdTransaccioncompraventa";
	
	@Id
	@Column(name="id_transaccioncaja")
	private Integer idTransaccioncaja;
	
	@Column(name="codigo_agencia")
	private String codigoAgencia;
	
	@Column(name="denominacion_agencia")
	private String denominacionAgencia;
	
	@Column(name="abreviatura_agencia")
	private String abreviaturaAgencia;

	@Column(name="abreviatura_caja")
	private String abreviaturaCaja;
	
	@Column(name="denominacion_caja")
	private String denominacionCaja;
	
	@Column(name="denominacion_tipotransaccioncv")
	private String denominacionTipotransaccioncv;

	@Column(name="abreviatura_tipotransaccioncv")
	private String abreviaturaTipotransaccioncv;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Timestamp hora;
	
	@Column(name="numero_operacion")
	private Integer numeroOperacion;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "tipo_cambio")) })
	private TasaCambio tipoCambio;
	
	@Column(name="id_transaccioncompraventa")
	private Integer idTransaccioncompraventa;
	
	@Column(name="dni_ruc")
	private String dniRuc;
	
	@Column(name="nombres_razonsocial")
	private String nombresRazonsocial;

	@ManyToOne
	@JoinColumn(name = "id_tipomonedaentregado", nullable = false)
	private Tipomoneda tipomonedaentregado;

	@ManyToOne
	@JoinColumn(name = "id_tipomonedarecibido", nullable = false)
	private Tipomoneda tipomonedarecibido;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto_entregado")) })
	private Moneda montoEntregado;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto_recibido")) })
	private Moneda montoRecibido;

	public ViewvouchercompraventaView() {
	}

	public String getAbreviaturaAgencia() {
		return this.abreviaturaAgencia;
	}

	public void setAbreviaturaAgencia(String abreviaturaAgencia) {
		this.abreviaturaAgencia = abreviaturaAgencia;
	}

	public String getAbreviaturaCaja() {
		return this.abreviaturaCaja;
	}

	public void setAbreviaturaCaja(String abreviaturaCaja) {
		this.abreviaturaCaja = abreviaturaCaja;
	}

	public String getAbreviaturaTipotransaccioncv() {
		return this.abreviaturaTipotransaccioncv;
	}

	public void setAbreviaturaTipotransaccioncv(String abreviaturaTipotransaccioncv) {
		this.abreviaturaTipotransaccioncv = abreviaturaTipotransaccioncv;
	}

	public String getCodigoAgencia() {
		return this.codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getDenominacionAgencia() {
		return this.denominacionAgencia;
	}

	public void setDenominacionAgencia(String denominacionAgencia) {
		this.denominacionAgencia = denominacionAgencia;
	}

	public String getDenominacionCaja() {
		return this.denominacionCaja;
	}

	public void setDenominacionCaja(String denominacionCaja) {
		this.denominacionCaja = denominacionCaja;
	}

	public String getDenominacionTipotransaccioncv() {
		return this.denominacionTipotransaccioncv;
	}

	public void setDenominacionTipotransaccioncv(String denominacionTipotransaccioncv) {
		this.denominacionTipotransaccioncv = denominacionTipotransaccioncv;
	}

	public String getDniRuc() {
		return this.dniRuc;
	}

	public void setDniRuc(String dniRuc) {
		this.dniRuc = dniRuc;
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

	public Integer getIdTransaccioncaja() {
		return this.idTransaccioncaja;
	}

	public void setIdTransaccioncaja(Integer idTransaccioncaja) {
		this.idTransaccioncaja = idTransaccioncaja;
	}

	public Integer getIdTransaccioncompraventa() {
		return this.idTransaccioncompraventa;
	}

	public void setIdTransaccioncompraventa(Integer idTransaccioncompraventa) {
		this.idTransaccioncompraventa = idTransaccioncompraventa;
	}

	public String getNombresRazonsocial() {
		return this.nombresRazonsocial;
	}

	public void setNombresRazonsocial(String nombresRazonsocial) {
		this.nombresRazonsocial = nombresRazonsocial;
	}

	public Integer getNumeroOperacion() {
		return this.numeroOperacion;
	}

	public void setNumeroOperacion(Integer numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public Tipomoneda getTipomonedaentregado() {
		return tipomonedaentregado;
	}

	public void setTipomonedaentregado(Tipomoneda tipomonedaentregado) {
		this.tipomonedaentregado = tipomonedaentregado;
	}

	public Tipomoneda getTipomonedarecibido() {
		return tipomonedarecibido;
	}

	public void setTipomonedarecibido(Tipomoneda tipomonedarecibido) {
		this.tipomonedarecibido = tipomonedarecibido;
	}

	public Moneda getMontoRecibido() {
		return montoRecibido;
	}

	public void setMontoRecibido(Moneda montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public Moneda getMontoEntregado() {
		return montoEntregado;
	}

	public void setMontoEntregado(Moneda montoEntregado) {
		this.montoEntregado = montoEntregado;
	}

	public TasaCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TasaCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
}