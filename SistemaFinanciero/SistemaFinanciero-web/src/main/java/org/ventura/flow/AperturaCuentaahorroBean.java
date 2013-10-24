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

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaAhorroBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.Agencia;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Tipomoneda;
import org.ventura.entity.Titularcuenta;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@FlowScoped("aperturarCuentaahorro-flow")
public class AperturaCuentaahorroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	@Inject
	private AgenciaBean agenciaBean;

	@EJB
	private CuentaahorroServiceLocal cuentaahorroServiceLocal;

	@Inject
	private Cuentaahorro cuentaahorro;
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

	private List<Titularcuenta> titularDefecto;

	public AperturaCuentaahorroBean() {
		this.titularDefecto = new ArrayList<Titularcuenta>();
	}

	
	@PostConstruct
	private void initValues() {
		Agencia agencia = agenciaBean.getAgencia();
		cuentaahorroServiceLocal.setAgencia(agencia);
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
					this.cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonanatural(cuentaahorro);
				}
				if (isPersonaJuridica()) {
					this.cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonajuridica(cuentaahorro);
				}			
				this.cuentaahorro = cuentaahorro;
				return "aperturarCuentaahorro-flowA";
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

	public Cuentaahorro establecerParametrosCuentaahorro(Cuentaahorro cuentaahorro) throws Exception {
		cuentaahorro = datosFinancierosCuentaAhorroMB.getCuentaahorro();
		cuentaahorro.getCuentaahorrohistorials().get(0).setCantidadretirantes(getCantidadRetirantes());
		
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
			
			List<Beneficiariocuenta> beneficiarios = beneficiariosMB.getListBeneficiarios();
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = iterator.next();
				beneficiariocuenta.setCuentaahorro(cuentaahorro);
			}		
			
			cuentaahorro.setBeneficiariocuentas(beneficiarios);		
		} if (isPersonaJuridica()) {			
			Personajuridica personajuridica = this.personaJuridicaMB.getPersonajuridicaProsesed();
			socio.setPersonajuridica(personajuridica);
			cuentaahorro.setSocio(socio);
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

	public List<Titularcuenta> getTitularDefecto() {
		return titularDefecto;
	}

	public void setTitularDefecto(List<Titularcuenta> titularDefecto) {
		this.titularDefecto = titularDefecto;
	}

}
