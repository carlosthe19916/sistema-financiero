package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarTrabajadorAdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Trabajador> tablaTrabajador;

	private String searched;
	
	@Inject Agencia agencia;
	
	@EJB private TrabajadorServiceLocal trabajadorServiceLocal;

	public AdministrarTrabajadorAdminBean() {
		
	}

	@PostConstruct
	private void initialize() {
		
	}

	public void buscarTrabajador(){
		try {
			List<Trabajador> resultList = trabajadorServiceLocal.find(null, null, searched);
			tablaTrabajador.clean();
			tablaTrabajador.setRows(resultList);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void deleteTrabajador(Trabajador trabajador) throws Exception {
		try {
			trabajadorServiceLocal.delete(trabajador);
			tablaTrabajador.removeRow(trabajador);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Inactivar Boveda");
		}
	}

	public TablaBean<Trabajador> getTablaTrabajador() {
		return tablaTrabajador;
	}

	public void setTablaTrabajador(TablaBean<Trabajador> tablaTrabajador) {
		this.tablaTrabajador = tablaTrabajador;
	}

	public String getSearched() {
		return searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

}
