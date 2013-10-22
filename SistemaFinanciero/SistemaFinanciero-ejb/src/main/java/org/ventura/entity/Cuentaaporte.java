package org.ventura.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "cuentaaporte", schema = "cuentapersonal")
@NamedQuery(name = "Cuentaaporte.findAll", query = "SELECT c FROM Cuentaaporte c")
public class Cuentaaporte implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique = true, nullable = false, length = 14)
	private String numerocuentaaporte;

	@Column(length = 8)
	private String codigosocio;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@Column(nullable = false)
	private double saldo;

	@ManyToOne
	@JoinColumn(name = "codigosocio", insertable = false, updatable = false)
	private Socio socio;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	// bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy = "cuentaaporte", cascade = CascadeType.ALL)
	private List<Beneficiariocuenta> beneficiariocuentas;

	// bi-directional many-to-one association to Cuentaahorrohistorial
	@OneToMany(mappedBy = "cuentaaporte", cascade = CascadeType.ALL)
	private List<Aporte> aporte;
	
	public Cuentaaporte() {
	}

	public String getNumerocuentaaporte() {
		return this.numerocuentaaporte;
	}

	public void setNumerocuentaaporte(String numerocuentaaporte) {
		this.numerocuentaaporte = numerocuentaaporte;
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

	public List<Beneficiariocuenta> getBeneficiariocuentas() {
		return this.beneficiariocuentas;
	}

	public void setBeneficiariocuentas(List<Beneficiariocuenta> beneficiariocuentas) {
		this.beneficiariocuentas = beneficiariocuentas;
	}

	public Beneficiariocuenta addBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().add(beneficiariocuenta);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().remove(beneficiariocuenta);

		return beneficiariocuenta;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
		this.idestadocuenta = estadocuenta.getIdestadocuenta();
	}
	
	public Aporte addAporte(Aporte aporte) {
		getAporte().add(aporte);

		return aporte;
	}

	public Aporte removeAporte(Aporte aporte) {
		getAporte().remove(aporte);
	
		return aporte;
	}
	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
		this.idtipomoneda = tipomoneda.getIdtipomoneda();
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public List<Aporte> getAporte() {
		return aporte;
	}

	public void setAporte(List<Aporte> aporte) {
		this.aporte = aporte;
	}

	public String getCodigosocio() {
		return codigosocio;
	}

	public void setCodigosocio(String codigosocio) {
		this.codigosocio = codigosocio;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}