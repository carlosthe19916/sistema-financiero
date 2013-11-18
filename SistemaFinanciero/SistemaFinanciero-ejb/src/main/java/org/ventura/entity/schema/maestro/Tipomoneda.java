package org.ventura.entity.schema.maestro;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the tipomoneda database table.
 * 
 */
@Entity
@Table(name = "tipomoneda", schema = "maestro")
@NamedQuery(name = "Tipomoneda.findAll", query = "SELECT t FROM Tipomoneda t")
@NamedQueries({
		@NamedQuery(name = Tipomoneda.ALL, query = "Select t From Tipomoneda t"),
		@NamedQuery(name = Tipomoneda.ALL_ACTIVE, query = "Select t From Tipomoneda t WHERE t.estado=true") })
public class Tipomoneda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Tipomoneda.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Tipomoneda.ALL_ACTIVE";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idtipomoneda;

	@Column(length = 3)
	private String abreviatura;

	@Column(length = 35, nullable = false)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	public Tipomoneda() {
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
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
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tipomoneda)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Tipomoneda other = (Tipomoneda) obj;
        return other.getIdtipomoneda()== idtipomoneda ? true:false;
	}
	
	@Override
    public int hashCode() {
        return idtipomoneda;
    }

}