package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the personajuridicacliente database table.
 * 
 */
@Entity
@Table(name="personajuridicacliente",schema="cliente")
@NamedQuery(name="Personajuridicacliente.findAll", query="SELECT p FROM Personajuridicacliente p")
public class Personajuridicacliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=11)
	private String ruc;

	public Personajuridicacliente() {
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

}