package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.sucursal.Sucursal;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarSucursalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Sucursal> tablaSucursal;

	@EJB private SucursalServiceLocal sucursalServiceLocal;
	
	private boolean failure;

	
	public AdministrarSucursalBean(){
		failure = false;
	}
	
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
	
	public void deleteSucursal(Sucursal sucursal) throws Exception {
		try {
			sucursalServiceLocal.delete(sucursal);
			tablaSucursal.removeRow(sucursal);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e, "Error al eliminar Sucursal");
		}
	}

	public TablaBean<Sucursal> getTablaSucursal() {
		return tablaSucursal;
	}

	public void setTablaSucursal(TablaBean<Sucursal> tablaSucursal) {
		this.tablaSucursal = tablaSucursal;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}
	
}
