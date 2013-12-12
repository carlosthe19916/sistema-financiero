package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.venturabank.managedbean.session.AgenciaBean;


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
	
		
	public void loadCaja() throws Exception{
		try {
			Object object = tablaCaja.getEditingRow();
			Caja caja = new Caja();
			if (object instanceof Caja) {
				caja = (Caja) object;
			}
			this.caja = cajaServiceLocal.find(caja.getIdcaja());
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
	
	public void imprimir(){
		Object objetc = tablaCaja.getSelectedRow();
		Caja caja = new Caja();
		if (objetc instanceof Caja) {
			caja = (Caja) objetc;
		}
		
		System.out.println(caja.getDenominacion());
	}
}
