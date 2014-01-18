package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarPersonajuridicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablaBean<Personajuridica> tablaPersonajuridica;

	private String searched;
	private Integer idpersonajuridica;
	
	@EJB private PersonajuridicaServiceLocal personajuridicaServiceLocal;

	public AdministrarPersonajuridicaBean() {
		idpersonajuridica = new Integer(-1);
	}

	@PostConstruct
	private void initialize() {
		//this.refreshTablaBoveda();
	}

	public void buscarPersona(){
		try {
			List<Personajuridica> resultList = personajuridicaServiceLocal.find(searched, 30);
			tablaPersonajuridica.clean();
			tablaPersonajuridica.setRows(resultList);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}

	public TablaBean<Personajuridica> getTablaPersonajuridica() {
		return tablaPersonajuridica;
	}

	public void setTablaPersonajuridica(
			TablaBean<Personajuridica> tablaPersonajuridica) {
		this.tablaPersonajuridica = tablaPersonajuridica;
	}

	public String getSearched() {
		return searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	public Integer getIdpersonajuridica() {
		return idpersonajuridica;
	}

	public void setIdpersonajuridica(Integer idpersonajuridica) {
		this.idpersonajuridica = idpersonajuridica;
	}

}
