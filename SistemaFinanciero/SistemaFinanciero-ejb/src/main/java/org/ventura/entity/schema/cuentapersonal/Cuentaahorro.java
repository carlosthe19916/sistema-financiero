package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cuentaahorro database table.
 * 
 */
@Entity
@Table(name = "cuentaahorro", schema = "cuentapersonal")
@NamedNativeQuery(name = Cuentaahorro.CUENTAS, query = "select f.numerocuentaahorro, f.dni, f.fechaapertura, f.idtipomoneda, f.ruc, f.saldo, f.idestadocuenta from cuentapersonal.f_retornar_cuentas(:dni) f", resultClass = Cuentaahorro.class)
@NamedQuery(name = "Cuentaahorro.findAll", query = "SELECT c FROM Cuentaahorro c")
public class Cuentaahorro implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String CUENTAS = "org.ventura.model.Cuentaahorro.CUENTAS";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentaahorro;
	
	@Column(length = 14)
	private String numerocuentaahorro;
	
	@Column
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
	
	@ManyToOne
	@JoinColumn(name = "idsocio", insertable = false, updatable = false)
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
		if (estadocuenta != null) {
			this.idestadocuenta = estadocuenta.getIdestadocuenta();
		} else {
			this.idestadocuenta = null;
		}
	}

	public List<Cuentaahorrohistorial> getCuentaahorrohistorials() {
		return this.cuentaahorrohistorials;
	}

	public void setCuentaahorrohistorials(List<Cuentaahorrohistorial> cuentaahorrohistorials) {
		this.cuentaahorrohistorials = cuentaahorrohistorials;
	}

	public Cuentaahorrohistorial addCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		if(cuentaahorrohistorials != null) {
			cuentaahorrohistorials.add(cuentaahorrohistorial);
		} else {
			cuentaahorrohistorials = new ArrayList<Cuentaahorrohistorial>();
			cuentaahorrohistorials.add(cuentaahorrohistorial);
		}	
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

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
		if(socio != null){
			this.idsocio = socio.getIdsocio();
		} else {
			this.idsocio = null;
		}
	}

	
	public Integer getIdcuentaahorro() {
		return idcuentaahorro;
	}

	public void setIdcuentaahorro(Integer idcuentaahorro) {
		this.idcuentaahorro = idcuentaahorro;
	}

	

	public String getNumerocuentaahorro() {
		return numerocuentaahorro;
	}

	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
	}
}
