package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cuentaahorro database table.
 * 
 */
@Entity
@Table(name = "cuentaahorro", schema = "cuentapersonal")
@NamedQuery(name = "Cuentaahorro.findAll", query = "SELECT c FROM Cuentaahorro c")
public class Cuentaahorro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 14)
	private String numerocuentaahorro;

	@Transient
	private Class tipoPersonaCliente;

	@Column(length = 8)
	private String dni;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@Column(length = 11)
	private String ruc;

	@Column(nullable = false)
	private double saldo;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanaturalcliente personanaturalcliente;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "ruc", insertable = false, updatable = false)
	private Personajuridicacliente personajuridicacliente;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	// bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy = "cuentaahorro", cascade = CascadeType.ALL)
	private List<Beneficiariocuenta> beneficiariocuentas;

	// bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy = "cuentaahorro", cascade = CascadeType.ALL)
	private List<Titularcuenta> titularcuentas;

	// bi-directional many-to-one association to Cuentaahorrohistorial
	@OneToMany(mappedBy = "cuentaahorro", cascade = CascadeType.ALL)
	private List<Cuentaahorrohistorial> cuentaahorrohistorials;

	// bi-directional many-to-one association to
	// Tarjetadebitoasignadocuentaahorro
	@OneToMany(mappedBy = "cuentaahorro")
	private List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros;

	public Cuentaahorro() {
	}

	public String getNumerocuentaahorro() {
		return this.numerocuentaahorro;
	}

	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public List<Cuentaahorrohistorial> getCuentaahorrohistorials() {
		return this.cuentaahorrohistorials;
	}

	public void setCuentaahorrohistorials(List<Cuentaahorrohistorial> cuentaahorrohistorials) {
		this.cuentaahorrohistorials = cuentaahorrohistorials;
	}

	public Cuentaahorrohistorial addCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		getCuentaahorrohistorials().add(cuentaahorrohistorial);

		return cuentaahorrohistorial;
	}

	public Cuentaahorrohistorial removeCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		getCuentaahorrohistorials().remove(cuentaahorrohistorial);
	
		return cuentaahorrohistorial;
	}

	public List<Tarjetadebitoasignadocuentaahorro> getTarjetadebitoasignadocuentaahorros() {
		return this.tarjetadebitoasignadocuentaahorros;
	}

	public void setTarjetadebitoasignadocuentaahorros(List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros) {
		this.tarjetadebitoasignadocuentaahorros = tarjetadebitoasignadocuentaahorros;
	}

	public Tarjetadebitoasignadocuentaahorro addTarjetadebitoasignadocuentaahorro(Tarjetadebitoasignadocuentaahorro tarjetadebitoasignadocuentaahorro) {
		getTarjetadebitoasignadocuentaahorros().add(tarjetadebitoasignadocuentaahorro);
		
		return tarjetadebitoasignadocuentaahorro;
	}

	public Tarjetadebitoasignadocuentaahorro removeTarjetadebitoasignadocuentaahorro(Tarjetadebitoasignadocuentaahorro tarjetadebitoasignadocuentaahorro) {
		getTarjetadebitoasignadocuentaahorros().remove(tarjetadebitoasignadocuentaahorro);
		
		return tarjetadebitoasignadocuentaahorro;
	}

	public List<Titularcuenta> getTitularcuentas() {
		return this.titularcuentas;
	}

	public void setTitularcuentas(List<Titularcuenta> titularcuentas) {
		this.titularcuentas = titularcuentas;
	}

	public Titularcuenta addTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().add(titularcuenta);
		
		return titularcuenta;
	}

	public Titularcuenta removeTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().remove(titularcuenta);
		
		return titularcuenta;
	}

	public Personanaturalcliente getPersonanaturalcliente() {
		return personanaturalcliente;
	}

	public void setPersonanaturalcliente(Personanaturalcliente personanaturalcliente) {
		this.personanaturalcliente = personanaturalcliente;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
		this.idtipomoneda = tipomoneda.getIdtipomoneda();
	}

	public Personajuridicacliente getPersonajuridicacliente() {
		return personajuridicacliente;
	}

	public void setPersonajuridicacliente(Personajuridicacliente personajuridicacliente) {
		this.personajuridicacliente = personajuridicacliente;
	}

	public Class getTipoPersonaCliente() {
		return tipoPersonaCliente;
	}

	public void setTipoPersonaCliente(Class tipoPersonaCliente) {
		this.tipoPersonaCliente = tipoPersonaCliente;
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

}