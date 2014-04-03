package org.ventura.cuentapersonal.flow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.caja.view.LoginBean;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Titular;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.persona.Tipoempresa;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.session.UsuarioMB;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;

@Named
@FlowScoped("aperturaCuentaplazofijo-flow")
public class AperturaCuentaplazofijoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject private UsuarioMB usuarioMB;
	
	private boolean isPersonanatural;
	private boolean isPersonajuridica;
	
	private boolean cuentaValida;
	private boolean cuentaCreada;
	private String numeroCuenta;
	private int idCuentaPlazoFijoPersonaNatural;
	private int idCuentaPlazoFijoPersonaJuridica;
	private Date fechaApertura;
	private Cuentabancaria cuentabancariaCreada;
	
	// DATOS DE LA VISTA
	// VISTA 01
	@Inject private ComboBean<String> comboTipopersona;
	
	@Inject private ComboBean<Tipomoneda> comboTipomoneda;
	private BigDecimal montoApertura;
	private Integer periodoDeposito;
	//private BigDecimal trea;
	private BigDecimal tea = new BigDecimal("12.00");
	private BigDecimal interesGenerado;
	
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoPersonanatural;
	private String numeroDocumentoPersonanatural;
	private String apellidoPaternoPersonanatural;
	private String apellidoMaternoPersonanatural;
	private String nombresPersonanatural;
	private Date fechaNacimientoPersonanatural;
	@Inject private ComboBean<Sexo> comboSexoPersonanatural;
	@Inject private ComboBean<Estadocivil> comboEstadocivilPersonanatural;
	private String ocupacionPersonanatural;
	private String direccionPersonanatural;
	private String referenciaPersonanatural;
	private String telefonoPersonanatural;
	private String celularPersonanatural;
	private String emailPersonanatural;

	@Inject private ComboBean<Tipodocumento> comboTipodocumentoPersonajuridica;
	private String numeroDocumentoPersonajuridica;	
	private String razonSocial;
	private String nombreComercial;
	private String actividadPrincipal;
	private Date fechaConstitucion;
	@Inject private ComboBean<Tipoempresa> comboTipoempresa;
	@Inject private ComboBean<String> comboFinsocial;
	private String direccionPersonajuridica;
	private String referenciaPersonajuridica;
	private String telefonoPersonajuridica;
	private String celularPersonajuridica;
	private String emailPersonajuridica;

	@Inject private ComboBean<Tipodocumento> comboTipodocumentoRepresentantelegal;
	private String numeroDocumentoRepresentantelegal;
	private String apellidoPaternoRepresentantelegal;
	private String apellidoMaternoRepresentantelegal;
	private String nombresRepresentantelegal;
	private Date fechaNacimientoRepresentantelegal;
	@Inject private ComboBean<Sexo> comboSexoRepresentantelegal;
	
	// VISTA 02
	private boolean isDlgAccionistaOpen;
	private Map<String, Accionista> accionistas;
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoAccionista;
	private String numeroDocumentoAccionista;
	private String apellidoPaternoAccionista;
	private String apellidoMaternoAccionista;
	private String nombresAccionista;
	private Date fechaNacimientoAccionista;
	@Inject private ComboBean<Sexo> comboSexoAccionista;
	private BigDecimal porcentajeParticipacionAccionista;
	
	//vista 03
	private boolean isDlgTitularOpen;
	@Min(value = 1) private Integer cantidadRetirantes;
	private Map<String, Personanatural> titulares;
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoTitular;
	private String numeroDocumentoTitular;
	private String apellidoPaternoTitular;
	private String apellidoMaternoTitular;
	private String nombresTitular;
	private Date fechaNacimientoTitular;
	@Inject private ComboBean<Sexo> comboSexoTitular;
	
	private boolean isDlgBeneficiarioOpen;
	private Map<String, Beneficiario> beneficiarios;
	private String numeroDocumentoBeneficiario;
	private String apellidoPaternoBeneficiario;
	private String apellidoMaternoBeneficiario;
	private String nombresBeneficiario;
	private Integer porcentajeBeneficio;
	
	@Inject private AgenciaBean agenciaBean;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private LoginBean loginBean;
	
	@EJB private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	@EJB private TasainteresServiceLocal tasainteresServiceLocal;
	@EJB private MaestrosServiceLocal maestrosServiceLocal;
	
	public AperturaCuentaplazofijoBean() {
		cuentaValida = true;
		cuentaCreada = false;
		fechaApertura = null;
		
		montoApertura = null;
		periodoDeposito = null;
		//trea = null;
		interesGenerado = null;
		
		isPersonanatural = false;
		isPersonajuridica = false;

		accionistas = new HashMap<String, Accionista>();
		titulares = new HashMap<String, Personanatural>();
		beneficiarios = new HashMap<String, Beneficiario>();
		
		isDlgAccionistaOpen = false;
		isDlgTitularOpen = false;
		isDlgBeneficiarioOpen = false;
		
		cantidadRetirantes = 1;
		
		porcentajeParticipacionAccionista = new BigDecimal(0);
		porcentajeParticipacionAccionista.setScale(2);
	}

	@PostConstruct
	private void initialize(){
		this.comboTipopersona.putItem(0, "--SELECCIONE--");
		this.comboTipopersona.putItem(1, "PERSONA NATURAL");
		this.comboTipopersona.putItem(2, "PERSONA JURIDICA");
		
		this.comboFinsocial.putItem(0, "--NO ESPECIFICA--");
		this.comboFinsocial.putItem(1, "CON FINES DE LUCRO");
		this.comboFinsocial.putItem(2, "SIN FINES DE LUCRO");
		
		try {
			List<Tipodocumento> listTipodocumentoPersonanatural = maestrosServiceLocal.getTipodocumentoForPersonaNatural();
			comboTipodocumentoPersonanatural.setItems(listTipodocumentoPersonanatural);
			comboTipodocumentoAccionista.setItems(listTipodocumentoPersonanatural);
			comboTipodocumentoTitular.setItems(listTipodocumentoPersonanatural);
			List<Tipodocumento> listTipodocumentoPersonajuridica = maestrosServiceLocal.getTipodocumentoForPersonaJuridica();
			comboTipodocumentoPersonajuridica.setItems(listTipodocumentoPersonajuridica);
			comboTipodocumentoRepresentantelegal.setItems(listTipodocumentoPersonanatural);
			
			comboSexoPersonanatural.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoAccionista.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoRepresentantelegal.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoTitular.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			
			comboEstadocivilPersonanatural.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
			
			comboTipoempresa.initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
			
			comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
			
			caja = cajaBean.getCaja();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}		
	}
	
	
	public String crearCuentaplazofijo(){
		try {
			if(this.cantidadRetirantes > titulares.size()){
				throw new Exception("La cantidad de retirantes exece a la cantidad de titulares");
			}
			
			if(cuentaCreada == false){		
				if (isPersonanatural) {
					Personanatural personaNaturalSocio = new Personanatural();
					personaNaturalSocio.setTipodocumento(comboTipodocumentoPersonanatural.getObjectItemSelected());
					personaNaturalSocio.setNumerodocumento(numeroDocumentoPersonanatural);
					personaNaturalSocio.setApellidopaterno(apellidoPaternoPersonanatural);
					personaNaturalSocio.setApellidomaterno(apellidoMaternoPersonanatural);
					personaNaturalSocio.setNombres(nombresPersonanatural);
					personaNaturalSocio.setFechanacimiento(fechaNacimientoPersonanatural);
					personaNaturalSocio.setSexo(comboSexoPersonanatural.getObjectItemSelected());
					personaNaturalSocio.setEstadocivil(comboEstadocivilPersonanatural.getObjectItemSelected());
					personaNaturalSocio.setOcupacion(ocupacionPersonanatural);
					personaNaturalSocio.setDireccion(direccionPersonanatural);
					personaNaturalSocio.setReferencia(referenciaPersonanatural);
					personaNaturalSocio.setTelefono(telefonoPersonanatural);					personaNaturalSocio.setCelular(celularPersonanatural);
					personaNaturalSocio.setEmail(emailPersonanatural);
					
					Cuentabancaria cuentabancaria = new Cuentabancaria();
					List<Titular> listTitulares = new ArrayList<Titular>();
					List<Beneficiario> listBeneficiarios = listBeneficiarios();
					List<Personanatural> listpersonaTitulares = listTitulares();
					for (Personanatural personanatural : listpersonaTitulares) {
						Titular titular = new Titular();
						titular.setPersonanatural(personanatural);
						listTitulares.add(titular);
					}
					
					Calendar calendarStart = Calendar.getInstance();
					Calendar calendarEnd = Calendar.getInstance();
					calendarEnd.add(Calendar.DATE, periodoDeposito);
					
					cuentabancaria.setFechaapertura(calendarStart.getTime());
					cuentabancaria.setFechacierre(calendarEnd.getTime());
					cuentabancaria.setTipomoneda(comboTipomoneda.getObjectItemSelected());
					cuentabancaria.setSaldo(new Moneda(montoApertura));				
					cuentabancaria.setCantidadretirantes(cantidadRetirantes);
					cuentabancaria.setTitulares(listTitulares);
					cuentabancaria.setBeneficiarios(listBeneficiarios);
					
					BigDecimal teaReal = tea.divide(new BigDecimal(100));
					//BigDecimal treaReal = trea.divide(new BigDecimal(100));
					cuentabancaria = cuentabancariaServiceLocal.createCuentaplazofijoPersonanatural(cuentabancaria, personaNaturalSocio,montoApertura, teaReal, caja, agenciaBean.getAgencia(), usuarioMB.getUsuario());
					
					cuentaCreada = true;
					numeroCuenta = cuentabancaria.getNumerocuenta();
					idCuentaPlazoFijoPersonaNatural = cuentabancaria.getIdcuentabancaria();
					fechaApertura = cuentabancaria.getFechaapertura();	
					
					this.cuentabancariaCreada = cuentabancaria;
				} else {
					if (isPersonajuridica) {
						
						Personajuridica personaJuridicaSocio = new Personajuridica();
						personaJuridicaSocio.setTipodocumento(comboTipodocumentoPersonajuridica.getObjectItemSelected());
						personaJuridicaSocio.setNumerodocumento(numeroDocumentoPersonajuridica);
						personaJuridicaSocio.setRazonsocial(razonSocial);
						personaJuridicaSocio.setNombrecomercial(nombreComercial);
						personaJuridicaSocio.setActividadprincipal(actividadPrincipal);
						personaJuridicaSocio.setFechaconstitucion(fechaConstitucion);
						personaJuridicaSocio.setTipoempresa(comboTipoempresa.getObjectItemSelected());
						personaJuridicaSocio.setFindelucro(comboFinsocial.getItemSelected() == 1 ? true : false);		
						personaJuridicaSocio.setDireccion(direccionPersonajuridica);
						personaJuridicaSocio.setReferencia(referenciaPersonajuridica);
						personaJuridicaSocio.setTelefono(telefonoPersonajuridica);
						personaJuridicaSocio.setCelular(celularPersonajuridica);
						personaJuridicaSocio.setEmail(emailPersonajuridica);
														
						Personanatural representanteLegal = new Personanatural();
						representanteLegal.setTipodocumento(comboTipodocumentoRepresentantelegal.getObjectItemSelected());
						representanteLegal.setNumerodocumento(numeroDocumentoRepresentantelegal);
						representanteLegal.setApellidopaterno(apellidoPaternoRepresentantelegal);
						representanteLegal.setApellidomaterno(apellidoMaternoRepresentantelegal);
						representanteLegal.setNombres(nombresRepresentantelegal);
						representanteLegal.setFechanacimiento(fechaNacimientoRepresentantelegal);
						representanteLegal.setSexo(comboSexoRepresentantelegal.getObjectItemSelected());
						
						List<Accionista> listAccionistas = new ArrayList<Accionista>();
						listAccionistas.addAll(accionistas.values());
						
						personaJuridicaSocio.setRepresentanteLegal(representanteLegal);
						personaJuridicaSocio.setAccionistas(listAccionistas);
						
						Cuentabancaria cuentabancaria = new Cuentabancaria();
						List<Titular> listTitulares = new ArrayList<Titular>();
						List<Beneficiario> listBeneficiarios = listBeneficiarios();
						List<Personanatural> listpersonaTitulares = listTitulares();
						for (Personanatural personanatural : listpersonaTitulares) {
							Titular titular = new Titular();
							titular.setPersonanatural(personanatural);
							listTitulares.add(titular);
						}
						
						Calendar calendarStart = Calendar.getInstance();
						Calendar calendarEnd = Calendar.getInstance();
						calendarEnd.add(Calendar.DATE, periodoDeposito);
						
						cuentabancaria.setFechaapertura(calendarStart.getTime());
						cuentabancaria.setFechacierre(calendarEnd.getTime());
						cuentabancaria.setTipomoneda(comboTipomoneda.getObjectItemSelected());
						cuentabancaria.setSaldo(new Moneda(montoApertura));				
						cuentabancaria.setCantidadretirantes(cantidadRetirantes);
						cuentabancaria.setTitulares(listTitulares);
						cuentabancaria.setBeneficiarios(listBeneficiarios);
						
						BigDecimal teaReal = tea.divide(new BigDecimal(100));
						cuentabancaria = cuentabancariaServiceLocal.createCuentaplazofijoPersonajuridica(cuentabancaria, personaJuridicaSocio, teaReal, agenciaBean.getAgencia());
						
						cuentaCreada = true;
						numeroCuenta = cuentabancaria.getNumerocuenta();
						idCuentaPlazoFijoPersonaJuridica = cuentabancaria.getIdcuentabancaria();
						fechaApertura = cuentabancaria.getFechaapertura();
						this.cuentabancariaCreada = cuentabancaria;
					} else {
						throw new Exception("El tipo de persona no es valido");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.cuentaValida = false;
			JsfUtil.addErrorMessage(e, e.getMessage());
			return null;
		}

		return null;
	}
	
	public String returnNumeroCertificado(Integer idPlazoFijo){
		String numeroCertificado = null;
		if(idPlazoFijo>0 && idPlazoFijo<10){
			numeroCertificado = "0000000" + idPlazoFijo;
		}if(idPlazoFijo>=10 && idPlazoFijo<100){
			numeroCertificado = "000000" + idPlazoFijo;
		}if(idPlazoFijo>=100 && idPlazoFijo<1000){
			numeroCertificado = "00000" + idPlazoFijo;
		}if(idPlazoFijo>=1000 && idPlazoFijo<10000){
			numeroCertificado = "0000" + idPlazoFijo;
		}if(idPlazoFijo>=10000 && idPlazoFijo<100000){
			numeroCertificado = "000" + idPlazoFijo;
		}if(idPlazoFijo>=100000 && idPlazoFijo<1000000){
			numeroCertificado = "00" + idPlazoFijo;
		}
		return numeroCertificado;
	}
	
	public void cagarTasaInteresTEA() {
		try {
			BigDecimal cien = new BigDecimal(100);
			BigDecimal tasaInteres = null;
			Tipomoneda tipomoneda = ProduceObject
					.getTipomoneda(TipomonedaType.NUEVO_SOL);
			tasaInteres = tasainteresServiceLocal.getTea(tipomoneda,
					periodoDeposito, montoApertura);
			tasaInteres = tasaInteres.setScale(2);
			setTea(tasaInteres.multiply(cien));
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void calcularInteresGenerado(){
		BigDecimal result = BigDecimal.ZERO;
		try {
			if(montoApertura != null){
				if(periodoDeposito != null){
					if(tea != null){
						BigDecimal teaReal = tea.divide(new BigDecimal(100));
						result = tasainteresServiceLocal.getInteresGeneradoPlazofijo(montoApertura, periodoDeposito, teaReal);
					}
				}
			}			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
			e.printStackTrace();
		}
		this.interesGenerado = result;
	}
	
	public BigDecimal calcularTotal(){
		BigDecimal result = BigDecimal.ZERO;
		if (interesGenerado != null) {
			if (montoApertura != null) {
				result = interesGenerado.add(montoApertura);
			}
		}
		return result;
	}
	
	public String calcularFecha(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(calendar.getTime());
	}
	
	public String calcularFechaCierre(){
		if(periodoDeposito != null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, periodoDeposito);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(calendar.getTime());
		}
		return null;
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
	
	public Personajuridica buscarPersonajuridica(Tipodocumento tipodocumento, String numeroDocumento){
		Personajuridica personajuridica = null;
		try {
			personajuridica = personajuridicaServiceLocal.find(tipodocumento, numeroDocumento);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
		return personajuridica;
	}
	
	public void buscarPersonanaturalSocio(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoPersonanatural.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoPersonanatural;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoPersonanatural.setItemSelected(personaNatural.getTipodocumento());
				this.numeroDocumentoPersonanatural = personaNatural.getNumerodocumento();
				this.apellidoPaternoPersonanatural = personaNatural.getApellidopaterno();
				this.apellidoMaternoPersonanatural = personaNatural.getApellidomaterno();
				this.nombresPersonanatural = personaNatural.getNombres();
				this.fechaNacimientoPersonanatural = personaNatural.getFechanacimiento();
				this.comboSexoPersonanatural.setItemSelected(personaNatural.getSexo());
				this.comboEstadocivilPersonanatural.setItemSelected(personaNatural.getEstadocivil());
				this.ocupacionPersonanatural = personaNatural.getOcupacion();
				this.direccionPersonanatural = personaNatural.getDireccion();
				this.referenciaPersonanatural = personaNatural.getReferencia();
				this.telefonoPersonanatural = personaNatural.getTelefono();
				this.celularPersonanatural = personaNatural.getCelular();
				this.emailPersonanatural = personaNatural.getEmail();
			} else {
				this.apellidoPaternoPersonanatural = "";
				this.apellidoMaternoPersonanatural = "";
				this.nombresPersonanatural = "";
				this.fechaNacimientoPersonanatural = null;
				this.comboSexoPersonanatural.setItemSelected(-1);
				this.comboEstadocivilPersonanatural.setItemSelected(-1);
				this.ocupacionPersonanatural = "";
				this.direccionPersonanatural = "";
				this.referenciaPersonanatural = "";
				this.telefonoPersonanatural = "";
				this.celularPersonanatural = "";
				this.emailPersonanatural = "";
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void buscarPersonanaturalRepresentantelegal(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoRepresentantelegal.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoRepresentantelegal;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoRepresentantelegal.setItemSelected(personaNatural.getTipodocumento());
				this.numeroDocumentoRepresentantelegal = personaNatural.getNumerodocumento();
				this.apellidoPaternoRepresentantelegal = personaNatural.getApellidopaterno();
				this.apellidoMaternoRepresentantelegal = personaNatural.getApellidomaterno();
				this.nombresRepresentantelegal = personaNatural.getNombres();
				this.fechaNacimientoRepresentantelegal = personaNatural.getFechanacimiento();
				this.comboSexoRepresentantelegal.setItemSelected(personaNatural.getSexo());
			} else {
				this.apellidoPaternoRepresentantelegal = "";
				this.apellidoMaternoRepresentantelegal = "";
				this.nombresRepresentantelegal = "";
				this.fechaNacimientoRepresentantelegal = null;
				this.comboSexoRepresentantelegal.setItemSelected(-1);
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void buscarPersonanaturalAccionista(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoAccionista.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoAccionista;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoAccionista.setItemSelected(personaNatural.getTipodocumento());
				this.numeroDocumentoAccionista = personaNatural.getNumerodocumento();
				this.apellidoPaternoAccionista = personaNatural.getApellidopaterno();
				this.apellidoMaternoAccionista = personaNatural.getApellidomaterno();
				this.nombresAccionista = personaNatural.getNombres();
				this.fechaNacimientoAccionista = personaNatural.getFechanacimiento();
				this.comboSexoAccionista.setItemSelected(personaNatural.getSexo());
				BigDecimal bigDecimal = new BigDecimal(0);
				bigDecimal.setScale(2);
				this.porcentajeParticipacionAccionista = bigDecimal;
			} else {
				this.apellidoPaternoAccionista = "";
				this.apellidoMaternoAccionista = "";
				this.nombresAccionista = "";
				this.fechaNacimientoAccionista = null;
				this.comboSexoAccionista.setItemSelected(-1);
				BigDecimal bigDecimal = new BigDecimal(0);
				bigDecimal.setScale(2);
				this.porcentajeParticipacionAccionista = bigDecimal;
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void buscarPersonanaturalTitular(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoTitular.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoTitular;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoTitular.setItemSelected(personaNatural.getTipodocumento());
				this.numeroDocumentoTitular = personaNatural.getNumerodocumento();
				this.apellidoPaternoTitular = personaNatural.getApellidopaterno();
				this.apellidoMaternoTitular = personaNatural.getApellidomaterno();
				this.nombresTitular = personaNatural.getNombres();
				this.fechaNacimientoTitular = personaNatural.getFechanacimiento();
				this.comboSexoTitular.setItemSelected(personaNatural.getSexo());
			} else {
				this.apellidoPaternoTitular = "";
				this.apellidoMaternoTitular = "";
				this.nombresTitular = "";
				this.fechaNacimientoTitular = null;
				this.comboSexoTitular.setItemSelected(-1);
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void buscarPersonajuridicaSocio(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoPersonajuridica.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoPersonajuridica;
			Personajuridica personajuridica = buscarPersonajuridica(tipodocumento, numeroDocumento);
			
			if(personajuridica != null){
				comboTipodocumentoPersonajuridica.setItemSelected(personajuridica.getTipodocumento());
				numeroDocumentoPersonajuridica = personajuridica.getNumerodocumento();	
				razonSocial = personajuridica.getRazonsocial();
				nombreComercial = personajuridica.getNombrecomercial();
				actividadPrincipal = personajuridica.getActividadprincipal();
				fechaConstitucion = personajuridica.getFechaconstitucion();
				comboTipoempresa.setItemSelected(personajuridica.getTipoempresa());
				if(personajuridica.getFindelucro() == true){
					comboFinsocial.setItemSelected(1);
				} else {
					comboFinsocial.setItemSelected(2);
				}		
				direccionPersonajuridica = personajuridica.getDireccion();
				referenciaPersonajuridica = personajuridica.getReferencia();
				telefonoPersonajuridica = personajuridica.getTelefono();
				celularPersonajuridica = personajuridica.getCelular();
				emailPersonajuridica = personajuridica.getEmail();
				
				comboTipodocumentoRepresentantelegal.setItemSelected(personajuridica.getRepresentanteLegal().getTipodocumento());
				numeroDocumentoRepresentantelegal = personajuridica.getRepresentanteLegal().getNumerodocumento();
				apellidoPaternoRepresentantelegal = personajuridica.getRepresentanteLegal().getApellidopaterno();
				apellidoMaternoRepresentantelegal = personajuridica.getRepresentanteLegal().getApellidomaterno();
				nombresRepresentantelegal = personajuridica.getRepresentanteLegal().getNombres();
				fechaNacimientoRepresentantelegal = personajuridica.getRepresentanteLegal().getFechanacimiento();
				comboSexoRepresentantelegal.setItemSelected(personajuridica.getRepresentanteLegal().getSexo());
				
				this.accionistas.clear();
				List<Accionista> listAccionistas = personajuridica.getAccionistas();
				for (Accionista accionista : listAccionistas) {
					Personanatural personanatural = accionista.getPersonanatural();
					String keyMap = personanatural.getTipodocumento().getIdtipodocumento() + personanatural.getNumerodocumento();
					this.accionistas.put(keyMap, accionista);
				}
				
			} else {
				razonSocial = "";
				nombreComercial = "";
				actividadPrincipal = "";
				fechaConstitucion = null;
				comboTipoempresa.setItemSelected(-1);				
				comboFinsocial.setItemSelected(0);				
				direccionPersonajuridica = "";
				referenciaPersonajuridica = "";
				telefonoPersonajuridica = "";
				celularPersonajuridica = "";
				emailPersonajuridica = "";
				
				comboTipodocumentoRepresentantelegal.setItemSelected(-1);
				numeroDocumentoRepresentantelegal = "";
				apellidoPaternoRepresentantelegal = "";
				apellidoMaternoRepresentantelegal = "";
				nombresRepresentantelegal = "";
				fechaNacimientoRepresentantelegal = null;
				comboSexoRepresentantelegal.setItemSelected(-1);
				
				this.accionistas.clear();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
	}
	
	public void addAccionista(){
		Tipodocumento tipodocumento = comboTipodocumentoAccionista.getObjectItemSelected();
		Sexo sexo = comboSexoAccionista.getObjectItemSelected();
		
		Personanatural personanatural = new Personanatural();
		personanatural.setNumerodocumento(numeroDocumentoAccionista);
		personanatural.setApellidopaterno(apellidoPaternoAccionista);
		personanatural.setApellidomaterno(apellidoMaternoAccionista);
		personanatural.setNombres(nombresAccionista);
		personanatural.setFechanacimiento(fechaNacimientoAccionista);
		personanatural.setTipodocumento(tipodocumento);
		personanatural.setSexo(sexo);
		
		String keyMap = personanatural.getTipodocumento().getIdtipodocumento()+personanatural.getNumerodocumento();
		
		Accionista accionista = new Accionista();
		accionista.setPersonanatural(personanatural);
		accionista.setPorcentajeparticipacion(porcentajeParticipacionAccionista);
		
		this.accionistas.put(keyMap, accionista);
		
		this.comboTipodocumentoAccionista.setItemSelected(-1);
		this.numeroDocumentoAccionista = "";
		this.apellidoPaternoAccionista = "";
		this.apellidoMaternoAccionista = "";
		this.nombresAccionista = "";
		this.fechaNacimientoAccionista = null;
		this.comboSexoAccionista.setItemSelected(-1);
		BigDecimal bigDecimal = BigDecimal.ZERO;
		bigDecimal.setScale(2);
		this.porcentajeParticipacionAccionista = bigDecimal;
	}
	
	public void editAccionista(Object obj) {
		if(obj instanceof Accionista){
			Accionista accionista = (Accionista) obj;
			Personanatural personanatural = accionista.getPersonanatural();
			
			comboTipodocumentoAccionista.setItemSelected(personanatural.getTipodocumento());
			numeroDocumentoAccionista = personanatural.getNumerodocumento();
			apellidoPaternoAccionista = personanatural.getApellidopaterno();
			apellidoMaternoAccionista = personanatural.getApellidomaterno();
			nombresAccionista = personanatural.getNombres();
			fechaNacimientoAccionista = personanatural.getFechanacimiento();
			comboSexoAccionista.setItemSelected(personanatural.getSexo());		
			porcentajeParticipacionAccionista = accionista.getPorcentajeparticipacion();
			setDlgAccionistaOpen(true);
		} 
	}
	
	public void removeAccionista(Object obj) {
		if(obj instanceof Accionista){
			Accionista accionista = (Accionista) obj;
			Personanatural personanatural = accionista.getPersonanatural();
			String keyMap = personanatural.getTipodocumento().getIdtipodocumento()+personanatural.getNumerodocumento();
			this.accionistas.remove(keyMap);
		} 
	}
	
	public void addTitularDefecto(){		
		Personanatural personanatural = new Personanatural();
		String keyMapTitularDefecto = null;
		if(isPersonanatural){
			keyMapTitularDefecto = comboTipodocumentoPersonanatural.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoPersonanatural;
			
			Tipodocumento tipodocumento = comboTipodocumentoPersonanatural.getObjectItemSelected();
			Sexo sexo = comboSexoPersonanatural.getObjectItemSelected();
			personanatural.setNumerodocumento(numeroDocumentoPersonanatural);
			personanatural.setApellidopaterno(apellidoPaternoPersonanatural);
			personanatural.setApellidomaterno(apellidoMaternoPersonanatural);
			personanatural.setNombres(nombresPersonanatural);
			personanatural.setFechanacimiento(fechaNacimientoPersonanatural);
			personanatural.setTipodocumento(tipodocumento);
			personanatural.setSexo(sexo);
		}
		if(isPersonajuridica){
			keyMapTitularDefecto = comboTipodocumentoRepresentantelegal.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoRepresentantelegal;
			
			Tipodocumento tipodocumento = comboTipodocumentoRepresentantelegal.getObjectItemSelected();
			Sexo sexo = comboSexoRepresentantelegal.getObjectItemSelected();
			personanatural.setNumerodocumento(numeroDocumentoRepresentantelegal);
			personanatural.setApellidopaterno(apellidoPaternoRepresentantelegal);
			personanatural.setApellidomaterno(apellidoMaternoRepresentantelegal);
			personanatural.setNombres(nombresRepresentantelegal);
			personanatural.setFechanacimiento(fechaNacimientoRepresentantelegal);
			personanatural.setTipodocumento(tipodocumento);
			personanatural.setSexo(sexo);
		}
			
		this.titulares.put(keyMapTitularDefecto, personanatural);
	}
	
	public void removeTitularDefecto(){		
		String keyMapTitularDefecto = null;
		if(isPersonanatural){
			keyMapTitularDefecto = comboTipodocumentoPersonanatural.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoPersonanatural;
		}
		if(isPersonajuridica){
			keyMapTitularDefecto = comboTipodocumentoRepresentantelegal.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoRepresentantelegal;
		}
			
		this.titulares.remove(keyMapTitularDefecto);
		
		calcularInteresGenerado();
	}
	
	public void addTitular(){
		Tipodocumento tipodocumento = comboTipodocumentoTitular.getObjectItemSelected();
		Sexo sexo = comboSexoTitular.getObjectItemSelected();
		
		Personanatural personanatural = new Personanatural();
		personanatural.setNumerodocumento(numeroDocumentoTitular);
		personanatural.setApellidopaterno(apellidoPaternoTitular);
		personanatural.setApellidomaterno(apellidoMaternoTitular);
		personanatural.setNombres(nombresTitular);
		personanatural.setFechanacimiento(fechaNacimientoTitular);
		personanatural.setTipodocumento(tipodocumento);
		personanatural.setSexo(sexo);
		
		String keyMap = personanatural.getTipodocumento().getIdtipodocumento()+personanatural.getNumerodocumento();
		
		String keyMapTitularDefecto = null;
		if(isPersonanatural){
			keyMapTitularDefecto = comboTipodocumentoPersonanatural.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoPersonanatural;
		}
		if(isPersonajuridica){
			keyMapTitularDefecto = comboTipodocumentoRepresentantelegal.getObjectItemSelected().getIdtipodocumento()+numeroDocumentoRepresentantelegal;
		}
		
		if(keyMap.compareTo(keyMapTitularDefecto) != 0){
			this.titulares.put(keyMap, personanatural);
		}
		
		this.comboTipodocumentoTitular.setItemSelected(-1);
		this.numeroDocumentoTitular = "";
		this.apellidoPaternoTitular = "";
		this.apellidoMaternoTitular = "";
		this.nombresTitular = "";
		this.fechaNacimientoTitular = null;
		this.comboSexoTitular.setItemSelected(-1);
	}
	
	public void editTitular(Object obj) {
		if(obj instanceof Personanatural){
			Personanatural personanatural = (Personanatural) obj;
			
			comboTipodocumentoTitular.setItemSelected(personanatural.getTipodocumento());
			numeroDocumentoTitular = personanatural.getNumerodocumento();
			apellidoPaternoTitular = personanatural.getApellidopaterno();
			apellidoMaternoTitular = personanatural.getApellidomaterno();
			nombresTitular = personanatural.getNombres();
			fechaNacimientoTitular = personanatural.getFechanacimiento();
			comboSexoTitular.setItemSelected(personanatural.getSexo());	
			
			setDlgTitularOpen(true);
		} 
	}
	
	public void removeTitular(Object obj) {
		if(obj instanceof Personanatural){
			Personanatural personanatural = (Personanatural) obj;
			String keyMap = personanatural.getTipodocumento().getIdtipodocumento()+personanatural.getNumerodocumento();
			this.titulares.remove(keyMap);
		} 
	}
	
	public void addBeneficiario(){

		Beneficiario beneficiario = new Beneficiario();
		
		beneficiario.setDni(numeroDocumentoBeneficiario);
		beneficiario.setApellidopaterno(apellidoPaternoBeneficiario);
		beneficiario.setApellidomaterno(apellidoMaternoBeneficiario);
		beneficiario.setNombres(nombresBeneficiario);
		beneficiario.setPorcentajebeneficio(porcentajeBeneficio);
			
		String keyMap = beneficiario.getApellidopaterno()+beneficiario.getApellidomaterno()+beneficiario.getNombres();
		this.beneficiarios.put(keyMap, beneficiario);
		
		numeroDocumentoBeneficiario = "";
		apellidoPaternoBeneficiario = "";
		apellidoMaternoBeneficiario = "";
		nombresBeneficiario = "";
		porcentajeBeneficio = 0;
	}
	
	public void editBeneficiario(Object obj) {
		if(obj instanceof Beneficiario){
			Beneficiario beneficiario = (Beneficiario) obj;
						
			numeroDocumentoBeneficiario = beneficiario.getDni();
			apellidoPaternoBeneficiario = beneficiario.getApellidopaterno();
			apellidoMaternoBeneficiario = beneficiario.getApellidomaterno();
			nombresBeneficiario = beneficiario.getNombres();
			porcentajeBeneficio = beneficiario.getPorcentajebeneficio();
			
			setDlgBeneficiarioOpen(true);
		} 
	}
	
	public void removeBeneficiario(Object obj) {
		if(obj instanceof Beneficiario){
			Beneficiario beneficiario = (Beneficiario) obj;
			String keyMap = beneficiario.getApellidopaterno()+beneficiario.getApellidomaterno()+beneficiario.getNombres();
			this.beneficiarios.remove(keyMap);
		} 
	}
	
	public void changeTipodocumentoPersonanatural(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoPersonanatural.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoTitular(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoTitular.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoPersonajuridica(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoPersonajuridica.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoAccionista(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoAccionista.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoRepresentantelegal(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoRepresentantelegal.getObjectItemSelected(key);
	}

	public void changeTipopersona(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		switch (key) {
		case 0:
			isPersonanatural = false;
			isPersonajuridica = false;
			this.comboTipopersona.setItemSelected(0);
			break;
		case 1:
			isPersonanatural = true;
			isPersonajuridica = false;
			this.comboTipopersona.setItemSelected(1);
			break;
		case 2:
			isPersonanatural = false;
			isPersonajuridica = true;
			this.comboTipopersona.setItemSelected(2);
			break;
		default:
			isPersonanatural = false;
			isPersonajuridica = false;
			this.comboTipopersona.setItemSelected(0);
			break;
		}
	}
	
	public ComboBean<String> getComboTipopersona() {
		return comboTipopersona;
	}

	public void setComboTipopersona(ComboBean<String> comboTipopersona) {
		this.comboTipopersona = comboTipopersona;
	}

	public String getApellidoPaterno() {
		return apellidoPaternoPersonanatural;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaternoPersonanatural = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaternoPersonanatural;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaternoPersonanatural = apellidoMaterno;
	}

	public String getNombres() {
		return nombresPersonanatural;
	}

	public void setNombres(String nombres) {
		this.nombresPersonanatural = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimientoPersonanatural;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimientoPersonanatural = fechaNacimiento;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexoPersonanatural;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexoPersonanatural = comboSexo;
	}

	public ComboBean<Estadocivil> getComboEstadocivil() {
		return comboEstadocivilPersonanatural;
	}

	public void setComboEstadocivil(ComboBean<Estadocivil> comboEstadocivil) {
		this.comboEstadocivilPersonanatural = comboEstadocivil;
	}

	public String getOcupacion() {
		return ocupacionPersonanatural;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacionPersonanatural = ocupacion;
	}

	public String getDireccionPersonanatural() {
		return direccionPersonanatural;
	}

	public void setDireccionPersonanatural(String direccionPersonanatural) {
		this.direccionPersonanatural = direccionPersonanatural;
	}

	public String getReferenciaPersonanatural() {
		return referenciaPersonanatural;
	}

	public void setReferenciaPersonanatural(String referenciaPersonanatural) {
		this.referenciaPersonanatural = referenciaPersonanatural;
	}

	public String getTelefonoPersonanatural() {
		return telefonoPersonanatural;
	}

	public void setTelefonoPersonanatural(String telefonoPersonanatural) {
		this.telefonoPersonanatural = telefonoPersonanatural;
	}

	public String getCelularPersonanatural() {
		return celularPersonanatural;
	}

	public void setCelularPersonanatural(String celularPersonanatural) {
		this.celularPersonanatural = celularPersonanatural;
	}

	public String getEmailPersonanatural() {
		return emailPersonanatural;
	}

	public void setEmailPersonanatural(String emailPersonanatural) {
		this.emailPersonanatural = emailPersonanatural;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	public ComboBean<Tipoempresa> getComboTipoempresa() {
		return comboTipoempresa;
	}

	public void setComboTipoempresa(ComboBean<Tipoempresa> comboTipoempresa) {
		this.comboTipoempresa = comboTipoempresa;
	}

	public ComboBean<String> getComboFinsocial() {
		return comboFinsocial;
	}

	public void setComboFinsocial(ComboBean<String> comboFinsocial) {
		this.comboFinsocial = comboFinsocial;
	}

	public String getDireccionPersonajuridica() {
		return direccionPersonajuridica;
	}

	public void setDireccionPersonajuridica(String direccionPersonajuridica) {
		this.direccionPersonajuridica = direccionPersonajuridica;
	}

	public String getReferenciaPersonajuridica() {
		return referenciaPersonajuridica;
	}

	public void setReferenciaPersonajuridica(String referenciaPersonajuridica) {
		this.referenciaPersonajuridica = referenciaPersonajuridica;
	}

	public String getTelefonoPersonajuridica() {
		return telefonoPersonajuridica;
	}

	public void setTelefonoPersonajuridica(String telefonoPersonajuridica) {
		this.telefonoPersonajuridica = telefonoPersonajuridica;
	}

	public String getCelularPersonajuridica() {
		return celularPersonajuridica;
	}

	public void setCelularPersonajuridica(String celularPersonajuridica) {
		this.celularPersonajuridica = celularPersonajuridica;
	}

	public String getEmailPersonajuridica() {
		return emailPersonajuridica;
	}

	public void setEmailPersonajuridica(String emailPersonajuridica) {
		this.emailPersonajuridica = emailPersonajuridica;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoPersonanatural() {
		return comboTipodocumentoPersonanatural;
	}

	public void setComboTipodocumentoPersonanatural(
			ComboBean<Tipodocumento> comboTipodocumentoPersonanatural) {
		this.comboTipodocumentoPersonanatural = comboTipodocumentoPersonanatural;
	}

	public String getNumeroDocumentoPersonanatural() {
		return numeroDocumentoPersonanatural;
	}

	public void setNumeroDocumentoPersonanatural(
			String numeroDocumentoPersonanatural) {
		this.numeroDocumentoPersonanatural = numeroDocumentoPersonanatural;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoPersonajuridica() {
		return comboTipodocumentoPersonajuridica;
	}

	public void setComboTipodocumentoPersonajuridica(
			ComboBean<Tipodocumento> comboTipodocumentoPersonajuridica) {
		this.comboTipodocumentoPersonajuridica = comboTipodocumentoPersonajuridica;
	}

	public String getNumeroDocumentoPersonajuridica() {
		return numeroDocumentoPersonajuridica;
	}

	public void setNumeroDocumentoPersonajuridica(
			String numeroDocumentoPersonajuridica) {
		this.numeroDocumentoPersonajuridica = numeroDocumentoPersonajuridica;
	}

	public boolean isPersonanatural() {
		return isPersonanatural;
	}

	public void setPersonanatural(boolean isPersonanatural) {
		this.isPersonanatural = isPersonanatural;
	}

	public boolean isPersonajuridica() {
		return isPersonajuridica;
	}

	public void setPersonajuridica(boolean isPersonajuridica) {
		this.isPersonajuridica = isPersonajuridica;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoRepresentantelegal() {
		return comboTipodocumentoRepresentantelegal;
	}

	public void setComboTipodocumentoRepresentantelegal(
			ComboBean<Tipodocumento> comboTipodocumentoRepresentantelegal) {
		this.comboTipodocumentoRepresentantelegal = comboTipodocumentoRepresentantelegal;
	}

	public String getNumeroDocumentoRepresentantelegal() {
		return numeroDocumentoRepresentantelegal;
	}

	public void setNumeroDocumentoRepresentantelegal(
			String numeroDocumentoRepresentantelegal) {
		this.numeroDocumentoRepresentantelegal = numeroDocumentoRepresentantelegal;
	}

	public String getApellidoPaternoRepresentantelegal() {
		return apellidoPaternoRepresentantelegal;
	}

	public void setApellidoPaternoRepresentantelegal(
			String apellidoPaternoRepresentantelegal) {
		this.apellidoPaternoRepresentantelegal = apellidoPaternoRepresentantelegal;
	}

	public String getApellidoMaternoRepresentantelegal() {
		return apellidoMaternoRepresentantelegal;
	}

	public void setApellidoMaternoRepresentantelegal(
			String apellidoMaternoRepresentantelegal) {
		this.apellidoMaternoRepresentantelegal = apellidoMaternoRepresentantelegal;
	}

	public String getNombresRepresentantelegal() {
		return nombresRepresentantelegal;
	}

	public void setNombresRepresentantelegal(String nombresRepresentantelegal) {
		this.nombresRepresentantelegal = nombresRepresentantelegal;
	}

	public Date getFechaNacimientoRepresentantelegal() {
		return fechaNacimientoRepresentantelegal;
	}

	public void setFechaNacimientoRepresentantelegal(
			Date fechaNacimientoRepresentantelegal) {
		this.fechaNacimientoRepresentantelegal = fechaNacimientoRepresentantelegal;
	}

	public ComboBean<Sexo> getComboSexoRepresentantelegal() {
		return comboSexoRepresentantelegal;
	}

	public void setComboSexoRepresentantelegal(
			ComboBean<Sexo> comboSexoRepresentantelegal) {
		this.comboSexoRepresentantelegal = comboSexoRepresentantelegal;
	}

	public List<Accionista> listAccionistas() {
		List<Accionista> list = new ArrayList<Accionista>();
		list.addAll(accionistas.values());
		return list;
	}
	
	public List<Personanatural> listTitulares() {
		List<Personanatural> list = new ArrayList<Personanatural>();
		list.addAll(titulares.values());
		return list;
	}
	
	public List<Beneficiario> listBeneficiarios() {
		List<Beneficiario> list = new ArrayList<Beneficiario>();
		list.addAll(beneficiarios.values());
		return list;
	}
	
	public Map<String, Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Map<String, Accionista> accionistas) {
		this.accionistas = accionistas;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoAccionista() {
		return comboTipodocumentoAccionista;
	}

	public void setComboTipodocumentoAccionista(
			ComboBean<Tipodocumento> comboTipodocumentoAccionista) {
		this.comboTipodocumentoAccionista = comboTipodocumentoAccionista;
	}

	public String getNumeroDocumentoAccionista() {
		return numeroDocumentoAccionista;
	}

	public void setNumeroDocumentoAccionista(String numeroDocumentoAccionista) {
		this.numeroDocumentoAccionista = numeroDocumentoAccionista;
	}

	public String getApellidoPaternoAccionista() {
		return apellidoPaternoAccionista;
	}

	public void setApellidoPaternoAccionista(String apellidoPaternoAccionista) {
		this.apellidoPaternoAccionista = apellidoPaternoAccionista;
	}

	public String getApellidoMaternoAccionista() {
		return apellidoMaternoAccionista;
	}

	public void setApellidoMaternoAccionista(String apellidoMaternoAccionista) {
		this.apellidoMaternoAccionista = apellidoMaternoAccionista;
	}

	public String getNombresAccionista() {
		return nombresAccionista;
	}

	public void setNombresAccionista(String nombresAccionista) {
		this.nombresAccionista = nombresAccionista;
	}

	public Date getFechaNacimientoAccionista() {
		return fechaNacimientoAccionista;
	}

	public void setFechaNacimientoAccionista(Date fechaNacimientoAccionista) {
		this.fechaNacimientoAccionista = fechaNacimientoAccionista;
	}

	public ComboBean<Sexo> getComboSexoAccionista() {
		return comboSexoAccionista;
	}

	public void setComboSexoAccionista(ComboBean<Sexo> comboSexoAccionista) {
		this.comboSexoAccionista = comboSexoAccionista;
	}

	public boolean isDlgAccionistaOpen() {
		return isDlgAccionistaOpen;
	}

	public void setDlgAccionistaOpen(boolean isDlgAccionistaOpen) {
		this.isDlgAccionistaOpen = isDlgAccionistaOpen;
	}

	public BigDecimal getPorcentajeParticipacionAccionista() {
		return porcentajeParticipacionAccionista;
	}

	public void setPorcentajeParticipacionAccionista(
			BigDecimal porcentajeParticipacionAccionista) {
		this.porcentajeParticipacionAccionista = porcentajeParticipacionAccionista;
	}

	public boolean isCuentaValida() {
		return cuentaValida;
	}

	public void setCuentaValida(boolean cuentaValida) {
		this.cuentaValida = cuentaValida;
	}

	public String getApellidoPaternoPersonanatural() {
		return apellidoPaternoPersonanatural;
	}

	public void setApellidoPaternoPersonanatural(
			String apellidoPaternoPersonanatural) {
		this.apellidoPaternoPersonanatural = apellidoPaternoPersonanatural;
	}

	public String getApellidoMaternoPersonanatural() {
		return apellidoMaternoPersonanatural;
	}

	public void setApellidoMaternoPersonanatural(
			String apellidoMaternoPersonanatural) {
		this.apellidoMaternoPersonanatural = apellidoMaternoPersonanatural;
	}

	public String getNombresPersonanatural() {
		return nombresPersonanatural;
	}

	public void setNombresPersonanatural(String nombresPersonanatural) {
		this.nombresPersonanatural = nombresPersonanatural;
	}

	public Date getFechaNacimientoPersonanatural() {
		return fechaNacimientoPersonanatural;
	}

	public void setFechaNacimientoPersonanatural(Date fechaNacimientoPersonanatural) {
		this.fechaNacimientoPersonanatural = fechaNacimientoPersonanatural;
	}

	public ComboBean<Sexo> getComboSexoPersonanatural() {
		return comboSexoPersonanatural;
	}

	public void setComboSexoPersonanatural(ComboBean<Sexo> comboSexoPersonanatural) {
		this.comboSexoPersonanatural = comboSexoPersonanatural;
	}

	public ComboBean<Estadocivil> getComboEstadocivilPersonanatural() {
		return comboEstadocivilPersonanatural;
	}

	public void setComboEstadocivilPersonanatural(
			ComboBean<Estadocivil> comboEstadocivilPersonanatural) {
		this.comboEstadocivilPersonanatural = comboEstadocivilPersonanatural;
	}

	public String getOcupacionPersonanatural() {
		return ocupacionPersonanatural;
	}

	public void setOcupacionPersonanatural(String ocupacionPersonanatural) {
		this.ocupacionPersonanatural = ocupacionPersonanatural;
	}

	public Map<String, Personanatural> getTitulares() {
		return titulares;
	}

	public void setTitulares(Map<String, Personanatural> titulares) {
		this.titulares = titulares;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoTitular() {
		return comboTipodocumentoTitular;
	}

	public void setComboTipodocumentoTitular(
			ComboBean<Tipodocumento> comboTipodocumentoTitular) {
		this.comboTipodocumentoTitular = comboTipodocumentoTitular;
	}

	public String getNumeroDocumentoTitular() {
		return numeroDocumentoTitular;
	}

	public void setNumeroDocumentoTitular(String numeroDocumentoTitular) {
		this.numeroDocumentoTitular = numeroDocumentoTitular;
	}

	public String getApellidoPaternoTitular() {
		return apellidoPaternoTitular;
	}

	public void setApellidoPaternoTitular(String apellidoPaternoTitular) {
		this.apellidoPaternoTitular = apellidoPaternoTitular;
	}

	public String getApellidoMaternoTitular() {
		return apellidoMaternoTitular;
	}

	public void setApellidoMaternoTitular(String apellidoMaternoTitular) {
		this.apellidoMaternoTitular = apellidoMaternoTitular;
	}

	public String getNombresTitular() {
		return nombresTitular;
	}

	public void setNombresTitular(String nombresTitular) {
		this.nombresTitular = nombresTitular;
	}

	public Date getFechaNacimientoTitular() {
		return fechaNacimientoTitular;
	}

	public void setFechaNacimientoTitular(Date fechaNacimientoTitular) {
		this.fechaNacimientoTitular = fechaNacimientoTitular;
	}

	public ComboBean<Sexo> getComboSexoTitular() {
		return comboSexoTitular;
	}

	public void setComboSexoTitular(ComboBean<Sexo> comboSexoTitular) {
		this.comboSexoTitular = comboSexoTitular;
	}

	public Map<String, Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(Map<String, Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getNumeroDocumentoBeneficiario() {
		return numeroDocumentoBeneficiario;
	}

	public void setNumeroDocumentoBeneficiario(String numeroDocumentoBeneficiario) {
		this.numeroDocumentoBeneficiario = numeroDocumentoBeneficiario;
	}

	public String getApellidoPaternoBeneficiario() {
		return apellidoPaternoBeneficiario;
	}

	public void setApellidoPaternoBeneficiario(String apellidoPaternoBeneficiario) {
		this.apellidoPaternoBeneficiario = apellidoPaternoBeneficiario;
	}

	public String getApellidoMaternoBeneficiario() {
		return apellidoMaternoBeneficiario;
	}

	public void setApellidoMaternoBeneficiario(String apellidoMaternoBeneficiario) {
		this.apellidoMaternoBeneficiario = apellidoMaternoBeneficiario;
	}

	public String getNombresBeneficiario() {
		return nombresBeneficiario;
	}

	public void setNombresBeneficiario(String nombresBeneficiario) {
		this.nombresBeneficiario = nombresBeneficiario;
	}

	public Integer getPorcentajeBeneficio() {
		return porcentajeBeneficio;
	}

	public void setPorcentajeBeneficio(Integer porcentajeBeneficio) {
		this.porcentajeBeneficio = porcentajeBeneficio;
	}

	public boolean isDlgTitularOpen() {
		return isDlgTitularOpen;
	}

	public void setDlgTitularOpen(boolean isDlgTitularOpen) {
		this.isDlgTitularOpen = isDlgTitularOpen;
	}

	public boolean isDlgBeneficiarioOpen() {
		return isDlgBeneficiarioOpen;
	}

	public void setDlgBeneficiarioOpen(boolean isDlgBeneficiarioOpen) {
		this.isDlgBeneficiarioOpen = isDlgBeneficiarioOpen;
	}

	public Integer getCantidadRetirantes() {
		return cantidadRetirantes;
	}

	public void setCantidadRetirantes(Integer cantidadRetirantes) {
		this.cantidadRetirantes = cantidadRetirantes;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public BigDecimal getMontoApertura() {
		return montoApertura;
	}

	public String getMontoAperturaAsString() {
		if(montoApertura != null)
			return Moneda.getMonedaFormat(montoApertura);
		else
			return "";
	}
	
	public void setMontoApertura(BigDecimal montoApertura) {
		this.montoApertura = montoApertura;
		this.montoApertura = this.montoApertura.setScale(2, RoundingMode.DOWN);
	}

	public Integer getPeriodoDeposito() {
		return periodoDeposito;
	}

	public void setPeriodoDeposito(Integer periodoDeposito) {
		this.periodoDeposito = periodoDeposito;
	}

	public BigDecimal getTea() {
		return tea;
	}

	public void setTea(BigDecimal tea) {
		this.tea = tea;
	}

	public BigDecimal getInteresGenerado() {
		return interesGenerado;
	}

	public boolean isCuentaCreada() {
		return cuentaCreada;
	}

	public void setCuentaCreada(boolean cuentaCreada) {
		this.cuentaCreada = cuentaCreada;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Cuentabancaria getCuentabancariaCreada() {
		return cuentabancariaCreada;
	}

	public void setCuentabancariaCreada(Cuentabancaria cuentabancariaCreada) {
		this.cuentabancariaCreada = cuentabancariaCreada;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public int getIdCuentaPlazoFijoPersonaNatural() {
		return idCuentaPlazoFijoPersonaNatural;
	}

	public void setIdCuentaPlazoFijoPersonaNatural(
			int idCuentaPlazoFijoPersonaNatural) {
		this.idCuentaPlazoFijoPersonaNatural = idCuentaPlazoFijoPersonaNatural;
	}

	public int getIdCuentaPlazoFijoPersonaJuridica() {
		return idCuentaPlazoFijoPersonaJuridica;
	}

	public void setIdCuentaPlazoFijoPersonaJuridica(
			int idCuentaPlazoFijoPersonaJuridica) {
		this.idCuentaPlazoFijoPersonaJuridica = idCuentaPlazoFijoPersonaJuridica;
	}
}
