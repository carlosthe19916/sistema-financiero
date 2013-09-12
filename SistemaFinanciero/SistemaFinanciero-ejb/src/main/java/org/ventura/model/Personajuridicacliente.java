package org.ventura.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the personajuridicacliente database table.
 * 
 */
@Entity
@Table(name = "personajuridicacliente", schema = "cliente")
@NamedQuery(name = "Personajuridicacliente.findAll", query = "SELECT p FROM Personajuridicacliente p")
public class Personajuridicacliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 11)
	private String ruc;

	@ManyToOne
	@JoinColumn(name = "ruc", nullable = false, insertable = false, updatable = false)
	private Personajuridica personajuridica;

	@OneToMany(mappedBy = "personajuridicacliente")
	private List<Cuentaahorro> cuentaahorros;
	
	public Personajuridicacliente() {
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

	public List<Cuentaahorro> getCuentaahorros() {
		return cuentaahorros;
	}

	public void setCuentaahorros(List<Cuentaahorro> cuentaahorros) {
		this.cuentaahorros = cuentaahorros;
	}

}