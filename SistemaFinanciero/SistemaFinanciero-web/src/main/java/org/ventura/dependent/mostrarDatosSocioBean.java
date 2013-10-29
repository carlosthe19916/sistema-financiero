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
import org.ventura.boundary.local.FunctionCuentasServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.FunctionCuentas;
import org.ventura.entity.schema.socio.Socio;

@ManagedBean
@ViewScoped
public class mostrarDatosSocioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonanaturalServiceLocal personaNaturalServiceLocal;
	
	@EJB
	SocioServiceLocal socioServiceLocal;
	
	@EJB
	FunctionCuentasServiceLocal functionCuentasServiceLocal;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<FunctionCuentas> tablaCuentasPN;
	
	@ManagedProperty(value = "#{PersonaNaturalBean}")
	private PersonaNaturalBean personaNaturalMB;
	
	private Personanatural oPersonaNatural;
	
	private Socio oSocio;
	
	
	public mostrarDatosSocioBean() {
	}
	
	@PostConstruct
	public void intiValues(){
		oPersonaNatural = new Personanatural();
		oSocio = new Socio();
		tablaCuentasPN = new TablaBean<FunctionCuentas>();
		personaNaturalMB = new PersonaNaturalBean();
	}
	
	public TablaBean<FunctionCuentas> getTablaCuentasPN() {
		return tablaCuentasPN;
	}

	public void setTablaCuentasPN(TablaBean<FunctionCuentas> tablaCuentasPN) {
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
	
	public boolean isPersonaNatural() {
		return true;
	}
	
	public void cargarDatosPersonaNatural(){
		try {
			setoSocio(socioServiceLocal.findByDNI(oSocio.getDni()));
			setoPersonaNatural(personaNaturalServiceLocal.find(oSocio.getDni()));
			personaNaturalMB.setPersonaNatural(oPersonaNatural);
			imprimir();
			CargarCuentasPersonales();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CargarCuentasPersonales(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigoSocio", oSocio.getIdsocio());
		List<FunctionCuentas> list = null;
		try {
			//list = functionCuentasServiceLocal.findByNamedQuery(FunctionCuentas.CUENTAS, parameters);
			//tablaCuentasPN.setRows(list);
			//System.out.println("Cuentas "+list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void imprimir(){
		System.out.println("--------Socio----------");
		System.out.println(oSocio.getIdsocio());
		System.out.println(oSocio.getDni());
		System.out.println(oSocio.getPersonanatural().getApellidopaterno());
		System.out.println("--------Persona Natural--------");
		System.out.println(oPersonaNatural.getDni());
		System.out.println(oPersonaNatural.getApellidopaterno());
		System.out.println("--------Managed Bean-----------");
		System.out.println(personaNaturalMB.getPersonaNatural().getDni());
		System.out.println(personaNaturalMB.getPersonaNatural().getApellidopaterno());
	}
}
