package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;


/**
 * The persistent class for the pendiente_caja database table.
 * 
 */
@Entity
@Table(name="pendiente_caja", schema = "caja")
@NamedQuery(name="PendienteCaja.findAll", query="SELECT p FROM PendienteCaja p")
public class PendienteCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	public PendienteCaja() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idpendientecaja;

	@ManyToOne
	@JoinColumn(name = "idhistorialcaja", nullable = false)
	private Historialcaja idhistorialcaja;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda", nullable = false)
	private Tipomoneda tipomoneda;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "monto")) })
	private Moneda monto;

	@Column(length = 300)
	private String observacion;

	@Column(nullable = false, length = 10)
	private String tipopendiente;

	

	public Integer getIdpendientecaja() {
		return this.idpendientecaja;
	}

	public void setIdpendientecaja(Integer idpendientecaja) {
		this.idpendientecaja = idpendientecaja;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipopendiente() {
		return this.tipopendiente;
	}

	public void setTipopendiente(String tipopendiente) {
		this.tipopendiente = tipopendiente;
	}

	public Historialcaja getIdhistorialcaja() {
		return idhistorialcaja;
	}

	public void setIdhistorialcaja(Historialcaja idhistorialcaja) {
		this.idhistorialcaja = idhistorialcaja;
	}

	public Moneda getMonto() {
		return monto;
	}

	public void setMonto(Moneda monto) {
		this.monto = monto;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

}