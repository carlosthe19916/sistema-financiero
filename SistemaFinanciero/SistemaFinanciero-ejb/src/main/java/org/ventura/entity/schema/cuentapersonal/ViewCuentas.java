package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import java.util.Date;

/**
 * The persistent class for the FunctionCuentas database table.
 * 
 */
//@Entity
//@Table(name = "view_retornar_cuentas", schema = "cuentapersonal")
// @NamedNativeQuery(name = ViewCuentas.CUENTAS, query =
// "select f.numerocuenta, f.idsocio, f.fechaapertura, f.idtipomoneda, f.saldo, f.idestadocuenta, f.idcuenta, f.idagencia, f.tipocuenta from cuentapersonal.f_retornar_cuentas(:codigoSocio) f",
// resultClass = ViewCuentas.class)
@NamedQueries({ @NamedQuery(name = ViewCuentas.CUENTAS, query = "select c from ViewCuentas c where c.idsocio = :codigoSocio") })
public class ViewCuentas implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String CUENTAS = "org.ventura.model.view_retornar_cuentas.CUENTAS";

	@Id
	@Column(unique = true, length = 14)
	private String numerocuentaahorro;

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
	private Integer idcuentaahorro;

	@Column
	private String tipocuenta;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	public ViewCuentas() {
	}

	public String getNumerocuentaahorro() {
		return numerocuentaahorro;
	}

	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Date getFechaapertura() {
		return fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Integer getIdtipomoneda() {
		return idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public Integer getIdcuentaahorro() {
		return idcuentaahorro;
	}

	public void setIdcuentaahorro(Integer idcuentaahorro) {
		this.idcuentaahorro = idcuentaahorro;
	}

	public String getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(String tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	// getters and setters bi-directional many-to-one association
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
		if (tipomoneda != null) {
			this.idtipomoneda = tipomoneda.getIdtipomoneda();
		} else {
			this.idtipomoneda = null;
		}
	}

}
