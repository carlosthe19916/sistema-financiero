package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadotarjeta database table.
 * 
 */
@Entity
@Table(name="estadotarjeta",schema="cuentapersonal")
@NamedQuery(name="Estadotarjeta.findAll", query="SELECT e FROM Estadotarjeta e")
public class Estadotarjeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idestadotargeta;

	@Column(length=50)
	private String abreviatura;

	@Column(nullable=false, length=50)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Tarjetadebito
	@OneToMany(mappedBy="estadotarjeta")
	private List<Tarjetadebito> tarjetadebitos;

	public Estadotarjeta() {
	}

	public Integer getIdestadotargeta() {
		return this.idestadotargeta;
	}

	public void setIdestadotargeta(Integer idestadotargeta) {
		this.idestadotargeta = idestadotargeta;
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

	public List<Tarjetadebito> getTarjetadebitos() {
		return this.tarjetadebitos;
	}

	public void setTarjetadebitos(List<Tarjetadebito> tarjetadebitos) {
		this.tarjetadebitos = tarjetadebitos;
	}

	public Tarjetadebito addTarjetadebito(Tarjetadebito tarjetadebito) {
		getTarjetadebitos().add(tarjetadebito);
		tarjetadebito.setEstadotarjeta(this);

		return tarjetadebito;
	}

	public Tarjetadebito removeTarjetadebito(Tarjetadebito tarjetadebito) {
		getTarjetadebitos().remove(tarjetadebito);
		tarjetadebito.setEstadotarjeta(null);

		return tarjetadebito;
	}

}