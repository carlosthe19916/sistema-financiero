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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.entity.GeneratedEstadocuenta;
import org.ventura.entity.GeneratedTiposervicio;
import org.ventura.entity.GeneratedTipotasaPasiva;
import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.GeneratedTiposervicio.TiposervicioType;
import org.ventura.entity.GeneratedTipotasaPasiva.TipotasaPasivaType;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Cuentacorrientehistorial;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;

@Named
@Dependent
public class DatosFinancierosCuentaCorrienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CuentacorrienteServiceLocal cuentacorrienteServiceLocal;
	@EJB
	private TasainteresServiceLocal tasainteresServiceLocal;
	
	@Inject
	private Cuentacorriente cuentacorriente;
	@Inject
	private Cuentacorrientehistorial cuentacorrientehistorial;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	@GeneratedEstadocuenta(strategy = EstadocuentaType.ACTIVO)
	private Estadocuenta estadocuenta;

	@Inject
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_CORRIENTE)
	private Tiposervicio tiposervicio;
	@Inject
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TICC)
	private Tipotasa tipotasa;
	
	@PostConstruct
	private void initValues() throws Exception{	
		
		this.cuentacorriente.setEstadocuenta(estadocuenta);
		this.cuentacorriente.addCuentacorrientehistorial(cuentacorrientehistorial);
		this.cuentacorriente.setFechaapertura(Calendar.getInstance().getTime());

		Double tasainteres = tasainteresServiceLocal.getTasainteres(tiposervicio, tipotasa, new Double(5));
		this.cuentacorrientehistorial.setTasainteres(tasainteres);
		this.cuentacorrientehistorial.setEstado(true);
		this.cuentacorrientehistorial.setCuentacorriente(cuentacorriente);
		cargarCombos();	
	}

	public boolean isValid() {
		return cuentacorriente.getIdtipomoneda() != null ? true : false;
	}

	private void cargarCombos() {
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.cuentacorriente.setTipomoneda(tipomonedaSelected);
	}

	public Cuentacorriente getCuentacorriente() {
		return cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public Cuentacorrientehistorial getCuentacorrientehistorial() {
		return cuentacorrientehistorial;
	}

	public void setCuentacorrientehistorial(Cuentacorrientehistorial cuentacorrientehistorial) {
		this.cuentacorrientehistorial = cuentacorrientehistorial;
	}

	public String fechaActual() {
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
