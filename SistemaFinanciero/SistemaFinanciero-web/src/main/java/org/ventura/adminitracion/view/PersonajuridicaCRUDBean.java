package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.persona.Tipoempresa;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class PersonajuridicaCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean pageOne;
	private boolean pageTwo;
	
	//vista 01
	@Inject 
	private ComboBean<Tipodocumento> comboTipodocumentoPersonajuridica;
	@NotNull
	private String numerodocumentoPersonajuridica;	
	@NotNull @Size(min = 2, max = 50)
	private String razonsocial;
	@NotNull @Size(min = 2, max = 50)
	private String nombrecomercial;
	@Size(min = 2, max = 50)
	private String actividadprincipal;
	@NotNull @Past
	private Date fechaconstitucion;
	@Inject private ComboBean<Tipoempresa> comboTipoempresa;
	@Inject private ComboBean<String> comboFinsocial;
	private String direccionPersonajuridica;
	private String referenciaPersonajuridica;
	private String telefonoPersonajuridica;
	private String celularPersonajuridica;
	private String emailPersonajuridica;
	
	@Inject private ComboBean<Tipodocumento> comboTipodocumentoRepresentantelegal;
	private String numerodocumentoRepresentantelegal;
	private String apellidopaternoRepresentantelegal;
	private String apellidomaternoRepresentantelegal;
	private String nombresRepresentantelegal;
	private Date fechanacimientoRepresentantelegal;
	@Inject private ComboBean<Sexo> comboSexoRepresentantelegal;
	
	// VISTA 02
	private boolean isDlgAccionistaOpen;
	private Map<String, Accionista> accionistas;
	@Inject
	private ComboBean<Tipodocumento> comboTipodocumentoAccionista;
	private String numeroDocumentoAccionista;
	private String apellidoPaternoAccionista;
	private String apellidoMaternoAccionista;
	private String nombresAccionista;
	private Date fechaNacimientoAccionista;
	@Inject
	private ComboBean<Sexo> comboSexoAccionista;
	private BigDecimal porcentajeParticipacionAccionista;

	private Personajuridica personajuridica;
	private Integer idpersonajuridica;
	
	private boolean success;
	private boolean failure;
	
	@EJB private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB private MaestrosServiceLocal maestrosServiceLocal;

	public PersonajuridicaCRUDBean() {
		success = false;
		failure = false;
		
		pageOne = true;
		pageTwo = false;
		
		accionistas = new HashMap<String, Accionista>();
	}
	
	@PostConstruct
	private void initialize() {		
		try {
			List<Tipodocumento> listTipodocumentoPersonajuridica = maestrosServiceLocal.getTipodocumentoForPersonaJuridica();
			List<Tipodocumento> listTipodocumentoPersonanatural = maestrosServiceLocal.getTipodocumentoForPersonaNatural();
			
			comboTipodocumentoPersonajuridica.setItems(listTipodocumentoPersonajuridica);
			comboTipodocumentoAccionista.setItems(listTipodocumentoPersonanatural);
			comboTipodocumentoRepresentantelegal.setItems(listTipodocumentoPersonanatural);
			
			comboTipoempresa.initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
			
			comboSexoRepresentantelegal.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboSexoAccionista.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			
			this.comboFinsocial.putItem(0, "--NO ESPECIFICA--");
			this.comboFinsocial.putItem(1, "CON FINES DE LUCRO");
			this.comboFinsocial.putItem(2, "SIN FINES DE LUCRO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loadPersonajuridicaForEdit() {		
		try {
			if (idpersonajuridica != null && idpersonajuridica != -1) {
				personajuridica = personajuridicaServiceLocal.find(idpersonajuridica);
				
				comboTipodocumentoPersonajuridica.setItemSelected(personajuridica.getTipodocumento());
				numerodocumentoPersonajuridica = personajuridica.getNumerodocumento();
				razonsocial = personajuridica.getRazonsocial();
				nombrecomercial = personajuridica.getNombrecomercial();
				actividadprincipal = personajuridica.getActividadprincipal();
				fechaconstitucion = personajuridica.getFechaconstitucion();
				comboTipoempresa.setItemSelected(personajuridica.getTipoempresa());
				comboFinsocial.setItemSelected(personajuridica.getFindelucro() ? 1 : 2);
				direccionPersonajuridica = personajuridica.getDireccion();
				referenciaPersonajuridica = personajuridica.getReferencia();
				telefonoPersonajuridica = personajuridica.getTelefono();
				celularPersonajuridica = personajuridica.getCelular();
				emailPersonajuridica = personajuridica.getEmail();
				
				Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
				comboTipodocumentoRepresentantelegal.setItemSelected(representanteLegal.getTipodocumento());
				numerodocumentoRepresentantelegal = representanteLegal.getNumerodocumento();
				apellidopaternoRepresentantelegal = representanteLegal.getApellidopaterno();
				apellidomaternoRepresentantelegal = representanteLegal.getApellidomaterno();
				nombresRepresentantelegal = representanteLegal.getNombres();
				fechanacimientoRepresentantelegal = representanteLegal.getFechanacimiento();
				comboSexoRepresentantelegal.setItemSelected(representanteLegal.getSexo());	
				
				this.accionistas.clear();
				List<Accionista> listAccionistas = personajuridica.getAccionistas();
				for (Accionista accionista : listAccionistas) {
					Personanatural personanatural = accionista.getPersonanatural();
					String keyMap = personanatural.getTipodocumento().getIdtipodocumento() + personanatural.getNumerodocumento();
					this.accionistas.put(keyMap, accionista);
				}
			} else {
				failure = true;
				JsfUtil.addErrorMessage("No se encontró la persona");
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public void createPersona() throws Exception {
		Personajuridica personajuridica;
		try {	
			if(success == false){		
				personajuridica = new Personajuridica();
				personajuridica.setTipodocumento(comboTipodocumentoPersonajuridica.getObjectItemSelected());
				personajuridica.setNumerodocumento(numerodocumentoPersonajuridica);
				personajuridica.setRazonsocial(razonsocial);
				personajuridica.setNombrecomercial(nombrecomercial);
				personajuridica.setActividadprincipal(actividadprincipal);
				personajuridica.setFechaconstitucion(fechaconstitucion);
				personajuridica.setTipoempresa(comboTipoempresa.getObjectItemSelected());
				personajuridica.setFindelucro(comboFinsocial.getItemSelected() == 1 ? true : false);		
				personajuridica.setDireccion(direccionPersonajuridica);
				personajuridica.setReferencia(referenciaPersonajuridica);
				personajuridica.setTelefono(telefonoPersonajuridica);
				personajuridica.setCelular(celularPersonajuridica);
				personajuridica.setEmail(emailPersonajuridica);
												
				Personanatural representanteLegal = new Personanatural();
				representanteLegal.setTipodocumento(comboTipodocumentoRepresentantelegal.getObjectItemSelected());
				representanteLegal.setNumerodocumento(numerodocumentoRepresentantelegal);
				representanteLegal.setApellidopaterno(apellidopaternoRepresentantelegal);
				representanteLegal.setApellidomaterno(apellidomaternoRepresentantelegal);
				representanteLegal.setNombres(nombresRepresentantelegal);
				representanteLegal.setFechanacimiento(fechanacimientoRepresentantelegal);
				representanteLegal.setSexo(comboSexoRepresentantelegal.getObjectItemSelected());
				
				List<Accionista> listAccionistas = new ArrayList<Accionista>();
				listAccionistas.addAll(accionistas.values());		
				personajuridica.setRepresentanteLegal(representanteLegal);
				personajuridica.setAccionistas(listAccionistas);
					
				boolean result = validarPorcentaje(listAccionistas);
				if(result == true){
					personajuridica = personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
					this.success = true;
					this.failure = false;
				} else {
					this.failure = true;
					JsfUtil.addErrorMessage("El porcentaje de participacion supera 100%");
				}		
			}	
		} catch (Exception e) {
			this.failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public boolean validarPorcentaje(List<Accionista> listAccionista){
		BigDecimal porcentajeTotal = BigDecimal.ZERO;
		for (Accionista accionista : listAccionista) {
			porcentajeTotal = porcentajeTotal.add(accionista.getPorcentajeparticipacion());
		}
		return porcentajeTotal.compareTo(new BigDecimal(100)) <= 0 ? true : false;
	}

	public void updatePersona() throws Exception {
		try {	
			if(success == false){
				personajuridica.setTipodocumento(comboTipodocumentoPersonajuridica.getObjectItemSelected());
				personajuridica.setNumerodocumento(numerodocumentoPersonajuridica);
				personajuridica.setRazonsocial(razonsocial);
				personajuridica.setNombrecomercial(nombrecomercial);
				personajuridica.setActividadprincipal(actividadprincipal);
				personajuridica.setFechaconstitucion(fechaconstitucion);				
				personajuridica.setTipoempresa(comboTipoempresa.getObjectItemSelected());
				personajuridica.setFindelucro(comboFinsocial.getItemSelected() == 1 ? true : false);		
				personajuridica.setDireccion(direccionPersonajuridica);
				personajuridica.setReferencia(referenciaPersonajuridica);
				personajuridica.setTelefono(telefonoPersonajuridica);
				personajuridica.setCelular(celularPersonajuridica);
				personajuridica.setEmail(emailPersonajuridica);
												
				Personanatural representanteLegal = new Personanatural();
				representanteLegal.setTipodocumento(comboTipodocumentoRepresentantelegal.getObjectItemSelected());
				representanteLegal.setNumerodocumento(numerodocumentoRepresentantelegal);
				representanteLegal.setApellidopaterno(apellidopaternoRepresentantelegal);
				representanteLegal.setApellidomaterno(apellidomaternoRepresentantelegal);
				representanteLegal.setNombres(nombresRepresentantelegal);
				representanteLegal.setFechanacimiento(fechanacimientoRepresentantelegal);
				representanteLegal.setSexo(comboSexoRepresentantelegal.getObjectItemSelected());
				
				List<Accionista> listAccionistas = new ArrayList<Accionista>();
				listAccionistas.addAll(accionistas.values());
				
				personajuridica.setRepresentanteLegal(representanteLegal);
				personajuridica.setAccionistas(listAccionistas);
				
				boolean result = validarPorcentaje(listAccionistas);
				if(result == true){
					Object obj = this.personajuridicaServiceLocal.find(idpersonajuridica);
					if(obj != null){
						this.personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
						this.success = true;
						this.failure = false;
					} else {
						throw new Exception("La persona no esta registrada, no se puede actualizar");
					}	
				} else {
					this.failure = true;
					JsfUtil.addErrorMessage("El porcentaje de participacion supera el 100%");
				}
			}	
		} catch (Exception e) {
			this.failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void changeToPageOne(){
		pageOne = true;
		pageTwo = false;
		failure = false;
	}
	
	public void changeToTwo(){	
		if(idpersonajuridica == null || idpersonajuridica == -1){
			Personajuridica personajuridica = buscarPersonajuridica();
			if(personajuridica == null){
				pageOne = false;
				pageTwo = true;
				failure = false;
			} else {
				failure = true;
				JsfUtil.addErrorMessage("La persona juridica ya existe, no se puede crear nuevamente");
			}
		} else {
			pageOne = false;
			pageTwo = true;
		}
	}
	
	public Personajuridica buscarPersonajuridica(){
		Personajuridica personajuridica = null;
		try {
			Tipodocumento tipodocumento = comboTipodocumentoPersonajuridica.getObjectItemSelected();
			String numerodocumento = numerodocumentoPersonajuridica;
			personajuridica = personajuridicaServiceLocal.find(tipodocumento, numerodocumento);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
		return personajuridica;
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
	
	public void buscarPersonanaturalRepresentantelegal(){
		try {
			Tipodocumento tipodocumento = comboTipodocumentoRepresentantelegal.getObjectItemSelected();
			String numeroDocumento = numerodocumentoRepresentantelegal;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null){
				this.comboTipodocumentoRepresentantelegal.setItemSelected(personaNatural.getTipodocumento());
				this.numerodocumentoRepresentantelegal = personaNatural.getNumerodocumento();
				this.apellidopaternoRepresentantelegal = personaNatural.getApellidopaterno();
				this.apellidomaternoRepresentantelegal = personaNatural.getApellidomaterno();
				this.nombresRepresentantelegal = personaNatural.getNombres();
				this.fechanacimientoRepresentantelegal = personaNatural.getFechanacimiento();
				this.comboSexoRepresentantelegal.setItemSelected(personaNatural.getSexo());
			} else {
				this.apellidopaternoRepresentantelegal = "";
				this.apellidomaternoRepresentantelegal = "";
				this.nombresRepresentantelegal = "";
				this.fechanacimientoRepresentantelegal = null;
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
		accionista.setPorcentajeparticipacion(new BigDecimal(porcentajeParticipacionAccionista.toString()));
		
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
	
	public List<Accionista> listAccionistas() {
		List<Accionista> list = new ArrayList<Accionista>();
		list.addAll(accionistas.values());
		return list;
	}
	
	public void changeTipodocumentoRepresentantelegal(ValueChangeEvent event) {
		//Integer key = (Integer) event.getNewValue();
		//Tipodocumento tipodocumentoSelected = comboTipodocumentoRepresentantelegal.getObjectItemSelected(key);
	}
	
	public void changeTipodocumentoAccionista(ValueChangeEvent event) {
		//Integer key = (Integer) event.getNewValue();
		//Tipodocumento tipodocumentoSelected = comboTipodocumentoRepresentantelegal.getObjectItemSelected(key);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public ComboBean<Tipodocumento> getComboTipodocumentoPersonajuridica() {
		return comboTipodocumentoPersonajuridica;
	}

	public void setComboTipodocumentoPersonajuridica(
			ComboBean<Tipodocumento> comboTipodocumentoPersonajuridica) {
		this.comboTipodocumentoPersonajuridica = comboTipodocumentoPersonajuridica;
	}

	public String getNumerodocumentoPersonajuridica() {
		return numerodocumentoPersonajuridica;
	}

	public void setNumerodocumentoPersonajuridica(
			String numeroocumentoPersonajuridica) {
		this.numerodocumentoPersonajuridica = numeroocumentoPersonajuridica;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public String getNombrecomercial() {
		return nombrecomercial;
	}

	public void setNombrecomercial(String nombrecomercial) {
		this.nombrecomercial = nombrecomercial;
	}

	public String getActividadprincipal() {
		return actividadprincipal;
	}

	public void setActividadprincipal(String actividadprincipal) {
		this.actividadprincipal = actividadprincipal;
	}

	public Date getFechaconstitucion() {
		return fechaconstitucion;
	}

	public void setFechaconstitucion(Date fechaconstitucion) {
		this.fechaconstitucion = fechaconstitucion;
	}

	public ComboBean<Tipoempresa> getComboTipoempresa() {
		return comboTipoempresa;
	}

	public void setComboTipoempresa(ComboBean<Tipoempresa> combotipoempresa) {
		this.comboTipoempresa = combotipoempresa;
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

	public ComboBean<Tipodocumento> getComboTipodocumentoRepresentantelegal() {
		return comboTipodocumentoRepresentantelegal;
	}

	public void setComboTipodocumentoRepresentantelegal(
			ComboBean<Tipodocumento> comboTipodocumentoRepresentantelegal) {
		this.comboTipodocumentoRepresentantelegal = comboTipodocumentoRepresentantelegal;
	}

	public String getNumerodocumentoRepresentantelegal() {
		return numerodocumentoRepresentantelegal;
	}

	public void setNumerodocumentoRepresentantelegal(
			String numerodocumentoRepresentantelegal) {
		this.numerodocumentoRepresentantelegal = numerodocumentoRepresentantelegal;
	}

	public String getApellidopaternoRepresentantelegal() {
		return apellidopaternoRepresentantelegal;
	}

	public void setApellidopaternoRepresentantelegal(
			String apellidopaternoRepresentantelegal) {
		this.apellidopaternoRepresentantelegal = apellidopaternoRepresentantelegal;
	}

	public String getApellidomaternoRepresentantelegal() {
		return apellidomaternoRepresentantelegal;
	}

	public void setApellidomaternoRepresentantelegal(
			String apellidomaternoRepresentantelegal) {
		this.apellidomaternoRepresentantelegal = apellidomaternoRepresentantelegal;
	}

	public String getNombresRepresentantelegal() {
		return nombresRepresentantelegal;
	}

	public void setNombresRepresentantelegal(String nombresRepresentantelegal) {
		this.nombresRepresentantelegal = nombresRepresentantelegal;
	}

	public Date getFechanacimientoRepresentantelegal() {
		return fechanacimientoRepresentantelegal;
	}

	public void setFechanacimientoRepresentantelegal(
			Date fechanacimientoRepresentantelegal) {
		this.fechanacimientoRepresentantelegal = fechanacimientoRepresentantelegal;
	}

	public ComboBean<Sexo> getComboSexoRepresentantelegal() {
		return comboSexoRepresentantelegal;
	}

	public void setComboSexoRepresentantelegal(
			ComboBean<Sexo> comboSexoRepresentantelegal) {
		this.comboSexoRepresentantelegal = comboSexoRepresentantelegal;
	}

	public boolean isDlgAccionistaOpen() {
		return isDlgAccionistaOpen;
	}

	public void setDlgAccionistaOpen(boolean isDlgAccionistaOpen) {
		this.isDlgAccionistaOpen = isDlgAccionistaOpen;
		
		if(isDlgAccionistaOpen == false){
			this.comboTipodocumentoAccionista.setItemSelected(-1);
			this.numeroDocumentoAccionista = "";
			this.apellidoPaternoAccionista = "";
			this.apellidoMaternoAccionista = "";
			this.nombresAccionista = "";
			this.fechaNacimientoAccionista = null;
			this.comboSexoAccionista.setItemSelected(-1);
			BigDecimal bigDecimal = new BigDecimal(0);
			bigDecimal.setScale(2);
			this.porcentajeParticipacionAccionista = bigDecimal;
		}	
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

	public BigDecimal getPorcentajeParticipacionAccionista() {
		return porcentajeParticipacionAccionista;
	}

	public void setPorcentajeParticipacionAccionista(
			BigDecimal porcentajeParticipacionAccionista) {
		this.porcentajeParticipacionAccionista = porcentajeParticipacionAccionista;
	}

	public boolean isPageOne() {
		return pageOne;
	}

	public void setPageOne(boolean pageOne) {
		this.pageOne = pageOne;
	}

	public boolean isPageTwo() {
		return pageTwo;
	}

	public void setPageTwo(boolean pageTwo) {
		this.pageTwo = pageTwo;
	}

	public Personajuridica getPersonajuridica() {
		return personajuridica;
	}

	public void setPersonajuridica(Personajuridica personajuridica) {
		this.personajuridica = personajuridica;
	}

	public Integer getIdpersonajuridica() {
		return idpersonajuridica;
	}

	public void setIdpersonajuridica(Integer idpersonajuridica) {
		this.idpersonajuridica = idpersonajuridica;
	}

}
