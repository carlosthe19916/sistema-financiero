package org.ventura.util.maestro;

import org.ventura.entity.tasas.Tipotasa;

public class ProduceObjectTasainteres {

	public static Tipotasa getTasaInteres(TipotasaCuentasPersonalesType tipotasaCuentasPersonalesType) {
		Tipotasa tipotasa = new Tipotasa();
		switch (tipotasaCuentasPersonalesType) {
		case CUENTA_AHORRO_TASA_INTERES:
			tipotasa.setIdtipotasa(1);
			break;
		case CUENTA_CORRIENTE_TASA_INTERES:
			tipotasa.setIdtipotasa(5);
			break;
		case TEA:
			tipotasa.setIdtipotasa(14);
			break;
		case TREA:
			tipotasa.setIdtipotasa(3);
			break;
		default:
			tipotasa.setIdtipotasa(null);
			break;
		}
		return tipotasa;
	}
	
	public static TipotasaCuentasPersonalesType getEstadoaperturaType(Tipotasa tipotasa) {
		TipotasaCuentasPersonalesType tipotasaCuentasPersonalesType;
		Integer id = tipotasa.getIdtipotasa();
		switch (id) {
		case 1:
			tipotasaCuentasPersonalesType = TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES;
			break;
		case 2:
			tipotasaCuentasPersonalesType = TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES;
			break;
		case 14:
			tipotasaCuentasPersonalesType = TipotasaCuentasPersonalesType.TEA;
			break;
		case 3:
			tipotasaCuentasPersonalesType = TipotasaCuentasPersonalesType.TREA;
			break;
		default:
			tipotasaCuentasPersonalesType = null;
			break;
		}
		return tipotasaCuentasPersonalesType;
	}

	public static Tipotasa getTipoCambioCompraVenta(TipoCambioCompraVentaType tipotasaCompraVentaType) {
		Tipotasa tipotasa = new Tipotasa();
		switch (tipotasaCompraVentaType) {
		case COMPRA_DOLAR_CON_SOL:
			tipotasa.setIdtipotasa(6);
			break;
		case COMPRA_DOLAR_CON_EURO:
			tipotasa.setIdtipotasa(7);
			break;
		case COMPRA_EURO_CON_SOL:
			tipotasa.setIdtipotasa(8);
			break;
		case COMPRA_EURO_CON_DOLAR:
			tipotasa.setIdtipotasa(9);
			break;
		case VENTA_DOLAR_CON_SOL:
			tipotasa.setIdtipotasa(10);
			break;
		case VENTA_DOLAR_CON_EURO:
			tipotasa.setIdtipotasa(11);
			break;
		case VENTA_EURO_CON_SOL:
			tipotasa.setIdtipotasa(12);
			break;
		case VENTA_EURO_CON_DOLAR:
			tipotasa.setIdtipotasa(13);
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
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.COMPRA_DOLAR_CON_SOL;
			break;
		case 7:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.COMPRA_DOLAR_CON_EURO;
			break;
		case 8:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.COMPRA_EURO_CON_SOL;
			break;
		case 9:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.COMPRA_EURO_CON_DOLAR;
			break;
		case 10:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.VENTA_DOLAR_CON_SOL;
			break;
		case 11:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.VENTA_DOLAR_CON_EURO;
			break;
		case 12:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.VENTA_EURO_CON_SOL;
			break;
		case 13:
			tipoTasaCompraVentaType = TipoCambioCompraVentaType.VENTA_EURO_CON_DOLAR;
			break;
		default:
			tipoTasaCompraVentaType = null;
			break;
		}
		return tipoTasaCompraVentaType;
	}


}
