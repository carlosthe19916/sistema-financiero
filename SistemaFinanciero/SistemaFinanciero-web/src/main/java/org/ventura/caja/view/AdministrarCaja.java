package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.picklist.PickList;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.PickListBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.venturabank.managedbean.session.AgenciaBean;


@Named
@ViewScoped
public class AdministrarCaja implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	@Inject
	private AgenciaBean agenciaBean;

	@Inject
	private TablaBean<Caja> tablaCaja;
	
	@Inject
	private PickListBean<Boveda> pickListBoveda;
	
	
	
	
	@PostConstruct
	private void initialize() {
		this.refreshTablaCaja();
		//this.initializePickList();
	}
	
	public void createCaja(){
		
	}
	
	public void refreshTablaCaja() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaCaja.initValuesFromNamedQueryName(Caja.ALL_ACTIVE_BY_AGENCIA,parameters);
	}
	
	public void initializePickList(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		pickListBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA, parameters);
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public TablaBean<Caja> getTablaCaja() {
		return tablaCaja;
	}

	public void setTablaCaja(TablaBean<Caja> tablaCaja) {
		this.tablaCaja = tablaCaja;
	}

	public PickListBean<Boveda> getPickListBoveda() {
		return pickListBoveda;
	}

	public void setPickListBoveda(PickListBean<Boveda> pickListBoveda) {
		this.pickListBoveda = pickListBoveda;
	}
	
}
