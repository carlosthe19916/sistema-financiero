package org.ventura.entity.tasas;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the tipocambio database table.
 * 
 */
@Entity
@Table(name = "tipocambio", schema = "tasas")
@NamedQuery(name="Tipocambio.findAll", query="SELECT t FROM Tipocambio t")
public class Tipocambio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String findAll = "org.ventura.entity.tasas.Tipocambio.findAll";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idtipocambio;

	@Column(nullable = false)
	private Boolean estado;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	private Date fechainicio;
	
	@ManyToOne
	@JoinColumn(name = "idtipomonedaentregado", nullable = false)
	private Tipomoneda tipomonedaentregado;

	@ManyToOne
	@JoinColumn(name = "idtipomonedarecibida", nullable = false)
	private Tipomoneda tipomonedarecibida;

	@ManyToOne
	@JoinColumn(name = "idtiposervicio", nullable = false)
	private Tiposervicio tiposervicio;

	@ManyToOne
	@JoinColumn(name = "idtipotasa", nullable = false)
	private Tipotasa tipotasa;

	private BigDecimal montomaximo;

	private BigDecimal montominimo;

	private BigDecimal tipocambio;

	public Tipocambio() {
	}

	public Integer getIdtipocambio() {
		return this.idtipocambio;
	}

	public void setIdtipocambio(Integer idtipocambio) {
		this.idtipocambio = idtipocambio;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public BigDecimal getMontomaximo() {
		return this.montomaximo;
	}

	public void setMontomaximo(BigDecimal montomaximo) {
		this.montomaximo = montomaximo;
	}

	public BigDecimal getMontominimo() {
		return this.montominimo;
	}

	public void setMontominimo(BigDecimal montominimo) {
		this.montominimo = montominimo;
	}

	public BigDecimal getTipocambio() {
		return this.tipocambio;
	}

	public void setTipocambio(BigDecimal tipocambio) {
		this.tipocambio = tipocambio;
	}

	public Tipotasa getTipotasa() {
		return tipotasa;
	}

	public void setTipotasa(Tipotasa tipotasa) {
		this.tipotasa = tipotasa;
	}

	public Tiposervicio getTiposervicio() {
		return tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public Tipomoneda getTipomonedarecibida() {
		return tipomonedarecibida;
	}

	public void setTipomonedarecibida(Tipomoneda tipomonedarecibida) {
		this.tipomonedarecibida = tipomonedarecibida;
	}

	public Tipomoneda getTipomonedaentregado() {
		return tipomonedaentregado;
	}

	public void setTipomonedaentregado(Tipomoneda tipomonedaentregado) {
		this.tipomonedaentregado = tipomonedaentregado;
	}

}