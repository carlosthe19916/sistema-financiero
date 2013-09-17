package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cuentacorriente database table.
 * 
 */
@Entity
@Table(name="cuentacorriente",schema="cuentapersonal")
@NamedQuery(name="Cuentacorriente.findAll", query="SELECT c FROM Cuentacorriente c")
public class Cuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=14)
	private String numerocuentacorriente;

	@Column(length=8)
	private String dni;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaapertura;

	@Column(nullable=false)
	private Integer idtipomoneda;

	@Column(length=11)
	private String ruc;

	//bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy="cuentacorriente")
	private List<Beneficiariocuenta> beneficiariocuentas;

	//bi-directional many-to-one association to Chequera
	@OneToMany(mappedBy="cuentacorriente")
	private List<Chequera> chequeras;

	//bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name="idestadocuenta", nullable=false)
	private Estadocuenta estadocuenta;

	//bi-directional many-to-one association to Cuentacorrientehistorial
	@OneToMany(mappedBy="cuentacorriente")
	private List<Cuentacorrientehistorial> cuentacorrientehistorials;

	//bi-directional many-to-one association to Tarjetadebitoasignadocuentacorriente
	@OneToMany(mappedBy="cuentacorriente")
	private List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes;

	//bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy="cuentacorriente")
	private List<Titularcuenta> titularcuentas;

	public Cuentacorriente() {
	}

	public String getNumerocuentacorriente() {
		return this.numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
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

	public List<Beneficiariocuenta> getBeneficiariocuentas() {
		return this.beneficiariocuentas;
	}

	public void setBeneficiariocuentas(List<Beneficiariocuenta> beneficiariocuentas) {
		this.beneficiariocuentas = beneficiariocuentas;
	}

	public Beneficiariocuenta addBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().add(beneficiariocuenta);
		beneficiariocuenta.setCuentacorriente(this);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().remove(beneficiariocuenta);
		beneficiariocuenta.setCuentacorriente(null);

		return beneficiariocuenta;
	}

	public List<Chequera> getChequeras() {
		return this.chequeras;
	}

	public void setChequeras(List<Chequera> chequeras) {
		this.chequeras = chequeras;
	}

	public Chequera addChequera(Chequera chequera) {
		getChequeras().add(chequera);
		chequera.setCuentacorriente(this);

		return chequera;
	}

	public Chequera removeChequera(Chequera chequera) {
		getChequeras().remove(chequera);
		chequera.setCuentacorriente(null);

		return chequera;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
	}

	public List<Cuentacorrientehistorial> getCuentacorrientehistorials() {
		return this.cuentacorrientehistorials;
	}

	public void setCuentacorrientehistorials(List<Cuentacorrientehistorial> cuentacorrientehistorials) {
		this.cuentacorrientehistorials = cuentacorrientehistorials;
	}

	public Cuentacorrientehistorial addCuentacorrientehistorial(Cuentacorrientehistorial cuentacorrientehistorial) {
		getCuentacorrientehistorials().add(cuentacorrientehistorial);
		cuentacorrientehistorial.setCuentacorriente(this);

		return cuentacorrientehistorial;
	}

	public Cuentacorrientehistorial removeCuentacorrientehistorial(Cuentacorrientehistorial cuentacorrientehistorial) {
		getCuentacorrientehistorials().remove(cuentacorrientehistorial);
		cuentacorrientehistorial.setCuentacorriente(null);

		return cuentacorrientehistorial;
	}

	public List<Tarjetadebitoasignadocuentacorriente> getTarjetadebitoasignadocuentacorrientes() {
		return this.tarjetadebitoasignadocuentacorrientes;
	}

	public void setTarjetadebitoasignadocuentacorrientes(List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes) {
		this.tarjetadebitoasignadocuentacorrientes = tarjetadebitoasignadocuentacorrientes;
	}

	public Tarjetadebitoasignadocuentacorriente addTarjetadebitoasignadocuentacorriente(Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().add(tarjetadebitoasignadocuentacorriente);
		tarjetadebitoasignadocuentacorriente.setCuentacorriente(this);

		return tarjetadebitoasignadocuentacorriente;
	}

	public Tarjetadebitoasignadocuentacorriente removeTarjetadebitoasignadocuentacorriente(Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().remove(tarjetadebitoasignadocuentacorriente);
		tarjetadebitoasignadocuentacorriente.setCuentacorriente(null);

		return tarjetadebitoasignadocuentacorriente;
	}

	public List<Titularcuenta> getTitularcuentas() {
		return this.titularcuentas;
	}

	public void setTitularcuentas(List<Titularcuenta> titularcuentas) {
		this.titularcuentas = titularcuentas;
	}

	public Titularcuenta addTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().add(titularcuenta);
		titularcuenta.setCuentacorriente(this);

		return titularcuenta;
	}

	public Titularcuenta removeTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().remove(titularcuenta);
		titularcuenta.setCuentacorriente(null);

		return titularcuenta;
	}

}