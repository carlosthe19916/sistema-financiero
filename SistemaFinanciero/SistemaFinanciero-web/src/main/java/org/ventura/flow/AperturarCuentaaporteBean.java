package org.ventura.flow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaAporteBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.sucursal.Agencia;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@FlowScoped("aperturarCuentaaporte-flow")
public class AperturarCuentaaporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private SocioServiceLocal socioServiceLocal;

	@Inject
	private Socio socio;
	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private ComboBean<String> comboTipoPersona;
	@Inject
	private PersonaNaturalBean personaNaturalMB;
	@Inject
	private PersonaJuridicaBean personaJuridicaMB;
	@Inject
	private DatosFinancierosCuentaAporteBean datosFinancierosCuentaAporteMB;
	@Inject
	private BeneficiariosBean beneficiariosMB;

	public String getReturnValue() {
		return "/index";
	}
		
	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentaaporte() {
		try {
 			if (validarAperturarCuentaaporteBean()) {
				Socio socio = new Socio();
				socio = establecerParametrosCuentaaporte(socio);
				socio = socioServiceLocal.create(socio);	
				this.socio = socio;
				validarValoresNulosParaImpresion();
				return "imprimirAperturaCuenta-flow";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "System Error", mensaje);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message); 
		}
		return null;
	}
	
	private void validarValoresNulosParaImpresion(){
		if(isPersonaNatural()){
			this.socio.setPersonajuridica(new Personajuridica());
			
			this.socio.getPersonajuridica().setRuc("");
			this.socio.getPersonajuridica().setRazonsocial("");
			this.socio.getPersonajuridica().setFechaconstitucion(Calendar.getInstance().getTime());
			
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);
			
			this.socio.getPersonajuridica().setPersonanatural(personanatural);
		}
		
		if(isPersonaJuridica()){
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);		
			this.socio.setPersonanatural(personanatural);
		}
	}

	public Socio establecerParametrosCuentaaporte(Socio socio) throws Exception {
		Agencia agencia = agenciaBean.getAgencia();
		Cuentaaporte cuentaaporte = new Cuentaaporte();
		cuentaaporte = datosFinancierosCuentaAporteMB.getCuentaaporte();
		
		socio.setAgencia(agencia);
		socio.setCuentaaporte(cuentaaporte);
		
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentaaporte(cuentaaporte);
			}			
			cuentaaporte.setBeneficiarios(beneficiarios);		
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
		}	
		return socio;
	}

	public boolean validarAperturarCuentaaporteBean() {
		boolean result = true;
		this.mensaje = "ERROR:\n";
		if (isPersonaNatural()) {
			if (!personaNaturalMB.isValid()) {
				result = false;
				this.mensaje = mensaje + "Datos de Persona Natural Invalidos \n";
			}
			if (!beneficiariosMB.isValid()) {
				result = false;
				this.mensaje = mensaje + "Datos de Beneficiarios \n";
			}
		}
		if (isPersonaJuridica()) {
			if (!personaJuridicaMB.isValid()) {
				result = false;
				this.mensaje = mensaje+ "Datos de Persona Juridica invalidos \n";
			}
		}
		if (!datosFinancierosCuentaAporteMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos Financieros invalidos \n";
		}
		return result;
	}
	
	public void establecerTitularDefecto() {
		if(comboTipoPersona.getItemSelected()==2){
			personaJuridicaMB.validarPorcentajeParticipacion();
		}
	}

	public boolean isPersonaNatural() {
		if (comboTipoPersona.getItemSelected() == 1)
			return true;
		else
			return false;
	}

	public boolean isPersonaJuridica() {
		if (comboTipoPersona.getItemSelected() == 2)
			return true;
		else
			return false;
	}
	
	public void cleanCuentaaporte(){
		this.personaNaturalMB.setPersonaNatural(new Personanatural());
		this.personaJuridicaMB.setoPersonajuridica(new Personajuridica());
		this.beneficiariosMB.getTablaBeneficiarios().setRows(new ArrayList<Beneficiariocuenta>());
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ComboBean<String> getComboTipoPersona() {
		return comboTipoPersona;
	}

	public void setComboTipoPersona(ComboBean<String> comboTipoPersona) {
		this.comboTipoPersona = comboTipoPersona;
	}

	public PersonaNaturalBean getPersonaNaturalMB() {
		return personaNaturalMB;
	}

	public void setPersonaNaturalMB(PersonaNaturalBean personaNaturalMB) {
		this.personaNaturalMB = personaNaturalMB;
	}

	public PersonaJuridicaBean getPersonaJuridicaMB() {
		return personaJuridicaMB;
	}

	public void setPersonaJuridicaMB(PersonaJuridicaBean personaJuridicaMB) {
		this.personaJuridicaMB = personaJuridicaMB;
	}

	public DatosFinancierosCuentaAporteBean getDatosFinancierosCuentaAporteMB() {
		return datosFinancierosCuentaAporteMB;
	}

	public void setDatosFinancierosCuentaAporteMB(DatosFinancierosCuentaAporteBean datosFinancierosCuentaAporteMB) {
		this.datosFinancierosCuentaAporteMB = datosFinancierosCuentaAporteMB;
	}

	public BeneficiariosBean getBeneficiariosMB() {
		return beneficiariosMB;
	}

	public void setBeneficiariosMB(BeneficiariosBean beneficiariosMB) {
		this.beneficiariosMB = beneficiariosMB;
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}

