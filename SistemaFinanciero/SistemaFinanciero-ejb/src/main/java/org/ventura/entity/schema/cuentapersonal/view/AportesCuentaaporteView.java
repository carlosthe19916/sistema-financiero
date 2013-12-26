package org.ventura.entity.schema.cuentapersonal.view;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.caja.Moneda;

import java.util.Date;
import java.math.BigDecimal;

/**
 * The persistent class for the aportes_cuentaaporte_view database table.
 * 
 */
@Entity
@Table(name = "aportes_cuentaaporte_view", schema = "cuentapersonal")
@NamedQuery(name = "AportesCuentaaporteView.findAll", query = "SELECT a FROM AportesCuentaaporteView a")
@NamedQueries({ @NamedQuery(name = AportesCuentaaporteView.findBetweenDates, query = "SELECT a FROM AportesCuentaaporteView a WHERE a.idcuentaaporte = :idcuentaaporte AND a.mes BETWEEN :startDate AND :endDate ORDER BY a.mes") })
public class AportesCuentaaporteView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findBetweenDates = "org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView.findBetweenDates";

	@Id
	@Column
	private Integer idcuentaaporte;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date mes;

	@Column(length = 14)
	private String numerocuentaaporte;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "total")) })
	private Moneda total;

	public AportesCuentaaporteView() {
	}

	public Integer getIdcuentaaporte() {
		return this.idcuentaaporte;
	}

	public void setIdcuentaaporte(Integer idcuentaaporte) {
		this.idcuentaaporte = idcuentaaporte;
	}

	public Date getMes() {
		return this.mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public String getNumerocuentaaporte() {
		return this.numerocuentaaporte;
	}

	public void setNumerocuentaaporte(String numerocuentaaporte) {
		this.numerocuentaaporte = numerocuentaaporte;
	}

	public void setTotal(Moneda total) {
		this.total = total;
	}

	public Moneda getTotal() {
		return total;
	}

}