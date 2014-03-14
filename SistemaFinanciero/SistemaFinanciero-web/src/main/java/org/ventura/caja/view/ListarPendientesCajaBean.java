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
import org.ventura.entity.schema.caja.PendienteCaja;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ListarPendientesCajaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject private TablaBean<PendienteCaja> tablaPendienteCaja;
	@Inject AgenciaBean agenciaBean;
	@Inject Agencia agencia;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	@PostConstruct
	public void initialize() throws Exception{
		try {
			agencia = agenciaBean.getAgencia();
			
			List<PendienteCaja> list = cajaServiceLocal.getPendientesCaja(agencia);
			tablaPendienteCaja.setRows(list);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
			throw e;
		}
	}
	
	public String getStringTime(Date date) {
	    SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
	    return ft.format(date);
	}

	public TablaBean<PendienteCaja> getTablaPendienteCaja() {
		return tablaPendienteCaja;
	}

	public void setTablaPendienteCaja(TablaBean<PendienteCaja> tablaPendienteCaja) {
		this.tablaPendienteCaja = tablaPendienteCaja;
	}
}
