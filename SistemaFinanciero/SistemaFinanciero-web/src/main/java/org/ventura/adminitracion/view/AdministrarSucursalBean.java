package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.sucursal.Sucursal;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarSucursalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Sucursal> tablaSucursal;

	@EJB private SucursalServiceLocal sucursalServiceLocal;

	@PostConstruct
	private void initialize() throws Exception {
		try {
			List<Sucursal> sucursales = sucursalServiceLocal.getAllActive();
			tablaSucursal.setRows(sucursales);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editSucursal(Sucursal sucursal){
		
	}
	
	public void deleteSucursal() throws Exception {
		/*try {
			//loadBoveda();
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
			bovedaServiceLocal.inactive(boveda);
			refreshBean();

			JsfUtil.addSuccessMessage("Boveda Desactivada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JsfUtil.addErrorMessage(e, "Error al Inactivar Boveda");
		}*/
	}

	public TablaBean<Sucursal> getTablaSucursal() {
		return tablaSucursal;
	}

	public void setTablaSucursal(TablaBean<Sucursal> tablaSucursal) {
		this.tablaSucursal = tablaSucursal;
	}
	
	
}
