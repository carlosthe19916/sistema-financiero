package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.ListSelectedBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.session.AgenciaBean;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.venturabank.util.JsfUtil;


@Named
@ViewScoped
public class AdministrarCajaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	@Inject
	private AgenciaBean agenciaBean;

	@Inject
	private TablaBean<Caja> tablaCaja;
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleSoles;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleDolares;
	@Inject
	private TablaBean<Detallehistorialcaja> tablaCajaDetalleEuros;

	@Inject
	private Caja caja;
		
	@Inject
	private ListSelectedBean<Boveda> listSelectedBean;
	
	@PostConstruct
	private void initialize() {
		this.refreshTablaCaja();	
		this.initializeListSelectedBean();
	}
	
	private void initializeListSelectedBean() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		listSelectedBean.initValuesFromNamedQueryName(Boveda.ALL_ACTIVE_BY_AGENCIA,parameters);		
	}

	public void createCaja(){
		try {
			String denominacionCaja = caja.getDenominacion();
			String abreviaturaCaja = caja.getAbreviatura();
			List<Boveda> listBovedas = listSelectedBean.getTarget();
			
			if (denominacionCaja == null || denominacionCaja.isEmpty() || denominacionCaja.trim().isEmpty()) {
				throw new Exception("Denominación de Caja Inválida");
			}
			if (abreviaturaCaja == null || abreviaturaCaja.isEmpty() || abreviaturaCaja.trim().isEmpty()) {
				throw new Exception("Abreviatura de Caja Inválida");
			}
			if (listBovedas==null) {
				throw new Exception("Error: No se selecciono ninguna boveda");
			}
			preCreateCaja(listBovedas);
			
			this.cajaServiceLocal.create(this.caja);
			refreshBean();
			System.out.println("hola"+listBovedas.size());
			
			FacesMessage message = new FacesMessage("Info", "Caja Creada satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
	public void preCreateCaja(List<Boveda> listBovedas){		
		caja.setBovedas(listBovedas);		
	}
	
	public void refreshTablaCaja() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agenciaBean.getAgencia().getIdagencia());
		tablaCaja.initValuesFromNamedQueryName(Caja.ALL_ACTIVE_BY_AGENCIA,parameters);
	}
	
	public void updateCaja(){
		try {
			String denominacionCaja = caja.getDenominacion();
			String abreviaturaCaja = caja.getAbreviatura();
			
			if (denominacionCaja == null || denominacionCaja.isEmpty() || denominacionCaja.trim().isEmpty()) {
				throw new Exception("Denominación de Caja Inválida");
			}
			if (abreviaturaCaja == null || abreviaturaCaja.isEmpty() || abreviaturaCaja.trim().isEmpty()) {
				throw new Exception("Abreviatura de Caja Inválida");
			}
			
			this.cajaServiceLocal.update(this.caja);
			refreshBean();
			
			FacesMessage message = new FacesMessage("Info", "Caja Actualizada satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
	public void deleteCaja(){
		try {
			loadCaja();

			caja.setEstado(false);
			cajaServiceLocal.update(caja);
			refreshBean();

			FacesMessage message = new FacesMessage("Info", "Caja eliminada correctamente");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
		
	public void loadCaja() throws Exception {
		try {
			Object object = tablaCaja.getEditingRow();
			Caja caja = new Caja();
			if (object instanceof Caja) {
				caja = (Caja) object;
				this.caja = cajaServiceLocal.find(caja.getIdcaja());
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void refreshBean() {
		this.refreshTablaCaja();		
		this.cleanCaja();
	}
	
	public void cleanCaja() {
		this.caja = new Caja();
	}

	public AgenciaBean getAgenciaBean() {
		return agenciaBean;
	}

	public void setAgenciaBean(AgenciaBean agenciaBean) {
		this.agenciaBean = agenciaBean;
	}

	public TablaBean<Caja> getTablaCaja() {
		return tablaCaja;
	}

	public void setTablaCaja(TablaBean<Caja> tablaCaja) {
		this.tablaCaja = tablaCaja;
	}

	

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public ListSelectedBean<Boveda> getListSelectedBean() {
		return listSelectedBean;
	}

	public void setListSelectedBean(ListSelectedBean<Boveda> listSelectedBean) {
		this.listSelectedBean = listSelectedBean;
	}
	
	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleSoles() {
		return tablaCajaDetalleSoles;
	}

	public void setTablaCajaDetalleSoles(TablaBean<Detallehistorialcaja> tablaCajaDetalleSoles) {
		this.tablaCajaDetalleSoles = tablaCajaDetalleSoles;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleDolares() {
		return tablaCajaDetalleDolares;
	}

	public void setTablaCajaDetalleDolares(TablaBean<Detallehistorialcaja> tablaCajaDetalleDolares) {
		this.tablaCajaDetalleDolares = tablaCajaDetalleDolares;
	}

	public TablaBean<Detallehistorialcaja> getTablaCajaDetalleEuros() {
		return tablaCajaDetalleEuros;
	}

	public void setTablaCajaDetalleEuros(TablaBean<Detallehistorialcaja> tablaCajaDetalleEuros) {
		this.tablaCajaDetalleEuros = tablaCajaDetalleEuros;
	}

	
	public void openCaja() throws Exception {
		try {
			cajaServiceLocal.openCaja(caja);
			refreshBean();
			FacesMessage message = new FacesMessage("Info", "Caja Abierta Satisfactoriamante");  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());  	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}
	
	public void loadTablaCajaDetalleAfterOpen() throws Exception {
		try {
			loadCaja();
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
				throw new Exception("Caja Abierta imposible abrirla nuevamente");
			}
		} catch (Exception e) {
			throw e;
		}
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
}
