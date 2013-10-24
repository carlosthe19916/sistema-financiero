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
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Personanatural;

@ManagedBean
@ViewScoped
public class mostrarDatosSocioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonanaturalServiceLocal personaNaturalServiceLocal;
	
	@EJB
	CuentaahorroServiceLocal cuentaAhorroServiceLocal;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Cuentaahorro> tablaCuentasPN;
	
	@ManagedProperty(value = "#{PersonaNaturalBean}")
	private PersonaNaturalBean personaNaturalMB;
	
	private Personanatural oPersonaNatural;
	
	
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
	
	public boolean isPersonaNatural() {
		return true;
	}
	
	public void cargarDatosPersonaNatural(){
		try {
			setoPersonaNatural(personaNaturalServiceLocal.find(oPersonaNatural.getDni()));
			personaNaturalMB.setPersonaNatural(oPersonaNatural);
			CargarCuentasPersonales();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CargarCuentasPersonales(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dni", oPersonaNatural.getDni());
		List list;
		try {
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
