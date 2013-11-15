package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.socio.Socio;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the view_bovedadetalle database table.
 * 
 */
@Entity
@Table(name = "view_bovedadetalle", schema = "caja")
@NamedQuery(name = "ViewBovedadetalle.findAll", query = "SELECT v FROM ViewBovedadetalle v")
@NamedQueries({
		@NamedQuery(name = ViewBovedadetalle.findAll, query = "SELECT v FROM ViewBovedadetalle v"),
		@NamedQuery(name = ViewBovedadetalle.findLastBovedaDetalleByBoveda, query = "SELECT v FROM ViewBovedadetalle v WHERE v.idboveda = :idboveda AND v.fechacierrehistorialboveda = (SELECT MAX(vv.fechacierrehistorialboveda) FROM ViewBovedadetalle vv WHERE vv.idboveda = v.idboveda)") })
public class ViewBovedadetalle implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAll = "org.ventura.entity.schema.caja.ViewBovedadetalle.findAll";
	public final static String findLastBovedaDetalleByBoveda = "org.ventura.entity.schema.caja.ViewBovedadetalle.findLastBovedaDetalleByBoveda";

	@EmbeddedId
	private ViewBovedadetallePK id;

	@Column(length = 10)
	private String abreviaturaagencia;

	@Column(length = 10)
	private String abreviaturaestadoapertura;

	@Column(length = 10)
	private String abreviaturaestadomovimiento;

	@Column(length = 3)
	private String abreviaturatipomoneda;

	private Integer cantidaddetallehistorialboveda;

	@Column(length = 3)
	private String codigoagecia;

	@Column(length = 150)
	private String denominacionagencia;

	@Column(length = 150)
	private String denominacionboveda;

	@Column(length = 150)
	private String denominaciondenominacionmoneda;

	@Column(length = 150)
	private String denominacionestadoapertura;

	@Column(length = 150)
	private String denominacionestadomovimiento;

	@Column(length = 35)
	private String denominaciontipomoneda;

	@Temporal(TemporalType.DATE)
	private Date fechaaperturahistorialboveda;

	@Temporal(TemporalType.DATE)
	private Date fechacierrehistorialboveda;

	private Timestamp horaaperturahistorialboveda;

	private Timestamp horacierrehistorialboveda;

	private Integer idagencia;

	@Column(insertable = false, updatable = false)
	private Integer idboveda;

	private Integer iddenominacionmoneda;

	@Column(insertable = false, updatable = false)
	private Integer iddetallehistorialboveda;

	private Integer idestadoapertura;

	private Integer idestadomovimiento;

	@Column(insertable = false, updatable = false)
	private Integer idhistorialboveda;

	private Integer idtipomoneda;

	private double saldoboveda;

	private Integer saldofinalhistorialboveda;

	private double saldoinicialhistorialboveda;

	private double totaldetallehistorialboveda;

	private double valordenominacionmoneda;

	public ViewBovedadetalle() {
	}

	public String getAbreviaturaagencia() {
		return this.abreviaturaagencia;
	}

	public void setAbreviaturaagencia(String abreviaturaagencia) {
		this.abreviaturaagencia = abreviaturaagencia;
	}

	public String getAbreviaturaestadoapertura() {
		return this.abreviaturaestadoapertura;
	}

	public void setAbreviaturaestadoapertura(String abreviaturaestadoapertura) {
		this.abreviaturaestadoapertura = abreviaturaestadoapertura;
	}

	public String getAbreviaturaestadomovimiento() {
		return this.abreviaturaestadomovimiento;
	}

	public void setAbreviaturaestadomovimiento(
			String abreviaturaestadomovimiento) {
		this.abreviaturaestadomovimiento = abreviaturaestadomovimiento;
	}

	public String getAbreviaturatipomoneda() {
		return this.abreviaturatipomoneda;
	}

	public void setAbreviaturatipomoneda(String abreviaturatipomoneda) {
		this.abreviaturatipomoneda = abreviaturatipomoneda;
	}

	public Integer getCantidaddetallehistorialboveda() {
		return this.cantidaddetallehistorialboveda;
	}

	public void setCantidaddetallehistorialboveda(
			Integer cantidaddetallehistorialboveda) {
		this.cantidaddetallehistorialboveda = cantidaddetallehistorialboveda;
	}

	public String getCodigoagecia() {
		return this.codigoagecia;
	}

	public void setCodigoagecia(String codigoagecia) {
		this.codigoagecia = codigoagecia;
	}

	public String getDenominacionagencia() {
		return this.denominacionagencia;
	}

	public void setDenominacionagencia(String denominacionagencia) {
		this.denominacionagencia = denominacionagencia;
	}

	public String getDenominacionboveda() {
		return this.denominacionboveda;
	}

	public void setDenominacionboveda(String denominacionboveda) {
		this.denominacionboveda = denominacionboveda;
	}

	public String getDenominaciondenominacionmoneda() {
		return this.denominaciondenominacionmoneda;
	}

	public void setDenominaciondenominacionmoneda(
			String denominaciondenominacionmoneda) {
		this.denominaciondenominacionmoneda = denominaciondenominacionmoneda;
	}

	public String getDenominacionestadoapertura() {
		return this.denominacionestadoapertura;
	}

	public void setDenominacionestadoapertura(String denominacionestadoapertura) {
		this.denominacionestadoapertura = denominacionestadoapertura;
	}

	public String getDenominacionestadomovimiento() {
		return this.denominacionestadomovimiento;
	}

	public void setDenominacionestadomovimiento(
			String denominacionestadomovimiento) {
		this.denominacionestadomovimiento = denominacionestadomovimiento;
	}

	public String getDenominaciontipomoneda() {
		return this.denominaciontipomoneda;
	}

	public void setDenominaciontipomoneda(String denominaciontipomoneda) {
		this.denominaciontipomoneda = denominaciontipomoneda;
	}

	public Date getFechaaperturahistorialboveda() {
		return this.fechaaperturahistorialboveda;
	}

	public void setFechaaperturahistorialboveda(
			Date fechaaperturahistorialboveda) {
		this.fechaaperturahistorialboveda = fechaaperturahistorialboveda;
	}

	public Date getFechacierrehistorialboveda() {
		return this.fechacierrehistorialboveda;
	}

	public void setFechacierrehistorialboveda(Date fechacierrehistorialboveda) {
		this.fechacierrehistorialboveda = fechacierrehistorialboveda;
	}

	public Timestamp getHoraaperturahistorialboveda() {
		return this.horaaperturahistorialboveda;
	}

	public void setHoraaperturahistorialboveda(
			Timestamp horaaperturahistorialboveda) {
		this.horaaperturahistorialboveda = horaaperturahistorialboveda;
	}

	public Timestamp getHoracierrehistorialboveda() {
		return this.horacierrehistorialboveda;
	}

	public void setHoracierrehistorialboveda(Timestamp horacierrehistorialboveda) {
		this.horacierrehistorialboveda = horacierrehistorialboveda;
	}

	public Integer getIdagencia() {
		return this.idagencia;
	}

	public void setIdagencia(Integer idagencia) {
		this.idagencia = idagencia;
	}

	public Integer getIdboveda() {
		return this.idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

	public Integer getIddenominacionmoneda() {
		return this.iddenominacionmoneda;
	}

	public void setIddenominacionmoneda(Integer iddenominacionmoneda) {
		this.iddenominacionmoneda = iddenominacionmoneda;
	}

	public Integer getIddetallehistorialboveda() {
		return this.iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public Integer getIdestadoapertura() {
		return this.idestadoapertura;
	}

	public void setIdestadoapertura(Integer idestadoapertura) {
		this.idestadoapertura = idestadoapertura;
	}

	public Integer getIdestadomovimiento() {
		return this.idestadomovimiento;
	}

	public void setIdestadomovimiento(Integer idestadomovimiento) {
		this.idestadomovimiento = idestadomovimiento;
	}

	public Integer getIdhistorialboveda() {
		return this.idhistorialboveda;
	}

	public void setIdhistorialboveda(Integer idhistorialboveda) {
		this.idhistorialboveda = idhistorialboveda;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public double getSaldoboveda() {
		return this.saldoboveda;
	}

	public void setSaldoboveda(double saldoboveda) {
		this.saldoboveda = saldoboveda;
	}

	public Integer getSaldofinalhistorialboveda() {
		return this.saldofinalhistorialboveda;
	}

	public void setSaldofinalhistorialboveda(Integer saldofinalhistorialboveda) {
		this.saldofinalhistorialboveda = saldofinalhistorialboveda;
	}

	public double getSaldoinicialhistorialboveda() {
		return this.saldoinicialhistorialboveda;
	}

	public void setSaldoinicialhistorialboveda(
			double saldoinicialhistorialboveda) {
		this.saldoinicialhistorialboveda = saldoinicialhistorialboveda;
	}

	public double getTotaldetallehistorialboveda() {
		return this.totaldetallehistorialboveda;
	}

	public void setTotaldetallehistorialboveda(
			double totaldetallehistorialboveda) {
		this.totaldetallehistorialboveda = totaldetallehistorialboveda;
	}

	public double getValordenominacionmoneda() {
		return this.valordenominacionmoneda;
	}

	public void setValordenominacionmoneda(double valordenominacionmoneda) {
		this.valordenominacionmoneda = valordenominacionmoneda;
	}

	public ViewBovedadetallePK getId() {
		return id;
	}

	public void setId(ViewBovedadetallePK id) {
		this.id = id;
	}

}