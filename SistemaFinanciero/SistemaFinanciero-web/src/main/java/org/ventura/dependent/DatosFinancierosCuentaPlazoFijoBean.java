package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.entity.GeneratedEstadocuenta;
import org.ventura.entity.GeneratedTiposervicio;
import org.ventura.entity.GeneratedTipotasaPasiva;
import org.ventura.entity.GeneratedTiposervicio.TiposervicioType;
import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.schema.cuentapersonal.Cuentaplazofijo;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.GeneratedTipotasaPasiva.TipotasaPasivaType;
import org.ventura.entity.schema.cuentapersonal.Frecuenciacapitalizacion;
import org.ventura.entity.schema.cuentapersonal.Retirointeres;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;



@Named
@Dependent
public class DatosFinancierosCuentaPlazoFijoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;
	@EJB
	private TasainteresServiceLocal tasainteresServiceLocal;
	@Inject
	private Cuentaplazofijo cuentaplazofijo;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;	
	@Inject
	private ComboBean<Frecuenciacapitalizacion> combofrecuenciacapitalizacion;	
	@Inject
	private ComboBean<Retirointeres> comboretirointeres;	
	@Inject
	@GeneratedEstadocuenta(strategy = EstadocuentaType.ACTIVO)
	private Estadocuenta estadocuenta;
	
	@Inject
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_PLAZO_FIJO)
	private Tiposervicio tiposervicio;

	@Inject
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TICEAF)
	private Tipotasa tipotasaTICEAF;
	
	@Inject
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TREA)
	private Tipotasa tipotasaTREA;
	
	@Inject
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.ITF)
	private Tipotasa tipotasaITF;
	
	/*
	 * Constructor
	 */

	@PostConstruct
	private void initValues() throws Exception{
		this.cuentaplazofijo.setEstadocuenta(estadocuenta);	
		this.cuentaplazofijo.setFechaapertura(Calendar.getInstance().getTime());
			
		cargarCombos();
			
	}

	/*
	 * Bussiness Logic
	 */
	public void cargartasasinteres() throws Exception{
		Double ticeaf = tasainteresServiceLocal.getTasainteres(tiposervicio, tipotasaTICEAF, new Double(cuentaplazofijo.getMonto()));
		this.cuentaplazofijo.setTiceaf(ticeaf);
		Double trea = tasainteresServiceLocal.getTasainteres(tiposervicio, tipotasaTREA, new Double(cuentaplazofijo.getMonto()));
		this.cuentaplazofijo.setTrea(trea);
		Double itf = tasainteresServiceLocal.getTasainteres(tiposervicio, tipotasaITF, new Double(cuentaplazofijo.getMonto()));
		this.cuentaplazofijo.setItf(itf);
		mostrardatos();
	}
	
	public void mostrardatos(){
		calcularMontoInteres();
		calcularFechavencimientoContrato();
	}
	
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
			return "CONFIRMAR SALDOS";
		return "NO CONFIRMAR SALDOS";
	}
	
	public void calcularFechavencimientoContrato(){
		Date fecha= new Date();
		fecha=Calendar.getInstance().getTime();
		int dias =cuentaplazofijo.getPlazo();
		    Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(fecha.getTime());
	        cal.add(Calendar.DATE, dias);
	        
	        this.cuentaplazofijo.setFechavencimiento(new Date(cal.getTimeInMillis()));	
	}
	
	public void calcularMontoInteres(){
		this.cuentaplazofijo.setMontointerespagado(cuentaplazofijo.getMonto()*cuentaplazofijo.getTiceaf());
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
