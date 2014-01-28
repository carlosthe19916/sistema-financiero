package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Entidadfinanciera;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccionboveda;
import org.ventura.session.AgenciaBean;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.InsufficientMoneyForTransactionException;
import org.ventura.util.exception.InvalidTransactionBovedaException;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;

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
	
	@Inject
	private TablaBean<Detallehistorialboveda> tablaDetallehistorialboveda;

	@PostConstruct
	private void initialize() {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("agencia", agenciaBean.getAgencia());
			parameters.put("estadoapertura",ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO));		
			parameters.put("estadomovimiento",ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO));
			
			comboBoveda.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA_AND_ESTADOMOVIMIENTO, parameters);		
			comboTipotransaccion.initValuesFromNamedQueryName(Tipotransaccion.ALL_ACTIVE);

			comboTipoentidad.putItem(1, "CAJA");
			comboTipoentidad.putItem(2, "OTRO");
		} catch (Exception e) {
		}		
	}

	public String createTransaccionboveda() {
		try {
			boolean result;
			result = this.validateBean();
			if (result == true) {
				Boveda boveda = this.boveda;
				Tipotransaccion tipotransaccion = comboTipotransaccion.getObjectItemSelected();
				Caja caja = comboCaja.getObjectItemSelected();
				Entidadfinanciera entidadfinanciera = comboEntidadfinanciera.getObjectItemSelected();
				List<Detalletransaccionboveda> detalletransaccionbovedas = tablaDetalletransaccionboveda.getAllRows();

				transaccionboveda.setTipotransaccion(tipotransaccion);
				transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);
				
				Transaccionboveda transaccionbovedaResult;
				if(isCaja()){
					transaccionbovedaResult = bovedaServiceLocal.createTransaccionboveda(boveda,caja, transaccionboveda);
				} else {
					transaccionbovedaResult = bovedaServiceLocal.createTransaccionboveda(boveda,entidadfinanciera, transaccionboveda);
				}							
				this.transaccionboveda = transaccionbovedaResult;
			} else {
				throw new Exception("Datos de Transaccion Invalidos");
			}
		} catch (InsufficientMoneyForTransactionException e) {
			return "retiroInvalido";
		} catch (InvalidTransactionBovedaException e) {
			return "invalidTransaction";
		} catch (Exception e) {
			return "failure";
		}
		return "success";
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
			List<Detallehistorialboveda> detallehistorialbovedas = bovedaServiceLocal.getDetallehistorialbovedaLastActive(boveda);
						
			tablaDetalletransaccionboveda.setRows(detalletransaccionbovedas);
			this.transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);
			
			tablaDetallehistorialboveda.setRows(detallehistorialbovedas);
		} catch (Exception e) {
		}
	}

	public void refreshBean() {
		boveda = new Boveda();
		transaccionboveda = new Transaccionboveda();
		comboBoveda.reset();
		comboTipotransaccion.reset();
		comboTipoentidad.reset();
		comboEntidadfinanciera.reset();
		comboCaja.reset();
		tablaDetalletransaccionboveda.clean();
	}
	
	public void changeBoveda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Boveda bovedaSelected = comboBoveda.getObjectItemSelected(key);
		this.boveda = bovedaSelected;
		if(this.boveda != null){
			this.comboTipoentidad.setItemSelected(-1);
			this.comboCaja.clean();
			this.comboEntidadfinanciera.clean();
			this.loadDetalleTransaccionboveda();
		} else {
			this.comboTipoentidad.setItemSelected(-1);
			this.comboCaja.clean();
			this.comboEntidadfinanciera.clean();
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
				Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("idboveda", boveda.getIdboveda());
				parameters.put("estadoapertura", estadoapertura);
				parameters.put("estadomovimiento", estadomovimiento);
				comboCaja.initValuesFromNamedQueryName(Caja.findAllByBovedaAndState, parameters);
				comboEntidadfinanciera.clean();
			} else {
				if (key == 2) {
					comboEntidadfinanciera.initValuesFromNamedQueryName(Entidadfinanciera.ALL_ACTIVE);
					comboCaja.clean();
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
	
	public Moneda getTotalTransaccion() {
		Moneda result = new Moneda();
		for (Detalletransaccionboveda e : tablaDetalletransaccionboveda.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
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

	public TablaBean<Detallehistorialboveda> getTablaDetallehistorialboveda() {
		return tablaDetallehistorialboveda;
	}

	public void setTablaDetallehistorialboveda(
			TablaBean<Detallehistorialboveda> tablaDetallehistorialboveda) {
		this.tablaDetallehistorialboveda = tablaDetallehistorialboveda;
	}

}
