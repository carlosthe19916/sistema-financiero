package org.ventura.dependent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.ViewCuentas;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.flow.SocioBean;

@ManagedBean
@ViewScoped
public class MostrarDatosSocioPNBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalServiceLocal personaNaturalServiceLocal;

	@EJB
	SocioServiceLocal socioServiceLocal;

	@EJB
	CuentaahorroServiceLocal cuentaAhorroServiceLocal;
	
	@EJB
	CuentaaporteServiceLocal cuentaAporteServiceLocal;

	@Inject
	private TablaBean<ViewCuentas> tablaCuentasPN;
	
	@Inject
	private TablaBean<Beneficiariocuenta> tablaBeneficiarioCAP;

	@Inject
	private PersonaNaturalBean personaNaturalMB;

	private Personanatural oPersonaNatural;

	private Socio oSocio;
	
	@Inject
	private ComboBean<Sexo> comboSexo;

	@Inject
	private ComboBean<Estadocivil> comboEstadoCivil;

	public MostrarDatosSocioPNBean() {
	}

	@PostConstruct
	public void intiValues() {
		oPersonaNatural = new Personanatural();
		oSocio = new Socio();
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadoCivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
	}

	public TablaBean<ViewCuentas> getTablaCuentasPN() {
		return tablaCuentasPN;
	}

	public void setTablaCuentasPN(TablaBean<ViewCuentas> tablaCuentasPN) {
		this.tablaCuentasPN = tablaCuentasPN;
	}

	public PersonaNaturalBean getPersonaNaturalMB() {
		return personaNaturalMB;
	}

	public void setPersonaNaturalMB(PersonaNaturalBean personaNaturalMB) {
		this.personaNaturalMB = personaNaturalMB;
	}

	public Personanatural getoPersonaNatural() {
		return oPersonaNatural;
	}

	public void setoPersonaNatural(Personanatural oPersonaNatural) {
		this.oPersonaNatural = oPersonaNatural;
	}

	public Socio getoSocio() {
		return oSocio;
	}

	public void setoSocio(Socio oSocio) {
		this.oSocio = oSocio;
	}
	
	public TablaBean<Beneficiariocuenta> getTablaBeneficiarioCAP() {
		return tablaBeneficiarioCAP;
	}

	public void setTablaBeneficiarioCAP(TablaBean<Beneficiariocuenta> tablaBeneficiarioCAP) {
		this.tablaBeneficiarioCAP = tablaBeneficiarioCAP;
	}
	
	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboBean<Estadocivil> getComboEstadoCivil() {
		return comboEstadoCivil;
	}

	public void setComboEstadoCivil(ComboBean<Estadocivil> comboEstadoCivil) {
		this.comboEstadoCivil = comboEstadoCivil;
	}

	public boolean isPersonaNatural() {
		return true;
	}
	
	int n=1;
	public void cargarDatosPersonaNatural() {
		if(oSocio.getDni() != null){
			if(n==1){
				n++;
				try {
					setoSocio(socioServiceLocal.findByDNI(oSocio.getDni()));
					setoPersonaNatural(personaNaturalServiceLocal.find(oSocio.getDni()));
					personaNaturalMB.setPersonaNatural(oPersonaNatural);
					cargarComboSexo();
					cargarComboEstadoCivil();
					personaNaturalMB.changeEditingState();
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

	private void cargarComboEstadoCivil() {
		personaNaturalMB.setComboEstadoCivil(comboEstadoCivil);
		personaNaturalMB.getComboEstadoCivil().setItemSelected(oPersonaNatural.getIdestadocivil());
	}

	private void cargarComboSexo() {
		personaNaturalMB.setComboSexo(comboSexo);
		personaNaturalMB.getComboSexo().setItemSelected(oPersonaNatural.getIdsexo());
	}

	public void CargarCuentasPersonales() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigoSocio", oSocio.getIdsocio());
		List<ViewCuentas> list = null;
		try {
			list = cuentaAhorroServiceLocal.findByNamedQueryCuentas(ViewCuentas.CUENTAS, parameters);
			tablaCuentasPN.setRows(list);
			cargarBeneficiariosCuentaAporte();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarBeneficiariosCuentaAporte() {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idCuentaAporte", oSocio.getCuentaaporte().getIdcuentaaporte());
			List<Beneficiariocuenta> list = null;
		try {
			list = cuentaAporteServiceLocal.findByNamedQueryBeneficiario(Cuentaaporte.BENEFICIARIOS, parameters);
			tablaBeneficiarioCAP.setRows(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addBeneficiario() {
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();
		beneficiariocuenta.setEstado(true);
		this.tablaBeneficiarioCAP.addRow(beneficiariocuenta);
	}
	
	public void removeBeneficiario() {
		this.tablaBeneficiarioCAP.removeSelectedRow();
	}
	
	public void actualizarDatosSocio(){
		try {
 			personaNaturalServiceLocal.update(oPersonaNatural);
 			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",  "Los datos se guardaron correctamente...");  
 		    FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los datos no se guardaron "+ e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message); 
		}
	}
	
	public void updateBeneficiario(){
		try {
			if(validarDatosBeneficio()){
				darBajaBeneficiario();
				oSocio.getCuentaaporte().setBeneficiarios(tablaBeneficiarioCAP.getAllRows());
				cuentaAporteServiceLocal.updateBeneficiario(oSocio);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",  "Los datos se guardaron correctamente...");  
	 		    FacesContext.getCurrentInstance().addMessage(null, message);
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  "Los datos no se guardaron...");  
	 		    FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void darBajaBeneficiario() {
		try {
			cuentaAporteServiceLocal.removeBeneficiario(Cuentaaporte.BAJA_BENEFICIARIO, oSocio.getCuentaaporte().getIdcuentaaporte());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean validarDatosBeneficio() {
		boolean valied = true;
		List<Beneficiariocuenta> beneficiarios = tablaBeneficiarioCAP.getAllRows();
		Double porcentaje_total = new Double(0.0);
		if (beneficiarios != null) {
			for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
				Beneficiariocuenta beneficiariocuenta = (Beneficiariocuenta) iterator.next();
				
				boolean appellidoPaterno = true;
				boolean appellidoMaterno = true;
				boolean nombres= true;
				
				porcentaje_total = (beneficiariocuenta.getPorcentajebeneficio() == null) ? porcentaje_total : porcentaje_total + beneficiariocuenta.getPorcentajebeneficio();
				
				if(beneficiariocuenta.getPorcentajebeneficio() == null){
					beneficiariocuenta.setPorcentajebeneficio(new Double(0));
				}
				
				if(beneficiariocuenta.getApellidopaterno()==null||beneficiariocuenta.getApellidopaterno().isEmpty()||beneficiariocuenta.getApellidopaterno().trim().isEmpty()){
					appellidoPaterno= false;
				}
				if(beneficiariocuenta.getApellidomaterno()==null||beneficiariocuenta.getApellidomaterno().isEmpty()||beneficiariocuenta.getApellidomaterno().trim().isEmpty()){
					appellidoMaterno= false;
				}
				if(beneficiariocuenta.getNombres()==null||beneficiariocuenta.getNombres().isEmpty()||beneficiariocuenta.getNombres().trim().isEmpty()){
					nombres= false;
				}
						
				if(!(appellidoPaterno&&appellidoMaterno&&nombres)){
					iterator.remove();
					valied = false;
				}
			}
			if (porcentaje_total != 100.0) {
				FacesContext context = FacesContext.getCurrentInstance();			
				context.validationFailed();	
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El porcentaje de beneficio no suma 100%");
				context.addMessage(null, message);
				valied = false;
			}
		}
		return valied;
	}
}
