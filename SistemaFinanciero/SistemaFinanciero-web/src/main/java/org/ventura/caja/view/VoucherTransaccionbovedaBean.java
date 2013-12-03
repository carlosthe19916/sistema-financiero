package org.ventura.caja.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.entity.schema.caja.Transaccionboveda;

@Named
@ViewScoped
public class VoucherTransaccionbovedaBean implements Serializable {


	private static final long serialVersionUID = 1L;

	@EJB
	private BovedaServiceLocal bovedaServiceLocal;

	private Integer idtransaccionboveda;
	private Transaccionboveda transaccionboveda;

	public void loadTransaccion() {
		try {
			bovedaServiceLocal.findTransaccionboveda(idtransaccionboveda);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Transaccionboveda getTransaccionboveda() {
		return transaccionboveda;
	}

	public void setTransaccionboveda(Transaccionboveda transaccionboveda) {
		this.transaccionboveda = transaccionboveda;
	}

	public Integer getIdtransaccionboveda() {
		return idtransaccionboveda;
	}

	public void setIdtransaccionboveda(Integer idtransaccionboveda) {
		this.idtransaccionboveda = idtransaccionboveda;
	}

}
