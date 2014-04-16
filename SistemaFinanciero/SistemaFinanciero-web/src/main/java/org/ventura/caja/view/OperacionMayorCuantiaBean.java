package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccionmayorcuantia;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.maestro.Ubigeo;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.tipodato.Moneda;
import org.venturabank.util.JsfUtil;

@Named
@Dependent
public class OperacionMayorCuantiaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject Transaccionmayorcuantia transaccionmayorcuantia;
	
	// datos de la operacion
	private Date fecha;
	
	private Tipotransaccion tipotransaccion;
	private String cuentaBeneficiario;
	private Tipomoneda tipomoneda;
	private BigDecimal monto;
	private String origenImporte;
	private String propositoImporte;

	// solicitante
	private @Inject ComboBean<Tipodocumento> comboTipodocumentoSolicitante;
	private String numerodocumentoSolicitante;
	private String nacionalidadSolicitante;
	private String apellidosnombresSolicitante;
	private String direccionSolicitante;
	private Ubigeo ubigeoSolicitante;
	private Map<String, String> mapDepartamentosSolicitante;
	private Map<String, String> mapProvinciasSolicitante;
	private Map<String, String> mapDistritoSolicitante;	
	private String idDepartamentoSelectedSolicitante;
	private String idProvinciaSelectedSolicitante;
	private String idDistritoSelectedSolicitante;
	
	private String telefonoSolicitante;
	private Date fechanacimientoSolicitante;
	private String ocupacionSolicitante;

	//beneficiario
	private Tipodocumento tipodocumentoBeneficiario;
	private String numerodocumentoBeneficiario;
	private String nacionalidadBeneficiario;
	private String apellidosnombresRazonsocialBeneficiario;
	private String direccionBeneficiario;
	private Ubigeo ubigeoBeneficiario;
	private String telefonoBeneficiario;
	private Date fechanacimientoConstitucionBeneficiario;
	private String ocupacionActividadEconomicaBeneficiario;
	private Map<String, String> mapDepartamentosBeneficiario;
	private Map<String, String> mapProvinciasBeneficiario;
	private Map<String, String> mapDistritoBeneficiario;	
	private String idDepartamentoSelectedBeneficiario;
	private String idProvinciaSelectedBeneficiario;
	private String idDistritoSelectedBeneficiario;

	//ordenante
	private @Inject ComboBean<Tipodocumento> comboTipodocumentoOrdenante;
	private String numerodocumentoOrdenante;
	private String nacionalidadOrdenante;
	private String apellidosYnombresOrdenante;
	private String direccionOrdenante;
	private Ubigeo ubigeoOrdenante;
	private String telefonoOrdenante;
	private Date fechanacimientoConstitucionOrdenante;
	private String ocupacionActividadEconomicaOrdenante;
	private Map<String, String> mapDepartamentosOrdenante;
	private Map<String, String> mapProvinciasOrdenante;
	private Map<String, String> mapDistritoOrdenante;	
	private String idDepartamentoSelectedOrdenante;
	private String idProvinciaSelectedOrdenante;
	private String idDistritoSelectedOrdenante;

	@EJB
	private MaestrosServiceLocal maestrosServiceLocal;
	
	@EJB PersonanaturalServiceLocal personanaturalServiceLocal;

	public OperacionMayorCuantiaBean() {
		// TODO Auto-generated constructor stub
		mapDepartamentosSolicitante = new HashMap<String, String>();
		mapProvinciasSolicitante = new HashMap<String, String>();
		mapDistritoSolicitante = new HashMap<String, String>();
		mapDepartamentosBeneficiario = new HashMap<String, String>();
		mapProvinciasBeneficiario = new HashMap<String, String>();
		mapDistritoBeneficiario = new HashMap<String, String>();
		mapDepartamentosOrdenante = new HashMap<String, String>();
		mapProvinciasOrdenante = new HashMap<String, String>();
		mapDistritoOrdenante = new HashMap<String, String>();
		
		Calendar calendar = Calendar.getInstance();
		fecha = calendar.getTime();
	}

	@PostConstruct
	public void initialize() throws Exception {
		List<Tipodocumento> listTipodocumento = maestrosServiceLocal.getTipodocumentoForPersonaNatural();
		comboTipodocumentoSolicitante.setItems(listTipodocumento);
		comboTipodocumentoOrdenante.setItems(new ArrayList<>(listTipodocumento));
		
		Map<String, String> mapDepartamentosNoOrdenados = new HashMap<String, String>();
		List<Ubigeo> listDepartamentos = maestrosServiceLocal.getDepartamentos();	
		for (Ubigeo u : listDepartamentos) {
			mapDepartamentosNoOrdenados.put(u.getIdubigeo().substring(0,2), u.getDepartamento());
		}
		//ordenando map departamentos 
		Map<String, String> map = ordenarMap(mapDepartamentosNoOrdenados);
		mapDepartamentosSolicitante = new HashMap<String, String>(map);
		mapDepartamentosBeneficiario = new HashMap<String, String>(map);
		mapDepartamentosOrdenante = new HashMap<String, String>(map);
		
	}

	public Transaccionmayorcuantia getTransaccionmayorcuantiaObject(){
		//datos generales
		transaccionmayorcuantia.setFechaTransaccion(fecha);
		transaccionmayorcuantia.setIdtipotransaccion(tipotransaccion.getIdtipotransaccion());
		transaccionmayorcuantia.setNumerocuenta(cuentaBeneficiario);
		transaccionmayorcuantia.setIdtipomoneda(tipomoneda.getIdtipomoneda());
		transaccionmayorcuantia.setImporte(monto);
		transaccionmayorcuantia.setOrigenimporte(origenImporte);
		transaccionmayorcuantia.setPropositoimporte(propositoImporte);
		//datos solicitante
		transaccionmayorcuantia.setIdtipodocumentoSolicitante(comboTipodocumentoSolicitante.getObjectItemSelected().getIdtipodocumento());
		transaccionmayorcuantia.setNumerodocumentoSolicitante(numerodocumentoSolicitante);
		transaccionmayorcuantia.setNacionalidadSolicitante(nacionalidadSolicitante);
		transaccionmayorcuantia.setApellidosNombresSolicitante(apellidosnombresSolicitante);
		transaccionmayorcuantia.setDireccionSolicitante(direccionSolicitante);
		transaccionmayorcuantia.setUbigeoSolicitante(idDepartamentoSelectedSolicitante+idProvinciaSelectedSolicitante+idDistritoSelectedSolicitante);
		transaccionmayorcuantia.setTelefonoSolicitante(telefonoSolicitante);
		transaccionmayorcuantia.setFechaNacimientoSolicitante(fechanacimientoSolicitante);
		transaccionmayorcuantia.setOcupacionSolicitante(ocupacionSolicitante);
		//datos beneficiario	
		transaccionmayorcuantia.setIdtipodocumentoBeneficiario(tipodocumentoBeneficiario.getIdtipodocumento());
		transaccionmayorcuantia.setNumerodocumentoBeneficiario(numerodocumentoBeneficiario);;
		transaccionmayorcuantia.setNacionalidadBeneficiario(nacionalidadBeneficiario);
		transaccionmayorcuantia.setApellidosnombresRazonsocialBeneficiario(apellidosnombresRazonsocialBeneficiario);
		transaccionmayorcuantia.setDireccionBeneficiario(direccionBeneficiario);
		transaccionmayorcuantia.setUbigeoBeneficiario(idDepartamentoSelectedBeneficiario+idProvinciaSelectedBeneficiario+idDistritoSelectedBeneficiario);
		transaccionmayorcuantia.setTelefonoBeneficiario(telefonoBeneficiario);
		transaccionmayorcuantia.setFechanacimientoConstitucionBeneficiario(fechanacimientoConstitucionBeneficiario);
		transaccionmayorcuantia.setOcupacionActividadeconomicaBeneficiario(ocupacionActividadEconomicaBeneficiario);
		//datos del ordenante
		transaccionmayorcuantia.setIdtipodocumentoOrdenante(comboTipodocumentoOrdenante.getObjectItemSelected().getIdtipodocumento());
		transaccionmayorcuantia.setNumerodocumentoOrdenante(numerodocumentoOrdenante);
		transaccionmayorcuantia.setNacionalidadOrdenante(nacionalidadOrdenante);
		transaccionmayorcuantia.setApellidosnombresRazonsocialOrdenante(apellidosYnombresOrdenante);
		transaccionmayorcuantia.setDireccionOrdenante(direccionOrdenante);
		transaccionmayorcuantia.setUgibeoOrdenante(idDepartamentoSelectedOrdenante+idProvinciaSelectedOrdenante+idDistritoSelectedOrdenante);
		transaccionmayorcuantia.setTelefonoOrdenante(telefonoOrdenante);
		transaccionmayorcuantia.setFechanacimientoConstitucionOrdenante(fechanacimientoConstitucionOrdenante);
		transaccionmayorcuantia.setOcupacionActividadeconomicaOrdenante(ocupacionActividadEconomicaOrdenante);
				
		return this.transaccionmayorcuantia;
	}
	
	public Map<String, String> ordenarMap(Map<String, String> map){
		HashMap<String, String> mapOrdenado = new LinkedHashMap<String, String>();
		List<String> mapKeys = new ArrayList<>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		
		TreeSet<String> conjuntoOrdenado = new TreeSet<String>(mapValues);
		String[] arrayOrdenado = conjuntoOrdenado.toArray(new String[conjuntoOrdenado.size()]);
		for (int i = 0; i < arrayOrdenado.length; i++) {
			mapOrdenado.put(mapKeys.get(mapValues.indexOf(arrayOrdenado[i])), arrayOrdenado[i]);
		}
		return mapOrdenado;
	}
	
	public void actualizarProvinciasSolicitante(){
		Map<String, String> mapProvinciasNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedSolicitante);
			List<Ubigeo> list = maestrosServiceLocal.getProvincias(ubigeo);
			mapProvinciasNoOrdenados.clear();
			mapProvinciasSolicitante.clear();
			for (Ubigeo u : list) {
				mapProvinciasNoOrdenados.put(u.getIdubigeo().substring(2, 4), u.getProvincia());
			}
			
			//ordenar map provincias
			mapProvinciasSolicitante = ordenarMap(mapProvinciasNoOrdenados);
			
			idProvinciaSelectedSolicitante = null;
			mapDistritoSolicitante.clear();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarDistritosSolicitante(){
		Map<String, String> mapDistritosNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedSolicitante+idProvinciaSelectedSolicitante);
			List<Ubigeo> list = maestrosServiceLocal.getDistritos(ubigeo);
			mapDistritosNoOrdenados.clear();
			mapDistritoSolicitante.clear();
			for (Ubigeo u : list) {
				mapDistritosNoOrdenados.put(u.getIdubigeo().substring(4, 6), u.getDistrito());
			}
			
			//ordenar map distritos
			mapDistritoSolicitante = ordenarMap(mapDistritosNoOrdenados);
			
			idDistritoSelectedSolicitante = null;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarProvinciasBeneficiario(){
		Map<String, String> mapProvinciasNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedBeneficiario);
			List<Ubigeo> list = maestrosServiceLocal.getProvincias(ubigeo);
			mapProvinciasNoOrdenados.clear();
			mapProvinciasBeneficiario.clear();
			for (Ubigeo u : list) {
				mapProvinciasNoOrdenados.put(u.getIdubigeo().substring(2, 4), u.getProvincia());
			}
			
			//ordenar map provincias
			mapProvinciasBeneficiario = ordenarMap(mapProvinciasNoOrdenados);
			
			idProvinciaSelectedBeneficiario = null;
			mapDistritoBeneficiario.clear();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarDistritosBeneficiario(){
		Map<String, String> mapDistritosNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedBeneficiario+idProvinciaSelectedBeneficiario);
			List<Ubigeo> list = maestrosServiceLocal.getDistritos(ubigeo);
			mapDistritosNoOrdenados.clear();
			mapDistritoBeneficiario.clear();
			for (Ubigeo u : list) {
				mapDistritosNoOrdenados.put(u.getIdubigeo().substring(4, 6), u.getDistrito());
			}
			
			//ordenar map distritos
			mapDistritoBeneficiario = ordenarMap(mapDistritosNoOrdenados);
			
			idDistritoSelectedBeneficiario = null;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarProvinciasOrdenante(){
		Map<String, String> mapProvinciasNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedOrdenante);
			List<Ubigeo> list = maestrosServiceLocal.getProvincias(ubigeo);
			mapProvinciasNoOrdenados.clear();
			mapProvinciasOrdenante.clear();
			for (Ubigeo u : list) {
				mapProvinciasNoOrdenados.put(u.getIdubigeo().substring(2, 4), u.getProvincia());
			}
			
			//ordenar map provincias
			mapProvinciasOrdenante = ordenarMap(mapProvinciasNoOrdenados);
			
			idProvinciaSelectedOrdenante = null;
			mapDistritoOrdenante.clear();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarDistritosOrdenante(){
		Map<String, String> mapDistritosNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelectedOrdenante+idProvinciaSelectedOrdenante);
			List<Ubigeo> list = maestrosServiceLocal.getDistritos(ubigeo);
			mapDistritosNoOrdenados.clear();
			mapDistritoOrdenante.clear();
			for (Ubigeo u : list) {
				mapDistritosNoOrdenados.put(u.getIdubigeo().substring(4, 6), u.getDistrito());
			}
			
			//ordenar map distritos
			mapDistritoOrdenante = ordenarMap(mapDistritosNoOrdenados);
			
			idDistritoSelectedOrdenante = null;
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void buscarPersonanaturalSolicitante(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoSolicitante.getObjectItemSelected();
			String numeroDocumento = numerodocumentoSolicitante;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				
				this.comboTipodocumentoSolicitante.setItemSelected(personaNatural.getTipodocumento());
				this.numerodocumentoSolicitante = personaNatural.getNumerodocumento();
				this.apellidosnombresSolicitante = personaNatural.getApellidopaterno() + " " +  personaNatural.getApellidomaterno() + "," + personaNatural.getNombres();				
				this.fechanacimientoSolicitante = personaNatural.getFechanacimiento();				
				this.ocupacionSolicitante = personaNatural.getOcupacion();
				this.direccionSolicitante = personaNatural.getDireccion();
				this.telefonoSolicitante = personaNatural.getTelefono();				
				
			} else {
				this.apellidosnombresSolicitante = "";				
				this.fechanacimientoSolicitante = null;				
				this.ocupacionSolicitante = "";
				this.direccionSolicitante = "";
				this.telefonoSolicitante = "";	
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void buscarPersonanaturalOrdenanate(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoOrdenante.getObjectItemSelected();
			String numeroDocumento = numerodocumentoOrdenante;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				
				this.comboTipodocumentoOrdenante.setItemSelected(personaNatural.getTipodocumento());
				this.numerodocumentoOrdenante = personaNatural.getNumerodocumento();
				this.apellidosYnombresOrdenante = personaNatural.getApellidopaterno() + " " +  personaNatural.getApellidomaterno() + "," + personaNatural.getNombres();				
				this.fechanacimientoConstitucionOrdenante = personaNatural.getFechanacimiento();				
				this.ocupacionActividadEconomicaOrdenante = personaNatural.getOcupacion();
				this.direccionOrdenante = personaNatural.getDireccion();
				this.telefonoOrdenante = personaNatural.getTelefono();				
				
			} else {
				this.apellidosYnombresOrdenante = "";			
				this.fechanacimientoConstitucionOrdenante = null;			
				this.ocupacionActividadEconomicaOrdenante = "";
				this.direccionOrdenante = "";
				this.telefonoOrdenante = "";
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public Personanatural buscarPersonanatural(Tipodocumento tipodocumento, String numeroDocumento){
		Personanatural personanatural = null;
		try {
			personanatural = personanaturalServiceLocal.find(tipodocumento, numeroDocumento);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
		return personanatural;
	}
	
	public String getFechaAsString(){
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	public String getHoraAsString(){
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("hh:mm:ss");
		return sdf.format(fecha);
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Tipotransaccion getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public BigDecimal getMonto() {
		return monto;
	}
	
	public String getMontoAsString() {
		return Moneda.getMonedaFormat(monto);
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getOrigenImporte() {
		return origenImporte;
	}

	public void setOrigenImporte(String origenImporte) {
		this.origenImporte = origenImporte;
	}

	public String getPropositoImporte() {
		return propositoImporte;
	}

	public void setPropositoImporte(String propositoImporte) {
		this.propositoImporte = propositoImporte;
	}

	public String getNumerodocumentoSolicitante() {
		return numerodocumentoSolicitante;
	}

	public void setNumerodocumentoSolicitante(String numerodocumentoSolicitante) {
		this.numerodocumentoSolicitante = numerodocumentoSolicitante;
	}

	public String getNacionalidadSolicitante() {
		return nacionalidadSolicitante;
	}

	public void setNacionalidadSolicitante(String nacionalidadSolicitante) {
		this.nacionalidadSolicitante = nacionalidadSolicitante;
	}

	public String getApellidosnombresSolicitante() {
		return apellidosnombresSolicitante;
	}

	public void setApellidosnombresSolicitante(
			String apellidosnombresSolicitante) {
		this.apellidosnombresSolicitante = apellidosnombresSolicitante;
	}

	public String getDireccionSolicitante() {
		return direccionSolicitante;
	}

	public void setDireccionSolicitante(String direccionSolicitante) {
		this.direccionSolicitante = direccionSolicitante;
	}

	public Ubigeo getUbigeoSolicitante() {
		return ubigeoSolicitante;
	}

	public void setUbigeoSolicitante(Ubigeo ubigeoSolicitante) {
		this.ubigeoSolicitante = ubigeoSolicitante;
	}

	public String getTelefonoSolicitante() {
		return telefonoSolicitante;
	}

	public void setTelefonoSolicitante(String telefonoSolicitante) {
		this.telefonoSolicitante = telefonoSolicitante;
	}

	public Date getFechanacimientoSolicitante() {
		return fechanacimientoSolicitante;
	}

	public void setFechanacimientoSolicitante(Date fechanacimientoSolicitante) {
		this.fechanacimientoSolicitante = fechanacimientoSolicitante;
	}

	public String getOcupacionSolicitante() {
		return ocupacionSolicitante;
	}

	public void setOcupacionSolicitante(String ocupacionSolicitante) {
		this.ocupacionSolicitante = ocupacionSolicitante;
	}

	public Tipodocumento getTipodocumentoBeneficiario() {
		return tipodocumentoBeneficiario;
	}

	public void setTipodocumentoBeneficiario(
			Tipodocumento tipodocumentoBeneficiario) {
		this.tipodocumentoBeneficiario = tipodocumentoBeneficiario;
	}

	public String getNumerodocumentoBeneficiario() {
		return numerodocumentoBeneficiario;
	}

	public void setNumerodocumentoBeneficiario(
			String numerodocumentoBeneficiario) {
		this.numerodocumentoBeneficiario = numerodocumentoBeneficiario;
	}

	public String getNacionalidadBeneficiario() {
		return nacionalidadBeneficiario;
	}

	public void setNacionalidadBeneficiario(String nacionalidadBeneficiario) {
		this.nacionalidadBeneficiario = nacionalidadBeneficiario;
	}

	public String getApellidosnombresRazonsocialBeneficiario() {
		return apellidosnombresRazonsocialBeneficiario;
	}

	public void setApellidosnombresRazonsocialBeneficiario(
			String apellidosnombresRazonsocialBeneficiario) {
		this.apellidosnombresRazonsocialBeneficiario = apellidosnombresRazonsocialBeneficiario;
	}

	public String getDireccionBeneficiario() {
		return direccionBeneficiario;
	}

	public void setDireccionBeneficiario(String direccionBeneficiario) {
		this.direccionBeneficiario = direccionBeneficiario;
	}

	public Ubigeo getUbigeoBeneficiario() {
		return ubigeoBeneficiario;
	}

	public void setUbigeoBeneficiario(Ubigeo ubigeoBeneficiario) {
		this.ubigeoBeneficiario = ubigeoBeneficiario;
	}

	public String getTelefonoBeneficiario() {
		return telefonoBeneficiario;
	}

	public void setTelefonoBeneficiario(String telefonoBeneficiario) {
		this.telefonoBeneficiario = telefonoBeneficiario;
	}

	public Date getFechanacimientoConstitucionBeneficiario() {
		return fechanacimientoConstitucionBeneficiario;
	}

	public void setFechanacimientoConstitucionBeneficiario(
			Date fechanacimientoConstitucionBeneficiario) {
		this.fechanacimientoConstitucionBeneficiario = fechanacimientoConstitucionBeneficiario;
	}

	public String getOcupacionActividadEconomicaBeneficiario() {
		return ocupacionActividadEconomicaBeneficiario;
	}

	public void setOcupacionActividadEconomicaBeneficiario(
			String ocupacionActividadEconomicaBeneficiario) {
		this.ocupacionActividadEconomicaBeneficiario = ocupacionActividadEconomicaBeneficiario;
	}

	public String getNumerodocumentoOrdenante() {
		return numerodocumentoOrdenante;
	}

	public void setNumerodocumentoOrdenante(String numerodocumentoOrdenante) {
		this.numerodocumentoOrdenante = numerodocumentoOrdenante;
	}

	public String getNacionalidadOrdenante() {
		return nacionalidadOrdenante;
	}

	public void setNacionalidadOrdenante(String nacionalidadOrdenante) {
		this.nacionalidadOrdenante = nacionalidadOrdenante;
	}

	public String getApellidosYnombresOrdenante() {
		return apellidosYnombresOrdenante;
	}

	public void setApellidosYnombresOrdenante(String apellidosYnombresOrdenante) {
		this.apellidosYnombresOrdenante = apellidosYnombresOrdenante;
	}

	public String getDireccionOrdenante() {
		return direccionOrdenante;
	}

	public void setDireccionOrdenante(String direccionOrdenante) {
		this.direccionOrdenante = direccionOrdenante;
	}

	public Ubigeo getUbigeoOrdenante() {
		return ubigeoOrdenante;
	}

	public void setUbigeoOrdenante(Ubigeo ubigeoOrdenante) {
		this.ubigeoOrdenante = ubigeoOrdenante;
	}

	public String getTelefonoOrdenante() {
		return telefonoOrdenante;
	}

	public void setTelefonoOrdenante(String telefonoOrdenante) {
		this.telefonoOrdenante = telefonoOrdenante;
	}

	public Date getFechanacimientoConstitucionOrdenante() {
		return fechanacimientoConstitucionOrdenante;
	}

	public void setFechanacimientoConstitucionOrdenante(
			Date fechanacimientoConstitucionOrdenante) {
		this.fechanacimientoConstitucionOrdenante = fechanacimientoConstitucionOrdenante;
	}

	public String getOcupacionActividadEconomicaOrdenante() {
		return ocupacionActividadEconomicaOrdenante;
	}

	public void setOcupacionActividadEconomicaOrdenante(
			String ocupacionActividadEconomicaOrdenante) {
		this.ocupacionActividadEconomicaOrdenante = ocupacionActividadEconomicaOrdenante;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoSolicitante() {
		return comboTipodocumentoSolicitante;
	}

	public void setComboTipodocumentoSolicitante(
			ComboBean<Tipodocumento> comboTipodocumentoSolicitante) {
		this.comboTipodocumentoSolicitante = comboTipodocumentoSolicitante;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public String getIdDepartamentoSelectedSolicitante() {
		return idDepartamentoSelectedSolicitante;
	}

	public void setIdDepartamentoSelectedSolicitante(
			String idDepartamentoSelectedSolicitante) {
		this.idDepartamentoSelectedSolicitante = idDepartamentoSelectedSolicitante;
	}

	public String getIdProvinciaSelectedSolicitante() {
		return idProvinciaSelectedSolicitante;
	}

	public void setIdProvinciaSelectedSolicitante(
			String idProvinciaSelectedSolicitante) {
		this.idProvinciaSelectedSolicitante = idProvinciaSelectedSolicitante;
	}

	public String getIdDistritoSelectedSolicitante() {
		return idDistritoSelectedSolicitante;
	}

	public void setIdDistritoSelectedSolicitante(
			String idDistritoSelectedSolicitante) {
		this.idDistritoSelectedSolicitante = idDistritoSelectedSolicitante;
	}

	public String getIdDepartamentoSelectedBeneficiario() {
		return idDepartamentoSelectedBeneficiario;
	}

	public void setIdDepartamentoSelectedBeneficiario(
			String idDepartamentoSelectedBeneficiario) {
		this.idDepartamentoSelectedBeneficiario = idDepartamentoSelectedBeneficiario;
	}

	public String getIdProvinciaSelectedBeneficiario() {
		return idProvinciaSelectedBeneficiario;
	}

	public void setIdProvinciaSelectedBeneficiario(
			String idProvinciaSelectedBeneficiario) {
		this.idProvinciaSelectedBeneficiario = idProvinciaSelectedBeneficiario;
	}

	public String getIdDistritoSelectedBeneficiario() {
		return idDistritoSelectedBeneficiario;
	}

	public void setIdDistritoSelectedBeneficiario(
			String idDistritoSelectedBeneficiario) {
		this.idDistritoSelectedBeneficiario = idDistritoSelectedBeneficiario;
	}

	public Map<String, String> getMapDepartamentosSolicitante() {
		return mapDepartamentosSolicitante;
	}

	public void setMapDepartamentosSolicitante(
			Map<String, String> mapDepartamentosSolicitante) {
		this.mapDepartamentosSolicitante = mapDepartamentosSolicitante;
	}

	public Map<String, String> getMapProvinciasSolicitante() {
		return mapProvinciasSolicitante;
	}

	public void setMapProvinciasSolicitante(
			Map<String, String> mapProvinciasSolicitante) {
		this.mapProvinciasSolicitante = mapProvinciasSolicitante;
	}

	public Map<String, String> getMapDepartamentosBeneficiario() {
		return mapDepartamentosBeneficiario;
	}

	public void setMapDepartamentosBeneficiario(
			Map<String, String> mapDepartamentosBeneficiario) {
		this.mapDepartamentosBeneficiario = mapDepartamentosBeneficiario;
	}

	public Map<String, String> getMapProvinciasBeneficiario() {
		return mapProvinciasBeneficiario;
	}

	public void setMapProvinciasBeneficiario(
			Map<String, String> mapProvinciasBeneficiario) {
		this.mapProvinciasBeneficiario = mapProvinciasBeneficiario;
	}

	public Map<String, String> getMapDistritoBeneficiario() {
		return mapDistritoBeneficiario;
	}

	public void setMapDistritoBeneficiario(
			Map<String, String> mapDistritoBeneficiario) {
		this.mapDistritoBeneficiario = mapDistritoBeneficiario;
	}

	public Map<String, String> getMapDistritoSolicitante() {
		return mapDistritoSolicitante;
	}

	public void setMapDistritoSolicitante(Map<String, String> mapDistritoSolicitante) {
		this.mapDistritoSolicitante = mapDistritoSolicitante;
	}

	public Map<String, String> getMapDepartamentosOrdenante() {
		return mapDepartamentosOrdenante;
	}

	public void setMapDepartamentosOrdenante(
			Map<String, String> mapDepartamentosOrdenante) {
		this.mapDepartamentosOrdenante = mapDepartamentosOrdenante;
	}

	public Map<String, String> getMapProvinciasOrdenante() {
		return mapProvinciasOrdenante;
	}

	public void setMapProvinciasOrdenante(Map<String, String> mapProvinciasOrdenante) {
		this.mapProvinciasOrdenante = mapProvinciasOrdenante;
	}

	public Map<String, String> getMapDistritoOrdenante() {
		return mapDistritoOrdenante;
	}

	public void setMapDistritoOrdenante(Map<String, String> mapDistritoOrdenante) {
		this.mapDistritoOrdenante = mapDistritoOrdenante;
	}

	public String getIdDepartamentoSelectedOrdenante() {
		return idDepartamentoSelectedOrdenante;
	}

	public void setIdDepartamentoSelectedOrdenante(
			String idDepartamentoSelectedOrdenante) {
		this.idDepartamentoSelectedOrdenante = idDepartamentoSelectedOrdenante;
	}

	public String getIdProvinciaSelectedOrdenante() {
		return idProvinciaSelectedOrdenante;
	}

	public void setIdProvinciaSelectedOrdenante(String idProvinciaSelectedOrdenante) {
		this.idProvinciaSelectedOrdenante = idProvinciaSelectedOrdenante;
	}

	public String getIdDistritoSelectedOrdenante() {
		return idDistritoSelectedOrdenante;
	}

	public void setIdDistritoSelectedOrdenante(String idDistritoSelectedOrdenante) {
		this.idDistritoSelectedOrdenante = idDistritoSelectedOrdenante;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoOrdenante() {
		return comboTipodocumentoOrdenante;
	}

	public void setComboTipodocumentoOrdenante(
			ComboBean<Tipodocumento> comboTipodocumentoOrdenante) {
		this.comboTipodocumentoOrdenante = comboTipodocumentoOrdenante;
	}

}
