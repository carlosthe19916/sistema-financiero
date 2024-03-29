package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;

@Entity
@Table(name = "cuentaaporte", schema = "cuentapersonal")
@NamedQueries({
		@NamedQuery(name = "Cuentaaporte.findAll", query = "SELECT c FROM Cuentaaporte c"),
		@NamedQuery(name = Cuentaaporte.findByNumerocuenta, query = "SELECT c FROM Cuentaaporte c WHERE c.numerocuentaaporte = :numerocuentaaporte") })
public class Cuentaaporte implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String BENEFICIARIOS = "org.ventura.model.Cuentaaporte.BENEFICIARIOS";
	public final static String ACCIONISTAS = "org.ventura.model.Cuentaaporte.ACCIONISTAS";
	public final static String BAJA_BENEFICIARIO = "org.ventura.model.Cuentaaporte.BAJA_BENEFICIARIO";
	public final static String findByNumerocuenta = "org.ventura.model.Cuentaaporte.findByNumerocuenta";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idcuentaaporte;

	@Column(unique = true, length = 14, nullable = false)
	private String numerocuentaaporte;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaapertura;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechacierre;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "saldo")) })
	private Moneda saldo;

	@ManyToOne
	@JoinColumn(name = "idestadocuenta", nullable = false)
	private Estadocuenta estadocuenta;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	public Cuentaaporte() {
	}

	public Date getFechaapertura() {
		return this.fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public Estadocuenta getEstadocuenta() {
		return this.estadocuenta;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
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

	public void setSaldo(Moneda saldo) {
		this.saldo = saldo;
	}

	public Date getFechacierre() {
		return fechacierre;
	}

	public void setFechacierre(Date fechacierre) {
		this.fechacierre = fechacierre;
	}

	public Moneda getSaldo() {
		return saldo;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Cuentaaporte)) {
			return false;
		}
		final Cuentaaporte other = (Cuentaaporte) obj;
		return other.getIdcuentaaporte().equals(this.idcuentaaporte) ? true : false;
	}

	@Override
	public int hashCode() {
		return idcuentaaporte;
	}

	public void setEstadocuenta(Estadocuenta estadocuenta) {
		this.estadocuenta = estadocuenta;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}
}
