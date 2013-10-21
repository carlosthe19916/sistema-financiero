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

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Cuentacorrientehistorial;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;

@ManagedBean
@NoneScoped
public class DatosFinancierosCuentaCorrienteMB implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentacorrienteServiceLocal cuentacorrienteServiceLocal;

	private Cuentacorriente cuentacorriente;
	
	private Cuentacorrientehistorial cuentacorrientehistorial;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaCorrienteMB() {
		this.cuentacorriente = new Cuentacorriente();
		this.cuentacorrientehistorial = new Cuentacorrientehistorial();	
		this.cuentacorrientehistorial.setTasainteres(5);
		this.cuentacorrientehistorial.setEstado(true);
		this.cuentacorrientehistorial.setCuentacorriente(cuentacorriente);
		
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("Activo");
		estadocuenta.setIdestadocuenta(1);
		
		this.cuentacorriente.setEstadocuenta(estadocuenta);
	}

	@PostConstruct
	private void initValues() {
		
		cargarCombos();
		
		List<Cuentacorrientehistorial> listCuentacorrientehistorial = new ArrayList<Cuentacorrientehistorial>();
		this.cuentacorriente.setCuentacorrientehistorials(listCuentacorrientehistorial);
		
		this.cuentacorriente.addCuentacorrientehistorial(cuentacorrientehistorial);
	}

	/*
	 * Bussiness Logic
	 */
	
	public boolean isValid(){
		return cuentacorriente.getIdtipomoneda() != null ? true : false;
	}
	
	private void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentacorriente.setTipomoneda(tipomonedaSelected);
	}

	/*
	 * Getters and Setters
	 */
	public Cuentacorriente getCuentacorriente() {
		return cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public ComboMB<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboMB<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public Cuentacorrientehistorial getCuentacorrientehistorial() {
		return cuentacorrientehistorial;
	}

	public void setCuentacorrientehistorial(
			Cuentacorrientehistorial cuentacorrientehistorial) {
		this.cuentacorrientehistorial = cuentacorrientehistorial;
	}
	
	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

}
