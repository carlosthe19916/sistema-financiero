package org.ventura.util.maestro;

import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipotransaccion;

public class ProduceObject {

	public static Estadoapertura getEstadoapertura(EstadoAperturaType estadoAperturaType) {
		Estadoapertura estadoapertura = new Estadoapertura();
		switch (estadoAperturaType) {
		case ABIERTO:
			estadoapertura.setIdestadoapertura(1);
			break;
		case CERRADO:
			estadoapertura.setIdestadoapertura(2);
			break;
		default:
			estadoapertura.setIdestadoapertura(null);
			break;
		}
		return estadoapertura;
	}
	
	public static Estadomovimiento getEstadomovimiento(EstadoMovimientoType estadoMovimientoType) {
		Estadomovimiento estadomovimiento = new Estadomovimiento();
		switch (estadoMovimientoType) {
		case CONGELADO:
			estadomovimiento.setIdestadomovimiento(1);
			break;
		case DESCONGELADO:
			estadomovimiento.setIdestadomovimiento(2);
			break;
		default:
			estadomovimiento.setIdestadomovimiento(null);
			break;
		}
		return estadomovimiento;
	}
	
	public static Tipotransaccion getTipotransaccion(TipoTransaccionType tipoTransaccionType) {
		Tipotransaccion tipotransaccion = new Tipotransaccion();
		switch (tipoTransaccionType) {
		case DEPOSITO:
			tipotransaccion.setIdtipotransaccion(1);
			break;
		case RETIRO:
			tipotransaccion.setIdtipotransaccion(2);
			break;
		default:
			tipotransaccion.setIdtipotransaccion(null);
			break;
		}
		return tipotransaccion;
	}
}
