package org.ventura.entity.tasas;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tipotasa database table.
 * 
 */
@Entity
@Table(name="tipotasa", schema = "tasas")
@NamedQuery(name="Tipotasa.findAll", query="SELECT t FROM Tipotasa t")
public class Tipotasa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idtipotasa;

	@Column(nullable=false, length=10)
	private String abreviatura;

	@Column(nullable=false, length=100)
	private String denominacion;

	@Column(length=300)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false, length=1)
	private String tipointeres;

	//bi-directional many-to-one association to Tasaintere
	@OneToMany(mappedBy="tipotasa")
	private List<Tasainteres> tasainteres;

	public Tipotasa() {
	}

	public Integer getIdtipotasa() {
		return this.idtipotasa;
	}

	public void setIdtipotasa(Integer idtipotasa) {
		this.idtipotasa = idtipotasa;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getTipointeres() {
		return this.tipointeres;
	}

	public void setTipointeres(String tipointeres) {
		this.tipointeres = tipointeres;
	}

	public List<Tasainteres> getTasainteres() {
		return this.tasainteres;
	}

	public void setTasainteres(List<Tasainteres> tasainteres) {
		this.tasainteres = tasainteres;
	}

	public Tasainteres addTasaintere(Tasainteres tasaintere) {
		getTasainteres().add(tasaintere);
		tasaintere.setTipotasa(this);

		return tasaintere;
	}

	public Tasainteres removeTasaintere(Tasainteres tasaintere) {
		getTasainteres().remove(tasaintere);
		tasaintere.setTipotasa(null);

		return tasaintere;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tipotasa)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Tipotasa other = (Tipotasa) obj;
        return other.getIdtipotasa() == idtipotasa ? true:false;
	}
	
	@Override
    public int hashCode() {
        return idtipotasa;
    }

}