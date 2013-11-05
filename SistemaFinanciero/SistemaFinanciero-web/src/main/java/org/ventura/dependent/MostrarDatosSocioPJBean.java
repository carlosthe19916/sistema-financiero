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

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.socio.Socio;

@ManagedBean
@ViewScoped
public class MostrarDatosSocioPJBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonajuridicaServiceLocal personaJuridicaServiceLocal;

	@EJB
	SocioServiceLocal socioServiceLocal;

	@EJB
	CuentaahorroServiceLocal cuentaAhorroServiceLocal;
	
	@EJB
	CuentaaporteServiceLocal cuentaAporteServiceLocal;

	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Cuentaahorro> tablaCuentasPN;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Accionista> tablaAccionistasCAP;

	@ManagedProperty(value = "#{PersonaJuridicaBean}")
	private PersonaJuridicaBean personaJuridicaMB;

	private Personajuridica oPersonaJuridica;
	
	private Socio oSocio;

	public MostrarDatosSocioPJBean() {
	}

	@PostConstruct
	public void intiValues() {
		oPersonaJuridica = new Personajuridica();
		oSocio = new Socio();
		tablaCuentasPN = new TablaBean<Cuentaahorro>();
		tablaAccionistasCAP = new TablaBean<Accionista>();
		personaJuridicaMB = new PersonaJuridicaBean();
	}

	public TablaBean<Cuentaahorro> getTablaCuentasPN() {
		return tablaCuentasPN;
	}

	public void setTablaCuentasPN(TablaBean<Cuentaahorro> tablaCuentasPN) {
		this.tablaCuentasPN = tablaCuentasPN;
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
	
	public boolean isPersonaJuridica() {
		return true;
	}

	public void cargarDatosPersonaJuridica() {
		try {
			setoSocio(socioServiceLocal.findByRUC(oSocio.getRuc()));
			setoPersonaJuridica(personaJuridicaServiceLocal.find(oSocio.getRuc()));
			personaJuridicaMB.setoPersonajuridica(oPersonaJuridica);
			CargarCuentasPersonales();
			cargarAccionistasCuentaAporte();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CargarCuentasPersonales() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigoSocio", oSocio.getIdsocio());
		List<Cuentaahorro> list = null;
		try {
			list = cuentaAhorroServiceLocal.findByNamedQuery(Cuentaahorro.CUENTAS, parameters);
			tablaCuentasPN.setRows(list);
			System.out.println("malllll");
			// System.out.println("Cuentas "+list.size());
			System.out.println("biennnnn");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarAccionistasCuentaAporte() {
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
