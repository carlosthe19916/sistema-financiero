package org.ventura.flow;

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

import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.ViewSocioPJServiceLocal;
import org.ventura.boundary.local.ViewSocioPNServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;

@ManagedBean
@ViewScoped
public class SocioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private SocioServiceLocal socioServicesLocal;
	
	@EJB
	private ViewSocioPNServiceLocal viewSocioPNServiceLocal;
	
	@EJB
	private ViewSocioPJServiceLocal viewSocioPJServiceLocal;

	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<ViewSocioPN> tablaSociosPN;
	
	@ManagedProperty(value = "#{TablaBean}")
	private TablaBean<ViewSocioPJ> tablaSociosPJ;

	@Inject
	private ComboBean<String> comboTipoPersona;

	private String valorBusqueda;
	private String dniTemporal;

	public SocioBean() {
		valorBusqueda = new String();
	}

	@PostConstruct
	public void initValues() {
		this.cargarCombos();
		tablaSociosPN = new TablaBean<ViewSocioPN>();
		tablaSociosPJ = new TablaBean<ViewSocioPJ>();
	}

	public TablaBean<ViewSocioPN> getTablaSociosPN() {
		return tablaSociosPN;
	}

	public void setTablaSociosPN(TablaBean<ViewSocioPN> tablaSociosPN) {
		this.tablaSociosPN = tablaSociosPN;
	}
	
	public TablaBean<ViewSocioPJ> getTablaSociosPJ() {
		return tablaSociosPJ;
	}

	public void setTablaSociosPJ(TablaBean<ViewSocioPJ> tablaSociosPJ) {
		this.tablaSociosPJ = tablaSociosPJ;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public String getDniTemporal() {
		return dniTemporal;
	}

	public void setDniTemporal(String dniTemporal) {
		this.dniTemporal = dniTemporal;
	}

	public ComboBean<String> getComboTipoPersona() {
		return comboTipoPersona;
	}

	public void setComboTipoPersona(ComboBean<String> comboTipoPersona) {
		this.comboTipoPersona = comboTipoPersona;
	}

	public void cargarCombos() {
		comboTipoPersona.getItems().put(1, "Persona Natural");
		comboTipoPersona.getItems().put(2, "Persona Juridica");
		comboTipoPersona.setItemSelected(1);
	}

	public boolean isPersonaNatural() {
		if (comboTipoPersona.getItemSelected() == 1)
			return true;
		else
			return false;
	}

	public boolean isPersonaJuridica() {
		if (comboTipoPersona.getItemSelected() == 2)
			return true;
		else
			return false;
	}

	public void buscarSocio() {
		if (isPersonaNatural()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("datoIngresado", "%" + valorBusqueda.toUpperCase()+ "%");
			List<ViewSocioPN> list;
			try {
				list = viewSocioPNServiceLocal.findByNamedQuery(ViewSocioPN.SOCIOSPN, parameters);
				tablaSociosPN.setRows(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("datoIngresado", "%" + valorBusqueda.toUpperCase()+ "%");
			List<ViewSocioPJ> list;
			try {
				list = viewSocioPJServiceLocal.findByNamedQuery(ViewSocioPJ.SOCIOSPJ, parameters);				
				tablaSociosPJ.setRows(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void obtenerDNISeleccionado() {
		ViewSocioPN viewSocioPN = getTablaSociosPN().getSelectedRow();
		dniTemporal = viewSocioPN.getDni();
	}

	public void limpiarTablas(){
		tablaSociosPN.getRows().clear();
		tablaSociosPJ.getRows().clear();
		valorBusqueda = new String();
	}
}
