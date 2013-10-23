package org.ventura.flow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.entity.Socio;
import org.venturabank.util.TablaMB;


@ManagedBean
@ViewScoped
public class SocioBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private SocioServiceLocal socioServicesLocal;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Socio> tablaSocios;
	
	private String valorBusqueda;
	private String dniTemporal;

	public SocioBean() {
		valorBusqueda = new String();
	}
	
	
	public TablaMB<Socio> getTablaSocios() {
		return tablaSocios;
	}

	public void setTablaSocios(TablaMB<Socio> tablaSocios) {
		this.tablaSocios = tablaSocios;
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
	
	public void buscarSocio(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("datoIngresado","%"+valorBusqueda.toUpperCase()+"%");
		List<Socio> list;
		try {
			list = socioServicesLocal.findByNamedQuery(Socio.SOCIOSPN, parameters);
			tablaSocios.setRows(list);
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void obtenerDNISeleccionado(){
		Socio socio = tablaSocios.getSelectedRow() ;
		dniTemporal = socio.getDni();
	}
}
