package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;

import java.util.List;

/**
 * The persistent class for the boveda database table.
 * 
 */
@Entity
@Table(name = "boveda", schema = "caja")
@NamedQuery(name = "Boveda.findAll", query = "SELECT b FROM Boveda b")
@NamedQueries({ @NamedQuery(name = Boveda.ALL_ACTIVE_BY_AGENCIA, query = "Select b From Boveda b INNER JOIN b.agencia a WHERE a.idagencia = :idagencia AND b.estado = true") })
public class Boveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.ALL_ACTIVE_BY_AGENCIA";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idboveda;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idagencia;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private Double saldo;

	@Column(nullable = false)
	private Integer idestadomovimiento;

	@ManyToOne
	@JoinColumn(name = "idestadomovimiento", nullable = false, insertable = false, updatable = false)
	private Estadomovimiento estadomovimiento;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false, insertable = false, updatable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idagencia", nullable = false, insertable = false, updatable = false)
	private Agencia agencia;

	@ManyToMany
	@JoinTable(name = "boveda_caja", schema = "caja", joinColumns = { @JoinColumn(name = "idboveda", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idcaja", nullable = false) })
	private List<Caja> cajas;

	@OneToMany(mappedBy = "boveda")
	private List<Historialboveda> historialbovedas;

	public Boveda() {
	}

	public Integer getIdboveda() {
		return this.idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
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

	public Integer getIdagencia() {
		return this.idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public Double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Estadomovimiento getEstadomovimiento() {
		return this.estadomovimiento;
	}

	public void setEstadomovimiento(Estadomovimiento estadomovimiento) {
		this.estadomovimiento = estadomovimiento;
	}

	public List<Caja> getCajas() {
		return this.cajas;
	}

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
	}

	public List<Historialboveda> getHistorialbovedas() {
		return this.historialbovedas;
	}

	public void setHistorialbovedas(List<Historialboveda> historialbovedas) {
		this.historialbovedas = historialbovedas;
	}

	public Historialboveda addHistorialboveda(Historialboveda historialboveda) {
		getHistorialbovedas().add(historialboveda);
		historialboveda.setBoveda(this);

		return historialboveda;
	}

	public Historialboveda removeHistorialboveda(Historialboveda historialboveda) {
		getHistorialbovedas().remove(historialboveda);
		historialboveda.setBoveda(null);

		return historialboveda;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
		this.idtipomoneda = (tipomoneda == null) ? null : tipomoneda
				.getIdtipomoneda();
	}

	public Integer getIdestadomovimiento() {
		return idestadomovimiento;
	}

	public void setIdestadomovimiento(Integer idestadomovimiento) {
		this.idestadomovimiento = idestadomovimiento;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}