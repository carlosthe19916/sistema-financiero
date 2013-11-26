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
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Entidadfinanciera;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccionboveda;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.EstadoValue;
import org.venturabank.managedbean.session.AgenciaBean;

@Named
@ViewScoped
public class TransaccionBovedaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private BovedaServiceLocal bovedaServiceLocal;
	
	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private Boveda boveda;
	@Inject
	private Transaccionboveda transaccionboveda;

	@Inject
	private ComboBean<Boveda> comboBoveda;
	@Inject
	private ComboBean<Tipotransaccion> comboTipotransaccion;
	@Inject
	private ComboBean<String> comboTipoentidad;
	@Inject
	private ComboBean<Entidadfinanciera> comboEntidadfinanciera;
	@Inject
	private ComboBean<Caja> comboCaja;
	@Inject
	private TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda;

	@PostConstruct
	private void initialize() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
			parameters.put("idestadomovimiento",EstadoValue.getEstadoMovimientoValue(EstadoMovimientoType.ABIERTO_DESCONGELADO));		
			
			comboBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA_AND_ESTADOMOVIMIENTO,parameters);		
			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccion.ALL_ACTIVE);

			comboTipoentidad.putItem(1, "Caja");
			comboTipoentidad.putItem(2, "Otro");
		} catch (Exception e) {
		}		
	}

	public void createTransaccionboveda() throws Exception {
		boolean result;
		try {
			result = this.validateBean();
			if (result == true) {
				Boveda boveda = this.boveda;
				Tipotransaccion tipotransaccion = comboTipotransaccion.getObjectItemSelected();
				Caja caja = comboCaja.getObjectItemSelected();
				Entidadfinanciera entidadfinanciera = comboEntidadfinanciera.getObjectItemSelected();
				List<Detalletransaccionboveda> detalletransaccionbovedas = tablaDetalletransaccionboveda.getAllRows();

				transaccionboveda.refreshMonto();
				transaccionboveda.setTipotransaccion(tipotransaccion);
				transaccionboveda.setCaja(caja);
				transaccionboveda.setEntidadfinanciera(entidadfinanciera);
				transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);
				bovedaServiceLocal.createTransaccionboveda(boveda,transaccionboveda);
			} else {
				throw new Exception("Datos de Transaccion Invalidos");
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");            
	        RequestContext.getCurrentInstance().showMessageInDialog(message);  
		}
	}
	
	public boolean validateBean() throws Exception {
		boolean result = true;
		Boveda boveda = this.boveda;
		Tipotransaccion tipotransaccion = comboTipotransaccion.getObjectItemSelected();
		Caja caja = null;
		Entidadfinanciera entidadfinanciera = null;
		List<Detalletransaccionboveda> detalletransaccionbovedas = tablaDetalletransaccionboveda.getAllRows();
		if (isCaja()) {
			caja = comboCaja.getObjectItemSelected();
			entidadfinanciera = null;
		} else {
			if (isOtro()) {
				entidadfinanciera = comboEntidadfinanciera.getObjectItemSelected();
				caja = null;
			} else {
				throw new Exception("Tipo de entidad origen no valida");
			}
		}
		if (boveda == null) {
			return false;
		}
		if (tipotransaccion == null) {
			return false;
		}
		if (caja == null && entidadfinanciera == null) {
			return false;
		}
		if (detalletransaccionbovedas == null) {
			return false;
		}
		return result;
	}
	
	public void loadDetalleTransaccionboveda() {
		try {
			List<Detalletransaccionboveda> detalletransaccionbovedas = bovedaServiceLocal.getDetalletransaccionboveda(boveda);
			tablaDetalletransaccionboveda.setRows(detalletransaccionbovedas);
			this.transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);
		} catch (Exception e) {
		}
	}

	public void changeBoveda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Boveda bovedaSelected = comboBoveda.getObjectItemSelected(key);
		this.boveda = bovedaSelected;
		if(this.boveda != null){
			this.loadDetalleTransaccionboveda();
		} else {
			this.tablaDetalletransaccionboveda.clean();
		}		
	}
	
	public void changeTipotransaccion(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Tipotransaccion tipotransaccionSelected = comboTipotransaccion.getObjectItemSelected(key);		
	}
	
	public void changeTipoentidad(ValueChangeEvent event) throws Exception {
		Integer key = (Integer) event.getNewValue();
		if(key != null){
			if (key == 1) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("idboveda", boveda.getIdboveda());
				comboCaja.initValuesFromNamedQueryName(Caja.findAllByBovedaAndState, parameters);
			} else {
				if (key == 2) {
					comboEntidadfinanciera.initValuesFromNamedQueryName(Entidadfinanciera.ALL_ACTIVE);
				} else {
					throw new Exception("Tipo de entidad no valida");
				}
			}
		} else {
			this.comboEntidadfinanciera.clean();
			this.comboCaja.clean();
		}		
	}

	public boolean isCaja() {
		Integer key = comboTipoentidad.getItemSelected();
		if (key != null) {
			if (key == 1)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	public boolean isOtro() {
		Integer key = comboTipoentidad.getItemSelected();
		if(key != null){
			if (key == 2)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	
	public TablaBean<Detalletransaccionboveda> getTablaDetalletransaccionboveda() {
		return tablaDetalletransaccionboveda;
	}

	public void setTablaDetalletransaccionboveda(TablaBean<Detalletransaccionboveda> tablaDetalletransaccionboveda) {
		this.tablaDetalletransaccionboveda = tablaDetalletransaccionboveda;
	}

	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public ComboBean<Boveda> getComboBoveda() {
		return comboBoveda;
	}

	public void setComboBoveda(ComboBean<Boveda> comboBoveda) {
		this.comboBoveda = comboBoveda;
	}

	public ComboBean<Tipotransaccion> getComboTipotransaccion() {
		return comboTipotransaccion;
	}

	public void setComboTipotransaccion(
			ComboBean<Tipotransaccion> comboTipotransaccion) {
		this.comboTipotransaccion = comboTipotransaccion;
	}

	public ComboBean<String> getComboTipoentidad() {
		return comboTipoentidad;
	}

	public void setComboTipoentidad(ComboBean<String> comboTipoentidad) {
		this.comboTipoentidad = comboTipoentidad;
	}

	public Transaccionboveda getTransaccionboveda() {
		return transaccionboveda;
	}

	public void setTransaccionboveda(Transaccionboveda transaccionboveda) {
		this.transaccionboveda = transaccionboveda;
	}

	public ComboBean<Entidadfinanciera> getComboEntidadfinanciera() {
		return comboEntidadfinanciera;
	}

	public void setComboEntidadfinanciera(
			ComboBean<Entidadfinanciera> comboEntidadfinanciera) {
		this.comboEntidadfinanciera = comboEntidadfinanciera;
	}

	public ComboBean<Caja> getComboCaja() {
		return comboCaja;
	}

	public void setComboCaja(ComboBean<Caja> comboCaja) {
		this.comboCaja = comboCaja;
	}

}
