package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tipoempresa database table.
 * 
 */
@Entity
@Table(name="tipoempresa",schema="persona")
@NamedQuery(name="Tipoempresa.findAll", query="SELECT t FROM Tipoempresa t")
@NamedQueries({
	@NamedQuery(name = Tipoempresa.ALL, query = "Select b From Tipoempresa b"),
	@NamedQuery(name = Tipoempresa.ALL_ACTIVE, query = "Select t From Tipoempresa t WHERE t.estado=true")})
public class Tipoempresa implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Tipoempresa.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Tipoempresa.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipoempresa;

	@Column(length = 50, nullable = false)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	// bi-directional many-to-one association to Personajuridica
	@OneToMany(mappedBy = "tipoempresa")
	private List<Personajuridica> listPersonajuridica;

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

	public List<Personajuridica> getListPersonajuridica() {
		return this.listPersonajuridica;
	}

	public void setListPersonajuridica(List<Personajuridica> listPersonajuridica) {
		this.listPersonajuridica = listPersonajuridica;
	}

	public Personajuridica addPersonajuridica(Personajuridica personajuridica) {
		getListPersonajuridica().add(personajuridica);
		personajuridica.setTipoempresa(this);

		return personajuridica;
	}

	public Personajuridica removePersonajuridica(Personajuridica personajuridica) {
		getListPersonajuridica().remove(personajuridica);
		personajuridica.setTipoempresa(null);

		return personajuridica;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tipoempresa)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Tipoempresa other = (Tipoempresa) obj;
        return other.getIdtipoempresa() == idtipoempresa ? true:false;
	}
	
	@Override
    public int hashCode() {
        return idtipoempresa;
    }

}
