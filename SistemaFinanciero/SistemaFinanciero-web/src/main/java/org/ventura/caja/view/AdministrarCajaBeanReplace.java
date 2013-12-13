package org.ventura.caja.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.view.CajaView;
import org.ventura.entity.schema.sucursal.Agencia;
import org.venturabank.managedbean.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarCajaBeanReplace implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CajaServiceLocal cajaServiceLocal;

	@Inject
	private AgenciaBean agenciaBean;
	@Inject
	private Agencia agencia;

	@Inject
	private TablaBean<CajaView> tablaCaja;

	private Integer idcaja;

	public AdministrarCajaBeanReplace() {
		idcaja = new Integer(-1);
	}

	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
		this.refreshTablaCaja();
	}

	public void deleteCaja() throws Exception {
		try {
			//loadBoveda();
			Caja caja =  new Caja();
			caja.setIdcaja(idcaja);
			//cajaServiceLocal.inactive(caja);
			refreshBean();

			JsfUtil.addSuccessMessage("Caja Desactivada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JsfUtil.addErrorMessage(e, "Error al Inactivar Caja");
		}
	}
	
	public void activarMovimiento() throws Exception {
		try {
			Caja caja =  new Caja();
			caja.setIdcaja(idcaja);
		
			//bovedaServiceLocal.defrostBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Caja Descongelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Descongelar Caja");
		}
	}

	public void desactivarMovimiento() throws Exception {
		try {
			Caja caja =  new Caja();
			caja.setIdcaja(idcaja);
			
			//bovedaServiceLocal.freezeBoveda(boveda);
			refreshBean();
			JsfUtil.addSuccessMessage("Caja Congelada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al Congelar Caja");
		}
	}

	public void setRowSelect() {
		CajaView cajaView;
		Object object = tablaCaja.getSelectedRow();
		if (object instanceof CajaView) {
			cajaView = (CajaView) object;
			this.idcaja = cajaView.getIdCaja();
		} else {
			System.out.println("La seleccion no fue valida");
		}
	}

	public void refreshBean() {
		this.refreshTablaCaja();
		this.cleanCaja();
	}

	public void refreshTablaCaja() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agencia.getIdagencia());
		tablaCaja.initValuesFromNamedQueryName(CajaView.ALL_ACTIVE_BY_AGENCIA, parameters);
	}

	public void cleanCaja() {
		this.idcaja = new Integer(-1);
	}

	public TablaBean<CajaView> getTablaCaja() {
		return tablaCaja;
	}

	public void setTablaCaja(TablaBean<CajaView> tablaCaja) {
		this.tablaCaja = tablaCaja;
	}

	public Integer getIdcaja() {
		return idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}

}
