package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the chequeraestado database table.
 * 
 */
@Entity
@Table(name="chequeraestado",schema="cuentapersonal")
@NamedQuery(name="Chequeraestado.findAll", query="SELECT c FROM Chequeraestado c")
public class Chequeraestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer idchequeraestado;

	@Column(length=2)
	private String abreviatura;

	@Column(nullable=false)
	private Integer denominacion;

	@Column(nullable=false)
	private Boolean estado;

	//bi-directional many-to-one association to Chequera
	@OneToMany(mappedBy="chequeraestado")
	private List<Chequera> chequeras;

	public Chequeraestado() {
	}

	public Integer getIdchequeraestado() {
		return this.idchequeraestado;
	}

	public void setIdchequeraestado(Integer idchequeraestado) {
		this.idchequeraestado = idchequeraestado;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(Integer denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Chequera> getChequeras() {
		return this.chequeras;
	}

	public void setChequeras(List<Chequera> chequeras) {
		this.chequeras = chequeras;
	}

	public Chequera addChequera(Chequera chequera) {
		getChequeras().add(chequera);
		chequera.setChequeraestado(this);

		return chequera;
	}

	public Chequera removeChequera(Chequera chequera) {
		getChequeras().remove(chequera);
		chequera.setChequeraestado(null);

		return chequera;
	}

}