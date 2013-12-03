package org.ventura.util.maestro;

import javax.ejb.EJB;

import org.ventura.dao.CrudService;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipotransaccion;

public class ProduceObject<T, V extends Enum<V>> {

	@EJB
	private CrudService crudService;

	public static Estadoapertura getEstadoapertura(
			EstadoAperturaType estadoAperturaType) {
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

	public static Estadomovimiento getEstadomovimiento(
			EstadoMovimientoType estadoMovimientoType) {
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

	public static Tipotransaccion getTipotransaccion(
			TipoTransaccionType tipoTransaccionType) {
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

	public static TipoTransaccionType getTipotransaccion(
			Tipotransaccion tipotransaccion) {
		TipoTransaccionType transaccionType;
		Integer id = tipotransaccion.getIdtipotransaccion();
		switch (id) {
		case 1:
			transaccionType = TipoTransaccionType.DEPOSITO;
			break;
		case 2:
			transaccionType = TipoTransaccionType.RETIRO;
			break;
		default:
			transaccionType = null;
			break;
		}
		return transaccionType;
	}

	public T getObject(Enum<V> e) {
		T obj = (T) new Object();

		Class<V> enumType = null;

		Integer id = new Integer(0);

		for (V c : enumType.getEnumConstants()) {
			if (c.equals(e))
				obj = (T) crudService.find(obj.getClass(), id);
			else
				id++;
		}

		return obj;
	}

	public <T extends Enum<T>> void enumValues(Class<T> enumType) {
		for (T c : enumType.getEnumConstants()) {
			System.out.println(c.name());
		}
	}
}
