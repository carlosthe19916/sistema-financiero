package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class TrabajadorCRUDAdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean succes;
	private boolean failure;
	
	private int longitudDocumento;
	
	@Inject private ComboBean<Tipodocumento> comboTipodocumento;
	@NotNull private String numerodocumento;
	@NotNull @Size(min = 2, max = 50) private String apellidopaterno;
	@NotNull @Size(min = 2, max = 50) private String apellidomaterno;
	@NotNull @Size(min = 2, max = 50) private String nombres;
	@Inject private ComboBean<Sexo> comboSexo;
	@NotNull @Past private Date fechanacimiento;	
	@Inject private ComboBean<Estadocivil> comboEstadocivil;
	@Size(min = 0, max = 50) private String ocupacion;
	@Size(min = 0, max = 50) private String direccion;
	@Size(min = 0, max = 50) private String referencia;
	@Size(min = 0, max = 30) private String telefono;
	@Size(min = 0, max = 30) private String celular;
	@Email private String email;
	
	private Integer idtrabajador;
	private Trabajador trabajador;
	
	@Inject private ComboBean<Agencia> comboAgencia;
	
	@EJB private MaestrosServiceLocal maestrosServiceLocal; 
	@EJB private TrabajadorServiceLocal trabajadorServiceLocal;
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	public TrabajadorCRUDAdminBean() {
		succes = false;
		failure = false;
		idtrabajador = -1;
		
		longitudDocumento = 15;
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {			
			List<Tipodocumento> list = maestrosServiceLocal.getTipodocumentoForPersonaNatural();
			comboTipodocumento.setItems(list);
			
			comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
			comboEstadocivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
			comboAgencia.initValuesFromNamedQueryName(Agencia.f_allActive);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void loadTrabajadorForEdit(){
		try {
			if (idtrabajador != null && idtrabajador != -1) {
				trabajador = trabajadorServiceLocal.find(idtrabajador);
				Personanatural personanatural = trabajador.getPersonanatural();
				
				comboTipodocumento.setItemSelected(personanatural.getTipodocumento());
				numerodocumento = personanatural.getNumerodocumento();
				apellidopaterno = personanatural.getApellidopaterno();
				apellidomaterno = personanatural.getApellidomaterno();
				nombres = personanatural.getNombres();
				comboSexo.setItemSelected(personanatural.getSexo());
				fechanacimiento = personanatural.getFechanacimiento();
				comboEstadocivil.setItemSelected(personanatural.getEstadocivil());
				ocupacion = personanatural.getOcupacion();
				direccion = personanatural.getDireccion();
				referencia = personanatural.getReferencia();
				telefono = personanatural.getTelefono();
				celular = personanatural.getCelular();
				email = personanatural.getEmail();
				
				comboAgencia.setItemSelected(trabajador.getAgencia());
			} else {
				failure = true;
				JsfUtil.addErrorMessage("No se encontró al trabajador");
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void updateTrabajador(){
		try {
			if(succes == false){
				Personanatural personanatural = trabajador.getPersonanatural();
				personanatural.setTipodocumento(comboTipodocumento.getObjectItemSelected());
				personanatural.setNumerodocumento(numerodocumento);
				personanatural.setApellidopaterno(apellidopaterno);
				personanatural.setApellidomaterno(apellidomaterno);
				personanatural.setNombres(nombres);
				personanatural.setSexo(comboSexo.getObjectItemSelected());
				personanatural.setFechanacimiento(fechanacimiento);				
				personanatural.setEstadocivil(comboEstadocivil.getObjectItemSelected());
				personanatural.setOcupacion(ocupacion);
				personanatural.setDireccion(direccion);
				personanatural.setReferencia(referencia);
				personanatural.setTelefono(telefono);
				personanatural.setCelular(celular);
				personanatural.setEmail(email);
				
				trabajador.setAgencia(comboAgencia.getObjectItemSelected());
				trabajador.setPersonanatural(personanatural);
				trabajadorServiceLocal.update(trabajador);
				
				succes = true;
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void createTrabajador(){
		try {
			if(succes == false){
				Trabajador trabajador = new Trabajador();
				Personanatural personanatural = new Personanatural();
				personanatural.setTipodocumento(comboTipodocumento.getObjectItemSelected());
				personanatural.setNumerodocumento(numerodocumento);
				personanatural.setApellidopaterno(apellidopaterno);
				personanatural.setApellidomaterno(apellidomaterno);
				personanatural.setNombres(nombres);
				personanatural.setSexo(comboSexo.getObjectItemSelected());
				personanatural.setFechanacimiento(fechanacimiento);				
				personanatural.setEstadocivil(comboEstadocivil.getObjectItemSelected());
				personanatural.setOcupacion(ocupacion);
				personanatural.setDireccion(direccion);
				personanatural.setReferencia(referencia);
				personanatural.setTelefono(telefono);
				personanatural.setCelular(celular);
				personanatural.setEmail(email);
				
				trabajador.setAgencia(comboAgencia.getObjectItemSelected());
				trabajador.setEstado(true);
				trabajador.setPersonanatural(personanatural);
				trabajadorServiceLocal.create(trabajador);
				
				succes = true;
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void buscarPersonanatural(){
		try {
			Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected();
			String numeroDocumento = numerodocumento;
			Personanatural personaNatural = buscarPersonanatural(tipodocumento, numeroDocumento);
			
			if(personaNatural != null) {
				this.comboTipodocumento.setItemSelected(personaNatural.getTipodocumento());
				this.numerodocumento = personaNatural.getNumerodocumento();
				this.apellidopaterno = personaNatural.getApellidopaterno();
				this.apellidomaterno = personaNatural.getApellidomaterno();
				this.nombres = personaNatural.getNombres();
				this.fechanacimiento = personaNatural.getFechanacimiento();
				this.comboSexo.setItemSelected(personaNatural.getSexo());
				this.comboEstadocivil.setItemSelected(personaNatural.getEstadocivil());
				this.ocupacion = personaNatural.getOcupacion();
				this.direccion = personaNatural.getDireccion();
				this.referencia = personaNatural.getReferencia();
				this.telefono = personaNatural.getTelefono();
				this.celular = personaNatural.getCelular();
				this.email = personaNatural.getEmail();
			} else {
				this.apellidopaterno = "";
				this.apellidomaterno = "";
				this.nombres = "";
				this.fechanacimiento = null;
				this.comboSexo.setItemSelected(-1);
				this.comboEstadocivil.setItemSelected(-1);
				this.ocupacion = "";
				this.direccion = "";
				this.referencia = "";
				this.telefono = "";
				this.celular = "";
				this.email = "";
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
		
	public void changeTipodocumento(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipodocumento tipodocumento = comboTipodocumento.getObjectItemSelected(key);
		if(tipodocumento != null){
			if(ProduceObject.getTipodocumento(TipodocumentoType.DNI).equals(tipodocumento)){
				this.longitudDocumento = 8;
			}
			if(ProduceObject.getTipodocumento(TipodocumentoType.PASAPORTE).equals(tipodocumento)){
				this.longitudDocumento = 11;
			}
			if(ProduceObject.getTipodocumento(TipodocumentoType.CARNET_EXTRANGERIA).equals(tipodocumento)){
				this.longitudDocumento = 11;
			}
		}		
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(ComboBean<Tipodocumento> comboTipodocumento) {
		this.comboTipodocumento = comboTipodocumento;
	}

	public String getNumerodocumento() {
		return numerodocumento;
	}

	public void setNumerodocumento(String numerodocumento) {
		this.numerodocumento = numerodocumento;
	}

	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdtrabajador() {
		return idtrabajador;
	}

	public void setIdtrabajador(Integer idtrabajador) {
		this.idtrabajador = idtrabajador;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public int getLongitudDocumento() {
		return longitudDocumento;
	}

	public void setLongitudDocumento(int longitudDocumento) {
		this.longitudDocumento = longitudDocumento;
	}

	public ComboBean<Agencia> getComboAgencia() {
		return comboAgencia;
	}

	public void setComboAgencia(ComboBean<Agencia> comboAgencia) {
		this.comboAgencia = comboAgencia;
	}
	
}
