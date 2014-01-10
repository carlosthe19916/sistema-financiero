package org.ventura.entity.tasas;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.ventura.entity.schema.maestro.Tipomoneda;

import org.ventura.tipodato.TasaCambio;
import java.util.Date;

/**
 * The persistent class for the tasainteres database table.
 * 
 */
@Entity
@Table(name = "tasainteres", schema = "tasas")
@NamedQuery(name = "Tasainteres.findAll", query = "SELECT t FROM Tasainteres t")
@NamedQueries({
		@NamedQuery(name = Tasainteres.FindById, query = "Select ti From Tasainteres ti INNER JOIN ti.tiposervicio ts INNER JOIN ts.servicio s INNER JOIN ti.tipotasa tt WHERE s.estado = TRUE and ts.estado = TRUE and ti.estado = TRUE and tt.estado = TRUE and tt.idtipotasa=:idtipotasa AND :monto BETWEEN ti.montominimo and ti.montomaximo"),
		@NamedQuery(name = Tasainteres.TASA_INTERES_BY_CV, query = "select ti from Tasainteres ti where ti.tipotasa.idtipotasa = :parametro and ti.estado = true"),
		@NamedQuery(name = Tasainteres.f_tipotasa_moneda_periodo_monto, query = "SELECT t FROM Tasainteres t INNER JOIN t.tipotasa tt WHERE t.tipotasa = :tipotasa AND t.tipomoneda = :tipomoneda AND :periodo BETWEEN t.periodoinicial AND t.periodofinal AND :monto BETWEEN t.montominimo AND t.montomaximo"),
		@NamedQuery(name = Tasainteres.f_tipotasa_moneda, query = "SELECT t FROM Tasainteres t INNER JOIN t.tipotasa tt WHERE t.tipotasa = :tipotasa AND t.tipomoneda = :tipomoneda") })
public class Tasainteres implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String FindById = "org.ventura.entity.tasas.Tasainteres.FindById";
	public final static String TASA_INTERES_BY_CV = "org.ventura.entity.tasas.Tasainteres.TASA_INTERES_BY_CV";
	public final static String f_tipotasa_moneda_periodo_monto = "org.ventura.entity.tasas.Tasainteres.f_moneda_periodo_monto";
	public final static String f_tipotasa_moneda = "org.ventura.entity.tasas.Tasainteres.f_tipotasa_moneda";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idtasainteres;

	@Column(nullable = false)
	private Boolean estado;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechainicio;

	@Column(nullable = false)
	private BigDecimal montomaximo;

	@Column(nullable = false)
	private BigDecimal montominimo;

	@Column
	private Integer periodoinicial;

	@Column
	private Integer periodofinal;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "tasa")) })
	private TasaCambio tasa;

	@ManyToOne
	@JoinColumn(name = "idtiposervicio", nullable = false)
	private Tiposervicio tiposervicio;

	@ManyToOne
	@JoinColumn(name = "idtipotasa", nullable = false)
	private Tipotasa tipotasa;

	@ManyToOne
	@JoinColumn(name = "idtipomoneda")
	private Tipomoneda tipomoneda;

	public Tasainteres() {
	}

	public Integer getIdtasainteres() {
		return this.idtasainteres;
	}

	public void setIdtasainteres(Integer idtasainteres) {
		this.idtasainteres = idtasainteres;
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

	public Tiposervicio getTiposervicio() {
		return this.tiposervicio;
	}

	public void setTiposervicio(Tiposervicio tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public Tipotasa getTipotasa() {
		return this.tipotasa;
	}

	public void setTipotasa(Tipotasa tipotasa) {
		this.tipotasa = tipotasa;
	}

	public TasaCambio getTasa() {
		return tasa;
	}

	public void setTasa(TasaCambio tasa) {
		this.tasa = tasa;
	}

	public Integer getPeriodoinicial() {
		return periodoinicial;
	}

	public void setPeriodoinicial(Integer periodoinicial) {
		this.periodoinicial = periodoinicial;
	}

	public Integer getPeriodofinal() {
		return periodofinal;
	}

	public void setPeriodofinal(Integer periodofinal) {
		this.periodofinal = periodofinal;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

}
