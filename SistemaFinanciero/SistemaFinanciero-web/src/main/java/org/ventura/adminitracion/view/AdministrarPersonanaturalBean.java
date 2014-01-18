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
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Personanatural;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarPersonanaturalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Personanatural> tablaPersonanatural;

	private String searched;
	private Integer idpersonanatural;
	
	@EJB private PersonanaturalServiceLocal personanaturalServiceLocal;

	public AdministrarPersonanaturalBean() {
		idpersonanatural = new Integer(-1);
	}

	@PostConstruct
	private void initialize() {
		//this.refreshTablaBoveda();
	}

	public void buscarPersona(){
		try {
			List<Personanatural> resultList = personanaturalServiceLocal.find(searched, 30);
			tablaPersonanatural.clean();
			tablaPersonanatural.setRows(resultList);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String editPersona(Personanatural personanatural){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("personanatural", personanatural);
	    return "personanatural?faces-redirect=true";
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
	
	public void activarMovimiento() throws Exception {
		/*try {
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
		
			bovedaServiceLocal.defrostBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Boveda Descongelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Descongelar Boveda");
		}*/
	}

	public void desactivarMovimiento() throws Exception {
		/*try {
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
			
			bovedaServiceLocal.freezeBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Boveda Congelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Congelar Boveda");
		}*/
	}

	public void setRowSelect() {
		/*BovedaView bovedaView;
		Object object = tablaBoveda.getSelectedRow();
		if (object instanceof BovedaView) {
			bovedaView = (BovedaView) object;
			this.idboveda = bovedaView.getIdBoveda();
		} else {
			System.out.println("La seleccion no fue valida");
		}*/
	}

	public void refreshBean() {
		this.refreshTablaBoveda();
		this.cleanBoveda();
	}

	public void refreshTablaBoveda() {
		/*Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("agencia", agenciaBean.getAgencia());
		tablaBoveda.initValuesFromNamedQueryName(
				BovedaView.ALL_ACTIVE_BY_AGENCIA, parameters);*/
	}

	public void cleanBoveda() {
		//this.idboveda = new Integer(-1);
	}

	public TablaBean<Personanatural> getTablaPersonanatural() {
		return tablaPersonanatural;
	}

	public void setTablaPersonanatural(TablaBean<Personanatural> tablaPersonanatural) {
		this.tablaPersonanatural = tablaPersonanatural;
	}

	public String getSearched() {
		return searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	public Integer getIdpersonanatural() {
		return idpersonanatural;
	}

	public void setIdpersonanatural(Integer idpersonanatural) {
		this.idpersonanatural = idpersonanatural;
	}

}
