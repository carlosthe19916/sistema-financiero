package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;

@Named
@Dependent
public class DatosFinancierosCuentaAporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaaporteServiceLocal cuentaaporteServiceLocal;

	private Cuentaaporte cuentaaporte;


	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;

	/*
	 * Constructor
	 */
	public DatosFinancierosCuentaAporteBean() {
		this.cuentaaporte = new Cuentaaporte();
		Estadocuenta estadocuenta =new Estadocuenta();
		estadocuenta.setIdestadocuenta(1);
		estadocuenta.setEstado(true);
		this.cuentaaporte.setEstadocuenta(estadocuenta);
		
		Tipomoneda tipomoneda =new Tipomoneda();
		tipomoneda.setIdtipomoneda(1);
		tipomoneda.setDenominacion("NUEVOS SOLES");
		this.cuentaaporte.setTipomoneda(tipomoneda);
		
	}

	@PostConstruct
	private void initValues() {
		
	//	cargarCombos();		
		
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

	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

}