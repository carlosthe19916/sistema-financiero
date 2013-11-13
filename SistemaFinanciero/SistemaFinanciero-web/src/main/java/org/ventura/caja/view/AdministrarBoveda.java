package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@ViewScoped
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

	@PostConstruct
	private void initialize() {
		boveda.setEstado(true);
		boveda.setIdagencia(agenciaBean.getAgencia().getIdagencia());
		boveda.setIdestadomovimiento(1);
		comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA,
				parameters);
	}

	public void createBoveda() {
		try {
			bovedaServiceLocal.create(this.boveda);
		} catch (Exception e) {

		}
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

}
