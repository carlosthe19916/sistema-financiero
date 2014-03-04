package org.ventura.cuentapersonal.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.dao.impl.CuentabancariaTipotasaDAO;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasa;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasaPK;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.session.CajaBean;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.ProduceObjectTasainteres;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class RenovarCuentaplazofijoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean cuentaValida;
	private boolean cuentaCreada;
	
	//buscar cuenta
	private boolean dlgBusquedaOpen;
	private String campoBusqueda;
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@Inject private TablaBean<CuentabancariaView> tablaCuentabancaria;
	private CuentabancariaView cuentabancariaViewSelected;
	private Cuentabancaria cuentabancaria;
	
	private BigDecimal interesCuenta;
	private BigDecimal totalCuenta;
	private Integer periodoCuenta;
	private BigDecimal treaCuenta;
	private BigDecimal teaCuenta;

	private Integer periodoRenovacion;
	private BigDecimal montoRenovacion;
	private BigDecimal teaRenovacion;
	private BigDecimal treaRenovacion;
	private Date fechaAperturaRenovacion;
	private Date fechaCierreRenovacion;
	private BigDecimal interesRenovacion;
	private BigDecimal totalRenovacion;
	
	private Cuentabancaria cuentaPlazofijoCreado;
	
	@Inject CajaBean cajaBean;
	@Inject Caja caja;
	
	/*datos de cuenta para persona natural*/
	private Personanatural personanatural;
	private Personajuridica personajuridica;
	private boolean renderPersonanatural;
	private boolean renderPersonajuridica;
	/**/
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private TasainteresServiceLocal tasainteresServiceLocal;
	@EJB private SocioServiceLocal serviceLocal;
	@EJB private CuentabancariaTipotasaDAO cuentabancariaTipotasaDAO;
	
	public RenovarCuentaplazofijoBean() {
		cuentaValida = true;
		cuentaCreada = false;
		dlgBusquedaOpen = false;
		fechaAperturaRenovacion = Calendar.getInstance().getTime();
		fechaCierreRenovacion = Calendar.getInstance().getTime();
		
		renderPersonanatural = false;
		renderPersonajuridica = false;
	}

	@PostConstruct
	private void initialize(){	
		try {
			comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.All_active);
			caja = cajaBean.getCaja();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}		
	}

	public void renovarCuentaaporte(){
		try {
			if(cuentaCreada == false){	
				Cuentabancaria cuentabancaria = new Cuentabancaria();
				cuentabancaria.setIdcuentabancaria(cuentabancariaViewSelected.getIdCuentabancaria());
				BigDecimal teaReal = teaRenovacion.divide(new BigDecimal(100));
				BigDecimal treaReal = treaRenovacion.divide(new BigDecimal(100));
				
				cuentaPlazofijoCreado = cuentabancariaServiceLocal.renovarCuentaplazofijo(cuentabancaria,periodoRenovacion, teaReal, treaReal, caja);				
				cuentaCreada = true;
				cargarContrato();
			} else {
				cargarContrato();
			}
		} catch (Exception e) {
			this.cuentaValida = false;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cargarContrato(){
		try {
			Socio socio = cuentaPlazofijoCreado.getSocio();
			this.personanatural = socio.getPersonanatural();
			this.personajuridica = socio.getPersonajuridica();
			if(personanatural != null){
				renderPersonanatural = true;
			}
			if(personajuridica != null){
				renderPersonajuridica = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void buscarCuentabancaria(){
		List<CuentabancariaView> cuentabancariaViews;
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
		try {
			cuentabancariaViews = cuentabancariaServiceLocal.findCuentabancariaView(TipocuentabancariaType.CUENTA_PLAZO_FIJO, tipodocumento, campoBusqueda);
			tablaCuentabancaria.setRows(cuentabancariaViews);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setCuentabancariaSelected(){
		if(cuentabancariaViewSelected != null){
			try {
				this.cuentabancaria = cuentabancariaServiceLocal.find(cuentabancariaViewSelected.getIdCuentabancaria());
				
				interesCuenta = cuentabancariaServiceLocal.getInteresGeneradoPlazofijo(cuentabancariaViewSelected.getIdCuentabancaria());
				totalCuenta = interesCuenta.add(cuentabancariaViewSelected.getSaldoCuentabancaria());
				
				Calendar calStart = Calendar.getInstance();
				Calendar calEnd = Calendar.getInstance();
				calStart.setTime(cuentabancariaViewSelected.getFechaaperturaCuentabancaria());
				calEnd.setTime(cuentabancariaViewSelected.getFechacierreCuentabancaria());
				
				calStart.set(Calendar.HOUR, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				calStart.set(Calendar.MILLISECOND, 0);
				
				calEnd.set(Calendar.HOUR, 0);
				calEnd.set(Calendar.MINUTE, 0);
				calEnd.set(Calendar.SECOND, 0);
				calEnd.set(Calendar.MILLISECOND, 0);
				
				long milis1 = calStart.getTimeInMillis();
				long milis2 = calEnd.getTimeInMillis();	
				long diff = milis2 - milis1;
				
				periodoCuenta = (int) (diff / (24 * 60 * 60 * 1000));
				montoRenovacion = new BigDecimal(totalCuenta.toString());
				
				//cargar las tasas de interes para la cuenta
				this.teaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TEA, cuentabancariaViewSelected.getIdCuentabancaria());
				this.treaCuenta = cuentabancariaServiceLocal.getTasainteres(TipotasaCuentasPersonalesType.TREA, cuentabancariaViewSelected.getIdCuentabancaria());
				
				teaCuenta = teaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				treaCuenta = treaCuenta.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
				
				teaCuenta = teaCuenta.setScale(2);
				treaCuenta = treaCuenta.setScale(2);
			} catch (Exception e) {
				JsfUtil.addErrorMessage(e, e.getMessage());
				e.printStackTrace();
			}
		}	
		setDlgBusquedaOpen(false);
	}
	
	public void calcularInteresGenerado(){
		BigDecimal result = BigDecimal.ZERO;
		try {
			if(montoRenovacion != null){
				if(periodoRenovacion != null){
					if(teaRenovacion != null){
						BigDecimal teaReal = teaRenovacion.divide(new BigDecimal(100));
						result = tasainteresServiceLocal.getInteresGeneradoPlazofijo(montoRenovacion, periodoRenovacion, teaReal);
						
						this.interesRenovacion = result;
						this.totalRenovacion = interesRenovacion.add(montoRenovacion);
					}
				}
			}			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}	
	}
	
	public boolean renderPersonanatural(){
		return renderPersonanatural;
	}
	
	public boolean renderPersonajuridica(){
		return renderPersonajuridica;
	}
	
	public void changeTipodocumento(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
	}

	public boolean isCuentaValida() {
		return cuentaValida;
	}

	public void setCuentaValida(boolean cuentaValida) {
		this.cuentaValida = cuentaValida;
	}

	public boolean isCuentaCreada() {
		return cuentaCreada;
	}

	public void setCuentaCreada(boolean cuentaCreada) {
		this.cuentaCreada = cuentaCreada;
	}

	public boolean isDlgBusquedaOpen() {
		return dlgBusquedaOpen;
	}

	public void setDlgBusquedaOpen(boolean dlgBusquedaOpen) {
		this.dlgBusquedaOpen = dlgBusquedaOpen;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public TablaBean<CuentabancariaView> getTablaCuentabancaria() {
		return tablaCuentabancaria;
	}

	public void setTablaCuentabancaria(
			TablaBean<CuentabancariaView> tablaCuentabancaria) {
		this.tablaCuentabancaria = tablaCuentabancaria;
	}

	public CuentabancariaView getCuentabancariaViewSelected() {
		return cuentabancariaViewSelected;
	}

	public void setCuentabancariaViewSelected(
			CuentabancariaView cuentabancariaViewSelected) {
		this.cuentabancariaViewSelected = cuentabancariaViewSelected;
	}

	public BigDecimal getInteresCuenta() {
		return interesCuenta;
	}

	public void setInteresCuenta(BigDecimal interesCuenta) {
		this.interesCuenta = interesCuenta;
	}

	public BigDecimal getTotalCuenta() {
		return totalCuenta;
	}

	public void setTotalCuenta(BigDecimal totalCuenta) {
		this.totalCuenta = totalCuenta;
	}

	public Integer getPeriodoCuenta() {
		return periodoCuenta;
	}

	public void setPeriodoCuenta(Integer periodoCuenta) {
		this.periodoCuenta = periodoCuenta;
	}

	public Integer getPeriodoRenovacion() {
		return periodoRenovacion;
	}

	public void setPeriodoRenovacion(Integer periodoRenovacion) {
		this.periodoRenovacion = periodoRenovacion;
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(fechaCierreRenovacion);
		calEnd.add(Calendar.DAY_OF_MONTH, periodoRenovacion);	
		this.fechaCierreRenovacion = calEnd.getTime();
	}

	public BigDecimal getMontoRenovacion() {
		return montoRenovacion;
	}

	public void setMontoRenovacion(BigDecimal montoRenovacion) {
		this.montoRenovacion = montoRenovacion;
	}

	public BigDecimal getTeaRenovacion() {
		return teaRenovacion;
	}

	public void setTeaRenovacion(BigDecimal teaRenovacion) {
		this.teaRenovacion = teaRenovacion;
	}

	public BigDecimal getTreaRenovacion() {
		return treaRenovacion;
	}

	public void setTreaRenovacion(BigDecimal treaRenovacion) {
		this.treaRenovacion = treaRenovacion;
	}

	public Cuentabancaria getCuentaPlazofijoCreado() {
		return cuentaPlazofijoCreado;
	}

	public void setCuentaPlazofijoCreado(Cuentabancaria cuentaPlazofijoCreado) {
		this.cuentaPlazofijoCreado = cuentaPlazofijoCreado;
	}

	public Date getFechaAperturaRenovacion() {
		return fechaAperturaRenovacion;
	}

	public void setFechaAperturaRenovacion(Date fechaAperturaRenovacion) {
		this.fechaAperturaRenovacion = fechaAperturaRenovacion;
	}

	public Date getFechaCierreRenovacion() {
		return fechaCierreRenovacion;
	}

	public void setFechaCierreRenovacion(Date fechaCierreRenovacion) {
		this.fechaCierreRenovacion = fechaCierreRenovacion;
	}

	public BigDecimal getInteresRenovacion() {
		return interesRenovacion;
	}

	public void setInteresRenovacion(BigDecimal interesRenovacion) {
		this.interesRenovacion = interesRenovacion;
	}

	public BigDecimal getTotalRenovacion() {
		return totalRenovacion;
	}

	public void setTotalRenovacion(BigDecimal totalRenovacion) {
		this.totalRenovacion = totalRenovacion;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

	public BigDecimal getTreaCuenta() {
		return treaCuenta;
	}

	public void setTreaCuenta(BigDecimal treaCuenta) {
		this.treaCuenta = treaCuenta;
	}

	public BigDecimal getTeaCuenta() {
		return teaCuenta;
	}

	public void setTeaCuenta(BigDecimal teaCuenta) {
		this.teaCuenta = teaCuenta;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

}
