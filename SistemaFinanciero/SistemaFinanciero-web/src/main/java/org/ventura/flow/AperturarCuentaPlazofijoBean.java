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

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaAporteBean;
import org.ventura.dependent.DatosFinancierosCuentaPlazoFijoBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentaplazofijo;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.AccionistaPK;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.sucursal.Agencia;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@FlowScoped("aperturarCuentaplazofijo-flow")
public class AperturarCuentaPlazofijoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;

	@Inject
	private Cuentaplazofijo cuentaplazofijo;
	@Inject
	private ComboBean<String> comboTipoPersona;
	@Inject
	private PersonaNaturalBean personaNaturalMB;
	@Inject
	private PersonaJuridicaBean personaJuridicaMB;
	@Inject
	private DatosFinancierosCuentaPlazoFijoBean datosFinancierosCuentaPlazoFijoMB;
	@Inject
	private BeneficiariosBean beneficiariosMB;
	@Inject
	private TitularesBean titularesMB;
	
	
	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}
	
	public String getReturnValue() {
		return "/index?module=2";
	}		

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentaplazofijo() {
		try {
			if (validarCurrentBean()) {
				Cuentaplazofijo cuentaplazofijo = establecerParametrosCuentaplazofijo(this.cuentaplazofijo);
				if (isPersonaNatural()) {
					cuentaplazofijo = this.cuentaplazofijoServiceLocal.createCuentaPlazofijoWithPersonanatural(cuentaplazofijo);
					this.cuentaplazofijo=cuentaplazofijo;
				}
				if (isPersonaJuridica()) {
					this.cuentaplazofijo = this.cuentaplazofijoServiceLocal.createCuentaPlazofijoWithPersonajuridica(cuentaplazofijo);
				}				
				this.cuentaplazofijo = cuentaplazofijo;
				return "aperturarCuentaplazofijo-flowA";
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

	public Cuentaplazofijo establecerParametrosCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) throws Exception {
		cuentaplazofijo = datosFinancierosCuentaPlazoFijoMB.getCuentaplazofijo();
		Socio socio = new Socio();
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			cuentaplazofijo.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();			
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentaplazofijo(cuentaplazofijo);
			}
			cuentaplazofijo.setTitularcuentas(titulares);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentaplazofijo(cuentaplazofijo);
			}
			cuentaplazofijo.setBeneficiariocuentas(beneficiarios);
			
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			socio.setEstado(true);
			cuentaplazofijo.setSocio(socio);
			
			List<Titularcuenta> titulares = titularesMB.getListTitulares();
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titular = iterator.next();
				titular.setCuentaplazofijo(cuentaplazofijo);
			}
			cuentaplazofijo.setTitularcuentas(titulares);
			
			cuentaplazofijo.setBeneficiariocuentas(new ArrayList<Beneficiariocuenta>());
		}
		return cuentaplazofijo;
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

		if (!datosFinancierosCuentaPlazoFijoMB.isValid()) {
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

	public Cuentaplazofijo getCuentaplazofijo() {
		return cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
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

	public DatosFinancierosCuentaPlazoFijoBean getDatosFinancierosCuentaPlazoFijoMB() {
		return datosFinancierosCuentaPlazoFijoMB;
	}

	public void setDatosFinancierosCuentaPlazoFijoMB(
			DatosFinancierosCuentaPlazoFijoBean datosFinancierosCuentaPlazoFijoMB) {
		this.datosFinancierosCuentaPlazoFijoMB = datosFinancierosCuentaPlazoFijoMB;
	}

	public BeneficiariosBean getBeneficiariosMB() {
		return beneficiariosMB;
	}

	public void setBeneficiariosMB(BeneficiariosBean beneficiariosMB) {
		this.beneficiariosMB = beneficiariosMB;
	}



	public TitularesBean getTitularesMB() {
		return titularesMB;
	}

	public void setTitularesMB(TitularesBean titularesMB) {
		this.titularesMB = titularesMB;
	}

}

