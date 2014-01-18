package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class PersonanaturalCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject 
	ComboBean<Tipodocumento> comboTipodocumento;
	@NotNull 
	private String numerodocumento;
	@NotNull @Size(min = 2, max = 50)
	private String apellidopaterno;
	@NotNull @Size(min = 2, max = 50)
	private String apellidomaterno;
	@NotNull @Size(min = 2, max = 50)
	private String nombres;
	@Inject
	private ComboBean<Sexo> comboSexo;
	@NotNull @Past
	private Date fechanacimiento;	
	@Inject
	private ComboBean<Estadocivil> comboEstadocivil;
	@Size(min = 0, max = 50)
	private String ocupacion;
	@Size(min = 0, max = 50)
	private String direccion;
	@Size(min = 0, max = 50)
	private String referencia;
	@Size(min = 0, max = 20)
	private String telefono;
	@Size(min = 0, max = 20)
	private String celular;
	@Email
	private String email;
	
	private Personanatural personanatural;
	private Integer idpersonanatural;
	
	private boolean success;
	private boolean failure;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;

	public PersonanaturalCRUDBean() {
		success = false;
		failure = false;
	}
	
	@PostConstruct
	private void initialize() {
		comboTipodocumento.initValuesFromNamedQueryName(Tipodocumento.AllForPersonaNatural);
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadocivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
	}
	
	public void loadPersonanaturalForEdit() {		
		try {
			if (idpersonanatural != null && idpersonanatural != -1) {
				personanatural = personanaturalServiceLocal.find(idpersonanatural);
				
				comboTipodocumento.setItemSelected(personanatural.getTipodocumento());
				numerodocumento = personanatural.getNumerodocumento();
				apellidopaterno = personanatural.getApellidopaterno();
				apellidomaterno = personanatural.getApellidomaterno();
				nombres = personanatural.getNombres();
				comboSexo.setItemSelected(personanatural.getSexo());
				fechanacimiento = personanatural.getFechanacimiento();
				comboEstadocivil.setItemSelected(personanatural.getEstadocivil());
				ocupacion = personanatural.getOcupacion();
				referencia = personanatural.getReferencia();
				telefono = personanatural.getTelefono();
				celular = personanatural.getCelular();
				email = personanatural.getEmail();
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
		Personanatural personanatural;
		try {	
			if(success == false){
				personanatural = new Personanatural();
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
				
				Object obj = this.personanaturalServiceLocal.find(comboTipodocumento.getObjectItemSelected(), numerodocumento);
				if(obj == null){
					this.personanaturalServiceLocal.create(personanatural);
					this.success = true;
					JsfUtil.addSuccessMessage("Persona Creada");
				} else {
					throw new Exception("La persona ya esta registrada, no se puede crear nuevamente");
				}	
			}	
		} catch (Exception e) {
			this.failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public void updatePersona() throws Exception {
		try {	
			if(success == false){
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
				
				Object obj = this.personanaturalServiceLocal.find(idpersonanatural);
				if(obj != null){
					this.personanaturalServiceLocal.update(personanatural);
					this.success = true;
					JsfUtil.addSuccessMessage("Persona Actualizada");
				} else {
					throw new Exception("La no esta registrada, no se puede actualizar");
				}	
			}	
		} catch (Exception e) {
			this.failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public boolean isValidBean() {
		/*
		 * if(idboveda == null || idboveda == -1){ return false; } else { return
		 * true; }
		 */return true;
	}

	public ComboBean<Tipodocumento> getComboTipodocumento() {
		return comboTipodocumento;
	}

	public void setComboTipodocumento(
			ComboBean<Tipodocumento> comboTipodocumento) {
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

	public void setApellidomaterno(String appellidomaterno) {
		this.apellidomaterno = appellidomaterno;
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

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	public Integer getIdpersonanatural() {
		return idpersonanatural;
	}

	public void setIdpersonanatural(Integer idpersonanatural) {
		this.idpersonanatural = idpersonanatural;
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

}
