package org.ventura.entity.tasas;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;

import java.util.Date;


/**
 * The persistent class for the tipocambio database table.
 * 
 */
@Entity
@Table(name = "tipocambio", schema = "tasas")
@NamedQuery(name="Tipocambio.findAll", query="SELECT t FROM Tipocambio t")
@NamedQueries({
	@NamedQuery(name = Tipocambio.FindById, query = "Select tc From Tipocambio tc INNER JOIN tc.tiposervicio ts INNER JOIN ts.servicio s INNER JOIN tc.tipotasa tt WHERE s.estado = TRUE and ts.estado = TRUE and tc.estado = TRUE and tt.estado = TRUE and tt.idtipotasa=:idtipotasa AND :monto BETWEEN tc.montominimo and tc.montomaximo and tc.tipomonedarecibida.idtipomoneda = :idtipomonedarecibida and tc.tipomonedaentregado.idtipomoneda = :idtipomonedaentregado")})

public class Tipocambio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String findAll = "org.ventura.entity.tasas.Tipocambio.findAll";
	public final static String FindById = "org.ventura.entity.tasas.Tipocambio.FindById";
	
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

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montomaximo")) })
	private Moneda montomaximo;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "montominimo")) })
	private Moneda montominimo;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "tipocambio")) })
	private TasaCambio tipocambio;

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

	public Moneda getMontomaximo() {
		return montomaximo;
	}

	public void setMontomaximo(Moneda montomaximo) {
		this.montomaximo = montomaximo;
	}

	public Moneda getMontominimo() {
		return montominimo;
	}

	public void setMontominimo(Moneda montominimo) {
		this.montominimo = montominimo;
	}

	public TasaCambio getTipocambio() {
		return tipocambio;
	}

	public void setTipocambio(TasaCambio tipocambio) {
		this.tipocambio = tipocambio;
	}

}