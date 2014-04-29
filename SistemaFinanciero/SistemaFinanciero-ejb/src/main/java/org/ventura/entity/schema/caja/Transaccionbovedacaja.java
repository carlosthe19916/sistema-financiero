package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.seguridad.Usuario;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the transaccionbovedacaja database table.
 * 
 */
@Entity
@Table(name = "transaccionbovedacaja", schema = "caja")
@NamedQuery(name = "Transaccionbovedacaja.findAll", query = "SELECT t FROM Transaccionbovedacaja t")
@NamedQueries({ @NamedQuery(name = Transaccionbovedacaja.f_idcaja_origen, query = "SELECT t FROM Transaccionbovedacaja t WHERE t.historialcaja.caja.idcaja = :idcaja AND t.origen = :origen AND t.estadosolicitud = true") })
public class Transaccionbovedacaja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_idcaja_origen = "org.ventura.entity.schema.caja.Transaccionbovedacaja.f_idcaja_origen";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccionbovedacaja;

	@Column(nullable = false)
	private Boolean estadoconfirmacion;

	@Column(nullable = false)
	private Boolean estadosolicitud;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date hora;

	@ManyToOne
	@JoinColumn(name = "idhistorialboveda", nullable = false)
	private Historialboveda historialboveda;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja", nullable = false)
	private Historialcaja historialcaja;

	@ManyToOne
	@JoinColumn(name = "idusuariosolicita", nullable = false)
	private Usuario usuarioSolicita;

	@ManyToOne
	@JoinColumn(name = "idusuarioconfirma")
	private Usuario usuarioConfirma;

	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal monto;

	@Column(nullable = false)
	private String origen;

	@OneToMany(mappedBy = "transaccionbovedacaja")
	private List<Detalletransaccionboveda> detalleTransaccionboveda;

	public Transaccionbovedacaja() {
	}

	public Integer getIdtransaccionbovedacaja() {
		return this.idtransaccionbovedacaja;
	}

	public void setIdtransaccionbovedacaja(Integer idtransaccionbovedacaja) {
		this.idtransaccionbovedacaja = idtransaccionbovedacaja;
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

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Historialboveda getHistorialboveda() {
		return historialboveda;
	}

	public void setHistorialboveda(Historialboveda historialboveda) {
		this.historialboveda = historialboveda;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
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

	public List<Detalletransaccionboveda> getDetalleTransaccionboveda() {
		return detalleTransaccionboveda;
	}

	public void setDetalleTransaccionboveda(
			List<Detalletransaccionboveda> detalleTransaccionboveda) {
		this.detalleTransaccionboveda = detalleTransaccionboveda;
	}

}