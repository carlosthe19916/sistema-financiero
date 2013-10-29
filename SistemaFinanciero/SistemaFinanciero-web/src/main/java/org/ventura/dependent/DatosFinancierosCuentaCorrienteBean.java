package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Cuentacorrientehistorial;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;

@Named
@Dependent
public class DatosFinancierosCuentaCorrienteBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentacorrienteServiceLocal cuentacorrienteServiceLocal;

	private Cuentacorriente cuentacorriente;
	
	private Cuentacorrientehistorial cuentacorrientehistorial;

	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaCorrienteBean() {
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

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

}
