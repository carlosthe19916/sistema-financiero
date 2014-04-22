package org.ventura.util.maestro;

import org.ventura.entity.schema.maestro.VariableSistema;

public class ProduceObjectVariableSistema {

	public static VariableSistema getVariableSistema(VariableSistemaType variableSistemaType) {
		VariableSistema variableSistema = new VariableSistema();
		switch (variableSistemaType) {
		case MONTO_APORTE_MENOR_EDAD:
			variableSistema.setIdvariablesistema(1);
			break;
		case MONTO_APORTE_MAYOR_EDAD:
			variableSistema.setIdvariablesistema(2);
			break;
		case MONTO_MAXIMO_TRANSACCION_NUEVO_SOL:
			variableSistema.setIdvariablesistema(10);
			break;
		case MONTO_MAXIMO_TRANSACCION_DOLAR:
			variableSistema.setIdvariablesistema(11);
			break;
		case MONTO_MAXIMO_TRANSACCION_EURO:
			variableSistema.setIdvariablesistema(12);
			break;
		default:
			variableSistema.setIdvariablesistema(null);
			break;
		}
		return variableSistema;
	}
	
	public static VariableSistemaType getVariableSistemaType(VariableSistema variableSistema){
		VariableSistemaType variableSistemaType;
		Integer id = variableSistema.getIdvariablesistema();
		switch (id) {
		case 1:
			variableSistemaType = VariableSistemaType.MONTO_APORTE_MENOR_EDAD;
			break;
		case 2:
			variableSistemaType = VariableSistemaType.MONTO_APORTE_MAYOR_EDAD;
			break;
		case 10:
			variableSistemaType = VariableSistemaType.MONTO_MAXIMO_TRANSACCION_NUEVO_SOL;
			break;
		case 11:
			variableSistemaType = VariableSistemaType.MONTO_MAXIMO_TRANSACCION_DOLAR;
			break;
		case 12:
			variableSistemaType = VariableSistemaType.MONTO_MAXIMO_TRANSACCION_EURO;
			break;
		default:
			variableSistemaType = null;
			break;
		}
		return variableSistemaType;
	}
}
