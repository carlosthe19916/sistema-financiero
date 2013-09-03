package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cuentaahorro database table.
 * 
 */
@Entity
@Table(name="cuentaahorro",schema="cuentapersonal")
@NamedQuery(name="Cuentaahorro.findAll", query="SELECT c FROM Cuentaahorro c")
public class Cuentaahorro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=14)
	private String numerocuentaahorro;

	@Column(length=8)
	private String dni;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaapertura;

	@Column(nullable=false)
	private Integer idtipomoneda;

	@Column(length=11)
	private String ruc;

	@Column(nullable=false)
	private double saldo;

	//bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy="cuentaahorro")
	private List<Beneficiariocuenta> beneficiariocuentas;

	//bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name="idestadocuenta", nullable=false)
	private Estadocuenta estadocuenta;

	//bi-directional many-to-one association to Cuentaahorrohistorial
	@OneToMany(mappedBy="cuentaahorro")
	private List<Cuentaahorrohistorial> cuentaahorrohistorials;

	//bi-directional many-to-one association to Tarjetadebitoasignadocuentaahorro
	@OneToMany(mappedBy="cuentaahorro")
	private List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros;

	//bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy="cuentaahorro")
	private List<Titularcuenta> titularcuentas;

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
		beneficiariocuenta.setCuentaahorro(this);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().remove(beneficiariocuenta);
		beneficiariocuenta.setCuentaahorro(null);

		return beneficiariocuenta;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
	}

	public List<Cuentaahorrohistorial> getCuentaahorrohistorials() {
		return this.cuentaahorrohistorials;
	}

	public void setCuentaahorrohistorials(List<Cuentaahorrohistorial> cuentaahorrohistorials) {
		this.cuentaahorrohistorials = cuentaahorrohistorials;
	}

	public Cuentaahorrohistorial addCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		getCuentaahorrohistorials().add(cuentaahorrohistorial);
		cuentaahorrohistorial.setCuentaahorro(this);

		return cuentaahorrohistorial;
	}

	public Cuentaahorrohistorial removeCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		getCuentaahorrohistorials().remove(cuentaahorrohistorial);
		cuentaahorrohistorial.setCuentaahorro(null);

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
		tarjetadebitoasignadocuentaahorro.setCuentaahorro(this);

		return tarjetadebitoasignadocuentaahorro;
	}

	public Tarjetadebitoasignadocuentaahorro removeTarjetadebitoasignadocuentaahorro(Tarjetadebitoasignadocuentaahorro tarjetadebitoasignadocuentaahorro) {
		getTarjetadebitoasignadocuentaahorros().remove(tarjetadebitoasignadocuentaahorro);
		tarjetadebitoasignadocuentaahorro.setCuentaahorro(null);

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
		titularcuenta.setCuentaahorro(this);

		return titularcuenta;
	}

	public Titularcuenta removeTitularcuenta(Titularcuenta titularcuenta) {
		getTitularcuentas().remove(titularcuenta);
		titularcuenta.setCuentaahorro(null);

		return titularcuenta;
	}

}