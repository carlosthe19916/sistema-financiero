package org.ventura.flow;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.dependent.BeneficiariosBean;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.DatosFinancierosCuentaPlazoFijoBean;
import org.ventura.dependent.PersonaJuridicaBean;
import org.ventura.dependent.PersonaNaturalBean;
import org.ventura.dependent.TitularesBean;
import org.ventura.entity.Accionista;
import org.ventura.entity.AccionistaPK;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;

@Named
@FlowScoped("aperturarCuentaplazofijo-flow")
public class AperturarCuentaPlazofijoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	@EJB
	private CuentaplazofijoServiceLocal cuentaplazofijoServiceLocal;

	private Cuentaplazofijo cuentaplazofijo;

	@Inject
	private DatosFinancierosCuentaPlazoFijoBean datosFinancierosCuentaPlazoFijoMB;
	
	@Inject
	private ComboBean<String> comboTipoPersona;

	@Inject
	private PersonaNaturalBean personaNaturalMB;

	@Inject
	private PersonaJuridicaBean personaJuridicaMB;

	@Inject
	private TitularesBean titularesMB;

	@Inject
	private BeneficiariosBean beneficiariosMB;

	/**
	 * 
	 * CONTRUCT POSTCONTRUC PREDESTROY
	 * 
	 **/

	public AperturarCuentaPlazofijoBean() {
		this.cuentaplazofijo = new Cuentaplazofijo();
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

	public String createCuentaahorro() {
		try {
			if (validarCuentaAhorro()) {
				this.establecerParametrosCuentaahorro();
				if (isPersonaNatural()) {
					this.cuentaplazofijoServiceLocal.createCuentaPlazofijoWithPersonanatural(cuentaplazofijo);
				}
				if (isPersonaJuridica()) {
					this.cuentaplazofijoServiceLocal.createCuentaPlazofijoWithPersonajuridica(cuentaplazofijo);
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
		Cuentaplazofijo cuentaplazofijo  = datosFinancierosCuentaPlazoFijoMB.getCuentaplazofijo();
		if (comboTipoPersona.getItemSelected() == 1) {
			// se recuperan los datos de los Managed Bean invocados
			
			Personanatural personanatural = personaNaturalMB.getPersonaNatural();

			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			List<Beneficiariocuenta> listBeneficiariocuenta = beneficiariosMB.getTablaBeneficiarios().getRows();

			// se crean las clases a relacionar con la Cuenta de Ahorros
			Personanaturalcliente personanaturalcliente = new Personanaturalcliente();
			personanaturalcliente.setPersonanatural(personanatural);

			// Se relaciona la Cuenta de Ahorros con los objetos recuperados
			this.cuentaplazofijo = cuentaplazofijo;
			this.cuentaplazofijo.setPersonanaturalcliente(personanaturalcliente);
			this.cuentaplazofijo.setTitularcuentas(listTitularcuenta);
			this.cuentaplazofijo.setBeneficiariocuentas(listBeneficiariocuenta);

			listarBeneficiarioCuenta(cuentaplazofijo);		
			
			listartitularCuenta(cuentaplazofijo);
			
			String dniCliente = cuentaplazofijo.getPersonanaturalcliente().getPersonanatural().getDni();
			cuentaplazofijo.getPersonanaturalcliente().setDni(dniCliente);
			cuentaplazofijo.setDni(dniCliente);

		} if (comboTipoPersona.getItemSelected() == 2) {			
			Personajuridica personajuridica = personaJuridicaMB.getoPersonajuridica();

			personajuridica.setListAccionista(personaJuridicaMB.getTablaAccionistas().getRows());
			Personajuridicacliente personajuridicacliente = new Personajuridicacliente();
			
			personajuridicacliente.setPersonajuridica(personajuridica);
						
			listaraccionista(personajuridica);
			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			this.cuentaplazofijo = cuentaplazofijo;
			this.cuentaplazofijo.setPersonajuridicacliente(personajuridicacliente);
			this.cuentaplazofijo.setTitularcuentas(listTitularcuenta);
			
			listartitularCuenta(cuentaplazofijo);
			
			
			String rucCliente = cuentaplazofijo.getPersonajuridicacliente().getPersonajuridica().getRuc();
			cuentaplazofijo.getPersonajuridicacliente().setRuc(rucCliente);
			cuentaplazofijo.setRuc(rucCliente);
		}
		if (comboTipoPersona.getItemSelected() < 1 || comboTipoPersona.getItemSelected() > 2) {
			throw new Exception("Error al establecer los parametros de la cuenta de ahorros");
		}
	}
	
	private void listarBeneficiarioCuenta(Cuentaplazofijo cuentaplazofijo){
		List<Beneficiariocuenta> beneficiariocuentas = cuentaplazofijo.getBeneficiariocuentas();

		for (Iterator<Beneficiariocuenta> iterator = beneficiariocuentas.iterator(); iterator.hasNext();) {
			Beneficiariocuenta var = (Beneficiariocuenta) iterator.next();
			var.setCuentaplazofijo(cuentaplazofijo);
		}
	}
	
	private void listartitularCuenta(Cuentaplazofijo cuentaplazofijo){
		List<Titularcuenta> titularcuentas = cuentaplazofijo.getTitularcuentas();

		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
			Titularcuenta var = (Titularcuenta) iterator.next();
			String dni = var.getPersonanatural().getDni();
			var.setDni(dni);
			var.setCuentaplazofijo(cuentaplazofijo);
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
		Personanatural personanatural = personaNaturalMB.getPersonaNatural();

		Titularcuenta titularcuenta = new Titularcuenta();
		titularcuenta.setPersonanatural(personanatural);

		List<Titularcuenta> titularcuentas = titularesMB.getTablaTitulares()
				.getRows();

		if (titularcuentas.size() == 0) {
			titularcuentas.add(titularcuenta);
		} else {
			titularcuentas.set(0, titularcuenta);
		}
	}

	/**
	 * 
	 * GETTER AND SETTER
	 * 
	 * **/

	public Cuentaplazofijo getCuentacorriente() {
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

	public DatosFinancierosCuentaPlazoFijoBean getDatosFinancierosCuentaPlazoFijoMB() {
		return datosFinancierosCuentaPlazoFijoMB;
	}

	public void setDatosFinancierosCuentaPlazoFijoMB(
			DatosFinancierosCuentaPlazoFijoBean datosFinancierosCuentaPlazoFijoMB) {
		this.datosFinancierosCuentaPlazoFijoMB = datosFinancierosCuentaPlazoFijoMB;
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

	public Cuentaplazofijo getCuentaplazofijo() {
		return cuentaplazofijo;
	}

}
