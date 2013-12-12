package org.ventura.caja.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.venturabank.managedbean.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ListCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Sesion Objects
	@Inject
	private AgenciaBean agenciaBean;

	// administracion
	@Inject
	private Caja current;
	@Inject
	private TablaBean<Caja> tablaCaja;
	private int selectedItemIndex;

	// datos de interfaz
	private String denominacion;
	private String abreviatura;

	// datos auxiliares
	private DualListModel<Boveda> dualListModelBoveda;

	// servicios
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	@PostConstruct
	private void initialize() {
		refreshTablaCaja();
		refreshListBoveda();
	}

	public void refreshTablaCaja() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaCaja.initValuesFromNamedQueryName(Caja.ALL_ACTIVE_BY_AGENCIA,parameters);
	}
	
	public void refreshListBoveda() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		List<Boveda> bovedas = null;
		try {
			bovedas = bovedaServiceLocal.findByNamedQuery(Boveda.ALL_ACTIVE_BY_AGENCIA, parameters);
		} catch (Exception e) {

		}
		List<Boveda> source = bovedas;
		List<Boveda> target = new ArrayList<Boveda>();
		dualListModelBoveda = new DualListModel<Boveda>(source, target);
	}

	public String prepareCreate() {
        current = new Caja();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
        	Caja caja = new Caja();
        	caja.setDenominacion(denominacion);
        	caja.setAbreviatura(abreviatura);
        		
        	List bovedas = dualListModelBoveda.getTarget();   	
        	
        	caja.setBovedas(bovedas);
        	
        	cajaServiceLocal.create(caja);
            JsfUtil.addSuccessMessage("Caja Created");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocurrio un error, no se pudo guardar los cambios");
            return null;
        }
    }

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public Caja getCurrent() {
		return current;
	}

	public void setCurrent(Caja current) {
		this.current = current;
	}

	public TablaBean<Caja> getTablaCaja() {
		return tablaCaja;
	}

	public void setTablaCaja(TablaBean<Caja> tablaCaja) {
		this.tablaCaja = tablaCaja;
	}

	public int getSelectedItemIndex() {
		return selectedItemIndex;
	}

	public void setSelectedItemIndex(int selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public DualListModel<Boveda> getDualListModelBoveda() {
		return dualListModelBoveda;
	}

	public void setDualListModelBoveda(DualListModel<Boveda> dualListModelBoveda) {
		this.dualListModelBoveda = dualListModelBoveda;
	}
}
