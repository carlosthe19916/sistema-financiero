package org.ventura.flow;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaAporteBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;

@Named
@FlowScoped("aperturarCuentaaporte-flow")
public class AperturarCuentaaporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentaaporteServiceLocal cuentaaporteServiceLocal;

	@Inject
	private Cuentaaporte cuentaaporte;
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


	public AperturarCuentaaporteBean() {
	}

	public String getReturnValue() {
		return "/index?module=2";
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
			if (validarCuentaAporte()) {
				Cuentaaporte cuentaaporte = establecerParametrosCuentaaporte(this.cuentaaporte);
				if (isPersonaNatural()) {
					cuentaaporte = cuentaaporteServiceLocal.createCuentaAporteWithPersonanatural(cuentaaporte);
				}
				if (isPersonaJuridica()) {
					this.cuentaaporte = this.cuentaaporteServiceLocal.createCuentaAporteWithPersonajuridica(cuentaaporte);
				}				
				this.cuentaaporte = cuentaaporte;
				return "aperturarCuentaaporte-flowA";
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

	public Cuentaaporte establecerParametrosCuentaaporte(Cuentaaporte cuentaaporte) throws Exception {
		Socio socio = new Socio();
		if (isPersonaNatural()) {
			Personanatural personanatural = this.personaNaturalMB.getPersonaNatural();
			socio.setPersonanatural(personanatural);
			cuentaaporte.setSocio(socio);
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentaaporte(cuentaaporte);
			}			
			cuentaaporte.setBeneficiariocuentas(beneficiarios);		
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			cuentaaporte.setSocio(socio);
		}
		return cuentaaporte;
	}

	public boolean validarCuentaAporte() {

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

		if (!datosFinancierosCuentaAporteMB.isValid()) {
			result = false;
			this.mensaje = mensaje + "Datos Financieros invalidos \n";
		}

		return result;
	}
	
	public void establecerTitularDefecto() {
		
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
		this.beneficiariosMB.getTablaBeneficiarios().setRows(new ArrayList<Beneficiariocuenta>());
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
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

	public void setDatosFinancierosCuentaAporteMB(
			DatosFinancierosCuentaAporteBean datosFinancierosCuentaAporteMB) {
		this.datosFinancierosCuentaAporteMB = datosFinancierosCuentaAporteMB;
	}

	public BeneficiariosBean getBeneficiariosMB() {
		return beneficiariosMB;
	}

	public void setBeneficiariosMB(BeneficiariosBean beneficiariosMB) {
		this.beneficiariosMB = beneficiariosMB;
	}

}

