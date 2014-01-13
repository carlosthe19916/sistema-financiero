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
}
