package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the denominacionmoneda database table.
 * 
 */
@Entity
@Table(name = "denominacionmoneda", schema = "caja")
@NamedQuery(name = "Denominacionmoneda.findAll", query = "SELECT d FROM Denominacionmoneda d")
@NamedQueries({ @NamedQuery(name = Denominacionmoneda.findAllByTipoMoneda, query = "Select p From Personanatural p Where p.dni=:dni") })
public class Denominacionmoneda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAllByTipoMoneda = "org.ventura.entity.schema.caja.findAllByTipoMoneda";

	@Id
	@Column(unique = true, nullable = false)
	private Integer iddenominacionmoneda;

	@Column(nullable = false, length = 150)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private double valor;

	// bi-directional many-to-one association to Detallehistorialboveda
	@OneToMany(mappedBy = "denominacionmoneda")
	private List<Detallehistorialboveda> detallehistorialbovedas;

	// bi-directional many-to-one association to Detalletransaccionboveda
	@OneToMany(mappedBy = "denominacionmoneda")
	private List<Detalletransaccionboveda> detalletransaccionbovedas;

	public Denominacionmoneda() {
	}

	public Integer getIddenominacionmoneda() {
		return this.iddenominacionmoneda;
	}

	public void setIddenominacionmoneda(Integer iddenominacionmoneda) {
		this.iddenominacionmoneda = iddenominacionmoneda;
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

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<Detallehistorialboveda> getDetallehistorialbovedas() {
		return this.detallehistorialbovedas;
	}

	public void setDetallehistorialbovedas(
			List<Detallehistorialboveda> detallehistorialbovedas) {
		this.detallehistorialbovedas = detallehistorialbovedas;
	}

	public Detallehistorialboveda addDetallehistorialboveda(
			Detallehistorialboveda detallehistorialboveda) {
		getDetallehistorialbovedas().add(detallehistorialboveda);
		detallehistorialboveda.setDenominacionmoneda(this);

		return detallehistorialboveda;
	}

	public Detallehistorialboveda removeDetallehistorialboveda(
			Detallehistorialboveda detallehistorialboveda) {
		getDetallehistorialbovedas().remove(detallehistorialboveda);
		detallehistorialboveda.setDenominacionmoneda(null);

		return detallehistorialboveda;
	}

	public List<Detalletransaccionboveda> getDetalletransaccionbovedas() {
		return this.detalletransaccionbovedas;
	}

	public void setDetalletransaccionbovedas(
			List<Detalletransaccionboveda> detalletransaccionbovedas) {
		this.detalletransaccionbovedas = detalletransaccionbovedas;
	}

	public Detalletransaccionboveda addDetalletransaccionboveda(
			Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().add(detalletransaccionboveda);
		detalletransaccionboveda.setDenominacionmoneda(this);

		return detalletransaccionboveda;
	}

	public Detalletransaccionboveda removeDetalletransaccionboveda(
			Detalletransaccionboveda detalletransaccionboveda) {
		getDetalletransaccionbovedas().remove(detalletransaccionboveda);
		detalletransaccionboveda.setDenominacionmoneda(null);

		return detalletransaccionboveda;
	}

}