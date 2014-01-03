package org.ventura.caja.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class BovedaCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgenciaBean agenciaBean;

	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;

	private Agencia agencia;
	private String denominacion;
	private Tipomoneda tipomoneda;

	private Boveda boveda;
	private Integer idboveda;
	
	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
		this.comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}

	public void loadBovedaForEdit(){
		try {
			if(idboveda != null && idboveda != -1){
				boveda = bovedaServiceLocal.find(idboveda);
				this.denominacion = boveda.getDenominacion();
				this.tipomoneda = boveda.getTipomoneda();
				this.comboTipomoneda.setItemSelected(tipomoneda);
			} else {
				JsfUtil.addErrorMessage("No se encontró la Boveda");
			}	
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al cargar Boveda");
		}
	}
	
	public String createBoveda() throws Exception {
		Boveda boveda;
		try {
			boveda = new Boveda();
			boveda.setDenominacion(denominacion);
			boveda.setTipomoneda(tipomoneda);
			boveda.setAgencia(agencia);

			this.bovedaServiceLocal.create(boveda);

			JsfUtil.addSuccessMessage("Boveda Creada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al crear Boveda");
			return "failure";
		}
		return "success";
	}
	
	public String updateBoveda() throws Exception {
		Boveda boveda = this.boveda;
		try {
			boveda.setDenominacion(denominacion);
			boveda.setTipomoneda(tipomoneda);

			this.bovedaServiceLocal.update(boveda);

			JsfUtil.addSuccessMessage("Boveda Actualizada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al actualizar Boveda");
			return "failure";
		}
		return "success";
	}

	public boolean isValidBean(){
		if(idboveda == null || idboveda == -1){
			return false;
		} else {
			return true;
		}
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
		this.tipomoneda = tipomonedaSelected;
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Tipomoneda getTipomoneda() {
		return tipomoneda;
	}

	public void setTipomoneda(Tipomoneda tipomoneda) {
		this.tipomoneda = tipomoneda;
	}

	public BovedaServiceLocal getBovedaServiceLocal() {
		return bovedaServiceLocal;
	}

	public void setBovedaServiceLocal(BovedaServiceLocal bovedaServiceLocal) {
		this.bovedaServiceLocal = bovedaServiceLocal;
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

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

}
