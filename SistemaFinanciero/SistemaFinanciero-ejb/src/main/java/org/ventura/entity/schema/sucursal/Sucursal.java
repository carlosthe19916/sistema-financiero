package org.ventura.entity.schema.sucursal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Ubigeo;

/**
 * The persistent class for the sucursal database table.
 * 
 */
@Entity
@Table(name = "sucursal", schema = "sucursal")
@NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")
@NamedQueries({ @NamedQuery(name = Sucursal.f_allActive, query = "SELECT s FROM Sucursal s WHERE s.estado = TRUE") })
public class Sucursal implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String f_allActive = "org.ventura.entity.schema.sucursal.Sucursal.f_allActive";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idsucursal;

	@Column(length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idubigeo")
	private Ubigeo ubigeo;

	public Sucursal() {
	}

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
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
		if ((obj == null) || !(obj instanceof Sucursal)) {
			return false;
		}
		final Sucursal other = (Sucursal) obj;
		return other.getIdsucursal().equals(this.idsucursal) ? true : false;
	}

	@Override
	public int hashCode() {
		return idsucursal;
	}

	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}
}