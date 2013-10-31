package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Transient;

import org.apache.openjpa.persistence.FetchAttribute;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.sucursal.Agencia;

@Entity
@Table(name = "cuentaaporte", schema = "cuentapersonal")
@NamedQueries({
				@NamedQuery(name = "Cuentaaporte.findAll", query = "SELECT c FROM Cuentaaporte c"),
				@NamedQuery(name = Cuentaaporte.BENEFICIARIOS, query = "select be from Beneficiariocuenta be where be.idcuentaaporte = :idCuentaAporte")})
public class Cuentaaporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String BENEFICIARIOS = "org.ventura.model.Cuentaaporte.BENEFICIARIOS";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentaaporte;

	@Column(length = 14)
	private String numerocuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@Column(nullable = false)
	private double saldo;


	
	@Transient
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
	private List<Beneficiariocuenta> beneficiarios;
	
	public Cuentaaporte() {
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

	public Integer getIdestadocuenta() {
		return idestadocuenta;
	}

	public void setIdestadocuenta(Integer idestadocuenta) {
		this.idestadocuenta = idestadocuenta;
	}

	

	public Integer getIdcuentaaporte() {
		return idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}

	

	public String getNumerocuentaaporte() {
		return numerocuentaaporte;
	}

	public void setNumerocuentaaporte(String numerocuentaaporte) {
		this.numerocuentaaporte = numerocuentaaporte;
	}

	public List<Beneficiariocuenta> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiariocuenta> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}
