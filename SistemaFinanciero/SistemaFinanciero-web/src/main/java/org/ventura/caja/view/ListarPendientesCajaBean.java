package org.ventura.caja.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.view.PendientesView;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ListarPendientesCajaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject private TablaBean<PendientesView> tablaPendienteCaja;
	@Inject AgenciaBean agenciaBean;
	@Inject Agencia agencia;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	
	//Para imprimir
	private Integer idPendienteCaja;
	private PendientesView pendienteCajaView;
	private boolean faliure;
	
	public ListarPendientesCajaBean(){
		faliure = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{
		try {
			agencia = agenciaBean.getAgencia();
			List<PendientesView> list = cajaServiceLocal.getPendientesCaja(agencia);
			tablaPendienteCaja.setRows(list);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	public void loadPendienteCajaForPrint(){
		try {
			if (idPendienteCaja != null && idPendienteCaja != -1) {
				pendienteCajaView = cajaServiceLocal.finPendienteCaja(idPendienteCaja);
			} else {
				faliure = true;
				JsfUtil.addErrorMessage("No se encontraron datos de la pendiente");
			}
		} catch (Exception e) {
			faliure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public TablaBean<PendientesView> getTablaPendienteCaja() {
		return tablaPendienteCaja;
	}

	public void setTablaPendienteCaja(TablaBean<PendientesView> tablaPendienteCaja) {
		this.tablaPendienteCaja = tablaPendienteCaja;
	}

	public Integer getIdPendienteCaja() {
		return idPendienteCaja;
	}

	public void setIdPendienteCaja(Integer idPendienteCaja) {
		this.idPendienteCaja = idPendienteCaja;
	}

	public PendientesView getPendienteCajaView() {
		return pendienteCajaView;
	}

	public void setPendienteCajaView(PendientesView pendienteCajaView) {
		this.pendienteCajaView = pendienteCajaView;
	}

	public boolean isFaliure() {
		return faliure;
	}

	public void setFaliure(boolean faliure) {
		this.faliure = faliure;
	}
}
