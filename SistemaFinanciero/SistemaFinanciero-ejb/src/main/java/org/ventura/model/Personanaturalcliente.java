package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the personanaturalcliente database table.
 * 
 */
@Entity
@Table(name="personanaturalcliente",schema="cliente")
@NamedQuery(name="Personanaturalcliente.findAll", query="SELECT p FROM Personanaturalcliente p")
public class Personanaturalcliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=8)
	private String dni;

	public Personanaturalcliente() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}