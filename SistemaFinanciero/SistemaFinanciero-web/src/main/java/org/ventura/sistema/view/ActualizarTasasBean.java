package org.ventura.sistema.view;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.entity.tasas.Tasainteres;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.maestro.ProduceObjectTasainteres;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ActualizarTasasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TasainteresServiceLocal tasaInteresServiceLocal;
	
	@Inject
	private Tasainteres tipoCambioCompraDolarSol;
	@Inject
	private Tasainteres tipoCambioCompraEuroSol;
	@Inject
	private Tasainteres tipoCambioCompraDolarEuro;
	@Inject
	private Tasainteres tipoCambioCompraEuroDolar;
	@Inject
	private Tasainteres tipoCambioVentaDolarSol;
	@Inject
	private Tasainteres tipoCambioVentaEuroSol;
	@Inject
	private Tasainteres tipoCambioVentaDolarEuro;
	@Inject
	private Tasainteres tipoCambioVentaEuroDolar;
	
	private boolean isValidBean;
	
	public ActualizarTasasBean() {
		setValidBean(true);
	}

	@PostConstruct
	private void initialize() {
		cargarTiposDeCambio();
	}

	public void cargarTiposDeCambio() {
		cargarTipoCambioCompra();
		cargarTipoCambioVenta();
	}
	
	public void cargarTipoCambioVenta() {
		cargarTipoCambioVentaDolaresSoles();
		cargarTipoCambioVentaDolaresEuros();
		cargarTipoCambioVentaEurosSoles();
		cargarTipoCambioVentaEurosDolares();
	}

	public void cargarTipoCambioCompra(){
		cargarTipoCambioCompraDolaresSoles();
		cargarTipoCambioCompraDolaresEuros();
		cargarTipoCambioCompraEurosSoles();
		cargarTipoCambioCompraEurosDolares();
	}

	//tipos de cambio para la compra de monedas
	public void cargarTipoCambioCompraDolaresSoles() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_DOLAR_CON_SOL);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioCompraDolarSol.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por soles");
		}
	}
	
	public void cargarTipoCambioCompraDolaresEuros() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_DOLAR_CON_EURO);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioCompraDolarEuro.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por euros");
		}
	}
	
	private void cargarTipoCambioCompraEurosSoles() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_EURO_CON_SOL);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioCompraEuroSol.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de euros por soles");
		}
	}
	
	private void cargarTipoCambioCompraEurosDolares() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_EURO_CON_DOLAR);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioCompraEuroDolar.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de euros por dolares");
		}
	}
	
	
	//tipos de cambio para la venta de monedas
	private void cargarTipoCambioVentaDolaresSoles() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_DOLAR_CON_SOL);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioVentaDolarSol.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por soles");
		}
	}
	
	private void cargarTipoCambioVentaDolaresEuros() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_DOLAR_CON_EURO);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioVentaDolarEuro.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por euros");
		}
	}
	
	private void cargarTipoCambioVentaEurosSoles() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_EURO_CON_SOL);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioVentaEuroSol.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por soles");
		}
	}
	
	private void cargarTipoCambioVentaEurosDolares() {
		BigDecimal monto = BigDecimal.ZERO; 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_EURO_CON_DOLAR);
		TipoCambioCompraVentaType compraVentaType = ProduceObjectTasainteres.getTipoCambioCompraVenta(tipotasa);
		try {
		tipoCambio = tasaInteresServiceLocal.getTipoCambioCompraVenta(compraVentaType, monto);
		tipoCambioVentaEuroDolar.setTasa(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por dolares");
		}
	}

	public boolean isValidBean() {
		return isValidBean;
	}

	public void setValidBean(boolean isValidBean) {
		this.isValidBean = isValidBean;
	}
	
	public void setBeanInvalid() {
		this.isValidBean = false;
	}
	
	public Tasainteres getTipoCambioCompraDolarSol() {
		return tipoCambioCompraDolarSol;
	}

	public void setTipoCambioCompraDolarSol(Tasainteres tipoCambioCompraDolarSol) {
		this.tipoCambioCompraDolarSol = tipoCambioCompraDolarSol;
	}

	public Tasainteres getTipoCambioCompraEuroSol() {
		return tipoCambioCompraEuroSol;
	}

	public void setTipoCambioCompraEuroSol(Tasainteres tipoCambioCompraEuroSol) {
		this.tipoCambioCompraEuroSol = tipoCambioCompraEuroSol;
	}

	public Tasainteres getTipoCambioCompraDolarEuro() {
		return tipoCambioCompraDolarEuro;
	}

	public void setTipoCambioCompraDolarEuro(Tasainteres tipoCambioCompraDolarEuro) {
		this.tipoCambioCompraDolarEuro = tipoCambioCompraDolarEuro;
	}

	public Tasainteres getTipoCambioCompraEuroDolar() {
		return tipoCambioCompraEuroDolar;
	}

	public void setTipoCambioCompraEuroDolar(Tasainteres tipoCambioCompraEuroDolar) {
		this.tipoCambioCompraEuroDolar = tipoCambioCompraEuroDolar;
	}

	public Tasainteres getTipoCambioVentaDolarSol() {
		return tipoCambioVentaDolarSol;
	}

	public void setTipoCambioVentaDolarSol(Tasainteres tipoCambioVentaDolarSol) {
		this.tipoCambioVentaDolarSol = tipoCambioVentaDolarSol;
	}

	public Tasainteres getTipoCambioVentaEuroSol() {
		return tipoCambioVentaEuroSol;
	}

	public void setTipoCambioVentaEuroSol(Tasainteres tipoCambioVentaEuroSol) {
		this.tipoCambioVentaEuroSol = tipoCambioVentaEuroSol;
	}

	public Tasainteres getTipoCambioVentaDolarEuro() {
		return tipoCambioVentaDolarEuro;
	}

	public void setTipoCambioVentaDolarEuro(Tasainteres tipoCambioVentaDolarEuro) {
		this.tipoCambioVentaDolarEuro = tipoCambioVentaDolarEuro;
	}

	public Tasainteres getTipoCambioVentaEuroDolar() {
		return tipoCambioVentaEuroDolar;
	}

	public void setTipoCambioVentaEuroDolar(Tasainteres tipoCambioVentaEuroDolar) {
		this.tipoCambioVentaEuroDolar = tipoCambioVentaEuroDolar;
	}
}
