package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the caja database table.
 * 
 */
@Entity
@Table(name = "caja", schema = "caja")
@NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c")
@NamedQueries({ @NamedQuery(name = Caja.findAllByBovedaAndState, query = "SELECT c FROM Caja c INNER JOIN c.bovedas b WHERE b.idboveda = :idboveda"),
				@NamedQuery(name = Caja.ALL_ACTIVE_BY_AGENCIA, query = "Select c from Caja c inner join c.bovedas b where c.estado = true and b.agencia.idagencia = :idagencia group by c.idcaja")})
public class Caja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAllByBovedaAndState = "org.ventura.entity.schema.caja.Caja.findAllByBovedaAndState";
	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.Caja.ALL_ACTIVE_BY_AGENCIA";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idcaja;

	@Column(nullable = false, length = 30)
	private String abreviatura;

	@Column(nullable = false, length = 100)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	// bi-directional many-to-many association to boveda
	@ManyToMany(mappedBy="cajas")
	private List<Boveda> bovedas;
	
	 
	
	// bi-directional many-to-one association to Estadomovimiento
	@ManyToOne
	@JoinColumn(name = "idestadoapertura", nullable = false)
	private Estadoapertura estadoapertura;

	// bi-directional many-to-one association to Historialcaja
	@OneToMany(mappedBy = "caja")
	private List<Historialcaja> hitorialcajas;
	
	public Caja() {
	}

	public Integer getIdcaja() {
		return this.idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
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
	
	
	public List<Boveda> getBovedas() {
		return this.bovedas;
	}

	public void setBovedas(List<Boveda> bovedas) {
		this.bovedas = bovedas;
	}

	public Estadoapertura getEstadoapertura() {
		return estadoapertura;
	}

	public void setEstadoapertura(Estadoapertura estadoapertura) {
		this.estadoapertura = estadoapertura;
	}

	public List<Historialcaja> getHitorialcajas() {
		return hitorialcajas;
	}

	public void setHitorialcajas(List<Historialcaja> hitorialcajas) {
		this.hitorialcajas = hitorialcajas;
	}
}