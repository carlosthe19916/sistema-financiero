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
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarTrabajadorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Trabajador> tablaTrabajador;

	private String searched;
	
	@Inject AgenciaBean agenciaBean;
	@Inject Agencia agencia;
	
	@EJB private TrabajadorServiceLocal trabajadorServiceLocal;

	public AdministrarTrabajadorBean() {
		
	}

	@PostConstruct
	private void initialize() {
		agencia = agenciaBean.getAgencia();
	}

	public void buscarTrabajador(){
		try {
			List<Trabajador> resultList = trabajadorServiceLocal.find(agencia, null, searched);
			tablaTrabajador.clean();
			tablaTrabajador.setRows(resultList);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void editTrabajador(Personanatural personanatural){
		
	}
	
	public void deleteBoveda() throws Exception {
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
