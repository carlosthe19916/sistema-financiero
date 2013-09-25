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
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;

@ManagedBean
@NoneScoped
public class DatosFinancierosCuentaAhorroMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaahorroServiceLocal cuentaahorroServiceLocal;

	private Cuentaahorro cuentaahorro;
	
	private Cuentaahorrohistorial cuentaahorrohistorial;

	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaAhorroMB() {
		this.cuentaahorro = new Cuentaahorro();
		this.cuentaahorrohistorial = new Cuentaahorrohistorial();	
		this.cuentaahorrohistorial.setTasainteres(5);
		this.cuentaahorrohistorial.setEstado(true);
		this.cuentaahorrohistorial.setCuentaahorro(cuentaahorro);
		
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("Activo");
		estadocuenta.setIdestadocuenta(1);
		
		this.cuentaahorro.setEstadocuenta(estadocuenta);
	}

	@PostConstruct
	private void initValues() {
		
		cargarCombos();
		
		List<Cuentaahorrohistorial> listCuentaahorrohistorial = new ArrayList<Cuentaahorrohistorial>();
		this.cuentaahorro.setCuentaahorrohistorials(listCuentaahorrohistorial);
		
		this.cuentaahorro.addCuentaahorrohistorial(cuentaahorrohistorial);
	}

	/*
	 * Bussiness Logic
	 */
	private void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentaahorro.setTipomoneda(tipomonedaSelected);
	}

	/*
	 * Getters and Setters
	 */
	public Cuentaahorro getCuentaahorro() {
		return cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public ComboMB<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboMB<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public Cuentaahorrohistorial getCuentaahorrohistorial() {
		return cuentaahorrohistorial;
	}

	public void setCuentaahorrohistorial(
			Cuentaahorrohistorial cuentaahorrohistorial) {
		this.cuentaahorrohistorial = cuentaahorrohistorial;
	}
	
	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

}
