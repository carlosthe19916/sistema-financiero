package org.ventura.dependent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
	private PersonaNaturalBean personaNaturalBean;
	
	private Personanatural personanatural;
	
	
	public mostrarDatosSocioBean() {
		setPersonanatural(new Personanatural());
	}
	
	public TablaBean<Cuentaahorro> getTablaCuentasPN() {
		return tablaCuentasPN;
	}

	public void setTablaCuentasPN(TablaBean<Cuentaahorro> tablaCuentasPN) {
		this.tablaCuentasPN = tablaCuentasPN;
	}

	public PersonaNaturalBean getPersonaNaturalBean() {
		return personaNaturalBean;
	}
	
	public void setPersonaNaturalBean(PersonaNaturalBean personaNaturalBean) {
		this.personaNaturalBean = personaNaturalBean;
	}
	
	
	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}
	
	public boolean isPersonaNatural() {
		return true;
	}
	
	public void cargarDatosPersonaNatural(){
		try {
			setPersonanatural(personaNaturalServiceLocal.find(personanatural.getDni()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPersonaNaturalBean().setPersonaNatural(personanatural);
		CargarCuentasPersonales();
	}
	
	public void CargarCuentasPersonales(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dni", personanatural.getDni());
		//List list = cuentaAhorroServiceLocal.findByNamedQuery(Cuentaahorro.CUENTAS, parameters);
		//List<Cuentaahorro> list = cuentaAhorroServiceLocal.findByNamedQuery(Cuentaahorro.CUENTAS, parameters);
		//getTablaCuentasPN().setRows(list);
	}
	
}
