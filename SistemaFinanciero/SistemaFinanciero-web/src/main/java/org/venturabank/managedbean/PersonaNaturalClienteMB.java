package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.StaticMetamodel;

import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanaturalcliente;
import org.venturabank.util.TablaMB;


@ManagedBean
@ViewScoped
public class PersonaNaturalClienteMB implements Serializable{
	
	/**
	 * 
	 */
	private final long serialVersionUID = 1L;
	
	@EJB
	static PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;
	
	@ManagedProperty(value = "#{tablaMB}")
	private TablaMB<Personanaturalcliente> tablaClientes;
	
	private String valorBusqueda;
	private Personanaturalcliente selectedPersonaNaturalCliente;


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
	
	public Personanaturalcliente getSelectedPersonaNaturalCliente() {
		return selectedPersonaNaturalCliente;
	}

	public void setSelectedPersonaNaturalCliente(
			Personanaturalcliente selectedPersonaNaturalCliente) {
		this.selectedPersonaNaturalCliente = selectedPersonaNaturalCliente;
	}
	
	public void cargar(){
		Map<String, Object> pa = new HashMap<String, Object>();
		pa.put("valor","%"+valorBusqueda.toUpperCase()+"%");
		List<Personanaturalcliente> list = personaNaturalClienteServicesLocal.findByNamedQuery(Personanaturalcliente.OLL,pa);
		tablaClientes.setRows(list);
	}
}
