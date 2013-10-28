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
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;

@ManagedBean
@ViewScoped
public class mostrarDatosSocioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonanaturalServiceLocal personaNaturalServiceLocal;
	
	@EJB
	SocioServiceLocal socioServiceLocal;
	
	@EJB
	CuentaahorroServiceLocal cuentaAhorroServiceLocal;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Cuentaahorro> tablaCuentasPN;
	
	@ManagedProperty(value = "#{PersonaNaturalBean}")
	private PersonaNaturalBean personaNaturalMB;
	
	private Personanatural oPersonaNatural;
	
	private Socio oSocio;
	
	
	public mostrarDatosSocioBean() {
	}
	
	@PostConstruct
	public void intiValues(){
		oPersonaNatural = new Personanatural();
		tablaCuentasPN = new TablaBean<Cuentaahorro>();
		personaNaturalMB = new PersonaNaturalBean();
	}
	
	public TablaBean<Cuentaahorro> getTablaCuentasPN() {
		return tablaCuentasPN;
	}

	public void setTablaCuentasPN(TablaBean<Cuentaahorro> tablaCuentasPN) {
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
			setoSocio(socioServiceLocal.find(oSocio.getDni()));
			setoPersonaNatural(personaNaturalServiceLocal.find(oSocio.getDni()));
			personaNaturalMB.setPersonaNatural(oPersonaNatural);
			CargarCuentasPersonales();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CargarCuentasPersonales(){
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigoSocio", oSocio.getIdsocio());
			List list;
			list = cuentaAhorroServiceLocal.findByNamedQuery(Cuentaahorro.CUENTAS, parameters);
			tablaCuentasPN.setRows(list);
			System.out.println("Cuentas "+list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//List<Cuentaahorro> list = cuentaAhorroServiceLocal.findByNamedQuery(Cuentaahorro.CUENTAS, parameters);
	}
}
