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

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaAhorroBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorrohistorial;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;

@Named
@FlowScoped("aperturarCuentaahorro-flow")
public class AperturaCuentaahorroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentaahorroServiceLocal cuentaahorroServiceLocal;

	@Inject
	private Cuentaahorro cuentaahorro;
	@Inject
	private Cuentaahorrohistorial cuentaahorrohistorial;
	@Inject
	private ComboBean<String> comboTipoPersona;
	@Inject
	private PersonaNaturalBean personaNaturalMB;
	@Inject
	private PersonaJuridicaBean personaJuridicaMB;
	@Inject
	private DatosFinancierosCuentaAhorroBean datosFinancierosCuentaAhorroMB;
	@Inject
	private TitularesBean titularesMB;
	@Inject
	private BeneficiariosBean beneficiariosMB;

	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}
	
	public String getReturnValue() {
		return "/index?module=2";
	}
	
	public Integer getCantidadRetirantes(){
		Integer result  =0;
		List<Cuentaahorrohistorial> cuentaahorrohistorials = this.cuentaahorro.getCuentaahorrohistorials();
		for (Iterator<Cuentaahorrohistorial> iterator = cuentaahorrohistorials.iterator(); iterator.hasNext();) {
			Cuentaahorrohistorial cuentaahorrohistorial = (Cuentaahorrohistorial) iterator.next();
			if(cuentaahorrohistorial.getEstado()==true){
				result = cuentaahorrohistorial.getCantidadretirantes();
			}
		}
		return result;
	}

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentaahorro() {
		try {
			if (validarCurrentBean()) {
				Cuentaahorro cuentaahorro = establecerParametrosCuentaahorro(this.cuentaahorro);
				if (isPersonaNatural()) {
					cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonanatural(cuentaahorro);
				}
				if (isPersonaJuridica()) {
					cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonajuridica(cuentaahorro);
					//this.cuentaahorrohistorial = this.cuentaahorro.getCuentaahorrohistorials().get(0);
				}			
				this.cuentaahorro = cuentaahorro;
				validarValoresNulosParaImpresion();
				return "imprimirAperturaCuenta-flow";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "System Error", mensaje);
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	private void validarValoresNulosParaImpresion(){
		if(isPersonaNatural()){
			this.cuentaahorro.getSocio().setPersonajuridica(new Personajuridica());
			
			this.cuentaahorro.getSocio().getPersonajuridica().setRuc("");
			this.cuentaahorro.getSocio().getPersonajuridica().setRazonsocial("");
			this.cuentaahorro.getSocio().getPersonajuridica().setFechaconstitucion(Calendar.getInstance().getTime());
			
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);
			
			this.cuentaahorro.getSocio().getPersonajuridica().setPersonanatural(personanatural);
		}
		
		if(isPersonaJuridica()){
			Personanatural personanatural =  new Personanatural();
			personanatural.setDni("");
			personanatural.setFechanacimiento(Calendar.getInstance().getTime());
			Sexo sexo = new Sexo();
			sexo.setDenominacion("");
			personanatural.setSexo(sexo);		
			this.cuentaahorro.getSocio().setPersonanatural(personanatural);
		}
	}
	
	public Cuentaahorro establecerParametrosCuentaahorro(Cuentaahorro cuentaahorro) throws Exception {		
		Cuentaahorrohistorial cuentaahorrohistorial = datosFinancierosCuentaAhorroMB.getCuentaahorrohistorial();
		Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
		cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);
		
		cuentaahorro = datosFinancierosCuentaAhorroMB.getCuentaahorro();
		
		Socio socio = new Socio();
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			cuentaahorro.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentaahorro(cuentaahorro);
			}
			cuentaahorro.setTitularcuentas(titulares);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentaahorro(cuentaahorro);
			}		
			
			cuentaahorro.setBeneficiariocuentas(beneficiarios);		
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			socio.setEstado(true);
			cuentaahorro.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentaahorro(cuentaahorro);
			}
			cuentaahorro.setTitularcuentas(titulares);
			
			cuentaahorro.setBeneficiariocuentas(new ArrayList<Beneficiariocuenta>());	
		}
		
		return cuentaahorro;
	}
	

	public boolean validarCurrentBean() {

		boolean result = true;

		this.mensaje = "ERROR:\n";

		if (isPersonaNatural()) {
			if (!personaNaturalMB.isValid()) {
				result = false;
				this.mensaje = mensaje
						+ "Datos de Persona Natural Invalidos \n";
			}
			if (!beneficiariosMB.isValid()) {
				result = false;
				this.mensaje = mensaje + "Datos de Beneficiarios \n";
			}
		}

		if (isPersonaJuridica()) {
			if (!personaJuridicaMB.isValid()) {
				result = false;
				this.mensaje = mensaje
						+ "Datos de Persona Juridica invalidos \n";
			}
		}

		if (!datosFinancierosCuentaAhorroMB.isValid()) {
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
	
	public void cleanCuentaahorro(){
		this.personaNaturalMB.setPersonaNatural(new Personanatural());
		this.personaJuridicaMB.setoPersonajuridica(new Personajuridica());
		this.titularesMB.getTablaTitulares().setRows(new ArrayList<Titularcuenta>());
		this.beneficiariosMB.getTablaBeneficiarios().setRows(new ArrayList<Beneficiariocuenta>());
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

	public Cuentaahorro getCuentaahorro() {
		return cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
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

	public DatosFinancierosCuentaAhorroBean getDatosFinancierosCuentaAhorroMB() {
		return datosFinancierosCuentaAhorroMB;
	}

	public void setDatosFinancierosCuentaAhorroMB(
			DatosFinancierosCuentaAhorroBean datosFinancierosCuentaAhorroMB) {
		this.datosFinancierosCuentaAhorroMB = datosFinancierosCuentaAhorroMB;
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

	public Cuentaahorrohistorial getCuentaahorrohistorial() {
		return cuentaahorrohistorial;
	}

	public void setCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial) {
		this.cuentaahorrohistorial = cuentaahorrohistorial;
	}

}
