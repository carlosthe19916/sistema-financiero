package org.ventura.caja.view;

import java.io.Serializable;
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
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@ViewScoped
public class AdministrarBovedaBean implements Serializable {

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
	private TablaBean<Detallehistorialboveda> tablaBovedaDetalle;
	@Inject
	private TablaBean<Detallehistorialboveda> tablaBovedaDetalleLastNoActive;
	
	@PostConstruct
	private void initialize() {
		this.tablaBovedaDetalle = new TablaBean<Detallehistorialboveda>();
		this.comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
		this.refreshTablaBoveda();
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
			//List<Detallehistorialboveda> detalleaperturacierreboveda = tablaBovedaDetalle.getAllRows();
			
			//bovedaServiceLocal.openBovedaWithPendiente(boveda);
			//refreshBean();

		} catch (Exception e) {
			throw e;
		}
	}

	public void closeBoveda() throws Exception {
		try {
			bovedaServiceLocal.closeBoveda(boveda);
			refreshBean();
			
			FacesMessage message = new FacesMessage("Info", "Boveda Cerrada satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	        
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void createBoveda() throws Exception {
		try {
			String denominacionBoveda = boveda.getDenominacion();
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			if (denominacionBoveda == null || denominacionBoveda.isEmpty() || denominacionBoveda.trim().isEmpty()) {
				throw new Exception("Denominación de Bóveda Inválida");
			}
			if (tipomoneda == null) {
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
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			if (denominacionBoveda == null || denominacionBoveda.isEmpty() || denominacionBoveda.trim().isEmpty()) {
				throw new Exception("Denominación de Bóveda Inválida");
			}
			if (tipomoneda == null) {
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
		this.boveda.setAgencia(agenciaBean.getAgencia());
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
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();
		
			if (estadoapertura.equals(estadoapertura2)) {			
				List<Detallehistorialboveda> detalleaperturacierrebovedaList = bovedaServiceLocal.getDetalleforOpenBoveda(boveda);
				tablaBovedaDetalle.setRows(detalleaperturacierrebovedaList);
			} else {
				throw new Exception("Boveda Abierta imposible abrirla nuevamente");
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	public void loadTablaBovedaDetalleAfterClose() throws Exception {
		try {
			loadBoveda();
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();
			if (!estadoapertura.equals(estadoapertura2)) {			
				 List<Detallehistorialboveda> detallehistorialbovedaInicial = bovedaServiceLocal.getDetallehistorialbovedaLastNoActive(boveda);
				 List<Detallehistorialboveda> detallehistorialbovedaFinal = bovedaServiceLocal.getDetallehistorialbovedaLastActive(boveda);				 
				 tablaBovedaDetalleLastNoActive.setRows(detallehistorialbovedaInicial);
				 tablaBovedaDetalle.setRows(detallehistorialbovedaFinal);
			} else {
				throw new Exception("Boveda Cerrada imposible cerrarla nuevamente");
			}		
		} catch (Exception e) {
			throw e;
		}
	}

	public Moneda getTotalHistorialboveda() {
		Moneda result = new Moneda();
		for (Detallehistorialboveda e : tablaBovedaDetalle.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
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

	public TablaBean<Detallehistorialboveda> getTablaBovedaDetalle() {
		return tablaBovedaDetalle;
	}

	public void setTablaBovedaDetalle(TablaBean<Detallehistorialboveda> tablaBovedaDetalle) {
		this.tablaBovedaDetalle = tablaBovedaDetalle;
	}


	public TablaBean<Detallehistorialboveda> getTablaBovedaDetalleYesterday() {
		return tablaBovedaDetalleLastNoActive;
	}


	public void setTablaBovedaDetalleYesterday(
			TablaBean<Detallehistorialboveda> tablaBovedaDetalleYesterday) {
		this.tablaBovedaDetalleLastNoActive = tablaBovedaDetalleYesterday;
	}

}
