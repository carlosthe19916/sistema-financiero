package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cuentacorriente database table.
 * 
 */
@Entity
@Table(name = "cuentacorriente", schema = "cuentapersonal")
@NamedQuery(name = "Cuentacorriente.findAll", query = "SELECT c FROM Cuentacorriente c")
public class Cuentacorriente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentacorriente;

	@Column
	private Integer idsocio;

	@Column(unique = true, length = 14)
	private String numerocuentacorriente;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private double saldo;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@ManyToOne
	@JoinColumn(name = "idsocio", insertable = false, updatable = false)
	private Socio socio;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	// bi-directional many-to-one association to Beneficiariocuenta
	@OneToMany(mappedBy = "cuentacorriente", cascade = CascadeType.ALL)
	private List<Beneficiariocuenta> beneficiariocuentas;

	// bi-directional many-to-one association to Titularcuenta
	@OneToMany(mappedBy = "cuentacorriente", cascade = CascadeType.ALL)
	private List<Titularcuenta> titularcuentas;

	// bi-directional many-to-one association to Cuentacorrientehistorial
	@OneToMany(mappedBy = "cuentacorriente", cascade = CascadeType.ALL)
	private List<Cuentacorrientehistorial> cuentacorrientehistorials;

	// bi-directional many-to-one association to Chequera
	@OneToMany(mappedBy = "cuentacorriente")
	private List<Chequera> chequeras;

	// bi-directional many-to-one association to Estadocuenta
	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false, insertable = false, updatable = false)
	private Estadocuenta estadocuenta;

	// bi-directional many-to-one association to
	// Tarjetadebitoasignadocuentacorriente
	@OneToMany(mappedBy = "cuentacorriente")
	private List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes;

	public Cuentacorriente() {
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

	public List<Beneficiariocuenta> getBeneficiariocuentas() {
		return this.beneficiariocuentas;
	}

	public void setBeneficiariocuentas(
			List<Beneficiariocuenta> beneficiariocuentas) {
		this.beneficiariocuentas = beneficiariocuentas;
	}

	public Beneficiariocuenta addBeneficiariocuenta(
			Beneficiariocuenta beneficiariocuenta) {
		getBeneficiariocuentas().add(beneficiariocuenta);
		beneficiariocuenta.setCuentacorriente(this);

		return beneficiariocuenta;
	}

	public Beneficiariocuenta removeBeneficiariocuenta(
			Beneficiariocuenta beneficiariocuenta) {
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
		if (this.estadocuenta != null) {
			this.idestadocuenta = estadocuenta.getIdestadocuenta();
		} else {
			this.idestadocuenta = null;
		}
	}

	public List<Cuentacorrientehistorial> getCuentacorrientehistorials() {
		return this.cuentacorrientehistorials;
	}

	public void setCuentacorrientehistorials(
			List<Cuentacorrientehistorial> cuentacorrientehistorials) {
		this.cuentacorrientehistorials = cuentacorrientehistorials;
	}

	public Cuentacorrientehistorial addCuentacorrientehistorial(
			Cuentacorrientehistorial cuentacorrientehistorial) {
		if (this.cuentacorrientehistorials != null) {
			this.cuentacorrientehistorials.add(cuentacorrientehistorial);
		} else {
			this.cuentacorrientehistorials = new ArrayList<Cuentacorrientehistorial>();
			this.cuentacorrientehistorials.add(cuentacorrientehistorial);
		}
		return cuentacorrientehistorial;
	}

	public Cuentacorrientehistorial removeCuentacorrientehistorial(
			Cuentacorrientehistorial cuentacorrientehistorial) {
		getCuentacorrientehistorials().remove(cuentacorrientehistorial);
		cuentacorrientehistorial.setCuentacorriente(null);

		return cuentacorrientehistorial;
	}

	public List<Tarjetadebitoasignadocuentacorriente> getTarjetadebitoasignadocuentacorrientes() {
		return this.tarjetadebitoasignadocuentacorrientes;
	}

	public void setTarjetadebitoasignadocuentacorrientes(
			List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes) {
		this.tarjetadebitoasignadocuentacorrientes = tarjetadebitoasignadocuentacorrientes;
	}

	public Tarjetadebitoasignadocuentacorriente addTarjetadebitoasignadocuentacorriente(
			Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().add(
				tarjetadebitoasignadocuentacorriente);
		tarjetadebitoasignadocuentacorriente.setCuentacorriente(this);

		return tarjetadebitoasignadocuentacorriente;
	}

	public Tarjetadebitoasignadocuentacorriente removeTarjetadebitoasignadocuentacorriente(
			Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().remove(
				tarjetadebitoasignadocuentacorriente);
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

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
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

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
		if (socio != null) {
			this.idsocio = socio.getIdsocio();
		} else {
			this.idsocio = null;
		}
	}

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	public Integer getIdcuentacorriente() {
		return idcuentacorriente;
	}

	public void setIdcuentacorriente(Integer idcuentacorriente) {
		this.idcuentacorriente = idcuentacorriente;
	}

	public String getNumerocuentacorriente() {
		return numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}

}