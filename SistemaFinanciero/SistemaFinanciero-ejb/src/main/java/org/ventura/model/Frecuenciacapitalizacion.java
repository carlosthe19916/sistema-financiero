package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the frecuenciacapitalizacion database table.
 * 
 */
@Entity
@Table(name="frecuenciacapitalizacion",schema="cuentapersonal")
@NamedQuery(name="Frecuenciacapitalizacion.findAll", query="SELECT f FROM Frecuenciacapitalizacion f")
public class Frecuenciacapitalizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer idfrecuenciacapitalizacion;

	@Column(nullable=false, length=30)
	private String denomicacion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false)
	private Integer numerodias;

	//bi-directional many-to-one association to Cuentaplazofijo
	@OneToMany(mappedBy="frecuenciacapitalizacion")
	private List<Cuentaplazofijo> cuentaplazofijos;

	public Frecuenciacapitalizacion() {
	}

	public Integer getIdfrecuenciacapitalizacion() {
		return this.idfrecuenciacapitalizacion;
	}

	public void setIdfrecuenciacapitalizacion(Integer idfrecuenciacapitalizacion) {
		this.idfrecuenciacapitalizacion = idfrecuenciacapitalizacion;
	}

	public String getDenomicacion() {
		return this.denomicacion;
	}

	public void setDenomicacion(String denomicacion) {
		this.denomicacion = denomicacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getNumerodias() {
		return this.numerodias;
	}

	public void setNumerodias(Integer numerodias) {
		this.numerodias = numerodias;
	}

	public List<Cuentaplazofijo> getCuentaplazofijos() {
		return this.cuentaplazofijos;
	}

	public void setCuentaplazofijos(List<Cuentaplazofijo> cuentaplazofijos) {
		this.cuentaplazofijos = cuentaplazofijos;
	}

	public Cuentaplazofijo addCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().add(cuentaplazofijo);
		cuentaplazofijo.setFrecuenciacapitalizacion(this);

		return cuentaplazofijo;
	}

	public Cuentaplazofijo removeCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().remove(cuentaplazofijo);
		cuentaplazofijo.setFrecuenciacapitalizacion(null);

		return cuentaplazofijo;
	}

}