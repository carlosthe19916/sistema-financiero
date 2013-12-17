package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
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

import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.maestro.Tipomoneda;

@Entity
@Table(name = "cuentaaporte", schema = "cuentapersonal")
@NamedQueries({
		@NamedQuery(name = "Cuentaaporte.findAll", query = "SELECT c FROM Cuentaaporte c"),
		@NamedQuery(name = Cuentaaporte.BENEFICIARIOS, query = "select be from Beneficiariocuenta be where be.estado = true and be.idcuentaaporte = :idCuentaAporte"),
		@NamedQuery(name = Cuentaaporte.BAJA_BENEFICIARIO, query = "update Beneficiariocuenta B set B.estado = false where B.idcuentaaporte = :parametro"),
		@NamedQuery(name = Cuentaaporte.ACCIONISTAS, query = "select ac from Accionista ac where ac.personajuridica.ruc = :ruc") })
public class Cuentaaporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String BENEFICIARIOS = "org.ventura.model.Cuentaaporte.BENEFICIARIOS";
	public final static String ACCIONISTAS = "org.ventura.model.Cuentaaporte.ACCIONISTAS";
	public final static String BAJA_BENEFICIARIO = "org.ventura.model.Cuentaaporte.BAJA_BENEFICIARIO";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentaaporte;

	@Column(unique = true, length = 14)
	private String numerocuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private Integer idestadocuenta;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldo")) })
	private Moneda saldo;

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

	public void setSaldo(Moneda saldo) {
		this.saldo = saldo;
	}

}
