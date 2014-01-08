package org.ventura.sistema.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.control.TasainteresServiceBean;
import org.ventura.entity.schema.caja.TasaInteresTipoCambio;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.tasas.Tasainteres;

@Named
@ViewScoped
public class ActualizarTasasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TasainteresServiceLocal tasaInteresServiceLocal;

	
	public ActualizarTasasBean() {
	}

	@PostConstruct
	private void initialize() {
		cargarTiposDeCambio();
	}

	public void cargarTiposDeCambio() {
		cargarTipoCambioCompra();
		CargarTipoCambioVenta();
	}

	public void cargarTipoCambioCompra(){
		cargarTipoCambioCompraDolaresSoles();
		cargarTipoCambio
		
		try {
			TasaInteresTipoCambio tipocambio;
			tipocambio = tasaInteresServiceLocal.retornarTipoCambio(Tasainteres.TASA_INTERES_BY_CV, tipotransaccioncompraventa,
					tipomonedaRecibido, tipomonedaEntregado);
			setTipoCambio(tipocambio);

		} catch (Exception e) {}
	}

	public void CargarTipoCambioVenta() {
		
	}
}
