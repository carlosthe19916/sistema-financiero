package org.venturabank.managedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Cuentacorrientehistorial;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;


@ManagedBean
@NoneScoped
public class DatosFinancierosCuentaPlazoFijoMB implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;

	private Cuentaplazofijo cuentaplazofijo;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaPlazoFijoMB() {
		this.cuentaplazofijo = new Cuentaplazofijo();
				
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("Activo");
		estadocuenta.setIdestadocuenta(1);
		
		this.cuentaplazofijo.setEstadocuenta(estadocuenta);
	}

	@PostConstruct
	private void initValues() {
		
		cargarCombos();				
	}

	/*
	 * Bussiness Logic
	 */
	
	public boolean isValid(){
		return cuentaplazofijo.getIdtipomoneda() != null ? true : false;
	}
	
	private void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentaplazofijo.setTipomoneda(tipomonedaSelected);
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

	public ComboMB<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboMB<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

}
