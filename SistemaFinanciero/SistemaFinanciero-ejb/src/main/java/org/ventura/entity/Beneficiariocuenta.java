package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.BeneficiariocuentaListener;

/**
 * The persistent class for the beneficiariocuenta database table.
 * 
 */
@Entity
@Table(name = "beneficiariocuenta", schema = "cuentapersonal")
@EntityListeners({ BeneficiariocuentaListener.class })
@NamedQuery(name = "Beneficiariocuenta.findAll", query = "SELECT b FROM Beneficiariocuenta b")
public class Beneficiariocuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idbeneficiariocuenta;

	@Column(length = 8)
	private String dni;

	@Column(nullable = false, length = 50)
	private String apellidopaterno;

	@Column(nullable = false, length = 50)
	private String apellidomaterno;

	@Column(nullable = false, length = 60)
	private String nombres;

	@Column(nullable = false)
	private Double porcentajebeneficio;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idcuentaaporte;

	@Column(nullable = false)
	private Integer idcuentaahorro;

	@Column(nullable = false)
	private Integer idcuentacorriente;

	@Column(nullable = false)
	private Integer idcuentaplazofijo;

	// bi-directional many-to-one association to Cuentaaporte
	@ManyToOne
	@JoinColumn(name = "idcuentaaporte", insertable = false, updatable = false)
	private Cuentaaporte cuentaaporte;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "idcuentaahorro", insertable = false, updatable = false)
	private Cuentaahorro cuentaahorro;

	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "idcuentacorriente", insertable = false, updatable = false)
	private Cuentacorriente cuentacorriente;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "idcuentaplazofijo", insertable = false, updatable = false)
	private Cuentaplazofijo cuentaplazofijo;

	public Beneficiariocuenta() {
	}

	public Integer getIdbeneficiariocuenta() {
		return this.idbeneficiariocuenta;
	}

	public void setIdbeneficiariocuenta(Integer idbeneficiariocuenta) {
		this.idbeneficiariocuenta = idbeneficiariocuenta;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Double getPorcentajebeneficio() {
		return this.porcentajebeneficio;
	}

	public void setPorcentajebeneficio(Double porcentajebeneficio) {
		this.porcentajebeneficio = porcentajebeneficio;
	}

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
		if (cuentaahorro != null) {
			this.idcuentaahorro = cuentaahorro.getIdcuentaahorro();
		} else {
			this.idcuentaahorro = null;
		}
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
		if (cuentacorriente != null) {
			this.idcuentacorriente = cuentacorriente.getIdcuentacorriente();
		} else {
			this.idcuentacorriente = null;
		}
	}

	public Cuentaplazofijo getCuentaplazofijo() {
		return this.cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
		if (cuentaplazofijo != null) {
			this.idcuentaplazofijo = cuentaplazofijo.getIdcuentaplazofijo();
		} else {
			this.idcuentaplazofijo = null;
		}
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
		if (cuentaaporte != null) {
			this.idcuentaaporte = cuentaaporte.getIdcuentaaporte();
		} else {
			this.idcuentaaporte = null;
		}
	}
	
	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public Integer getIdcuentaaporte() {
		return idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}

	public Integer getIdcuentaahorro() {
		return idcuentaahorro;
	}

	public void setIdcuentaahorro(Integer idcuentaahorro) {
		this.idcuentaahorro = idcuentaahorro;
	}

	public Integer getIdcuentacorriente() {
		return idcuentacorriente;
	}

	public void setIdcuentacorriente(Integer idcuentacorriente) {
		this.idcuentacorriente = idcuentacorriente;
	}

	public Integer getIdcuentaplazofijo() {
		return idcuentaplazofijo;
	}

	public void setIdcuentaplazofijo(Integer idcuentaplazofijo) {
		this.idcuentaplazofijo = idcuentaplazofijo;
	}
	
	public String getNombreCompleto() {
		String apellidoPaterno = getApellidopaterno();
		String apellidoMaterno = getApellidomaterno();
		String nombres = getNombres();

		if (this.getApellidopaterno() == null) {
			apellidoPaterno = "";
		}
		if (this.getApellidomaterno() == null) {
			apellidoMaterno = "";
		}
		if (this.getNombres() == null) {
			nombres = "";
		}

		return apellidoPaterno + " " + apellidoMaterno + " " + nombres;
	}

	
}