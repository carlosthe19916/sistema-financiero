package org.ventura.flow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
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
import org.ventura.entity.Accionista;
import org.ventura.entity.AccionistaPK;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Personajuridica;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;
import org.venturabank.managedbean.session.UsuarioMB;

@Named
@FlowScoped("aperturarCuentaahorro-flow")
public class AperturaCuentaahorroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	@Inject
	private UsuarioMB usuarioMB;

	@EJB
	private CuentaahorroServiceLocal cuentaahorroServiceLocal;

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
	/**
	 * 
	 * CONTRUCT POSTCONTRUC PREDESTROY
	 * 
	 **/

	public AperturaCuentaahorroBean() {
		this.cuentaahorro = new Cuentaahorro();
		this.titularDefecto = new ArrayList<Titularcuenta>();
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
					this.cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonanatural(cuentaahorro);
				}
				if (isPersonaJuridica()) {
					this.cuentaahorro = this.cuentaahorroServiceLocal.createCuentaAhorroWithPersonajuridica(cuentaahorro);
				}				
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

	public void establecerParametrosCuentaahorro() throws Exception {
		Cuentaahorro cuentaahorro = datosFinancierosCuentaAhorroMB.getCuentaahorro();
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
			this.cuentaahorro = cuentaahorro;
			this.cuentaahorro.setPersonanaturalcliente(personanaturalcliente);
			this.cuentaahorro.setTitularcuentas(listTitularcuenta);
			this.cuentaahorro.setBeneficiariocuentas(listBeneficiariocuenta);

			// se relacionan los titulares y beneficiarios a la cuentacorriente
			List<Cuentaahorrohistorial> historiales = cuentaahorro.getCuentaahorrohistorials();
			Cuentaahorrohistorial cuentaahorrohistorial = historiales.get(0);

			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);
			listarBeneficiarioCuenta(cuentaahorro);		
			
			listartitularCuenta(cuentaahorro);
			
			String dniCliente = cuentaahorro.getPersonanaturalcliente().getPersonanatural().getDni();
			cuentaahorro.getPersonanaturalcliente().setDni(dniCliente);
			cuentaahorro.setDni(dniCliente);

		} if (comboTipoPersona.getItemSelected() == 2) {			
			Personajuridica personajuridica = personaJuridicaMB.getoPersonajuridica();

			personajuridica.setListAccionista(personaJuridicaMB.getTablaAccionistas().getRows());
			Personajuridicacliente personajuridicacliente = new Personajuridicacliente();
			
			personajuridicacliente.setPersonajuridica(personajuridica);
			
			
			listaraccionista(personajuridica);
			List<Titularcuenta> listTitularcuenta = titularesMB.getTablaTitulares().getRows();
			this.cuentaahorro = cuentaahorro;
			this.cuentaahorro.setPersonajuridicacliente(personajuridicacliente);
			this.cuentaahorro.setTitularcuentas(listTitularcuenta);
			
			List<Cuentaahorrohistorial> historiales = cuentaahorro.getCuentaahorrohistorials();
			Cuentaahorrohistorial cuentaahorrohistorial = historiales.get(0);
			Integer cantidadRetirantes = titularesMB.getCantidadRetirantes();
			cuentaahorrohistorial.setCantidadretirantes(cantidadRetirantes);
			listartitularCuenta(cuentaahorro);
			
			
			String rucCliente = cuentaahorro.getPersonajuridicacliente().getPersonajuridica().getRuc();
			cuentaahorro.getPersonajuridicacliente().setRuc(rucCliente);
			cuentaahorro.setRuc(rucCliente);
		}
		if (comboTipoPersona.getItemSelected() < 1 || comboTipoPersona.getItemSelected() > 2) {
			throw new Exception("Error al establecer los parametros de la cuenta de ahorros");
		}
	}
	
	private void listarBeneficiarioCuenta(Cuentaahorro cuentaahorro){
		List<Beneficiariocuenta> beneficiariocuentas = cuentaahorro.getBeneficiariocuentas();

		for (Iterator<Beneficiariocuenta> iterator = beneficiariocuentas.iterator(); iterator.hasNext();) {
			Beneficiariocuenta var = (Beneficiariocuenta) iterator.next();
			var.setCuentaahorro(cuentaahorro);
		}
	}
	
	private void listartitularCuenta(Cuentaahorro cuentaahorro){
		List<Titularcuenta> titularcuentas = cuentaahorro.getTitularcuentas();

		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
			Titularcuenta var = (Titularcuenta) iterator.next();
			String dni = var.getPersonanatural().getDni();
			var.setDni(dni);
			var.setCuentaahorro(cuentaahorro);
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

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}


}
