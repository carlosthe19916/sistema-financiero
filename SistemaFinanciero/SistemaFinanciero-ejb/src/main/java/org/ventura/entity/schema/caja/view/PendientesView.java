package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the pendientes_view database table.
 * 
 */
@Entity
@Table(name="pendientes_view", schema = "caja")
@NamedQuery(name="PendientesView.findAll", query="SELECT p FROM PendientesView p")
@NamedQueries({
	@NamedQuery(name = PendientesView.Pendientes_by_Agencia, query = "select p from PendientesView p where p.idagencia=:idagencia order by p.fecha")})
public class PendientesView implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String Pendientes_by_Agencia = "org.ventura.entity.schema.caja.view.PendientesView.Pendientes_by_Agencia";
	
	@Id
	@Column(name="idpendientecaja")
	private Integer idpendientecaja;
	
	@Column(name="abreviatura_agencia")
	private String abreviaturaAgencia;

	@Column(name="abreviatura_caja")
	private String abreviaturaCaja;

	private String codigoagencia;

	@Column(name="denominacion_agencia")
	private String denominacionAgencia;

	@Column(name="denominacion_caja")
	private String denominacionCaja;

	private Timestamp fecha;

	private Integer idagencia;

	private Integer idcaja;

	private Integer idsucursal;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda")
	private Tipomoneda tipomoneda;
	
	private Integer idusuario;

	private BigDecimal monto;

	private String observacion;

	private String tipopendiente;

	private String username;
	
	private String numerodocumento;
	
	private Integer idtrabajador;
	
	@Column(name="denominacion_tipodocumento")
	private String denominaciontipodocumento;
	
	@Column(name="abreviatura_tipodocumento")
	private String abreviaturatipodocumento;
	
	@Column(name="nombrecompleto")
	private String nombrecompleto;

	public PendientesView() {
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

	public String getCodigoagencia() {
		return this.codigoagencia;
	}

	public void setCodigoagencia(String codigoagencia) {
		this.codigoagencia = codigoagencia;
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

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Integer getIdagencia() {
		return this.idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Integer getIdcaja() {
		return this.idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}

	public Integer getIdpendientecaja() {
		return this.idpendientecaja;
	}

	public void setIdpendientecaja(Integer idpendientecaja) {
		this.idpendientecaja = idpendientecaja;
	}

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipopendiente() {
		return this.tipopendiente;
	}

	public void setTipopendiente(String tipopendiente) {
		this.tipopendiente = tipopendiente;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Integer getIdtrabajador() {
		return idtrabajador;
	}

	public void setIdtrabajador(Integer idtrabajador) {
		this.idtrabajador = idtrabajador;
	}

	public String getDenominaciontipodocumento() {
		return denominaciontipodocumento;
	}

	public void setDenominaciontipodocumento(String denominaciontipodocumento) {
		this.denominaciontipodocumento = denominaciontipodocumento;
	}

	public String getAbreviaturatipodocumento() {
		return abreviaturatipodocumento;
	}

	public void setAbreviaturatipodocumento(String abreviaturatipodocumento) {
		this.abreviaturatipodocumento = abreviaturatipodocumento;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	/**
	 * @return the numerodocumento
	 */
	public String getNumerodocumento() {
		return numerodocumento;
	}

	/**
	 * @param numerodocumento the numerodocumento to set
	 */
	public void setNumerodocumento(String numerodocumento) {
		this.numerodocumento = numerodocumento;
	}

}