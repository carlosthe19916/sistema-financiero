package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the retirointeres database table.
 * 
 */
@Entity
@Table(name="retirointeres",schema="cuentapersonal")
@NamedQuery(name="Retirointere.findAll", query="SELECT r FROM Retirointere r")
public class Retirointere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idretirointeres;

	@Column(length=5)
	private String abreviatura;

	@Column(nullable=false, length=50)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Cuentaplazofijo
	@OneToMany(mappedBy="retirointere")
	private List<Cuentaplazofijo> cuentaplazofijos;

	public Retirointere() {
	}

	public Integer getIdretirointeres() {
		return this.idretirointeres;
	}

	public void setIdretirointeres(Integer idretirointeres) {
		this.idretirointeres = idretirointeres;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Cuentaplazofijo> getCuentaplazofijos() {
		return this.cuentaplazofijos;
	}

	public void setCuentaplazofijos(List<Cuentaplazofijo> cuentaplazofijos) {
		this.cuentaplazofijos = cuentaplazofijos;
	}

	public Cuentaplazofijo addCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().add(cuentaplazofijo);
		cuentaplazofijo.setRetirointere(this);

		return cuentaplazofijo;
	}

	public Cuentaplazofijo removeCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		getCuentaplazofijos().remove(cuentaplazofijo);
		cuentaplazofijo.setRetirointere(null);

		return cuentaplazofijo;
	}

}