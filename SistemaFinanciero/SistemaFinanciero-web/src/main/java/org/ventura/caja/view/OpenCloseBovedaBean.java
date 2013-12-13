package org.ventura.caja.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.managedbean.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class OpenCloseBovedaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private Agencia agencia;

	private Boveda boveda;
	private Integer idboveda;

	@Inject
	private TablaBean<Detallehistorialboveda> tablaBovedaDetalle;
	@Inject
	private TablaBean<Detallehistorialboveda> tablaBovedaDetalleLastNoActive;
	
	private boolean isValidBean;
	
	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	public OpenCloseBovedaBean() {
		isValidBean = true;
	}
	
	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
	}

	public void loadBovedaForOpen() {
		try {
			if(idboveda != null && idboveda != -1){
				boveda = bovedaServiceLocal.find(idboveda);
				loadTablaBovedaDetalleForOpen();
			} else {
				JsfUtil.addErrorMessage("Boveda no encontrada");
				setInvalidBean();
			}		
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar la Boveda");
			setInvalidBean();
		}
	}

	public void loadBovedaForClose() {
		try {
			if(idboveda != null && idboveda != -1){
				boveda = bovedaServiceLocal.find(idboveda);
				loadTablaBovedaDetalleForClose();	
			} else {
				JsfUtil.addErrorMessage("Boveda no encontrada");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar la Boveda");
			setInvalidBean();
		}
	}

	public void loadTablaBovedaDetalleForOpen() throws Exception {
		try {
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();

			if (estadoapertura.equals(estadoapertura2)) {
				List<Detallehistorialboveda> detalleaperturacierrebovedaList = bovedaServiceLocal.getDetalleforOpenBoveda(boveda);
				tablaBovedaDetalle.setRows(detalleaperturacierrebovedaList);
			} else {
				JsfUtil.addErrorMessage("Boveda Abierta, no se puede Reabrir");
				setInvalidBean();
			}

		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar el detalle de boveda");
			setInvalidBean();
		}
	}
	
	public void loadTablaBovedaDetalleForClose() throws Exception {
		try {
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();
			if (!estadoapertura.equals(estadoapertura2)) {			
				 List<Detallehistorialboveda> detallehistorialbovedaInicial = bovedaServiceLocal.getDetallehistorialbovedaLastNoActive(boveda);
				 List<Detallehistorialboveda> detallehistorialbovedaFinal = bovedaServiceLocal.getDetallehistorialbovedaLastActive(boveda);				 
				 tablaBovedaDetalleLastNoActive.setRows(detallehistorialbovedaInicial);
				 tablaBovedaDetalle.setRows(detallehistorialbovedaFinal);
			} else {
				JsfUtil.addErrorMessage("Boveda Cerrada, no se puede Cerrar");
				setInvalidBean();
			}		
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar el detalle de boveda");
			setInvalidBean();
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
	
	public Moneda getTotalHistorialbovedaLast() {
		Moneda result = new Moneda();
		for (Detallehistorialboveda e : tablaBovedaDetalleLastNoActive.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	public String openBoveda() throws Exception {
		Boveda boveda = this.boveda;
		try {
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {
				this.bovedaServiceLocal.openBoveda(boveda);
				JsfUtil.addSuccessMessage("Boveda Abierta");
			} else {
				JsfUtil.addErrorMessage("Boveda Abierta, no se puede Reabrir");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al abrir Boveda");
			setInvalidBean();
			return "failure";
		}
		return "success";
	}
	
	public String closeBoveda() throws Exception {
		Boveda boveda = this.boveda;
		try {
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.boveda.getEstadoapertura();
			if (!estadoapertura.equals(estadoapertura2)) {
				this.bovedaServiceLocal.closeBoveda(boveda);
				JsfUtil.addSuccessMessage("Boveda Cerrada");
			} else {
				JsfUtil.addErrorMessage("Boveda Cerrada, no se puede Cerrar");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al cerrar Boveda");
			setInvalidBean();
			return "failure";
		}
		return "success";
	}

	public void setInvalidBean(){
		this.isValidBean = false;
	}
	
	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public BovedaServiceLocal getBovedaServiceLocal() {
		return bovedaServiceLocal;
	}

	public void setBovedaServiceLocal(BovedaServiceLocal bovedaServiceLocal) {
		this.bovedaServiceLocal = bovedaServiceLocal;
	}

	public Integer getIdboveda() {
		return idboveda;
	}

	public void setIdboveda(Integer idboveda) {
		this.idboveda = idboveda;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public TablaBean<Detallehistorialboveda> getTablaBovedaDetalle() {
		return tablaBovedaDetalle;
	}

	public void setTablaBovedaDetalle(
			TablaBean<Detallehistorialboveda> tablaBovedaDetalle) {
		this.tablaBovedaDetalle = tablaBovedaDetalle;
	}

	public TablaBean<Detallehistorialboveda> getTablaBovedaDetalleLastNoActive() {
		return tablaBovedaDetalleLastNoActive;
	}

	public void setTablaBovedaDetalleLastNoActive(
			TablaBean<Detallehistorialboveda> tablaBovedaDetalleLastNoActive) {
		this.tablaBovedaDetalleLastNoActive = tablaBovedaDetalleLastNoActive;
	}

	public boolean isValidBean() {
		return isValidBean;
	}

	public void setValidBean(boolean isValidBean) {
		this.isValidBean = isValidBean;
	}

}
