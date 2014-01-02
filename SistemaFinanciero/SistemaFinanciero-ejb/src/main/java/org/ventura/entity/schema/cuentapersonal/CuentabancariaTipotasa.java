package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.ventura.util.helper.TasaInteres;

/**
 * The persistent class for the cuentabancaria_tipotasa database table.
 * 
 */
@Entity
@Table(name = "cuentabancaria_tipotasa", schema = "cuentapersonal")
@NamedQuery(name = "CuentabancariaTipotasa.findAll", query = "SELECT c FROM CuentabancariaTipotasa c")
public class CuentabancariaTipotasa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CuentabancariaTipotasaPK id;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "tasainteres")) })
	private TasaInteres tasainteres;

	public CuentabancariaTipotasa() {
	}

	public CuentabancariaTipotasaPK getId() {
		return id;
	}

	public void setId(CuentabancariaTipotasaPK id) {
		this.id = id;
	}

	public TasaInteres getTasainteres() {
		return tasainteres;
	}

	public void setTasainteres(TasaInteres tasainteres) {
		this.tasainteres = tasainteres;
	}

}