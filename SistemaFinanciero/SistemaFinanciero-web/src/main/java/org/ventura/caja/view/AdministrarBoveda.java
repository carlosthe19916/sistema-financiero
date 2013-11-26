package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Detalleaperturacierreboveda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.venturabank.managedbean.session.AgenciaBean;
import org.venturabank.util.DetalleTransaccionBean;
import org.ventura.util.maestro.EstadoValue;

@Named
@ViewScoped
public class AdministrarBoveda implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BovedaServiceLocal bovedaServiceLocal;
	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private TablaBean<Boveda> tablaBoveda;
	@Inject
	private ComboBean<Tipomoneda> comboTipomoneda;
	@Inject
	private Boveda boveda;
	@Inject
	private TablaBean<Detalleaperturacierreboveda> tablaBovedaDetalle;

	@Inject
	private TablaBean<DetalleTransaccionBean> tablaDetallemonedaboveda;

	@PostConstruct
	private void initialize() {
		this.tablaBovedaDetalle = new TablaBean<Detalleaperturacierreboveda>();
		this.comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		this.refreshTablaBoveda();
		boveda.getSaldo().setValue(BigDecimal.ZERO);
	}

	public void loadDetalleTransaccionboveda() {
		try {
			List<DetalleTransaccionBean> detalleMonedaBeans = new ArrayList<DetalleTransaccionBean>();
			tablaDetallemonedaboveda.setRows(detalleMonedaBeans);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openBoveda() throws Exception {
		try {
			bovedaServiceLocal.openBoveda(boveda);
			refreshBean();
			FacesMessage message = new FacesMessage("Info", "Boveda Abierta satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void openBovedaWithPendiente() throws Exception {
		try {
			List<Detalleaperturacierreboveda> detalleaperturacierreboveda = tablaBovedaDetalle.getAllRows();
			
			bovedaServiceLocal.openBovedaWithPendiente(boveda);
			refreshBean();

		} catch (Exception e) {
			throw e;
		}
	}

	public void closeBoveda() throws Exception {
		try {
			bovedaServiceLocal.closeBoveda(boveda);
			refreshBean();

		} catch (Exception e) {
			throw e;
		}
	}

	public void createBoveda() throws Exception {
		try {
			String denominacionBoveda = boveda.getDenominacion();
			Integer idTipomoneda = boveda.getIdtipomoneda();
			if (denominacionBoveda == null || denominacionBoveda.isEmpty() || denominacionBoveda.trim().isEmpty()) {
				throw new Exception("Denominación de Bóveda Inválida");
			}
			if (idTipomoneda == null) {
				throw new Exception("Tipo de Moneda Invalida");
			}
			preCreateBoveda();
			this.bovedaServiceLocal.create(this.boveda);
			refreshBean();
			
			FacesMessage message = new FacesMessage("Info", "Boveda Creada satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void updateBoveda() throws Exception {
		try {
			String denominacionBoveda = boveda.getDenominacion();
			Integer idTipomoneda = boveda.getIdtipomoneda();
			if (denominacionBoveda == null || denominacionBoveda.isEmpty() || denominacionBoveda.trim().isEmpty()) {
				throw new Exception("Denominación de Bóveda Inválida");
			}
			if (idTipomoneda == null) {
				throw new Exception("Tipo de Moneda Invalida");
			}
			this.bovedaServiceLocal.update(this.boveda);
			refreshBean();
			
			FacesMessage message = new FacesMessage("Info", "Boveda Creada satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void deleteBoveda() throws Exception {
		try {
			loadBoveda();

			boveda.setEstado(false);
			bovedaServiceLocal.update(boveda);
			refreshBean();

			FacesMessage message = new FacesMessage("Info", "Bóveda eliminada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void preCreateBoveda() throws Exception {
		this.boveda.setIdagencia(agenciaBean.getAgencia().getIdagencia());
	}

	public void activarMovimiento() throws Exception {
		try {
			loadBoveda();
			Boveda boveda = this.boveda;
			bovedaServiceLocal.defrostBoveda(boveda);
			refreshBean();
			FacesMessage message = new FacesMessage("Info", "Bóveda activada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void desactivarMovimiento() throws Exception {
		try {
			loadBoveda();
			Boveda boveda = this.boveda;
			bovedaServiceLocal.freezeBoveda(boveda);
			refreshBean();
			FacesMessage message = new FacesMessage("Info", "Bóveda activada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
	public void loadBoveda() throws Exception {
		try {
			Object object = tablaBoveda.getEditingRow();
			Boveda boveda = new Boveda();
			if (object instanceof Boveda) {
				boveda = (Boveda) object;
			}
			this.boveda = bovedaServiceLocal.find(boveda.getIdboveda());
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			this.comboTipomoneda.setItemSelected(tipomoneda);
		} catch (Exception e) {
			throw e;
		}
	}

	public void loadTablaBovedaDetalleAfterOpen() throws Exception {
		try {
			loadBoveda();
			EstadoMovimientoType estadoMovimientoType = EstadoValue.getEstadoType(boveda.getIdestadomovimiento());
			if (estadoMovimientoType != EstadoMovimientoType.CERRADO) {
				throw new Exception("Boveda Abierta imposible abrirla nuevamente");
			}
			List<Detalleaperturacierreboveda> detalleaperturacierrebovedaList = bovedaServiceLocal.getDetalleforOpenBoveda(boveda);
			tablaBovedaDetalle.setRows(detalleaperturacierrebovedaList);
		} catch (Exception e) {
			throw e;
		}
	}

	public void loadTablaBovedaDetalleAfterClose() {
		try {
			loadBoveda();

			// List<Detallehistorialboveda> detallehistorialbovedaList =
			// bovedaServiceLocal.getLastDetallehistorialboveda(boveda);
			// tablaBovedaDetalle.setRows(detallehistorialbovedaList);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"Error", e.getMessage());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void refreshBean() {
		this.refreshTablaBoveda();
		this.refreshComboTipomoneda();
		this.cleanBoveda();
	}

	public void refreshTablaBoveda() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA,parameters);
	}

	public void refreshComboTipomoneda() {
		this.comboTipomoneda.setItemSelected(-1);
	}

	public void cleanBoveda() {
		this.boveda = new Boveda();
	}

	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipomoneda tipomonedaSelected = comboTipomoneda.getObjectItemSelected(key);
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

	public TablaBean<Detalleaperturacierreboveda> getTablaBovedaDetalle() {
		return tablaBovedaDetalle;
	}

	public void setTablaBovedaDetalle(TablaBean<Detalleaperturacierreboveda> tablaBovedaDetalle) {
		this.tablaBovedaDetalle = tablaBovedaDetalle;
	}

}
