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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaCorrienteBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Cuentacorrientehistorial;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;

@Named
@FlowScoped("aperturarCuentacorriente-flow")
public class AperturarCuentacorrienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentacorrienteServiceLocal cuentacorrienteServiceLocal;

	@Inject
	private Cuentacorriente cuentacorriente;
	@Inject
	private ComboBean<String> comboTipoPersona;
	@Inject
	private PersonaNaturalBean personaNaturalMB;
	@Inject
	private PersonaJuridicaBean personaJuridicaMB;
	@Inject
	private DatosFinancierosCuentaCorrienteBean datosFinancierosCuentaCorrienteMB;
	@Inject
	private TitularesBean titularesMB;
	@Inject
	private BeneficiariosBean beneficiariosMB;

	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}

	public String getReturnValue(){
		return "index";
	}

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentacorriente() {
		try {
			if (validarCuentacorriente(this.cuentacorriente)) {
				Cuentacorriente cuentacorriente = establecerParametrosCuentacorriente(this.cuentacorriente);
				if (isPersonaNatural()) {
					cuentacorriente = cuentacorrienteServiceLocal.createCuentaCorrienteWithPersonanatural(cuentacorriente);
				}
				if (isPersonaJuridica()) {
					cuentacorriente = cuentacorrienteServiceLocal.createCuentaCorrienteWithPersonajuridica(cuentacorriente);
				}				
				this.cuentacorriente = cuentacorriente;
				validarValoresNulosParaImpresion();
				return "imprimirAperturaCuenta-flow";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "System Error", mensaje);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "System Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	private void validarValoresNulosParaImpresion(){
		if(isPersonaNatural()){
			this.cuentacorriente.getSocio().setPersonajuridica(new Personajuridica());
			
			this.cuentacorriente.getSocio().getPersonajuridica().setRuc("");
			this.cuentacorriente.getSocio().getPersonajuridica().setRazonsocial("");
			this.cuentacorriente.getSocio().getPersonajuridica().setFechaconstitucion(Calendar.getInstance().getTime());
			
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);
			
			this.cuentacorriente.getSocio().getPersonajuridica().setPersonanatural(personanatural);
		}
		
		if(isPersonaJuridica()){
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);		
			this.cuentacorriente.getSocio().setPersonanatural(personanatural);
		}
	}
	
	public Cuentacorriente establecerParametrosCuentacorriente(Cuentacorriente cuentacorriente) throws Exception {
		Cuentacorrientehistorial cuentacorrientehistorial = datosFinancierosCuentaCorrienteMB.getCuentacorrientehistorial();
		Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
		cuentacorrientehistorial.setCantidadretirantes(cantidadRetirantes);
		
		cuentacorriente = datosFinancierosCuentaCorrienteMB.getCuentacorriente();
		
		Socio socio = new Socio();
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			cuentacorriente.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentacorriente(cuentacorriente);;
			}
			cuentacorriente.setTitularcuentas(titulares);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentacorriente(cuentacorriente);
			}		
			
			cuentacorriente.setBeneficiariocuentas(beneficiarios);				
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			socio.setEstado(true);
			cuentacorriente.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentacorriente(cuentacorriente);
			}
			cuentacorriente.setTitularcuentas(titulares);
			
			cuentacorriente.setBeneficiariocuentas(new ArrayList<Beneficiariocuenta>());
		}
		return cuentacorriente;
	}
	
	public void a (Cuentacorriente cuentacorriente){
		List<Cuentacorrientehistorial> historiales = cuentacorriente.getCuentacorrientehistorials();
		Cuentacorrientehistorial cuentaahorrohistorial = historiales.get(0);
		Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
		cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);
	}

	public boolean validarCuentacorriente(Cuentacorriente cuentacorriente) {

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
				this.mensaje = mensaje + "Datos de Persona Juridica invalidos \n";
			}
		}

		if (!datosFinancierosCuentaCorrienteMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos Financieros invalidos \n";
		}

		if (!titularesMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos de Titulares \n";
		}

		// falta validar datos financieros

		return result;
	}
	
	public void cleanCuentacorriente(){
		this.personaNaturalMB.setPersonaNatural(new Personanatural());
		this.personaJuridicaMB.setoPersonajuridica(new Personajuridica());
		this.titularesMB.getTablaTitulares().setRows(new ArrayList<Titularcuenta>());
		this.beneficiariosMB.getTablaBeneficiarios().setRows(new ArrayList<Beneficiariocuenta>());
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

	public void establecerTitularDefecto() {
		if (isPersonaNatural()) {		
			Personanatural personanatural = personaNaturalMB.getPersonaNatural();
			Titularcuenta titular = new Titularcuenta();
			titular.setPersonanatural(personanatural);
				
			Titularcuentahistorial titularcuentahistorial = new Titularcuentahistorial();
			titularcuentahistorial.setEstado(true);
			titularcuentahistorial.setFechaactiva(Calendar.getInstance().getTime());
			titularcuentahistorial.setTitularcuenta(titular);
			
			titular.addTitularhistorial(titularcuentahistorial);
			
			List<Titularcuenta> titularcuentas = this.titularesMB.getTablaTitulares().getFrozenRows();
			titularcuentas.clear();
			titularcuentas.add(titular);						
		}
		if (isPersonaJuridica()) {
			Personanatural representanteLegal = personaJuridicaMB.getoPersonajuridica().getPersonanatural();
			Titularcuenta titularRepresentante = new Titularcuenta();
			titularRepresentante.setPersonanatural(representanteLegal);

			Titularcuentahistorial titularcuentahistorial = new Titularcuentahistorial();
			titularcuentahistorial.setEstado(true);
			titularcuentahistorial.setFechaactiva(Calendar.getInstance().getTime());
			titularcuentahistorial.setTitularcuenta(titularRepresentante);
			
			titularRepresentante.addTitularhistorial(titularcuentahistorial);
			
			List<Titularcuenta> titularcuentas = this.titularesMB.getTablaTitulares().getFrozenRows();
			titularcuentas.clear();
			titularcuentas.add(titularRepresentante);
		}
	}

	/**
	 * 
	 * GETTER AND SETTER
	 * 
	 * **/

	public Cuentacorriente getCuentacorriente() {
		return cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
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

	public DatosFinancierosCuentaCorrienteBean getDatosFinancierosCuentaCorrienteMB() {
		return datosFinancierosCuentaCorrienteMB;
	}

	public void setDatosFinancierosCuentaCorrienteMB(
			DatosFinancierosCuentaCorrienteBean datosFinancierosCuentaCorrienteMB) {
		this.datosFinancierosCuentaCorrienteMB = datosFinancierosCuentaCorrienteMB;
	}

	public TitularesBean getTitularesMB() {
		return titularesMB;
	}

	public void setTitularesMB(TitularesBean titularesMB) {
		this.titularesMB = titularesMB;
	}

	public BeneficiariosBean getBeneficiariosMB() {
		return beneficiariosMB;
	}

	public void setBeneficiariosMB(BeneficiariosBean beneficiariosMB) {
		this.beneficiariosMB = beneficiariosMB;
	}
}
