package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cuentabancaria_view database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria_view", schema = "caja")
@NamedQuery(name = "CuentabancariaView.findAll", query = "SELECT c FROM CuentabancariaView c")
public class CuentabancariaView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "numero_cuenta", length = 14)
	private String numeroCuenta;

	@Column(name = "denominacion_estado_cuenta", length = 20)
	private String denominacionEstadoCuenta;

	@Column(name = "denominacion_moneda", length = 35)
	private String denominacionMoneda;

	@Column(name = "denominacion_tipo_cuenta_bancaria", length = 100)
	private String denominacionTipoCuentaBancaria;

	@Column(name = "estado_estado_cuenta")
	private Boolean estadoEstadoCuenta;

	@Column(name = "estado_moneda")
	private Boolean estadoMoneda;

	@Column(name = "estado_tipo_cuenta_bancaria")
	private Boolean estadoTipoCuentaBancaria;

	@Column(name = "id_estado_cuenta")
	private Integer idEstadoCuenta;

	@Column(name = "id_tipo_cuenta_bancaria")
	private Integer idTipoCuentaBancaria;

	@Column(name = "id_tipo_moneda")
	private Integer idTipoMoneda;

	public CuentabancariaView() {
	}

	public String getDenominacionEstadoCuenta() {
		return this.denominacionEstadoCuenta;
	}

	public void setDenominacionEstadoCuenta(String denominacionEstadoCuenta) {
		this.denominacionEstadoCuenta = denominacionEstadoCuenta;
	}

	public String getDenominacionMoneda() {
		return this.denominacionMoneda;
	}

	public void setDenominacionMoneda(String denominacionMoneda) {
		this.denominacionMoneda = denominacionMoneda;
	}

	public String getDenominacionTipoCuentaBancaria() {
		return this.denominacionTipoCuentaBancaria;
	}

	public void setDenominacionTipoCuentaBancaria(
			String denominacionTipoCuentaBancaria) {
		this.denominacionTipoCuentaBancaria = denominacionTipoCuentaBancaria;
	}

	public Boolean getEstadoEstadoCuenta() {
		return this.estadoEstadoCuenta;
	}

	public void setEstadoEstadoCuenta(Boolean estadoEstadoCuenta) {
		this.estadoEstadoCuenta = estadoEstadoCuenta;
	}

	public Boolean getEstadoMoneda() {
		return this.estadoMoneda;
	}

	public void setEstadoMoneda(Boolean estadoMoneda) {
		this.estadoMoneda = estadoMoneda;
	}

	public Boolean getEstadoTipoCuentaBancaria() {
		return this.estadoTipoCuentaBancaria;
	}

	public void setEstadoTipoCuentaBancaria(Boolean estadoTipoCuentaBancaria) {
		this.estadoTipoCuentaBancaria = estadoTipoCuentaBancaria;
	}

	public Integer getIdEstadoCuenta() {
		return this.idEstadoCuenta;
	}

	public void setIdEstadoCuenta(Integer idEstadoCuenta) {
		this.idEstadoCuenta = idEstadoCuenta;
	}

	public Integer getIdTipoCuentaBancaria() {
		return this.idTipoCuentaBancaria;
	}

	public void setIdTipoCuentaBancaria(Integer idTipoCuentaBancaria) {
		this.idTipoCuentaBancaria = idTipoCuentaBancaria;
	}

	public Integer getIdTipoMoneda() {
		return this.idTipoMoneda;
	}

	public void setIdTipoMoneda(Integer idTipoMoneda) {
		this.idTipoMoneda = idTipoMoneda;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

}