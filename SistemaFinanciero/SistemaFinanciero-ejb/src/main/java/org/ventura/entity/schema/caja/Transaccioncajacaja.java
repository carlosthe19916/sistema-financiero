package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.tipodato.Moneda;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the transaccioncajacaja database table.
 * 
 */
@Entity
@Table(name = "transaccioncajacaja", schema = "caja")
@NamedQuery(name = "Transaccioncajacaja.findAll", query = "SELECT t FROM Transaccioncajacaja t")
@NamedQueries({
		@NamedQuery(name = Transaccioncajacaja.f_idhistorialorigen, query = "SELECT t FROM Transaccioncajacaja t WHERE t.historialcajaorigen.idhistorialcaja = :idhistorialcajaorigen"),
		@NamedQuery(name = Transaccioncajacaja.f_idhistorialdestino, query = "SELECT t FROM Transaccioncajacaja t WHERE t.historialcajadestino.idhistorialcaja = :idhistorialcajadestino"),
		@NamedQuery(name = Transaccioncajacaja.f_count_trans_caja_caja_enviados, query = "SELECT tcc FROM Transaccioncajacaja tcc WHERE tcc.estadosolicitud = true AND tcc.estadoconfirmacion  = true AND tcc.historialcajaorigen.idcreacion = :idcreacion"),
		@NamedQuery(name = Transaccioncajacaja.f_count_trans_caja_caja_recibidos, query = "SELECT tcc FROM Transaccioncajacaja tcc WHERE tcc.estadosolicitud = true AND tcc.estadoconfirmacion  = true AND tcc.historialcajadestino.idcreacion = :idcreacion")})
public class Transaccioncajacaja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idhistorialorigen = "org.ventura.entity.schema.caja.Transaccioncajacaja.f_idhistorialorigen";
	public final static String f_idhistorialdestino = "org.ventura.entity.schema.caja.Transaccioncajacaja.f_idhistorialdestino";
	public final static String f_count_trans_caja_caja_enviados = "org.ventura.entity.schema.caja.Transaccioncajacaja.f_count_trans_caja_caja_enviados";
	public final static String f_count_trans_caja_caja_recibidos = "org.ventura.entity.schema.caja.Transaccioncajacaja.f_count_trans_caja_caja_recibidos";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccioncajacaja;

	@Column(nullable = false)
	private Boolean estadoconfirmacion;

	@Column(nullable = false)
	private Boolean estadosolicitud;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Column(nullable = false)
	private Timestamp hora;

	@ManyToOne
	@JoinColumn(name = "idhistorialcajadestino", nullable = false)
	private Historialcaja historialcajadestino;

	@ManyToOne
	@JoinColumn(name = "idhistorialcajaorigen", nullable = false)
	private Historialcaja historialcajaorigen;

	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal monto;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idusuariosolicita")
	private Usuario usuarioSolicita;
	
	@ManyToOne
	@JoinColumn(name = "idusuarioconfirma")
	private Usuario usuarioConfirma;
	
	public Transaccioncajacaja() {
	}

	public Integer getIdtransaccioncajacaja() {
		return this.idtransaccioncajacaja;
	}

	public void setIdtransaccioncajacaja(Integer idtransaccioncajacaja) {
		this.idtransaccioncajacaja = idtransaccioncajacaja;
	}

	public Boolean getEstadoconfirmacion() {
		return this.estadoconfirmacion;
	}

	public void setEstadoconfirmacion(Boolean estadoconfirmacion) {
		this.estadoconfirmacion = estadoconfirmacion;
	}

	public Boolean getEstadosolicitud() {
		return this.estadosolicitud;
	}

	public void setEstadosolicitud(Boolean estadosolicitud) {
		this.estadosolicitud = estadosolicitud;
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

	public BigDecimal getMonto() {
		return this.monto;
	}

	public String getMontoAsString() {
		if(monto != null)
			return Moneda.getMonedaFormat(this.monto);
		else
			return "";
	}
	
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Historialcaja getHistorialcajaorigen() {
		return historialcajaorigen;
	}

	public void setHistorialcajaorigen(Historialcaja historialcajaorigen) {
		this.historialcajaorigen = historialcajaorigen;
	}

	public Historialcaja getHistorialcajadestino() {
		return historialcajadestino;
	}

	public void setHistorialcajadestino(Historialcaja historialcajadestino) {
		this.historialcajadestino = historialcajadestino;
	}

	public Usuario getUsuarioSolicita() {
		return usuarioSolicita;
	}

	public void setUsuarioSolicita(Usuario usuarioSolicita) {
		this.usuarioSolicita = usuarioSolicita;
	}

	public Usuario getUsuarioConfirma() {
		return usuarioConfirma;
	}

	public void setUsuarioConfirma(Usuario usuarioConfirma) {
		this.usuarioConfirma = usuarioConfirma;
	}

}