package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the chequeestado database table.
 * 
 */
@Entity
@NamedQuery(name="Chequeestado.findAll", query="SELECT c FROM Chequeestado c")
public class Chequeestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idchequeestado;

	private String abreviatura;

	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Cheque
	@OneToMany(mappedBy="chequeestado")
	private List<Cheque> cheques;

	public Chequeestado() {
	}

	public Integer getIdchequeestado() {
		return this.idchequeestado;
	}

	public void setIdchequeestado(Integer idchequeestado) {
		this.idchequeestado = idchequeestado;
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

	public List<Cheque> getCheques() {
		return this.cheques;
	}

	public void setCheques(List<Cheque> cheques) {
		this.cheques = cheques;
	}

	public Cheque addCheque(Cheque cheque) {
		getCheques().add(cheque);
		cheque.setChequeestado(this);

		return cheque;
	}

	public Cheque removeCheque(Cheque cheque) {
		getCheques().remove(cheque);
		cheque.setChequeestado(null);

		return cheque;
	}

}