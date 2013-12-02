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
@NamedQueries({
		@NamedQuery(name = Boveda.ALL_ACTIVE_BY_AGENCIA, query = "Select b From Boveda b INNER JOIN b.agencia a WHERE a.idagencia = :idagencia AND b.estado = true ORDER BY b.idboveda"),
		@NamedQuery(name = Boveda.ALL_ACTIVE_BY_AGENCIA_AND_ESTADOMOVIMIENTO, query = "Select b From Boveda b INNER JOIN b.agencia a INNER JOIN b.historialbovedas hb WHERE a = :agencia AND b.estado = true AND b.estadoapertura = :estadoapertura AND b.estado = true AND hb.estadomovimiento = :estadomovimiento AND hb.idcreacion = (SELECT MAX(hh.idcreacion) FROM Historialboveda hh WHERE hh.boveda.idboveda = b.idboveda )") })
public class Boveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.Boveda.ALL_ACTIVE_BY_AGENCIA";
	public final static String ALL_ACTIVE_BY_AGENCIA_AND_ESTADOMOVIMIENTO = "org.ventura.entity.schema.caja.Boveda.ALL_ACTIVE_BY_AGENCIA_AND_ESTADOMOVIMIENTO";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idboveda;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idestadoapertura", nullable = false)
	private Estadoapertura estadoapertura;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@ManyToOne
	@JoinColumn(name = "idagencia", nullable = false)
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Estadoapertura getEstadoapertura() {
		return estadoapertura;
	}

	public void setEstadoapertura(Estadoapertura estadoapertura) {
		this.estadoapertura = estadoapertura;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

}