package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.dao.impl.RetirointeresDAO;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Frecuenciacapitalizacion;
import org.ventura.entity.Retirointeres;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;


@Named
@Dependent
public class DatosFinancierosCuentaPlazoFijoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;
	@Inject
	private Cuentaplazofijo cuentaplazofijo;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;	
	@Inject
	private ComboBean<Frecuenciacapitalizacion> combofrecuenciacapitalizacion;	
	@Inject
	private ComboBean<Retirointeres> comboretirointeres;
	
	

	/*
	 * Constructor
	 */

	@PostConstruct
	private void initValues() {		
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setIdestadocuenta(1);
		estadocuenta.setDenominacion("ACTIVO");
		estadocuenta.setEstado(true);
		this.cuentaplazofijo.setEstadocuenta(estadocuenta);
		this.cuentaplazofijo.setTiceaf(0.01);
		this.cuentaplazofijo.setTrea(0.01);	
		this.cuentaplazofijo.setItf(0.25);
		this.cuentaplazofijo.setMontointerespagado(12);
		this.cuentaplazofijo.setFechaapertura(Calendar.getInstance().getTime());
		cargarCombos();	
	}

	/*
	 * Bussiness Logic
	 */
	
	public boolean isValid(){
		return cuentaplazofijo.getIdtipomoneda() != null ? true : false;
	}
	
	public void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		comboretirointeres.initValuesFromNamedQueryName(Retirointeres.ALL_ACTIVE);
		combofrecuenciacapitalizacion.initValuesFromNamedQueryName(Frecuenciacapitalizacion.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentaplazofijo.setTipomoneda(tipomonedaSelected);
	}
	public void changeFrecuenciacapitalizacion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Frecuenciacapitalizacion frecuenciacapitalizacionSelected = combofrecuenciacapitalizacion.getObjectItemSelected(key);
		this.cuentaplazofijo.setFrecuenciacapitalizacion(frecuenciacapitalizacionSelected);
	}
	public void changeRetirointeres(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Retirointeres retirointeresSelected = comboretirointeres.getObjectItemSelected(key);
		this.cuentaplazofijo.setRetirointeres(retirointeresSelected);
	}
	

	public String confirmarSaldos(){
		boolean saldos = cuentaplazofijo.isConfirmacionsaldos();
		if(saldos)
			return "Confirmar saldos";
		return "No confirmar saldos";
	}
	
	public void calcularFechavencimientoContrato(){
		Date fecha=cuentaplazofijo.getFechaapertura();
		int dias =cuentaplazofijo.getPlazo();
		    Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(fecha.getTime());
	        cal.add(Calendar.DATE, dias);
	        
	        this.cuentaplazofijo.setFechavencimiento(new Date(cal.getTimeInMillis()));	
	}
	
	public void calcularMontoInteres(){
		this.cuentaplazofijo.setMontointerespagado(cuentaplazofijo.getMonto()*cuentaplazofijo.getTiceaf());
	}
	public void cargarDatos(){
		
		calcularFechavencimientoContrato();
		calcularMontoInteres();
	}

	/*
	 * Getters and Setters
	 */
	public Cuentaplazofijo getCuentaplazofijo() {
		return cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
	}

	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}
	

	
	public double MontoInteresPagado(){
		return cuentaplazofijo.getMonto()*cuentaplazofijo.getTiceaf();
	}

	public ComboBean<Frecuenciacapitalizacion> getCombofrecuenciacapitalizacion() {
		return combofrecuenciacapitalizacion;
	}

	public void setCombofrecuenciacapitalizacion(
			ComboBean<Frecuenciacapitalizacion> combofrecuenciacapitalizacion) {
		this.combofrecuenciacapitalizacion = combofrecuenciacapitalizacion;
	}

	public ComboBean<Retirointeres> getComboretirointeres() {
		return comboretirointeres;
	}

	public void setComboretirointeres(ComboBean<Retirointeres> comboretirointeres) {
		this.comboretirointeres = comboretirointeres;
	}

}
