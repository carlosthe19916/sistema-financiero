package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.tipodato.Moneda;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentabancaria;

	@Column(nullable = false)
	private Integer cantidadretirantes;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column
	private Date fechacierre;

	@Column(nullable = false, length = 14)
	private String numerocuenta;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldo")) })
	private Moneda saldo;

	@ManyToOne
	@JoinColumn(name = "idsocio", nullable = false)
	private Socio socio;

	@ManyToOne
	@JoinColumn(name = "idtipocuentabancaria", nullable = false)
	private Tipocuentabancaria tipocuentabancaria;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false)
	private Estadocuenta estadocuenta;

	@OneToMany(mappedBy = "cuentabancaria")
	private List<Titular> titulares;

	@OneToMany(mappedBy = "cuentabancaria")
	private List<Beneficiario> beneficiarios;
	
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

	public Integer getCantidadretirantes() {
		return cantidadretirantes;
	}

	public void setCantidadretirantes(Integer cantidadretirantes) {
		this.cantidadretirantes = cantidadretirantes;
	}

	public List<Titular> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Titular> titulares) {
		this.titulares = titulares;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
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