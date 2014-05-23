package org.ventura.sistema.view;

import java.io.Serializable;
import java.util.Calendar;

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
	private boolean validUpdateAndCreate;
	
	public ActualizarTasaCambioBean() {
		setValidBean(true);
		setValidUpdateAndCreate(true);
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
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioCompraDolarSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por soles");
		}
	}
	
	private void cargarTipoCambioCompraEurosSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioCompraEuroSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de euros por soles");
		}
	}
	
	public void cargarTipoCambioCompraDolaresEuros() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioCompraDolarEuro.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la compra de dolares por euros");
		}
	}
	
	private void cargarTipoCambioCompraEurosDolares() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
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
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioVentaDolarSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por soles");
		}
	}
	
	private void cargarTipoCambioVentaEurosSoles() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioVentaEuroSol.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por soles");
		}
	}
	
	private void cargarTipoCambioVentaDolaresEuros() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioVentaDolarEuro.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de dolares por euros");
		}
	}
	
	private void cargarTipoCambioVentaEurosDolares() {
		Moneda monto = new Moneda(); 
		TasaCambio tipoCambio = null;
		
		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibida = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregada = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			tipoCambio = tipocambioServiceLocal.getTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibida, tipoMonedaEntregada);
			tipoCambioVentaEuroDolar.setTipocambio(tipoCambio);
		} catch (Exception e) {
			setBeanInvalid();
			JsfUtil.addErrorMessage(e, "Error al obtener el tipo de cambio para la venta de euros por dolares");
		}
	}
	
	public void actualizarTasasDeCambio(){
		actualizarTasaCambioCompraDolarSol();
		actualizarTasaCambioCompraEuroSol();
		actualizarTasaCambioCompraDolarEuro();
		actualizarTasaCambioCompraEuroDolar();
		actualizarTasaCambioVentaDolarSol();
		actualizarTasaCambioVentaEuroSol();
		actualizarTasaCambioVentaDolarEuro();
		actualizarTasaCambioVentaEuroDolar();
	}
	
	//actulizando y creando tipos de cambio compra
	public void actualizarTasaCambioCompraDolarSol() {
		Moneda monto = new Moneda(); 

		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregado = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			Tipocambio tipocambio = tipocambioServiceLocal.retornarObjetoTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);
			
			createTipoCambio(tipocambio, tipoCambioCompraDolarSol.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e, "Error al actualizar la tasa de cambio compra $ x S/.");
		}
	}
	
	public void actualizarTasaCambioCompraEuroSol() {
		Moneda monto = new Moneda(); 

		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregado = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		
		try {
			Tipocambio tipocambio = tipocambioServiceLocal.retornarObjetoTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);
			
			createTipoCambio(tipocambio, tipoCambioCompraEuroSol.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e, "Error al actualizar la tasa de cambio compra € x S/.");
		}
	}
	
	public void actualizarTasaCambioCompraDolarEuro() {
		Moneda monto = new Moneda(); 

		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregado = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		
		try {
			Tipocambio tipocambio = tipocambioServiceLocal.retornarObjetoTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);
			
			createTipoCambio(tipocambio, tipoCambioCompraDolarEuro.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e, "Error al actualizar la tasa de cambio compra $ x €");
		}
	}
	
	public void actualizarTasaCambioCompraEuroDolar() {
		Moneda monto = new Moneda(); 

		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.COMPRA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregado = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);
		
		try {
			Tipocambio tipocambio = tipocambioServiceLocal.retornarObjetoTipoCambioCompraVenta(tipotasa, monto, tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);
			
			createTipoCambio(tipocambio, tipoCambioCompraEuroDolar.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e, "Error al actualizar la tasa de cambio compra € x $");
		}
	}
	
	// actulizando y creando tipos de cambio venta
	public void actualizarTasaCambioVentaDolarSol() {
		Moneda monto = new Moneda();

		Tipotasa tipotasa = ProduceObjectTipocambio
				.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject
				.getTipomoneda(TipomonedaType.NUEVO_SOL);
		Tipomoneda tipoMonedaEntregado = ProduceObject
				.getTipomoneda(TipomonedaType.DOLAR);

		try {
			Tipocambio tipocambio = tipocambioServiceLocal
					.retornarObjetoTipoCambioCompraVenta(tipotasa, monto,
							tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);

			createTipoCambio(tipocambio,
					tipoCambioVentaDolarSol.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e,
					"Error al actualizar la tasa de cambio venta $ x S/.");
		}
	}
		
	public void actualizarTasaCambioVentaEuroSol() {
		Moneda monto = new Moneda();

		Tipotasa tipotasa = ProduceObjectTipocambio
				.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject
				.getTipomoneda(TipomonedaType.NUEVO_SOL);
		Tipomoneda tipoMonedaEntregado = ProduceObject
				.getTipomoneda(TipomonedaType.EURO);

		try {
			Tipocambio tipocambio = tipocambioServiceLocal
					.retornarObjetoTipoCambioCompraVenta(tipotasa, monto,
							tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);

			createTipoCambio(tipocambio, tipoCambioVentaEuroSol.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e,
					"Error al actualizar la tasa de cambio venta € x S/.");
		}
	}
		
	public void actualizarTasaCambioVentaDolarEuro() {
		Moneda monto = new Moneda();

		Tipotasa tipotasa = ProduceObjectTipocambio.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject.getTipomoneda(TipomonedaType.EURO);
		Tipomoneda tipoMonedaEntregado = ProduceObject.getTipomoneda(TipomonedaType.DOLAR);

		try {
			Tipocambio tipocambio = tipocambioServiceLocal
					.retornarObjetoTipoCambioCompraVenta(tipotasa, monto,
							tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);

			createTipoCambio(tipocambio,
					tipoCambioVentaDolarEuro.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e,
					"Error al actualizar la tasa de cambio venta $ x €");
		}
	}
		
	public void actualizarTasaCambioVentaEuroDolar() {
		Moneda monto = new Moneda();

		Tipotasa tipotasa = ProduceObjectTipocambio
				.getTipoCambioCompraVenta(TipoCambioCompraVentaType.VENTA_MONEDA);
		Tipomoneda tipoMonedaRecibido = ProduceObject
				.getTipomoneda(TipomonedaType.DOLAR);
		Tipomoneda tipoMonedaEntregado = ProduceObject
				.getTipomoneda(TipomonedaType.EURO);

		try {
			Tipocambio tipocambio = tipocambioServiceLocal
					.retornarObjetoTipoCambioCompraVenta(tipotasa, monto,
							tipoMonedaRecibido, tipoMonedaEntregado);
			tipocambio.setEstado(false);
			tipocambio.setFechafin(Calendar.getInstance().getTime());
			updateTipoCambio(tipocambio);

			createTipoCambio(tipocambio,
					tipoCambioVentaEuroDolar.getTipocambio());
		} catch (Exception e) {
			setValidUpdateCreate();
			JsfUtil.addErrorMessage(e,
					"Error al actualizar la tasa de cambio compra € x $");
		}
	}
	
	public void updateTipoCambio(Tipocambio tipocambio) {
		try {
			tipocambioServiceLocal.update(tipocambio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTipoCambio(Tipocambio tipocambio, TasaCambio tasacambio) {
		Tipocambio tipocambioNew = new Tipocambio();
		tipocambioNew.setTiposervicio(tipocambio.getTiposervicio());
		tipocambioNew.setTipotasa(tipocambio.getTipotasa());
		tipocambioNew.setTipocambio(tasacambio);
		tipocambioNew.setEstado(true);
		tipocambioNew.setFechainicio(Calendar.getInstance().getTime());
		tipocambioNew.setMontominimo(tipocambio.getMontominimo());
		tipocambioNew.setMontomaximo(tipocambio.getMontomaximo());
		tipocambioNew.setTipomonedarecibida(tipocambio.getTipomonedarecibida());
		tipocambioNew.setTipomonedaentregado(tipocambio.getTipomonedaentregado());
		
		try {
			tipocambioServiceLocal.create(tipocambioNew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//calcula las tasas de dolar a euro y viceversa en compra
	public void calculateTasaCambioCompra(){
	 	TasaCambio compraDolarSol = tipoCambioCompraDolarSol.getTipocambio();
		TasaCambio ventaEuroSol = tipoCambioVentaEuroSol.getTipocambio();
		
		tipoCambioCompraDolarEuro.setTipocambio(compraDolarSol.divide(ventaEuroSol));
		tipoCambioVentaEuroDolar.setTipocambio(compraDolarSol.divide(ventaEuroSol));
	}
	
	//calcula las tasas de dolar a euro y viceversa en venta
	public void calculateTasaCambioVenta(){
		TasaCambio ventaDolarSol = tipoCambioVentaDolarSol.getTipocambio();
		TasaCambio compraEuroSol = tipoCambioCompraEuroSol.getTipocambio();
		
		tipoCambioCompraEuroDolar.setTipocambio(compraEuroSol.divide(ventaDolarSol));
	 	tipoCambioVentaDolarEuro.setTipocambio(compraEuroSol.divide(ventaDolarSol));
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

	public boolean isValidUpdateAndCreate() {
		return validUpdateAndCreate;
	}

	public void setValidUpdateAndCreate(boolean validUpdateAndCreate) {
		this.validUpdateAndCreate = validUpdateAndCreate;
	}
	
	public void setValidUpdateCreate() {
		this.validUpdateAndCreate = false;
	}
}
