package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the FunctionCuentas database table.
 * 
 */

@NamedNativeQuery(name = FunctionCuentas.CUENTAS, query = "select f from cuentapersonal.f_retornar_cuentas(:codigoSocio) f", resultClass = FunctionCuentas.class)
@NamedQuery(name = "Cuentaahorro.findAll", query = "SELECT c FROM Cuentaahorro c")
public class FunctionCuentas implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public final static String CUENTAS = "org.ventura.model.FunctionCuentas.CUENTAS";
	
	@Id
	@Column(unique = true, nullable = false)
	private Integer idcuenta;

	@Column(length = 14)
	private String numerocuenta;
	
	@Column(nullable = false)
	private Integer idsocio;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;
	
	@Column(nullable = false)
	private Integer idtipomoneda;
	
	@Column(nullable = false)
	private double saldo;
	
	@Column(nullable = false)
	private Integer idestadocuenta;
	
	@Column(nullable = false)
	private Integer idagencia;
	
	@Column
	private String tipocuenta;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	@ManyToOne
	@JoinColumn(name = "idagencia", nullable = false, insertable = false, updatable = false)
	private Agencia agencia;
	
	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	public FunctionCuentas() {
	}
	
	public Integer getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(Integer idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getNumerocuenta() {
		return numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public String getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(String tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
		if (estadocuenta != null) {
			this.idestadocuenta = estadocuenta.getIdestadocuenta();
		} else {
			this.idestadocuenta = null;
		}
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
		if(tipomoneda != null){
			this.idtipomoneda = tipomoneda.getIdtipomoneda();
		} else {
			this.idtipomoneda = null;
		}		
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer codigosocio) {
		this.idsocio = codigosocio;
	}

	public Integer getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}
