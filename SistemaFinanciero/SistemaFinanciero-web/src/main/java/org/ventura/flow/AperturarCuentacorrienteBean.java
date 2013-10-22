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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaCorrienteBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.Accionista;
import org.ventura.entity.AccionistaPK;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Cuentacorrientehistorial;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;

@Named
@FlowScoped("aperturarCuentacorriente-flow")
public class AperturarCuentacorrienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;

	@EJB
	private CuentacorrienteServiceLocal cuentacorrienteServiceLocal;

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

	/**
	 * 
	 * CONTRUCT POSTCONTRUC PREDESTROY
	 * 
	 **/

	public AperturarCuentacorrienteBean() {
		this.cuentacorriente = new Cuentacorriente();
		this.titularDefecto = new ArrayList<Titularcuenta>();
	}

	@PostConstruct
	private void initValues() {
		this.cargarCombos();
	}

	/**
	 * 
	 * BUSSINES LOGIC
	 * 
	 * **/

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public String createCuentacorriente() {
		try {
			if (validarCuentaAhorro()) {
				this.establecerParametrosCuentaahorro();
				if (isPersonaNatural()) {
					this.cuentacorrienteServiceLocal.createCuentaCorrienteWithPersonanatural(cuentacorriente);
				}
				if (isPersonaJuridica()) {
					this.cuentacorrienteServiceLocal.createCuentaCorrienteWithPersonajuridica(cuentacorriente);
				}				
				return "cuentaAhorroImprimirDatos";
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

	public void establecerParametrosCuentaahorro() throws Exception {
		Cuentacorriente cuentacorriente  = datosFinancierosCuentaCorrienteMB.getCuentacorriente();
		if (comboTipoPersona.getItemSelected() == 1) {
			// se recuperan los datos de los Managed Bean invocados
			
			Personanatural personanatural = personaNaturalMB.getPersonaNatural();

			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			listTitularcuenta.add(titularDefecto.get(0));
			List<Beneficiariocuenta> listBeneficiariocuenta = beneficiariosMB.getTablaBeneficiarios().getRows();

			// se crean las clases a relacionar con la Cuenta de Ahorros
			Personanaturalcliente personanaturalcliente = new Personanaturalcliente();
			personanaturalcliente.setPersonanatural(personanatural);

			// Se relaciona la Cuenta de Ahorros con los objetos recuperados
			this.cuentacorriente = cuentacorriente;
			this.cuentacorriente.setPersonanaturalcliente(personanaturalcliente);
			this.cuentacorriente.setTitularcuentas(listTitularcuenta);
			this.cuentacorriente.setBeneficiariocuentas(listBeneficiariocuenta);

			// se relacionan los titulares y beneficiarios a la cuentacorriente
			List<Cuentacorrientehistorial> historiales = cuentacorriente.getCuentacorrientehistorials();
			Cuentacorrientehistorial cuentacorrientehistorial = historiales.get(0);

			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentacorrientehistorial.setCantidadretirantes(cantidadRetirantes);
			listarBeneficiarioCuenta(cuentacorriente);		
			
			listartitularCuenta(cuentacorriente);
			
			String dniCliente = cuentacorriente.getPersonanaturalcliente().getPersonanatural().getDni();
			cuentacorriente.getPersonanaturalcliente().setDni(dniCliente);
			cuentacorriente.setDni(dniCliente);

		} if (comboTipoPersona.getItemSelected() == 2) {			
			Personajuridica personajuridica = personaJuridicaMB.getoPersonajuridica();

			personajuridica.setListAccionista(personaJuridicaMB.getTablaAccionistas().getRows());
			Personajuridicacliente personajuridicacliente = new Personajuridicacliente();
			
			personajuridicacliente.setPersonajuridica(personajuridica);
			
			
			listaraccionista(personajuridica);
			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			this.cuentacorriente = cuentacorriente;
			this.cuentacorriente.setPersonajuridicacliente(personajuridicacliente);
			this.cuentacorriente.setTitularcuentas(listTitularcuenta);
			
			List<Cuentacorrientehistorial> historiales = cuentacorriente.getCuentacorrientehistorials();
			Cuentacorrientehistorial cuentacorrientehistorial = historiales.get(0);
			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentacorrientehistorial.setCantidadretirantes(cantidadRetirantes);
			listartitularCuenta(cuentacorriente);
			
			
			String rucCliente = cuentacorriente.getPersonajuridicacliente().getPersonajuridica().getRuc();
			cuentacorriente.getPersonajuridicacliente().setRuc(rucCliente);
			cuentacorriente.setRuc(rucCliente);
		}
		if (comboTipoPersona.getItemSelected() < 1 || comboTipoPersona.getItemSelected() > 2) {
			throw new Exception("Error al establecer los parametros de la cuenta de ahorros");
		}
	}
	
	private void listarBeneficiarioCuenta(Cuentacorriente cuentacorriente){
		List<Beneficiariocuenta> beneficiariocuentas = cuentacorriente.getBeneficiariocuentas();

		for (Iterator<Beneficiariocuenta> iterator = beneficiariocuentas.iterator(); iterator.hasNext();) {
			Beneficiariocuenta var = (Beneficiariocuenta) iterator.next();
			var.setCuentacorriente(cuentacorriente);
		}
	}
	
	private void listartitularCuenta(Cuentacorriente cuentacorriente){
		List<Titularcuenta> titularcuentas = cuentacorriente.getTitularcuentas();

		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
			Titularcuenta var = (Titularcuenta) iterator.next();
			String dni = var.getPersonanatural().getDni();
			var.setDni(dni);
			var.setCuentacorriente(cuentacorriente);
		}
	}
	
	private void listaraccionista(Personajuridica personajuridica){
		List<Accionista> accionistas = personaJuridicaMB.getTablaAccionistas().getRows();

		for (Iterator<Accionista> iterator = accionistas.iterator(); iterator.hasNext();) {
			Accionista var = (Accionista) iterator.next();
			AccionistaPK accionistaPK = new AccionistaPK();
			accionistaPK.setDni(var.getPersonanatural().getDni());
			accionistaPK.setRuc(personajuridica.getRuc());
			
			var.setId(accionistaPK);
			var.setEstado(true);
		}
	}

	public boolean validarCuentaAhorro() {

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
		/*Personanatural personanatural = personaNaturalMB.getPersonaNatural();

		Titularcuenta titularcuenta = new Titularcuenta();
		titularcuenta.setPersonanatural(personanatural);

		List<Titularcuenta> titularcuentas = titularesMB.getTablaTitulares()
				.getRows();

		if (titularcuentas.size() == 0) {
			titularcuentas.add(titularcuenta);
		} else {
			titularcuentas.set(0, titularcuenta);
		}*/
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