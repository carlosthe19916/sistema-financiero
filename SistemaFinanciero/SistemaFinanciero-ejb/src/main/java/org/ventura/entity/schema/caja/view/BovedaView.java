package org.ventura.entity.schema.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.tipodato.Moneda;

/**
 * The persistent class for the boveda_view database table.
 * 
 */
@Entity
@Table(name = "boveda_view", schema = "caja")
@NamedQuery(name = "BovedaView.findAll", query = "SELECT b FROM BovedaView b")
@NamedQueries({ @NamedQuery(name = BovedaView.ALL_ACTIVE_BY_AGENCIA, query = "Select b From BovedaView b WHERE b.agencia = :agencia") })
public class BovedaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.view.BovedaView.ALL_ACTIVE_BY_AGENCIA";

	@Id
	@Column(name = "id_boveda")
	private Integer idBoveda;

	@Column(name = "abreviatura_estadoapertura", length = 10)
	private String abreviaturaEstadoapertura;

	@Column(name = "abreviatura_estadomovimiento", length = 10)
	private String abreviaturaEstadomovimiento;

	@Column(name = "abreviatura_tipomoneda", length = 3)
	private String abreviaturaTipomoneda;

	@Column(name = "denominacion_agencia", length = 150)
	private String denominacionAgencia;

	@Column(name = "denominacion_boveda", length = 150)
	private String denominacionBoveda;

	@Column(name = "denominacion_estadoapertura", length = 150)
	private String denominacionEstadoapertura;

	@Column(name = "denominacion_estadomovimiento", length = 150)
	private String denominacionEstadomovimiento;

	@Column(name = "denominacion_tipomoneda", length = 35)
	private String denominacionTipomoneda;

	@Column(name = "saldo")
	private BigDecimal saldo;
	
	@ManyToOne
	@JoinColumn(name = "id_agencia", nullable = false)
	private Agencia agencia;

	@ManyToOne
	@JoinColumn(name = "id_estadoapertura", nullable = false)
	private Estadoapertura estadoapertura;

	@ManyToOne
	@JoinColumn(name = "id_estadomovimiento", nullable = false)
	private Estadomovimiento estadomovimiento;

	@ManyToOne
	@JoinColumn(name = "id_tipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	public BovedaView() {
	}

	public Integer getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Integer idBoveda) {
		this.idBoveda = idBoveda;
	}

	public String getAbreviaturaEstadoapertura() {
		return abreviaturaEstadoapertura;
	}

	public void setAbreviaturaEstadoapertura(String abreviaturaEstadoapertura) {
		this.abreviaturaEstadoapertura = abreviaturaEstadoapertura;
	}

	public String getAbreviaturaEstadomovimiento() {
		return abreviaturaEstadomovimiento;
	}

	public void setAbreviaturaEstadomovimiento(
			String abreviaturaEstadomovimiento) {
		this.abreviaturaEstadomovimiento = abreviaturaEstadomovimiento;
	}

	public String getAbreviaturaTipomoneda() {
		return abreviaturaTipomoneda;
	}

	public void setAbreviaturaTipomoneda(String abreviaturaTipomoneda) {
		this.abreviaturaTipomoneda = abreviaturaTipomoneda;
	}

	public String getDenominacionAgencia() {
		return denominacionAgencia;
	}

	public void setDenominacionAgencia(String denominacionAgencia) {
		this.denominacionAgencia = denominacionAgencia;
	}

	public String getDenominacionBoveda() {
		return denominacionBoveda;
	}

	public void setDenominacionBoveda(String denominacionBoveda) {
		this.denominacionBoveda = denominacionBoveda;
	}

	public String getDenominacionEstadoapertura() {
		return denominacionEstadoapertura;
	}

	public void setDenominacionEstadoapertura(String denominacionEstadoapertura) {
		this.denominacionEstadoapertura = denominacionEstadoapertura;
	}

	public String getDenominacionEstadomovimiento() {
		return denominacionEstadomovimiento;
	}

	public void setDenominacionEstadomovimiento(
			String denominacionEstadomovimiento) {
		this.denominacionEstadomovimiento = denominacionEstadomovimiento;
	}

	public String getDenominacionTipomoneda() {
		return denominacionTipomoneda;
	}

	public void setDenominacionTipomoneda(String denominacionTipomoneda) {
		this.denominacionTipomoneda = denominacionTipomoneda;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Estadoapertura getEstadoapertura() {
		return estadoapertura;
	}

	public void setEstadoapertura(Estadoapertura estadoapertura) {
		this.estadoapertura = estadoapertura;
	}

	public Estadomovimiento getEstadomovimiento() {
		return estadomovimiento;
	}

	public void setEstadomovimiento(Estadomovimiento estadomovimiento) {
		this.estadomovimiento = estadomovimiento;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public String getSaldoAsString() {
		return saldo == null ? "" : Moneda.getMonedaFormat(saldo);
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}