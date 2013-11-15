package org.ventura.dependent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
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

	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<ViewCuentas> tablaCuentasPJ;

	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Accionista> tablaAccionistasCAP;

	@ManagedProperty(value = "#{PersonaJuridicaBean}")
	private PersonaJuridicaBean personaJuridicaMB;

	private Personajuridica oPersonaJuridica;
	
	private Socio oSocio;
	
	@Inject
	private ComboBean<Tipoempresa> comboTipoempresa;
	@Inject
	private ComboBean<Sexo> comboSexo;
	@Inject
	private ComboBean<Estadocivil> comboEstadocivil;

	public MostrarDatosSocioPJBean() {
	}

	@PostConstruct
	public void intiValues() {
		oPersonaJuridica = new Personajuridica();
		oSocio = new Socio();
		tablaCuentasPJ = new TablaBean<ViewCuentas>();
		tablaAccionistasCAP = new TablaBean<Accionista>();
		personaJuridicaMB = new PersonaJuridicaBean();
		comboTipoempresa.initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboEstadocivil.initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);
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
	
	public boolean isPersonaJuridica() {
		return true;
	}

	public void cargarDatosPersonaJuridica() {
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

	public void cargarTipoEmpresa() {
		personaJuridicaMB.setComboTipoempresa(comboTipoempresa);
		personaJuridicaMB.getComboTipoempresa().setItemSelected(oPersonaJuridica.getIdtipoempresa());
	}

	public void cargarDatosReprentanteLegal() {
		personaJuridicaMB.setComboSexo(comboSexo);
		personaJuridicaMB.getComboSexo().setItemSelected(oPersonaJuridica.getPersonanatural().getIdsexo());
		personaJuridicaMB.setComboEstadocivil(comboEstadocivil);
		personaJuridicaMB.getComboEstadocivil().setItemSelected(oPersonaJuridica.getPersonanatural().getIdestadocivil());
		
		personaJuridicaMB.changeEditingPersonanaturalState();
	}

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
	
	public void cargarAccionistas() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ruc", oSocio.getRuc());
		List<Accionista> list = null;
		try {
			list = cuentaAporteServiceLocal.findByNamedQueryAccionista(Cuentaaporte.ACCIONISTAS, parameters);
			tablaAccionistasCAP.setRows(list);
			personaJuridicaMB.setTablaAccionistas(tablaAccionistasCAP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
