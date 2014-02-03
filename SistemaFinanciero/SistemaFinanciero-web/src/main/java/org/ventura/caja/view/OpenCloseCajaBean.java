package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class OpenCloseCajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private Agencia agencia;
	@Inject
	private TablaBean<Caja> tablaCaja;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleSoles;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleDolares;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleEuros;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleSolesLastNoActive;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleDolaresLastNoActive;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleEurosLastNoActive;
	
	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	private Caja caja;
	private Integer idcaja;
	private boolean isValidBean;
	private boolean validSaldoCajaSoles;
	private boolean validSaldoCajaDolares;
	private boolean validSaldoCajaEuros;
	private boolean pendiente;

	public OpenCloseCajaBean() {
		isValidBean = true;
		validSaldoCajaSoles = true;
		setValidSaldoCajaDolares(true);
		setValidSaldoCajaEuros(true);
		pendiente = false;
	}
	
	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
	}

	public void loadCajaForOpen() {
		try {
			if (idcaja != null && idcaja != -1) {
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
	
	public void loadCajaForClose() {
		try {
			if(idcaja != null && idcaja != -1){
				caja = cajaServiceLocal.find(idcaja);
				loadTablaCajaDetalleForClose();	
			} else {
				JsfUtil.addErrorMessage("Caja no encontrada");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar la Caja");
			setInvalidBean();
		}
	}

	public void loadTablaCajaDetalleForOpen() throws Exception {
		try {
			Tipomoneda tipomonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
			Tipomoneda tipomonedaDolar = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
			Tipomoneda tipomonedaEuro = ProduceObject.getTipomoneda(TipomonedaType.EURO);
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			
			if (estadoapertura.equals(estadoapertura2)) {
				HashMap<Tipomoneda, List<Detallehistorialcaja>> detalleaperturacierrecajaList = cajaServiceLocal.getDetallehistorialcajaLastActive(caja);
				
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
				JsfUtil.addErrorMessage("Caja Abierta, no se puede volver a abrir");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar el detalle de caja");
			setInvalidBean();
		}
	}
	
	public void loadTablaCajaDetalleForClose() throws Exception {
		try {
			Tipomoneda tipomonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
			Tipomoneda tipomonedaDolar = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
			Tipomoneda tipomonedaEuro = ProduceObject.getTipomoneda(TipomonedaType.EURO);
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			
			if (!estadoapertura.equals(estadoapertura2)) {
				HashMap<Tipomoneda, List<Detallehistorialcaja>> detallehistorialcajaInicial = cajaServiceLocal.getDetallehistorialcajaLastActive(caja);
				HashMap<Tipomoneda, List<Detallehistorialcaja>> detallehistorialcajaFinal = cajaServiceLocal.getDetallehistorialcajaInZero(caja);
				
				Set<Tipomoneda> a = detallehistorialcajaInicial.keySet();
				
				for (Tipomoneda tipomoneda : a) {
					if (tipomoneda.equals(tipomonedaSoles)) {
						tablaCajaDetalleSoles.setRows(detallehistorialcajaFinal.get(tipomoneda));
						tablaCajaDetalleSolesLastNoActive.setRows(detallehistorialcajaInicial.get(tipomoneda));
					}
					if (tipomoneda.equals(tipomonedaDolar)) {
						tablaCajaDetalleDolares.setRows(detallehistorialcajaFinal.get(tipomoneda));
						tablaCajaDetalleDolaresLastNoActive.setRows(detallehistorialcajaInicial.get(tipomoneda));
					}
					if (tipomoneda.equals(tipomonedaEuro)) {
						tablaCajaDetalleEuros.setRows(detallehistorialcajaFinal.get(tipomoneda));
						tablaCajaDetalleEurosLastNoActive.setRows(detallehistorialcajaInicial.get(tipomoneda));
					}
				}
			} else {
				JsfUtil.addErrorMessage("Caja Cerrada, no se puede volver a cerrar");
				setInvalidBean();
			}	
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "No se pudo cargar el detalle de caja");
			setInvalidBean();
		}
	}
	
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
		
	public String closeCaja() throws Exception {
		Caja caja = this.caja;
		List<Detallehistorialcaja> detalleSoles = tablaCajaDetalleSoles.getRows();
		List<Detallehistorialcaja> detalleDolares = tablaCajaDetalleDolares.getRows();
		List<Detallehistorialcaja> detalleEuros = tablaCajaDetalleEuros.getRows();
			
		try {
			Estadoapertura estadoapertura = ProduceObject
					.getEstadoapertura(EstadoAperturaType.ABIERTO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {
				this.cajaServiceLocal.closeCaja(caja, detalleSoles, detalleDolares, detalleEuros);
				
				//validar los saldos en caja y base de datos en soles
				if (cajaServiceLocal.compareSaldoTotalCajaSoles(caja).containsKey(-1)) {
					Map<Integer, Moneda> haspSoles = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaSoles(false);
					Moneda faltateSoles = haspSoles.get(-1);
					JsfUtil.addErrorMessage("Dinero Faltante de " + faltateSoles + " Nuevos Soles");
					
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaSoles(caja).containsKey(1)) {
					Map<Integer, Moneda> haspSoles = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaSoles(false);
					Moneda sobranteSoles = haspSoles.get(1);
					JsfUtil.addErrorMessage("Dinero Sobrante de " + sobranteSoles + " Nuevos Soles");
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaSoles(caja).containsKey(0)) {
					JsfUtil.addSuccessMessage("Caja de Nuevo Sol Cerrada correctamente");
				}
				
				//validar los saldos en caja y base de datos en Dolares
				if (cajaServiceLocal.compareSaldoTotalCajaDolares(caja).containsKey(-1)) {
					Map<Integer, Moneda> haspDolares = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaDolares(false);
					Moneda faltanteDolares = haspDolares.get(-1);
					System.out.println("Faltante Dolares "+faltanteDolares);
					JsfUtil.addErrorMessage("Dinero Faltante De " + faltanteDolares + " Dolares");
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaDolares(caja).containsKey(1)) {
					Map<Integer, Moneda> haspDolares = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaDolares(false);
					Moneda sobranteDolares = haspDolares.get(1);
					System.out.println("Sobrante Dolares "+sobranteDolares);
					JsfUtil.addErrorMessage("Dinero Sobrante de " + sobranteDolares + " Dolares");
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaDolares(caja).containsKey(0)) {
					JsfUtil.addSuccessMessage("Caja de Dolares Cerrada Correctamente");
				}
				
				//validar los saldos en caja y base de datos en euros
				if (cajaServiceLocal.compareSaldoTotalCajaEuros(caja).containsKey(-1)) {
					Map<Integer, Moneda> haspEuros = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaEuros(false);
					Moneda faltanteEuros = haspEuros.get(-1);
					JsfUtil.addErrorMessage("Dinero Faltante de " + faltanteEuros + " Euros");
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaEuros(caja).containsKey(1)) {
					Map<Integer, Moneda> haspEuros = cajaServiceLocal.compareSaldoTotalCajaSoles(caja);
					setValidSaldoCajaEuros(false);
					Moneda sobranteEuros = haspEuros.get(1);
					JsfUtil.addErrorMessage("Dinero Sobrante de " + sobranteEuros + " Euros");
					return null;
				}
				if (cajaServiceLocal.compareSaldoTotalCajaEuros(caja).containsKey(0)) {
					JsfUtil.addSuccessMessage("Caja de Euros Cerrada Correctamente");
				}
			} else {
				JsfUtil.addErrorMessage("Caja Cerrada, Imposible cerrar caja");
				setInvalidBean();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al cerrar Caja");
			setInvalidBean();
			return "failure";
		}
		return "success";
	}
	
	// total en soles final
	public Moneda totalHistorialCajaSoles() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleSoles.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// total en dolares final
	public Moneda totalHistorialCajaDolares() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleDolares.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// total en en euros final
	public Moneda totalHistorialCajaEuros() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleEuros.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}
	
	//total en soles inicial
	public Moneda getTotalHistorialcajaSolesLastNoActive() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleSolesLastNoActive.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}
	
	// total en dolares inicial
	public Moneda getTotalHistorialcajaDolaresLastNoActive() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleDolaresLastNoActive
				.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// total en dolares inicial
	public Moneda getTotalHistorialcajaEurosLastNoActive() {
		Moneda result = new Moneda();
		for (Detallehistorialcaja e : tablaCajaDetalleEurosLastNoActive
				.getRows()) {
			Moneda subtotal = e.getSubtotal();
			result = result.add(subtotal);
		}
		return result;
	}

	// verifica si la caja se relaciona con la boveda soles
	public boolean caja_Boveda_Nuevo_Sol() {
		boolean result = false;
		if (idcaja != null && idcaja != -1) {
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
		if (idcaja != null && idcaja != -1) {
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
		if (idcaja != null && idcaja != -1) {
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
	
	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleSolesLastNoActive() {
		return tablaCajaDetalleSolesLastNoActive;
	}

	public void setTablaCajaDetalleSolesLastNoActive(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleSolesLastNoActive) {
		this.tablaCajaDetalleSolesLastNoActive = tablaCajaDetalleSolesLastNoActive;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleDolaresLastNoActive() {
		return tablaCajaDetalleDolaresLastNoActive;
	}

	public void setTablaCajaDetalleDolaresLastNoActive(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleDolaresLastNoActive) {
		this.tablaCajaDetalleDolaresLastNoActive = tablaCajaDetalleDolaresLastNoActive;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleEurosLastNoActive() {
		return tablaCajaDetalleEurosLastNoActive;
	}

	public void setTablaCajaDetalleEurosLastNoActive(
			TablaBean<Detallehistorialcaja> tablaCajaDetalleEurosLastNoActive) {
		this.tablaCajaDetalleEurosLastNoActive = tablaCajaDetalleEurosLastNoActive;
	}

	public boolean isValidSaldoCajaSoles() {
		return validSaldoCajaSoles;
	}

	public void setValidSaldoCajaSoles(boolean validSaldoCajaSoles) {
		this.validSaldoCajaSoles = validSaldoCajaSoles;
	}

	public boolean isValidSaldoCajaDolares() {
		return validSaldoCajaDolares;
	}

	public void setValidSaldoCajaDolares(boolean validSaldoCajaDolares) {
		this.validSaldoCajaDolares = validSaldoCajaDolares;
	}

	public boolean isValidSaldoCajaEuros() {
		return validSaldoCajaEuros;
	}

	public void setValidSaldoCajaEuros(boolean validSaldoCajaEuros) {
		this.validSaldoCajaEuros = validSaldoCajaEuros;
	}

	public boolean isPendiente() {
		return pendiente;
	}

	public void setPendiente(boolean pendiente) {
		this.pendiente = pendiente;
	}	
	
	public void setPendiente(){
		this.pendiente = true;
	}
}
