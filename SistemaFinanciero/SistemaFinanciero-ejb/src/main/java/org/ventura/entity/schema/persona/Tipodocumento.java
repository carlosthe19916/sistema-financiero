package org.ventura.entity.schema.persona;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tipodocumento database table.
 * 
 */
@Entity
@Table(name = "tipodocumento", schema = "persona")
@NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t")
public class Tipodocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipodocumento;

	@Column(nullable = false, length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 50)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	// bi-directional many-to-one association to Personajuridica
	@OneToMany(mappedBy = "tipodocumento")
	private List<Personajuridica> personajuridicas;

	// bi-directional many-to-one association to Personanatural
	@OneToMany(mappedBy = "tipodocumento")
	private List<Personanatural> personanaturals;

	public Tipodocumento() {
	}

	public Integer getIdtipodocumento() {
		return this.idtipodocumento;
	}

	public void setIdtipodocumento(Integer idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
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

	public List<Personajuridica> getPersonajuridicas() {
		return this.personajuridicas;
	}

	public void setPersonajuridicas(List<Personajuridica> personajuridicas) {
		this.personajuridicas = personajuridicas;
	}

	public Personajuridica addPersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().add(personajuridica);
		personajuridica.setTipodocumento(this);

		return personajuridica;
	}

	public Personajuridica removePersonajuridica(Personajuridica personajuridica) {
		getPersonajuridicas().remove(personajuridica);
		personajuridica.setTipodocumento(null);

		return personajuridica;
	}

	public List<Personanatural> getPersonanaturals() {
		return this.personanaturals;
	}

	public void setPersonanaturals(List<Personanatural> personanaturals) {
		this.personanaturals = personanaturals;
	}

	public Personanatural addPersonanatural(Personanatural personanatural) {
		getPersonanaturals().add(personanatural);
		personanatural.setTipodocumento(this);

		return personanatural;
	}

	public Personanatural removePersonanatural(Personanatural personanatural) {
		getPersonanaturals().remove(personanatural);
		personanatural.setTipodocumento(null);

		return personanatural;
	}

}