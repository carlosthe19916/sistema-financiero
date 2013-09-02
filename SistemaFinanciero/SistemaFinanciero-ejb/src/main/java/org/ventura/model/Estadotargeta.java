package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadotargeta database table.
 * 
 */
@Entity
@Table(name="estadotargeta",schema="cuentapersonal")
@NamedQuery(name="Estadotargeta.findAll", query="SELECT e FROM Estadotargeta e")
public class Estadotargeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idestadotargeta;

	private String abreviatura;

	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Tarjetadebito
	@OneToMany(mappedBy="estadotargeta")
	private List<Tarjetadebito> tarjetadebitos;

	public Estadotargeta() {
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
		tarjetadebito.setEstadotargeta(this);

		return tarjetadebito;
	}

	public Tarjetadebito removeTarjetadebito(Tarjetadebito tarjetadebito) {
		getTarjetadebitos().remove(tarjetadebito);
		tarjetadebito.setEstadotargeta(null);

		return tarjetadebito;
	}

}