package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.view.BovedaView;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarBovedaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	@Inject
	private AgenciaBean agenciaBean;

	@Inject
	private TablaBean<BovedaView> tablaBoveda;

	private Integer idboveda;

	public AdministrarBovedaBean() {
		idboveda = new Integer(-1);
	}

	@PostConstruct
	private void initialize() {
		this.refreshTablaBoveda();
	}

	public void deleteBoveda() throws Exception {
		try {
			//loadBoveda();
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
			bovedaServiceLocal.inactive(boveda);
			refreshBean();

			JsfUtil.addSuccessMessage("Boveda Desactivada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JsfUtil.addErrorMessage(e, "Error al Inactivar Boveda");
		}
	}
	
	public void activarMovimiento() throws Exception {
		try {
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
		
			bovedaServiceLocal.defrostBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Boveda Descongelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Descongelar Boveda");
		}
	}

	public void desactivarMovimiento() throws Exception {
		try {
			Boveda boveda =  new Boveda();
			boveda.setIdboveda(idboveda);
			
			bovedaServiceLocal.freezeBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Boveda Congelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Congelar Boveda");
		}
	}

	public void setRowSelect() {
		BovedaView bovedaView;
		Object object = tablaBoveda.getSelectedRow();
		if (object instanceof BovedaView) {
			bovedaView = (BovedaView) object;
			this.idboveda = bovedaView.getIdBoveda();
		} else {
			System.out.println("La seleccion no fue valida");
		}
	}

	public void refreshBean() {
		this.refreshTablaBoveda();
		this.cleanBoveda();
	}

	public void refreshTablaBoveda() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("agencia", agenciaBean.getAgencia());
		tablaBoveda.initValuesFromNamedQueryName(
				BovedaView.ALL_ACTIVE_BY_AGENCIA, parameters);
	}

	public void cleanBoveda() {
		this.idboveda = new Integer(-1);
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public TablaBean<BovedaView> getTablaBoveda() {
		return tablaBoveda;
	}

	public void setTablaBoveda(TablaBean<BovedaView> tablaBoveda) {
		this.tablaBoveda = tablaBoveda;
	}

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

}
