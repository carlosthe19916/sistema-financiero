package org.ventura.flow;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Cuentacorrientehistorial;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Titularcuenta;

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
	
	private List<Titularcuenta> titularDefecto;

	public AperturarCuentacorrienteBean() {
		this.titularDefecto = new ArrayList<Titularcuenta>();
	}

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
				return "aperturarCuentacorriente-flowA";
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

	public Cuentacorriente establecerParametrosCuentacorriente(Cuentacorriente cuentacorriente) throws Exception {
		Socio socio = new Socio();
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			cuentacorriente.setSocio(socio);
			
			List<Titularcuenta> titularcuentas = titularesMB.getListTitulares();	
			cuentacorriente.setTitularcuentas(titularcuentas);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			cuentacorriente.setBeneficiariocuentas(beneficiarios);		
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			cuentacorriente.setSocio(socio);
			
			List<Titularcuenta> titularcuentas = titularesMB.getListTitulares();	
			cuentacorriente.setTitularcuentas(titularcuentas);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			cuentacorriente.setBeneficiariocuentas(beneficiarios);
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
			Titularcuenta titularcuenta = new Titularcuenta();
			titularcuenta.setPersonanatural(personanatural);
									
			this.titularDefecto.clear();
			this.titularDefecto.add(titularcuenta);
							
		}
		if (isPersonaJuridica()) {
			Personanatural representanteLegal = personaJuridicaMB.getoPersonajuridica().getPersonanatural();
			Titularcuenta titularRepresentante = new Titularcuenta();
			titularRepresentante.setPersonanatural(representanteLegal);

			this.titularDefecto.clear();
			this.titularDefecto.add(titularRepresentante);
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

	public List<Titularcuenta> getTitularDefecto() {
		return titularDefecto;
	}

	public void setTitularDefecto(List<Titularcuenta> titularDefecto) {
		this.titularDefecto = titularDefecto;
	}
	
}
