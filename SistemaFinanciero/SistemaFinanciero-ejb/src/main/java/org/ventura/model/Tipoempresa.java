package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipoempresa database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoempresa.findAll", query="SELECT t FROM Tipoempresa t")
public class Tipoempresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idtipoempresa;

	private String denominacion;

	private Boolean estado;

	//bi-directional many-to-one association to Personajuridica
	@OneToMany(mappedBy="tipoempresa")
	private List<Personajuridica> personajuridicas;

	public Tipoempresa() {
	}

	public Integer getIdtipoempresa() {
		return this.idtipoempresa;
	}

	public void setIdtipoempresa(Integer idtipoempresa) {
		this.idtipoempresa = idtipoempresa;
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

	public List<Personajuridica> getPersonajuridicas() {
		return this.personajuridicas;
	}

	public void setPersonajuridicas(List<Personajuridica> personajuridicas) {
		this.personajuridicas = personajuridicas;
	}

	public Personajuridica addPersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().add(personajuridica);
		personajuridica.setTipoempresa(this);

		return personajuridica;
	}

	public Personajuridica removePersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().remove(personajuridica);
		personajuridica.setTipoempresa(null);

		return personajuridica;
	}

}