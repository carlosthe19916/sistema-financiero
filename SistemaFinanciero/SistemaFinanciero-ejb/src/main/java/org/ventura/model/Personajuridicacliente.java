package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the personajuridicacliente database table.
 * 
 */
@Entity
@NamedQuery(name="Personajuridicacliente.findAll", query="SELECT p FROM Personajuridicacliente p")
public class Personajuridicacliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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