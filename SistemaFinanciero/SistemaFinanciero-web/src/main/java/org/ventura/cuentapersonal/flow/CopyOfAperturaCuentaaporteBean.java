package org.ventura.cuentapersonal.flow;

import java.io.Serializable;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.persona.Tipoempresa;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CopyOfAperturaCuentaaporteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isPersonanatural;
	private boolean isPersonajuridica;
	
	// DATOS DE LA VISTA
	// VISTA 01
	@Inject private ComboBean<String> comboTipopersona;
	private boolean personanaturalSelected;
	private boolean personanajuridicaSelected;

	@Inject private ComboBean<Tipodocumento> comboTipodocumentoPersonanatural;
	private String numeroDocumentoPersonanatural;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private Date fechaNacimiento;
	@Inject private ComboBean<Sexo> comboSexo;
	@Inject private ComboBean<Estadocivil> comboEstadocivil;
	private String ocupacion;
	private String direccionPersonanatural;
	private String referenciaPersonanatural;
	private String telefonoPersonanatural;
	private String celularPersonanatural;
	private String emailPersonanatural;
	
	private boolean existeApoderado;
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoApoderado;
	private String numeroDocumentoApoderado;
	private String apellidoPaternoApoderado;
	private String apellidoMaternoApoderado;
	private String nombresApoderado;
	private Date fechaNacimientoApoderado;
	@Inject private ComboBean<Sexo> comboSexoApoderado;

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
	private Map<String, Personanatural> titulares;
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoTitular;
	private String numeroDocumentoTitular;
	private String apellidoPaternoTitular;
	private String apellidoMaternoTitular;
	private String nombresTitular;
	private Date fechaNacimientoTitular;
	@Inject private ComboBean<Sexo> comboSexoTitular;
	
	private Map<String, Beneficiario> beneficiarios;
	private String numeroDocumentoBeneficiario;
	private String apellidoPaternoBeneficiario;
	private String apellidoMaternoBeneficiario;
	private String nombresBeneficiario;
	private Integer porcentajeBeneficio;
	
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	@EJB private MaestrosServiceLocal maestrosServiceLocal;
	
	public CopyOfAperturaCuentaaporteBean() {
		isPersonanatural = false;
		isPersonajuridica = false;
		personanaturalSelected = false;
		personanajuridicaSelected = false;
		existeApoderado = false;
		titulares = new HashMap<String, Personanatural>();
		beneficiarios = new HashMap<String, Beneficiario>();
	}

	@PostConstruct
	private void initialize(){
		this.comboTipopersona.putItem(0, "--SELECCIONE--");
		this.comboTipopersona.putItem(1, "PERSONA NATURAL");
		this.comboTipopersona.putItem(2, "PERSONA JURIDICA");
		
		this.comboFinsocial.putItem(0, "--SELECCIONE--");
		this.comboFinsocial.putItem(1, "CON FINES DE LUCRO");
		this.comboFinsocial.putItem(2, "SIN FINES DE LUCRO");
		
		try {
			List<Tipodocumento> listTipodocumentoPersonanatural = maestrosServiceLocal.getTipodocumentoForPersonaNatural();
			comboTipodocumentoPersonanatural.setItems(listTipodocumentoPersonanatural);
			comboTipodocumentoApoderado.setItems(listTipodocumentoPersonanatural);
			comboTipodocumentoTitular.setItems(listTipodocumentoPersonanatural);
			List<Tipodocumento> listTipodocumentoPersonajuridica = maestrosServiceLocal.getTipodocumentoForPersonaJuridica();
			comboTipodocumentoPersonajuridica.setItems(listTipodocumentoPersonajuridica);
			comboTipodocumentoRepresentantelegal.setItems(listTipodocumentoPersonanatural);
			
			comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoApoderado.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoTitular.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoRepresentantelegal.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			
			comboEstadocivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}		
	}
	
	public Personanatural buscarPersonanatural(Tipodocumento tipodocumento, String numeroDocumento){
		Personanatural personanatural = null;
		try {
			personanatural = personanaturalServiceLocal.findByTipodocumento(tipodocumento, numeroDocumento);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
		return personanatural;
	}
	
	public Personajuridica buscarPersonajuridica(Tipodocumento tipodocumento, String numeroDocumento){
		Personajuridica personajuridica = null;
		try {
			personajuridica = personajuridicaServiceLocal.findByTipodocumento(tipodocumento, numeroDocumento);
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
				this.apellidoPaterno = personaNatural.getApellidopaterno();
				this.apellidoMaterno = personaNatural.getApellidomaterno();
				this.nombres = personaNatural.getNombres();
				this.fechaNacimiento = personaNatural.getFechanacimiento();
				this.comboSexo.setItemSelected(personaNatural.getSexo());
				this.comboEstadocivil.setItemSelected(personaNatural.getEstadocivil());
				this.ocupacion = personaNatural.getOcupacion();
				this.direccionPersonanatural = personaNatural.getDireccion();
				this.referenciaPersonanatural = personaNatural.getReferencia();
				this.telefonoPersonanatural = personaNatural.getTelefono();
				this.celularPersonanatural = personaNatural.getCelular();
				this.emailPersonanatural = personaNatural.getEmail();
			} else {
				this.apellidoPaterno = "";
				this.apellidoMaterno = "";
				this.nombres = "";
				Calendar calendar = Calendar.getInstance();
				calendar.clear();
				this.fechaNacimiento = calendar.getTime();
				this.comboSexo.setItemSelected(-1);
				this.comboEstadocivil.setItemSelected(-1);
				this.ocupacion = "";
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
	
	public void buscarPersonanaturalApoderado(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoApoderado.getObjectItemSelected();
			String numeroDocumento = numeroDocumentoApoderado;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoApoderado.setItemSelected(personaNatural.getTipodocumento());
				this.numeroDocumentoApoderado = personaNatural.getNumerodocumento();
				this.apellidoPaternoApoderado = personaNatural.getApellidopaterno();
				this.apellidoMaternoApoderado = personaNatural.getApellidomaterno();
				this.nombresApoderado = personaNatural.getNombres();
				this.fechaNacimientoApoderado = personaNatural.getFechanacimiento();
				this.comboSexoApoderado.setItemSelected(personaNatural.getSexo());
			} else {
				this.apellidoPaternoApoderado = "";
				this.apellidoMaternoApoderado = "";
				this.nombresApoderado = "";
				this.fechaNacimientoApoderado = Calendar.getInstance().getTime();
				this.comboSexoApoderado.setItemSelected(-1);
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
				this.fechaNacimientoRepresentantelegal = Calendar.getInstance().getTime();
				this.comboSexoRepresentantelegal.setItemSelected(-1);
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
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, e.getMessage());
		}
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
		this.titulares.put(keyMap, personanatural);
		
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
	
	public void changeTipodocumentoPersonajuridica(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoPersonajuridica.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoApoderado(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoApoderado.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoTitular(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoTitular.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoRepresentantelegal(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumentoSelected = comboTipodocumentoTitular.getObjectItemSelected(key);
	}

	public void changeTipopersona(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		switch (key) {
		case 0:
			isPersonanatural = false;
			isPersonajuridica = false;
			personanaturalSelected = false;
			personanajuridicaSelected = false;
			this.comboTipopersona.setItemSelected(0);
			break;
		case 1:
			isPersonanatural = true;
			isPersonajuridica = false;
			personanaturalSelected = true;
			personanajuridicaSelected = false;
			this.comboTipopersona.setItemSelected(1);
			break;
		case 2:
			isPersonanatural = false;
			isPersonajuridica = true;
			personanaturalSelected = false;
			personanajuridicaSelected = true;
			this.comboTipopersona.setItemSelected(2);
			break;
		default:
			isPersonanatural = false;
			isPersonajuridica = false;
			personanaturalSelected = false;
			personanajuridicaSelected = false;
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
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboBean<Estadocivil> getComboEstadocivil() {
		return comboEstadocivil;
	}

	public void setComboEstadocivil(ComboBean<Estadocivil> comboEstadocivil) {
		this.comboEstadocivil = comboEstadocivil;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
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

	public boolean isPersonanaturalSelected() {
		return personanaturalSelected;
	}

	public void setPersonanaturalSelected(boolean personanaturalSelected) {
		this.personanaturalSelected = personanaturalSelected;
	}

	public boolean isPersonanajuridicaSelected() {
		return personanajuridicaSelected;
	}

	public void setPersonanajuridicaSelected(boolean personanajuridicaSelected) {
		this.personanajuridicaSelected = personanajuridicaSelected;
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

	public ComboBean<Tipodocumento> getComboTipodocumentoApoderado() {
		return comboTipodocumentoApoderado;
	}

	public void setComboTipodocumentoApoderado(
			ComboBean<Tipodocumento> comboTipodocumentoApoderado) {
		this.comboTipodocumentoApoderado = comboTipodocumentoApoderado;
	}

	public String getNumeroDocumentoApoderado() {
		return numeroDocumentoApoderado;
	}

	public void setNumeroDocumentoApoderado(String numeroDocumentoApoderado) {
		this.numeroDocumentoApoderado = numeroDocumentoApoderado;
	}

	public String getApellidoPaternoApoderado() {
		return apellidoPaternoApoderado;
	}

	public void setApellidoPaternoApoderado(String apellidoPaternoApoderado) {
		this.apellidoPaternoApoderado = apellidoPaternoApoderado;
	}

	public String getApellidoMaternoApoderado() {
		return apellidoMaternoApoderado;
	}

	public void setApellidoMaternoApoderado(String apellidoMaternoApoderado) {
		this.apellidoMaternoApoderado = apellidoMaternoApoderado;
	}

	public String getNombresApoderado() {
		return nombresApoderado;
	}

	public void setNombresApoderado(String nombresApoderado) {
		this.nombresApoderado = nombresApoderado;
	}

	public Date getFechaNacimientoApoderado() {
		return fechaNacimientoApoderado;
	}

	public void setFechaNacimientoApoderado(Date fechaNacimientoApoderado) {
		this.fechaNacimientoApoderado = fechaNacimientoApoderado;
	}

	public ComboBean<Sexo> getComboSexoApoderado() {
		return comboSexoApoderado;
	}

	public void setComboSexoApoderado(ComboBean<Sexo> comboSexoApoderado) {
		this.comboSexoApoderado = comboSexoApoderado;
	}

	public boolean isExisteApoderado() {
		return existeApoderado;
	}

	public void setExisteApoderado(boolean existeApoderado) {
		this.existeApoderado = existeApoderado;
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

	public Map<String, Personanatural> getTitulares() {
		return titulares;
	}
	
	public List<Personanatural> listTitulares() {
		List<Personanatural> list = new ArrayList<Personanatural>();
		list.addAll(titulares.values());
		return list;
	}

	public void setTitulares(Map<String, Personanatural> titulares) {
		this.titulares = titulares;
	}

	public List<Beneficiario> listBeneficiarios() {
		List<Beneficiario> list = new ArrayList<Beneficiario>();
		list.addAll(beneficiarios.values());
		return list;
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

}
