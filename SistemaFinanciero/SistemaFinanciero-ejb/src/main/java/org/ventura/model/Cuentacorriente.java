package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cuentacorriente database table.
 * 
 */
@Entity
@NamedQuery(name="Cuentacorriente.findAll", query="SELECT c FROM Cuentacorriente c")
public class Cuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String numerocuentacorriente;

	private String dni;

	@Temporal(TemporalType.DATE)
	private Date fechaapertura;

	private Integer idtipomoneda;

	private String ruc;

	//bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy="cuentacorriente")
	private List<Beneficiariocuenta> beneficiariocuentas;

	//bi-directional many-to-one association to Chequera
	@OneToMany(mappedBy="cuentacorriente")
	private List<Chequera> chequeras;

	//bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name="idestadocuenta")
	private Estadocuenta estadocuenta;

	//bi-directional many-to-one association to Cuentacorrientehistorial
	@OneToMany(mappedBy="cuentacorriente")
	private List<Cuentacorrientehistorial> cuentacorrientehistorials;

	//bi-directional many-to-one association to Targetadebitoasignadocuentacorriente
	@OneToMany(mappedBy="cuentacorriente")
	private List<Targetadebitoasignadocuentacorriente> targetadebitoasignadocuentacorrientes;

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

	public List<Targetadebitoasignadocuentacorriente> getTargetadebitoasignadocuentacorrientes() {
		return this.targetadebitoasignadocuentacorrientes;
	}

	public void setTargetadebitoasignadocuentacorrientes(List<Targetadebitoasignadocuentacorriente> targetadebitoasignadocuentacorrientes) {
		this.targetadebitoasignadocuentacorrientes = targetadebitoasignadocuentacorrientes;
	}

	public Targetadebitoasignadocuentacorriente addTargetadebitoasignadocuentacorriente(Targetadebitoasignadocuentacorriente targetadebitoasignadocuentacorriente) {
		getTargetadebitoasignadocuentacorrientes().add(targetadebitoasignadocuentacorriente);
		targetadebitoasignadocuentacorriente.setCuentacorriente(this);

		return targetadebitoasignadocuentacorriente;
	}

	public Targetadebitoasignadocuentacorriente removeTargetadebitoasignadocuentacorriente(Targetadebitoasignadocuentacorriente targetadebitoasignadocuentacorriente) {
		getTargetadebitoasignadocuentacorrientes().remove(targetadebitoasignadocuentacorriente);
		targetadebitoasignadocuentacorriente.setCuentacorriente(null);

		return targetadebitoasignadocuentacorriente;
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