package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;
import org.venturabank.util.ComboMB;


@Named
@Dependent
public class DatosFinancierosCuentaPlazoFijoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;

	private Cuentaplazofijo cuentaplazofijo;

	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaPlazoFijoBean() {
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

	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

}
