package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.managedbean.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class OpenCloseCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private Agencia agencia;
	//@Inject
	//private TablaBean<Detallehistorialboveda> tablaBovedaDetalle;
	@Inject
	private TablaBean<Caja> tablaCaja;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleSoles;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleDolares;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleEuros;
	//@Inject
	//private TablaBean<Detallehistorialboveda> tablaBovedaDetalleLastNoActive;
	
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	private Caja caja;
	private Integer idcaja;
	private boolean isValidBean;

	public OpenCloseCajaBean() {
		isValidBean = true;
	}
	
	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
	}

	public void loadCajaForOpen() {
		try {
			if(idcaja != null && idcaja != -1){
				caja = cajaServiceLocal.find(idcaja);
				loadTablaCajaDetalleForOpen();
			} else {
				JsfUtil.addErrorMessage("Caja no encontrada");
				setInvalidBean();
			}		
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar la Caja");
			setInvalidBean();
		}
	}
/*
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
*/
	public void loadTablaCajaDetalleForOpen() throws Exception {
		try {
			Tipomoneda tipomonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
			Tipomoneda tipomonedaDolar = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
			Tipomoneda tipomonedaEuro = ProduceObject.getTipomoneda(TipomonedaType.EURO);
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			
			if (estadoapertura.equals(estadoapertura2)) {
				HashMap<Tipomoneda, List<Detallehistorialcaja>> detalleaperturacierrecajaList = cajaServiceLocal.getDetalleforOpenCaja(caja);
				
				Set<Tipomoneda> a= detalleaperturacierrecajaList.keySet();
				for (Tipomoneda tipomoneda : a) {
					if (tipomoneda.equals(tipomonedaSoles)) {
						tablaCajaDetalleSoles.setRows(detalleaperturacierrecajaList.get(tipomoneda));
					}
					if (tipomoneda.equals(tipomonedaDolar)) {
						tablaCajaDetalleDolares.setRows(detalleaperturacierrecajaList.get(tipomoneda));
					}
					if (tipomoneda.equals(tipomonedaEuro)) {
						tablaCajaDetalleEuros.setRows(detalleaperturacierrecajaList.get(tipomoneda));
					}
				}
			} else {
				JsfUtil.addErrorMessage("Caja Abierta, no se puede Reabrir");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar el detalle de caja");
			setInvalidBean();
		}
	}
	
	// total en soles
	public Moneda getTotalHistorialCajaSoles() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleSoles.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// total en dolares
	public Moneda getTotalHistorialCajaDolares() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleDolares.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// total en en euros
	public Moneda getTotalHistorialCajaEuros() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleEuros.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	/*
	public Moneda getTotalHistorialbovedaLast() {
		Moneda result = new Moneda();
		for (Detallehistorialboveda e : tablaBovedaDetalleLastNoActive.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}
*/
	public String openCaja() throws Exception {
		Caja caja = this.caja;
		try {
			Estadoapertura estadoapertura = ProduceObject
					.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {
				this.cajaServiceLocal.openCaja(caja);
				JsfUtil.addSuccessMessage("Caja Abierta");
			} else {
				JsfUtil.addErrorMessage("Caja Abierta, no se puede Reabrir");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al abrir Caja");
			setInvalidBean();
			return "failure";
		}
		return "success";
	}
	
	// verifica si la caja se relaciona con la boveda soles
		public boolean caja_Boveda_Nuevo_Sol() {
			boolean result = false;
			if (caja.getIdcaja() != null) {
				Tipomoneda tipomoneda = ProduceObject
						.getTipomoneda(TipomonedaType.NUEVO_SOL);
				for (Iterator<Boveda> iterator = caja.getBovedas().iterator(); iterator
						.hasNext();) {
					Boveda boveda = (Boveda) iterator.next();
					if (boveda.getTipomoneda().equals(tipomoneda)) {
						result = true;
					}
				}
			}
			return result;
		}

		// verifica si la caja se relaciona con la boveda dolar
		public boolean caja_Boveda_Dolar() {
			boolean result = false;
			if (caja.getIdcaja() != null) {
				Tipomoneda tipomoneda = ProduceObject
						.getTipomoneda(TipomonedaType.DOLAR);
				for (Iterator<Boveda> iterator = caja.getBovedas().iterator(); iterator
						.hasNext();) {
					Boveda boveda = (Boveda) iterator.next();
					if (boveda.getTipomoneda().equals(tipomoneda)) {
						result = true;
					}
				}
			}
			return result;
		}

		// verifica si la caja se relaciona con la boveda euros
		public boolean caja_Boveda_Euro() {
			boolean result = false;
			if (caja.getIdcaja() != null) {
				Tipomoneda tipomoneda = ProduceObject
						.getTipomoneda(TipomonedaType.EURO);
				for (Iterator<Boveda> iterator = caja.getBovedas().iterator(); iterator
						.hasNext();) {
					Boveda boveda = (Boveda) iterator.next();
					if (boveda.getTipomoneda().equals(tipomoneda)) {
						result = true;
					}
				}
			}
			return result;
		}
/*	
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
*/
	public void setInvalidBean(){
		this.isValidBean = false;
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

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Integer getIdcaja() {
		return idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}
	/*
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
	}*/

	public boolean isValidBean() {
		return isValidBean;
	}

	public void setValidBean(boolean isValidBean) {
		this.isValidBean = isValidBean;
	}
	
	public TablaBean<Caja> getTablaCaja() {
		return tablaCaja;
	}

	public void setTablaCaja(TablaBean<Caja> tablaCaja) {
		this.tablaCaja = tablaCaja;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleSoles() {
		return tablaCajaDetalleSoles;
	}

	public void setTablaCajaDetalleSoles(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleSoles) {
		this.tablaCajaDetalleSoles = tablaCajaDetalleSoles;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleDolares() {
		return tablaCajaDetalleDolares;
	}

	public void setTablaCajaDetalleDolares(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleDolares) {
		this.tablaCajaDetalleDolares = tablaCajaDetalleDolares;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleEuros() {
		return tablaCajaDetalleEuros;
	}

	public void setTablaCajaDetalleEuros(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleEuros) {
		this.tablaCajaDetalleEuros = tablaCajaDetalleEuros;
	}
}
