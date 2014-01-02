package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;

/**
 * The persistent class for the cuentabancaria database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria", schema = "cuentapersonal")
@NamedQuery(name = "Cuentabancaria.findAll", query = "SELECT c FROM Cuentabancaria c")
@NamedQueries({ @NamedQuery(name = Cuentabancaria.findByNumerocuenta, query = "SELECT c FROM Cuentabancaria c WHERE c.numerocuenta = :numerocuenta") })
public class Cuentabancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByNumerocuenta = "org.ventura.entity.schema.cuentapersonal.Cuentabancaria.findByNumerocuenta";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idcuentabancaria;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column
	private Date fechacierre;

	@Column(nullable = false)
	private Integer idsocio;

	@Column(nullable = false, length = 14)
	private String numerocuenta;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldo")) })
	private Moneda saldo;

	@ManyToOne
	@JoinColumn(name = "idtipocuentabancaria", nullable = false)
	private Tipocuentabancaria tipocuentabancaria;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false)
	private Estadocuenta estadocuenta;

	public Cuentabancaria() {
	}

	public Integer getIdcuentabancaria() {
		return idcuentabancaria;
	}

	public void setIdcuentabancaria(Integer idcuentabancaria) {
		this.idcuentabancaria = idcuentabancaria;
	}

	public Date getFechaapertura() {
		return fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Date getFechacierre() {
		return fechacierre;
	}

	public void setFechacierre(Date fechacierre) {
		this.fechacierre = fechacierre;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public String getNumerocuenta() {
		return numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public Moneda getSaldo() {
		return saldo;
	}

	public void setSaldo(Moneda saldo) {
		this.saldo = saldo;
	}

	public Tipocuentabancaria getTipocuentabancaria() {
		return tipocuentabancaria;
	}

	public void setTipocuentabancaria(Tipocuentabancaria tipocuentabancaria) {
		this.tipocuentabancaria = tipocuentabancaria;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public Estadocuenta getEstadocuenta() {
		return estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Cuentabancaria)) {
			return false;
		}
		final Cuentabancaria other = (Cuentabancaria) obj;
		return other.getIdcuentabancaria() == this.idcuentabancaria ? true
				: false;
	}

	@Override
	public int hashCode() {
		return idcuentabancaria;
	}
}