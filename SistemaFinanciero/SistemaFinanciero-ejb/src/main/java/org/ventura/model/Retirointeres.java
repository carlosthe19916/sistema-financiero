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
@NamedQuery(name="Retirointere.findAll", query="SELECT r FROM Retirointeres r")
public class Retirointeres implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idretirointeres;

	private String abreviatura;

	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Cuentaplazofijo
	@OneToMany(mappedBy="retirointere")
	private List<Cuentaplazofijo> cuentaplazofijos;

	public Retirointeres() {
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