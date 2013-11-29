package org.ventura.dependent;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.ViewCuentas;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipoempresa;
import org.ventura.entity.schema.socio.Socio;

@ManagedBean
@ViewScoped
public class MostrarDatosSocioPJBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonajuridicaServiceLocal personaJuridicaServiceLocal;
	
	@EJB
	PersonanaturalServiceLocal personanaturalServiceLocal;

	@EJB
	SocioServiceLocal socioServiceLocal;

	@EJB
	CuentaahorroServiceLocal cuentaAhorroServiceLocal;
	
	@EJB
	CuentaaporteServiceLocal cuentaAporteServiceLocal;

	@Inject
	private TablaBean<ViewCuentas> tablaCuentasPJ;

	@Inject
	private TablaBean<Accionista> tablaAccionistasCAP;

	@Inject
	private PersonaJuridicaBean personaJuridicaMB;
	
	@EJB
	private AccionistaDAO accionistaDAO;
	
	private Personajuridica oPersonaJuridica;
	
	private Socio oSocio;
	
	@Inject
	private ComboBean<Tipoempresa> comboTipoempresa;
	@Inject
	private ComboBean<Sexo> comboSexo;
	@Inject
	private ComboBean<Estadocivil> comboEstadocivil;
	@Inject
	private ComboBean<Sexo> comboSexoAccionista;

	public MostrarDatosSocioPJBean() {
	}

	@PostConstruct
	public void intiValues() {
		oPersonaJuridica = new Personajuridica();
		oSocio = new Socio();
		comboTipoempresa.initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadocivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
		comboSexoAccionista.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
	}

	public TablaBean<ViewCuentas> getTablaCuentasPJ() {
		return tablaCuentasPJ;
	}

	public void setTablaCuentasPJ(TablaBean<ViewCuentas> tablaCuentasPJ) {
		this.tablaCuentasPJ = tablaCuentasPJ;
	}

	public TablaBean<Accionista> getTablaAccionistasCAP() {
		return tablaAccionistasCAP;
	}

	public void setTablaAccionistasCAP(TablaBean<Accionista> tablaAccionistasCAP) {
		this.tablaAccionistasCAP = tablaAccionistasCAP;
	}

	public PersonaJuridicaBean getPersonaJuridicaMB() {
		return personaJuridicaMB;
	}

	public void setPersonaJuridicaMB(PersonaJuridicaBean personaJuridicaMB) {
		this.personaJuridicaMB = personaJuridicaMB;
	}

	public Personajuridica getoPersonaJuridica() {
		return oPersonaJuridica;
	}

	public void setoPersonaJuridica(Personajuridica oPersonaJuridica) {
		this.oPersonaJuridica = oPersonaJuridica;
	}	

	public Socio getoSocio() {
		return oSocio;
	}

	public void setoSocio(Socio oSocio) {
		this.oSocio = oSocio;
	}
	
	public ComboBean<Tipoempresa> getComboTipoempresa() {
		return comboTipoempresa;
	}

	public void setComboTipoempresa(ComboBean<Tipoempresa> comboTipoempresa) {
		this.comboTipoempresa = comboTipoempresa;
	}
	
	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboBean<Estadocivil> getComboEstadocivil() {
		return comboEstadocivil;
	}

	public void setComboEstadocivil(ComboBean<Estadocivil> comboEstadocivil) {
		this.comboEstadocivil = comboEstadocivil;
	}
	
	public ComboBean<Sexo> getComboSexoAccionista() {
		return comboSexoAccionista;
	}

	public void setComboSexoAccionista(ComboBean<Sexo> comboSexoAccionista) {
		this.comboSexoAccionista = comboSexoAccionista;
	}
	
	public boolean isPersonaJuridica() {
		return true;
	}
	
	int n = 1;
	//carga los datos de la persona juridica
	public void cargarDatosPersonaJuridica() {
		if(oSocio.getRuc() != null){
			if (n == 1) {
				n++;
				try {
					setoSocio(socioServiceLocal.findByRUC(oSocio.getRuc()));
					setoPersonaJuridica(personaJuridicaServiceLocal.find(oSocio.getRuc()));
					personaJuridicaMB.setoPersonajuridica(oPersonaJuridica);
					cargarTipoEmpresa();
					personaJuridicaMB.changeEditingState();
					cargarDatosReprentanteLegal();
					cargarAccionistas();
					CargarCuentasPersonales();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Socio no seleccionado",  "Debe seleccionar un socio...");  
 		    FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void cargarTipoEmpresa() {
		personaJuridicaMB.setComboTipoempresa(comboTipoempresa);
		personaJuridicaMB.getComboTipoempresa().setItemSelected(oPersonaJuridica.getIdtipoempresa());
	}

	//carga datos de representante legal
	public void cargarDatosReprentanteLegal() {
		personaJuridicaMB.setComboSexo(comboSexo);
		personaJuridicaMB.getComboSexo().setItemSelected(oPersonaJuridica.getPersonanatural().getIdsexo());
		personaJuridicaMB.setComboEstadocivil(comboEstadocivil);
		personaJuridicaMB.getComboEstadocivil().setItemSelected(oPersonaJuridica.getPersonanatural().getIdestadocivil());
	}
	
	//cargar las otras cuentas personales que tiene este socio
	public void CargarCuentasPersonales() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigoSocio", oSocio.getIdsocio());
		List<ViewCuentas> list = null;
		try {
			list = cuentaAhorroServiceLocal.findByNamedQueryCuentas(ViewCuentas.CUENTAS, parameters);
			tablaCuentasPJ.setRows(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//carga los accionistas de esta persona juridica
	public void cargarAccionistas() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ruc", oSocio.getRuc());
		List<Accionista> list = null;
		try {
			list = cuentaAporteServiceLocal.findByNamedQueryAccionista(Cuentaaporte.ACCIONISTAS, parameters);
			tablaAccionistasCAP.setRows(list);
			personaJuridicaMB.setTablaAccionistas(tablaAccionistasCAP);
			cargarSexoAccionistas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//carga el sexo para cada uno de los accionistas
	public void cargarSexoAccionistas() {
		List<Accionista> acionistas = tablaAccionistasCAP.getAllRows();
		if(acionistas != null){
			for (Iterator<Accionista> iterator = acionistas.iterator(); iterator.hasNext();) {
				Accionista accionista = (Accionista) iterator.next();
				personaJuridicaMB.setComboSexoAccionista(comboSexoAccionista);
				personaJuridicaMB.getComboSexoAccionista().setItemSelected(accionista.getPersonanatural().getIdsexo());
			}
		}
	}

	//busca el representante legal y carga sus datos
	public void buscarPersonanatural(){
		Personanatural personanatural;
		try {
			personanatural = personanaturalServiceLocal.find(oPersonaJuridica.getDnirepresentantelegal());
			if (personanatural != null) {
				oPersonaJuridica.setPersonanatural(personanatural);
				comboSexo.setItemSelected(personanatural.getSexo());
				comboEstadocivil.setItemSelected(personanatural.getEstadocivil());
			} else {
				personanatural = new Personanatural();
				personanatural.setDni(oPersonaJuridica.getDnirepresentantelegal());

				this.oPersonaJuridica.setPersonanatural(personanatural);
				this.comboSexo.setItemSelected(-1);
				this.comboEstadocivil.setItemSelected(-1);
			}
		} catch (Exception e) {

		}
	}
	
	//Agregar accionista a la tabla accionistas
	public void addAccionista() {
		Accionista accionista = new Accionista();
		Personanatural oPersonaNatural = new Personanatural();	
		accionista.setPersonanatural(oPersonaNatural);
		accionista.setEstado(true);
		this.comboSexoAccionista.setItemSelected(-1);
		tablaAccionistasCAP.addRow(accionista);		
	}
	
	//elimina accionistas de la tabla
	public void removeAccionista() {
		this.tablaAccionistasCAP.removeSelectedRow();
	}
	
	//busca accionistas y carga sus datos en la tabla
	public void buscarAccionista(){	
		try {
			Accionista accionista = tablaAccionistasCAP.getEditingRow();
			Personanatural personanatural = accionista.getPersonanatural();
			personanatural = personanaturalServiceLocal.find(personanatural.getDni());
			
			if (personanatural != null) {
				accionista.setPersonanatural(personanatural);
				comboSexoAccionista.setItemSelected(personanatural.getIdsexo());
				tablaAccionistasCAP.setEditingRow(accionista);
			} else {
				personanatural = new Personanatural();			
				personanatural.setDni(tablaAccionistasCAP.getSelectedRow().getPersonanatural().getDni());
				accionista.setPersonanatural(personanatural);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void changeSexoAccionista(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Sexo sexoSelected = comboSexoAccionista.getObjectItemSelected(key);
		this.tablaAccionistasCAP.getEditingRow().getPersonanatural().setSexo(sexoSelected);		
	}
	
	//actualiza datos del socio
	public void updateSocioPersonaJuridica(){
		if (updatePersonaJuridica() && updateAccionistas()) { 
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",  "Los datos se guardaron correctamente...");  
 		    FacesContext.getCurrentInstance().addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  "Los datos no se guardaron...");  
 		    FacesContext.getCurrentInstance().addMessage(null, message); 
		}	 
	}
	
	//calcula la edad de una persona y da una advertencia
	public boolean menorEdad(Date date) {
		boolean menorEdad = false;
		Date fechaactual = Calendar.getInstance().getTime();
		Date fechanacimiento;
		int anio = fechaactual.getYear() - date.getYear();
		int mes = fechaactual.getMonth() - date.getMonth();
		int dia = fechaactual.getDay() - date.getDay();
		if (mes < 0 || (mes == 0 && dia < 0)) {
			anio--;
		}
		if (anio < 18) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia",  "Esta persona es menor de edad...");  
 		    FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Menor de Edad " + anio);
			menorEdad = true;
		} else {
			System.out.println("Mayor de Edad " + anio);
		}
		return menorEdad;
	}

	//actuaiza los datos de la persona juridica y representante
	public boolean updatePersonaJuridica() {
		boolean updatePJ = true;
		try {
			personaJuridicaServiceLocal.update(oPersonaJuridica);
		} catch (Exception e) {
			updatePJ = false;
			e.printStackTrace();
		}
		return updatePJ;
	}

	//actualiza los accionistas
	public boolean updateAccionistas() {
		boolean updateAcc = true;
		try {
			if (validarDatosAccionistas()) {
				if (deleteAccionista()) {
					oPersonaJuridica.setListAccionista(tablaAccionistasCAP.getAllRows());
					personaJuridicaServiceLocal.updateAccionista(oPersonaJuridica);
				}else{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  "Error al acctualizar los accionistas...");  
		 		    FacesContext.getCurrentInstance().addMessage(null, message);
		 		    updateAcc = false;
				}
			} else {
	 		    updateAcc = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			updateAcc = false;
			e.printStackTrace();
		}
		return updateAcc;
	}
	
	// eliminar todos los accionistas de una persona juridica
	public boolean deleteAccionista() {
		boolean deleteAcc = true;
		try {
			personaJuridicaServiceLocal.deleteAccionista(
					Personajuridica.Delete_Accionista,
					oPersonaJuridica.getRuc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deleteAcc = false;
			e.printStackTrace();
		}
		return deleteAcc;
	}

	public boolean validarDatosAccionistas(){
		boolean validAcc = true;
		
		List<Accionista> acionistas = tablaAccionistasCAP.getAllRows();
		Double porcentajeTotal = new Double(0.0);
		if(acionistas != null){
			for (Iterator<Accionista> iterator = acionistas.iterator(); iterator.hasNext();) {
				Accionista accionista = (Accionista) iterator.next();
				
				boolean dni = true;
				boolean apPaterno = true;
				boolean apMaterno = true;
				boolean nombres = true;
				boolean fecNacimiento = true;
				boolean sexo = true;
				
				porcentajeTotal = (accionista.getPorcentajeparticipacion() == null) ? porcentajeTotal : porcentajeTotal + accionista.getPorcentajeparticipacion();
				
				if(accionista.getPersonanatural().getDni()==null || accionista.getPersonanatural().getDni().isEmpty() || accionista.getPersonanatural().getDni().trim().isEmpty()){
					dni = false;
				}
				if(accionista.getPersonanatural().getApellidopaterno()==null||accionista.getPersonanatural().getApellidopaterno().isEmpty()||accionista.getPersonanatural().getApellidopaterno().trim().isEmpty()){
					apPaterno = false;
				}
				if(accionista.getPersonanatural().getApellidomaterno()==null||accionista.getPersonanatural().getApellidomaterno().isEmpty()||accionista.getPersonanatural().getApellidomaterno().trim().isEmpty()){
					apMaterno = false;
				}
				if(accionista.getPersonanatural().getNombres()==null||accionista.getPersonanatural().getNombres().isEmpty()||accionista.getPersonanatural().getNombres().trim().isEmpty()){
					nombres = false;
				}
				if(accionista.getPersonanatural().getFechanacimiento()==null||accionista.getPersonanatural().getFechanacimiento().getDate() == 0){
					fecNacimiento = false;
					if (!menorEdad(accionista.getPersonanatural().getFechanacimiento())) {
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia",  "El Accionista es menor de Edad...");  
			 		    FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}
				if(accionista.getPersonanatural().getSexo() == null||accionista.getPersonanatural().getSexo().getIdsexo()<=0){
					sexo = false;
				}
				if(!(dni&apPaterno&apMaterno&nombres&fecNacimiento&sexo)){
					iterator.remove();
					validAcc = false;
				}
			}
			if (porcentajeTotal != 100.0) {
				FacesContext context = FacesContext.getCurrentInstance();			
				context.validationFailed();	
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El porcentaje de participación no suma 100%");
				context.addMessage(null, message);
				validAcc = false;
			}
		}
		return validAcc;
	}
}
