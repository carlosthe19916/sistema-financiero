package org.ventura.util.maestro;

import org.ventura.entity.tasas.Tasainteres;
import org.ventura.entity.tasas.Tipotasa;

public class ProduceObjectTasainteres {

	public static Tipotasa getTasaInteres(TipotasaCuentasPersonalesType tipotasaCuentasPersonalesType) {
		Tipotasa tipotasa = new Tipotasa();
		switch (tipotasaCuentasPersonalesType) {
		case CUENTA_AHORRO_TASA_INTERES:
			tipotasa.setIdtipotasa(1);
			break;
		case CUENTA_CORRIENTE_TASA_INTERES:
			tipotasa.setIdtipotasa(2);
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
		default:
			tipotasaCuentasPersonalesType = null;
			break;
		}
		return tipotasaCuentasPersonalesType;
	}

	public static Tasainteres getTasaInteres(TipotasaCompraVentaType tipotasaCompraVentaType) {
		Tasainteres tasainteres = new Tasainteres();
		switch (tipotasaCompraVentaType) {
		case COMPRA_DOLARES_SOLES:
			tasainteres.setIdtasainteres(1);
			break;
		case COMPRA_EUROS_SOLES:
			tasainteres.setIdtasainteres(2);
			break;
		case VENTA_DOLARES_SOLES:
			tasainteres.setIdtasainteres(2);
			break;
		case VENTA_EUROS_DOLARES:
			tasainteres.setIdtasainteres(2);
			break;
		default:
			tasainteres.setIdtasainteres(null);
			break;
		}
		return tasainteres;
	}

}
