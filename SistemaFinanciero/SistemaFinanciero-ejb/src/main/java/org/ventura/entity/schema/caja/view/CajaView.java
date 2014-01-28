package org.ventura.entity.schema.caja.view;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the caja_view database table.
 * 
 */
@Entity
@Table(name = "caja_view", schema = "caja")
@NamedQuery(name = "CajaView.findAll", query = "SELECT c FROM CajaView c")
@NamedQueries({ @NamedQuery(name = CajaView.ALL_ACTIVE_BY_AGENCIA, query = "SELECT c FROM CajaView c WHERE c.idagencia = :idagencia") })
public class CajaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.view.CajaView.ALL_ACTIVE_BY_AGENCIA";

	@Id
	@Column(name = "id_caja")
	private Integer idCaja;

	@Column(length = 10)
	private String abreviatura;

	@Column(name = "abreviatura_caja", length = 30)
	private String abreviaturaCaja;

	@Column(name = "abreviatura_estadoapertura", length = 10)
	private String abreviaturaEstadoapertura;

	@Column(name = "abreviatura_estadomovimiento", length = 10)
	private String abreviaturaEstadomovimiento;

	@Column(length = 3)
	private String codigoagencia;

	@Column(length = 150)
	private String denominacion;

	@Column(name = "denominacion_caja", length = 100)
	private String denominacionCaja;

	@Column(name = "denominacion_estadoapertura", length = 150)
	private String denominacionEstadoapertura;

	@Column(name = "denominacion_estadomovimiento", length = 150)
	private String denominacionEstadomovimiento;

	@Column(name = "id_estadoapertura")
	private Integer idEstadoapertura;

	@Column(name = "id_estadomovimiento")
	private Integer idEstadomovimiento;

	private Integer idagencia;
	
	@Column(name = "saldo")
	private String saldo;

	public CajaView() {
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviaturaCaja() {
		return this.abreviaturaCaja;
	}

	public void setAbreviaturaCaja(String abreviaturaCaja) {
		this.abreviaturaCaja = abreviaturaCaja;
	}

	public String getAbreviaturaEstadoapertura() {
		return this.abreviaturaEstadoapertura;
	}

	public void setAbreviaturaEstadoapertura(String abreviaturaEstadoapertura) {
		this.abreviaturaEstadoapertura = abreviaturaEstadoapertura;
	}

	public String getAbreviaturaEstadomovimiento() {
		return this.abreviaturaEstadomovimiento;
	}

	public void setAbreviaturaEstadomovimiento(
			String abreviaturaEstadomovimiento) {
		this.abreviaturaEstadomovimiento = abreviaturaEstadomovimiento;
	}

	public String getCodigoagencia() {
		return this.codigoagencia;
	}

	public void setCodigoagencia(String codigoagencia) {
		this.codigoagencia = codigoagencia;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDenominacionCaja() {
		return this.denominacionCaja;
	}

	public void setDenominacionCaja(String denominacionCaja) {
		this.denominacionCaja = denominacionCaja;
	}

	public String getDenominacionEstadoapertura() {
		return this.denominacionEstadoapertura;
	}

	public void setDenominacionEstadoapertura(String denominacionEstadoapertura) {
		this.denominacionEstadoapertura = denominacionEstadoapertura;
	}

	public String getDenominacionEstadomovimiento() {
		return this.denominacionEstadomovimiento;
	}

	public void setDenominacionEstadomovimiento(
			String denominacionEstadomovimiento) {
		this.denominacionEstadomovimiento = denominacionEstadomovimiento;
	}

	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	public Integer getIdEstadoapertura() {
		return this.idEstadoapertura;
	}

	public void setIdEstadoapertura(Integer idEstadoapertura) {
		this.idEstadoapertura = idEstadoapertura;
	}

	public Integer getIdEstadomovimiento() {
		return this.idEstadomovimiento;
	}

	public void setIdEstadomovimiento(Integer idEstadomovimiento) {
		this.idEstadomovimiento = idEstadomovimiento;
	}

	public Integer getIdagencia() {
		return this.idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

}