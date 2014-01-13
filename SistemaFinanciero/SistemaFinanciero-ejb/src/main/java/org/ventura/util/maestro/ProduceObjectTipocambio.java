package org.ventura.util.maestro;

import org.ventura.entity.tasas.Tipotasa;

public class ProduceObjectTipocambio {

	public static Tipotasa getTipoCambioCompraVenta(TipoCambioCompraVentaType tipotasaCompraVentaType) {
		Tipotasa tipotasa = new Tipotasa();
		switch (tipotasaCompraVentaType) {
		case COMPRA_MONEDA:
			tipotasa.setIdtipotasa(6);
			break;
		case VENTA_MONEDA:
			tipotasa.setIdtipotasa(7);
			break;
		default:
			tipotasa.setIdtipotasa(null);
			break;
		}
		return tipotasa;
	}
	
	public static TipoCambioCompraVentaType getTipoCambioCompraVenta(Tipotasa tipotasa){
		TipoCambioCompraVentaType tipoTasaCompraVentaType;
		Integer id = tipotasa.getIdtipotasa();
		switch (id) {
		case 6:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.COMPRA_MONEDA;
			break;
		case 7:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.VENTA_MONEDA;
			break;
		default:
			tipoTasaCompraVentaType = null;
			break;
		}
		return tipoTasaCompraVentaType;
	}
}
