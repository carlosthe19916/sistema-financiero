package org.ventura.dependent;

import java.io.Serializable;
import java.util.HashMap;
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

	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<ViewCuentas> tablaCuentasPN;
	
	@Inject
	private TablaBean<Beneficiariocuenta> tablaBeneficiarioCAP;

	@ManagedProperty(value = "#{PersonaNaturalBean}")
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
		tablaCuentasPN =  new TablaBean<ViewCuentas>();
		personaNaturalMB = new PersonaNaturalBean();
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

	public void cargarDatosPersonaNatural() {
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
		System.out.println("Hola");
		System.out.println(tablaBeneficiarioCAP.getRows().size());
		Beneficiariocuenta beneficiariocuenta = new Beneficiariocuenta();
		beneficiariocuenta.setEstado(true);
		System.out.println(tablaBeneficiarioCAP.getRows().size());
	}
	
	public void removeBeneficiario() {
		System.out.println(tablaBeneficiarioCAP.getRows().size());
		this.tablaBeneficiarioCAP.removeSelectedRow();
		System.out.println(tablaBeneficiarioCAP.getRows().size());
	}
	
	public void actualizarDatosSocio(){
		try {
 			personaNaturalServiceLocal.update(oPersonaNatural);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message); 
		}
	}  
}
