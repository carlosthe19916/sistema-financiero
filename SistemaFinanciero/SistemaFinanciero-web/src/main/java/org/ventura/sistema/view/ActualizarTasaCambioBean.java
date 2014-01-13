package org.ventura.sistema.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.TipocambioServiceLocal;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tipocambio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.ProduceObjectTipocambio;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class ActualizarTasaCambioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TipocambioServiceLocal tipocambioServiceLocal;
	
	@Inject
	private Tipocambio tipoCambioCompraDolarSol;
	@Inject
	private Tipocambio tipoCambioCompraEuroSol;
	@Inject
	private Tipocambio tipoCambioCompraDolarEuro;
	@Inject
	private Tipocambio tipoCambioCompraEuroDolar;
	@Inject
	private Tipocambio tipoCambioVentaDolarSol;
	@Inject
	private Tipocambio tipoCambioVentaEuroSol;
	@Inject
	private Tipocambio tipoCambioVentaDolarEuro;
	@Inject
	private Tipocambio tipoCambioVentaEuroDolar;
	
	private boolean isValidBean;
	
	public ActualizarTasaCambioBean() {
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
	
	public void cargarTipoCambioCompra(){
		cargarTipoCambioCompraDolaresSoles();
		cargarTipoCambioCompraDolaresEuros();
		cargarTipoCambioCompraEurosSoles();
		cargarTipoCambioCompraEurosDolares();
	}
	
	public void cargarTipoCambioVenta() {
		cargarTipoCambioVentaDolaresSoles();
		cargarTipoCambioVentaDolaresEuros();
		cargarTipoCambioVentaEurosSoles();
		cargarTipoCambioVentaEurosDolares();
	}

	//tipos de cambio para la compra de monedas
	public void cargarTipoCambioCompraDolaresSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaDolares, tipoMonedaSoles);
			tipoCambioCompraDolarSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por soles");
		}
	}
	
	public void cargarTipoCambioCompraDolaresEuros() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaDolares, tipoMonedaEuros);
			tipoCambioCompraDolarEuro.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por euros");
		}
	}
	
	private void cargarTipoCambioCompraEurosSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaEuros, tipoMonedaSoles);
			tipoCambioCompraEuroSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de euros por soles");
		}
	}
	
	private void cargarTipoCambioCompraEurosDolares() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaEuros, tipoMonedaDolares);
			tipoCambioCompraEuroDolar.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de euros por dolares");
		}
	}
	
	//tipos de cambio para la venta de monedas
	private void cargarTipoCambioVentaDolaresSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaSoles, tipoMonedaDolares);
			tipoCambioVentaDolarSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por soles");
		}
	}
	
	private void cargarTipoCambioVentaDolaresEuros() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaDolares, tipoMonedaEuros);
			tipoCambioVentaDolarEuro.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por euros");
		}
	}
	
	private void cargarTipoCambioVentaEurosSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaSoles, tipoMonedaEuros);
			tipoCambioVentaEuroSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por soles");
		}
	}
	
	private void cargarTipoCambioVentaEurosDolares() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaEuros = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaDolares = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaEuros, tipoMonedaDolares);
			tipoCambioVentaEuroDolar.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por dolares");
		}
	}
	
	public void configurarTasasDeCambio(){
		configurarTasaCambioCompraDolarSOl();
	}

	public void configurarTasaCambioCompraDolarSOl() {
		
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
	
	public Tipocambio getTipoCambioCompraDolarSol() {
		return tipoCambioCompraDolarSol;
	}

	public void setTipoCambioCompraDolarSol(Tipocambio tipoCambioCompraDolarSol) {
		this.tipoCambioCompraDolarSol = tipoCambioCompraDolarSol;
	}

	public Tipocambio getTipoCambioCompraEuroSol() {
		return tipoCambioCompraEuroSol;
	}

	public void setTipoCambioCompraEuroSol(Tipocambio tipoCambioCompraEuroSol) {
		this.tipoCambioCompraEuroSol = tipoCambioCompraEuroSol;
	}

	public Tipocambio getTipoCambioCompraDolarEuro() {
		return tipoCambioCompraDolarEuro;
	}

	public void setTipoCambioCompraDolarEuro(Tipocambio tipoCambioCompraDolarEuro) {
		this.tipoCambioCompraDolarEuro = tipoCambioCompraDolarEuro;
	}

	public Tipocambio getTipoCambioCompraEuroDolar() {
		return tipoCambioCompraEuroDolar;
	}

	public void setTipoCambioCompraEuroDolar(Tipocambio tipoCambioCompraEuroDolar) {
		this.tipoCambioCompraEuroDolar = tipoCambioCompraEuroDolar;
	}

	public Tipocambio getTipoCambioVentaDolarSol() {
		return tipoCambioVentaDolarSol;
	}

	public void setTipoCambioVentaDolarSol(Tipocambio tipoCambioVentaDolarSol) {
		this.tipoCambioVentaDolarSol = tipoCambioVentaDolarSol;
	}

	public Tipocambio getTipoCambioVentaEuroSol() {
		return tipoCambioVentaEuroSol;
	}

	public void setTipoCambioVentaEuroSol(Tipocambio tipoCambioVentaEuroSol) {
		this.tipoCambioVentaEuroSol = tipoCambioVentaEuroSol;
	}

	public Tipocambio getTipoCambioVentaDolarEuro() {
		return tipoCambioVentaDolarEuro;
	}

	public void setTipoCambioVentaDolarEuro(Tipocambio tipoCambioVentaDolarEuro) {
		this.tipoCambioVentaDolarEuro = tipoCambioVentaDolarEuro;
	}

	public Tipocambio getTipoCambioVentaEuroDolar() {
		return tipoCambioVentaEuroDolar;
	}

	public void setTipoCambioVentaEuroDolar(Tipocambio tipoCambioVentaEuroDolar) {
		this.tipoCambioVentaEuroDolar = tipoCambioVentaEuroDolar;
	}
}
