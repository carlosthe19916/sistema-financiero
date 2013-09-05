package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipotarjetadebito database table.
 * 
 */
@Entity
@Table(name="tipotarjetadebito",schema="cuentapersonal")
@NamedQuery(name="Tipotarjetadebito.findAll", query="SELECT t FROM Tipotarjetadebito t")
public class Tipotarjetadebito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer idtargetadebitotipo;

	@Column(nullable=false, length=50)
	private String denominacion;

	@Column(nullable=false)
	private Boolean estado;

	public Tipotarjetadebito() {
	}

	public Integer getIdtargetadebitotipo() {
		return this.idtargetadebitotipo;
	}

	public void setIdtargetadebitotipo(Integer idtargetadebitotipo) {
		this.idtargetadebitotipo = idtargetadebitotipo;
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

}