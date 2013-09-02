package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipotarjetadebito database table.
 * 
 */
@Entity
@Table(name="tipotarjetadebito",schema="cuentapersonal")
@NamedQuery(name="Tipotarjetadebito.findAll", query="SELECT t FROM Tipotarjetadebito t")
public class Tipotarjetadebito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idtargetadebitotipo;

	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Tarjetadebito
	@OneToMany(mappedBy="tipotarjetadebito")
	private List<Tarjetadebito> tarjetadebitos;

	public Tipotarjetadebito() {
	}

	public Integer getIdtargetadebitotipo() {
		return this.idtargetadebitotipo;
	}

	public void setIdtargetadebitotipo(Integer idtargetadebitotipo) {
		this.idtargetadebitotipo = idtargetadebitotipo;
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
		tarjetadebito.setTipotarjetadebito(this);

		return tarjetadebito;
	}

	public Tarjetadebito removeTarjetadebito(Tarjetadebito tarjetadebito) {
		getTarjetadebitos().remove(tarjetadebito);
		tarjetadebito.setTipotarjetadebito(null);

		return tarjetadebito;
	}

}