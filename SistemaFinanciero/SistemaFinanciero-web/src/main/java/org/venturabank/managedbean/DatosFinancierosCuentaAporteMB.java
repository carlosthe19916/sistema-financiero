package org.venturabank.managedbean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;


import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.entity.Aporte;

import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;

public class DatosFinancierosCuentaAporteMB {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaaporteServiceLocal cuentaaporteServiceLocal;

	private Cuentaaporte cuentaaporte;
	
	private Aporte aporte;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaAporteMB() {
		this.cuentaaporte = new Cuentaaporte();
		this.aporte = new Aporte();	
		this.aporte.setFechaaporte(Calendar.getInstance().getTime());
		this.aporte.setMonto(0);
		this.aporte.setCuentaaporte(cuentaaporte);
		
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("Activo");
		estadocuenta.setIdestadocuenta(1);		
		this.cuentaaporte.setEstadocuenta(estadocuenta);
		
		Tipomoneda tipomoneda = new Tipomoneda();
		tipomoneda.setIdtipomoneda(1);
		tipomoneda.setDenominacion("NUEVOS SOLES");
		this.cuentaaporte.setTipomoneda(tipomoneda);	
		
	}

	@PostConstruct
	private void initValues() {
		
		cargarCombos();
		
		List<Aporte> listAportes = new ArrayList<Aporte>();
		this.cuentaaporte.setAporte(listAportes);
	}

	/*
	 * Bussiness Logic
	 */
	
	public boolean isValid(){
		return cuentaaporte.getIdtipomoneda() != null ? true : false;
	}
	
	private void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentaaporte.setTipomoneda(tipomonedaSelected);
	}

	/*
	 * Getters and Setters
	 */
	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

	public ComboMB<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboMB<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public Aporte getAporte() {
		return aporte;
	}

	public void setAporte(
			Aporte aporte) {
		this.aporte = aporte;
	}
	
	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

}
