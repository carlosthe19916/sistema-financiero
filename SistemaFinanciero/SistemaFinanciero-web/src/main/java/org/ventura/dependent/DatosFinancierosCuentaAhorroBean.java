package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.entity.TiposervicioType;
import org.ventura.entity.TipotasaPasivaType;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorrohistorial;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;

@Named
@Dependent
public class DatosFinancierosCuentaAhorroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CuentaahorroServiceLocal cuentaahorroServiceLocal;
	@EJB
	private TasainteresServiceLocal tasainteresServiceLocal;
	@Inject
	private Cuentaahorro cuentaahorro;
	@Inject
	private Cuentaahorrohistorial cuentaahorrohistorial;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;

	@PostConstruct
	private void initValues() throws Exception {
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("Activo");
		estadocuenta.setIdestadocuenta(1);
		estadocuenta.setEstado(true);		
		this.cuentaahorro.setEstadocuenta(estadocuenta);	
		this.cuentaahorro.addCuentaahorrohistorial(cuentaahorrohistorial);
		this.cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
		
		Double tasainteres = tasainteresServiceLocal.getTasainteres(TiposervicioType.CUENTA_AHORRO, TipotasaPasivaType.TICAH, new Double(0));
		
		this.cuentaahorrohistorial.setCantidadretirantes(0);
		this.cuentaahorrohistorial.setEstado(true);
		this.cuentaahorrohistorial.setTasainteres(tasainteres);
		this.cuentaahorrohistorial.setCuentaahorro(cuentaahorro);
	
		cargarCombos();
		
		
	}

	private void cargarCombos(){
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public boolean isValid(){
		return cuentaahorro.getIdtipomoneda() != null ? true : false;
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentaahorro.setTipomoneda(tipomonedaSelected);
	}
	
	public Cuentaahorro getCuentaahorro() {
		return cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public Cuentaahorrohistorial getCuentaahorrohistorial() {
		return cuentaahorrohistorial;
	}

	public void setCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		this.cuentaahorrohistorial = cuentaahorrohistorial;
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
