package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.entity.Personanaturalcliente;
import org.venturabank.util.TablaMB;


@ManagedBean
@ViewScoped
public class PersonaNaturalClienteMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Personanaturalcliente> tablaClientes;
	
	private String valorBusqueda;
	private String dniTemporal;

	public PersonaNaturalClienteMB() {
		setTablaClientes(new TablaMB<Personanaturalcliente>());
		valorBusqueda = new String();
	}
	
	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}
	
	public TablaMB<Personanaturalcliente> getTablaClientes() {
		return tablaClientes;
	}

	public void setTablaClientes(TablaMB<Personanaturalcliente> tablaClientes) {
		this.tablaClientes = tablaClientes;
	}

	public String getDniTemporal() {
		return dniTemporal;
	}

	public void setDniTemporal(String dniTemporal) {
		this.dniTemporal = dniTemporal;
	}
	
	public void buscarCliente(){
		
		
		try {
			Map<String, Object> pa = new HashMap<String, Object>();
			pa.put("valor","%"+valorBusqueda.toUpperCase()+"%");
			List<Personanaturalcliente> list;
			
			list = personaNaturalClienteServicesLocal.findByNamedQuery(Personanaturalcliente.OLL,pa);
			
			tablaClientes.setRows(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void obtenerDNISeleccionado(){
		Personanaturalcliente personaNaturalCliente = tablaClientes.getSelectedRow() ;
		dniTemporal = personaNaturalCliente.getDni();
	}
}
