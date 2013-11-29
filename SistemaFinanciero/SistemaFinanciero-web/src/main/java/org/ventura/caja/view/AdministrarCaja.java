package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
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
	private TablaBean<Boveda> tablaBoveda;
	
	@PostConstruct
	private void initialize() {
		this.refreshTablaCaja();
	}
	
	public void createCaja(){
		
	}
	
	public void refreshTablaCaja() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", getAgenciaBean().getAgencia().getIdagencia());
		getTablaBoveda().initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA,parameters);
	}

	public TablaBean<Boveda> getTablaBoveda() {
		return tablaBoveda;
	}

	public void setTablaBoveda(TablaBean<Boveda> tablaBoveda) {
		this.tablaBoveda = tablaBoveda;
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}
}
