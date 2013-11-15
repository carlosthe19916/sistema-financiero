package org.ventura.caja.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.venturabank.managedbean.session.AgenciaBean;

@javax.inject.Named
@javax.faces.view.ViewScoped
public class AdministrarBoveda implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	@Inject
	private TablaBean<Boveda> tablaBoveda;
	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	private Boveda boveda;
	
	private TablaBean<Detallehistorialboveda> tablaBovedaDetalle;

	@PostConstruct
	private void initialize() {
		tablaBovedaDetalle = new TablaBean<Detallehistorialboveda>();
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		this.refreshTablaBoveda();	
	}

	public void loadTablaBovedaDetalle(){
		try {
			tablaBovedaDetalle.setRows(bovedaServiceLocal.getLastDetallehistorialboveda(boveda));
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	public void refreshBoveda() {
		boveda = new Boveda();
	}

	public void refreshComboTipomoneda() {
		comboTipomoneda.setItemSelected(-1);
	}

	public void refreshTablaBoveda() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA, parameters);
	}

	public void openBoveda() {
		try {
			bovedaServiceLocal.openBoveda(boveda);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error", e.getMessage());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
	public void createBoveda() {
		try {
			preCreateBoveda();
			bovedaServiceLocal.create(this.boveda);
			refreshTablaBoveda();
			refreshBoveda();
			refreshComboTipomoneda();

			FacesMessage message = new FacesMessage("Boveda creada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error", e.getMessage());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void updateBoveda() {
		try {
			bovedaServiceLocal.update(this.boveda);
			refreshTablaBoveda();
			refreshBoveda();
			refreshComboTipomoneda();
			FacesMessage message = new FacesMessage("Boveda actualizada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void deleteBoveda() {
		try {
			bovedaServiceLocal.create(this.boveda);
		} catch (Exception e) {

		}
	}

	public void preCreateBoveda() {
		this.boveda.setEstado(true);
		this.boveda.setIdagencia(agenciaBean.getAgencia().getIdagencia());
		this.boveda.setIdestadomovimiento(1);
	}

	public void preUpdateBoveda() {
		Object object = tablaBoveda.getSelectedRow();
		Boveda boveda = null;
		if (object instanceof Boveda) {
			boveda = (Boveda) object;
		}
		this.boveda = boveda;
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda
				.getObjectItemSelected(key);
		this.boveda.setTipomoneda(tipomonedaSelected);
	}

	public TablaBean<Boveda> getTablaBoveda() {
		return tablaBoveda;
	}

	public void setTablaBoveda(TablaBean<Boveda> tablaBoveda) {
		this.tablaBoveda = tablaBoveda;
	}

	public ComboBean<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboBean<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public TablaBean<Detallehistorialboveda> getTablaBovedaDetalle() {
		return tablaBovedaDetalle;
	}

	public void setTablaBovedaDetalle(
			TablaBean<Detallehistorialboveda> tablaBovedaDetalle) {
		this.tablaBovedaDetalle = tablaBovedaDetalle;
	}

}
