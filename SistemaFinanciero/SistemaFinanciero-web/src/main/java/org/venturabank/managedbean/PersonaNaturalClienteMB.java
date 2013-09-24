package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
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
	PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Personanaturalcliente> tablaClientes;
	
	private String valorBusqueda;
	
	
	public PersonaNaturalClienteMB() {
		setTablaClientes(new TablaMB<Personanaturalcliente>());
		valorBusqueda = new String("");
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
	
	public void cargar(){
		List<Personanaturalcliente> list = (List<Personanaturalcliente>) personaNaturalClienteServicesLocal.findByNamedQuery(Personanaturalcliente.ALL,null);
				//personaNaturalClienteServicesLocal.findByNamedQuery(Personanaturalcliente.ALL);
				//personaNaturalClienteServicesLocal.findByNamedQuery("SELECT p FROM persona.personanatural p");
		//List<Personanaturalcliente> list = (List<Personanaturalcliente>) personaNaturalClienteServicesLocal.findByNamedQuery(Personanaturalcliente.ALL);
		
		tablaClientes.setRows(list);
		System.out.println(valorBusqueda);
		System.out.println("Pruebaaaa");
	}
}
