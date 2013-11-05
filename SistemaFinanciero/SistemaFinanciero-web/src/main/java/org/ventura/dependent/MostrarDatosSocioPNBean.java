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
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
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
	private TablaBean<Cuentaahorro> tablaCuentasPN;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<Beneficiariocuenta> tablaBeneficiarioCAP;

	@ManagedProperty(value = "#{PersonaNaturalBean}")
	private PersonaNaturalBean personaNaturalMB;

	private Personanatural oPersonaNatural;

	private Socio oSocio;

	public MostrarDatosSocioPNBean() {
	}

	@PostConstruct
	public void intiValues() {
		oPersonaNatural = new Personanatural();
		oSocio = new Socio();
		tablaCuentasPN = new TablaBean<Cuentaahorro>();
		tablaBeneficiarioCAP = new TablaBean<Beneficiariocuenta>();
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
	
	public TablaBean<Beneficiariocuenta> getTablaBeneficiarioCAP() {
		return tablaBeneficiarioCAP;
	}

	public void setTablaBeneficiarioCAP(TablaBean<Beneficiariocuenta> tablaBeneficiarioCAP) {
		this.tablaBeneficiarioCAP = tablaBeneficiarioCAP;
	}

	public boolean isPersonaNatural() {
		return true;
	}

	public void cargarDatosPersonaNatural() {
		try {
			setoSocio(socioServiceLocal.findByDNI(oSocio.getDni()));
			setoPersonaNatural(personaNaturalServiceLocal.find(oSocio.getDni()));
			personaNaturalMB.setPersonaNatural(oPersonaNatural);
			CargarCuentasPersonales();
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
			System.out.println("Cuentas "+list.size());
			System.out.println("biennnnn");
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
}
