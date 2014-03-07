package org.ventura.entity.schema.sucursal;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the agencia database table.
 * 
 */
@Entity
@Table(name = "agencia", schema = "sucursal")
@NamedQuery(name = "Agencia.findAll", query = "SELECT a FROM Agencia a")
public class Agencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idagencia;

	@Column(length = 10)
	private String abreviatura;

	@Column(nullable = false, length = 3)
	private String codigoagencia;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idsucursal")
	private Sucursal sucursal;

	public Agencia() {
	}

	public Integer getIdagencia() {
		return this.idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getCodigoagencia() {
		return this.codigoagencia;
	}

	public void setCodigoagencia(String codigoagencia) {
		this.codigoagencia = codigoagencia;
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
		if ((obj == null) || !(obj instanceof Agencia)) {
			return false;
		}
		final Agencia other = (Agencia) obj;
		if(this.idagencia != null && other.idagencia != null)
			return other.getIdagencia().equals(this.idagencia) ? true : false;
		else
			return other.hashCode() == this.hashCode() ? true : false;
	}

	@Override
	public int hashCode() {
		if(idagencia != null)
			return idagencia;
		else
			return denominacion.hashCode()+abreviatura.hashCode()+codigoagencia.hashCode();
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
}