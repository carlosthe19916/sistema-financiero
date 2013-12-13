package org.ventura.util.maestro;

import javax.ejb.EJB;

import org.omg.CORBA.TIMEOUT;
import org.ventura.dao.CrudService;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;

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

	public static Estadocuenta getEstadocuenta(
			EstadocuentaType tipoTransaccionType) {
		Estadocuenta tipotransaccion = new Estadocuenta();
		switch (tipoTransaccionType) {
		case PENDIENTE_DE_APERTURA:
			tipotransaccion.setIdestadocuenta(1);
			break;
		case ACTIVO:
			tipotransaccion.setIdestadocuenta(2);
			break;
		case CONGELADO:
			tipotransaccion.setIdestadocuenta(3);
			break;
		case INACTIVO:
			tipotransaccion.setIdestadocuenta(4);
			break;
		default:
			tipotransaccion.setIdestadocuenta(5);
			break;
		}
		return tipotransaccion;
	}

	public static EstadocuentaType getEstadocuenta(Estadocuenta tipotransaccion) {
		EstadocuentaType transaccionType;
		Integer id = tipotransaccion.getIdestadocuenta();
		switch (id) {
		case 1:
			transaccionType = EstadocuentaType.PENDIENTE_DE_APERTURA;
			break;
		case 2:
			transaccionType = EstadocuentaType.ACTIVO;
			break;
		case 3:
			transaccionType = EstadocuentaType.CONGELADO;
			break;
		case 4:
			transaccionType = EstadocuentaType.INACTIVO;
			break;
		default:
			transaccionType = null;
			break;
		}
		return transaccionType;
	}

	public static Tipocuentabancaria getTipocuentabancaria(
			TipocuentabancariaType tipoTransaccionType) {
		Tipocuentabancaria tipotransaccion = new Tipocuentabancaria();
		switch (tipoTransaccionType) {
		case CUENTA_AHORRO:
			tipotransaccion.setIdtipocuentabancaria(1);
			break;
		case CUENTA_CORRIENTE:
			tipotransaccion.setIdtipocuentabancaria(2);
			break;
		case CUENTA_PLAZO_FIJO:
			tipotransaccion.setIdtipocuentabancaria(3);
			break;

		default:
			tipotransaccion.setIdtipocuentabancaria(null);
			break;
		}
		return tipotransaccion;
	}

	public static TipocuentabancariaType getTipocuentabancaria(Tipocuentabancaria tipotransaccion) {
		TipocuentabancariaType transaccionType;
		Integer id = tipotransaccion.getIdtipocuentabancaria();
		switch (id) {
		case 1:
			transaccionType = TipocuentabancariaType.CUENTA_AHORRO;
			break;
		case 2:
			transaccionType = TipocuentabancariaType.CUENTA_CORRIENTE;
			break;
		case 3:
			transaccionType = TipocuentabancariaType.CUENTA_PLAZO_FIJO;
			break;
		default:
			transaccionType = null;
			break;
		}
		return transaccionType;
	}
	
	public static TipomonedaType getTipomoneda(Tipomoneda tipomoneda) {
		TipomonedaType tipomonedaType;
		Integer id = tipomoneda.getIdtipomoneda();
		switch (id) {
		case 0:
			tipomonedaType = TipomonedaType.DOLAR;
			break;
		case 1:
			tipomonedaType = TipomonedaType.NUEVO_SOL;
			break;
		case 2:
			tipomonedaType = TipomonedaType.EURO;
			break;
		default:
			tipomonedaType = null;
			break;
		}
		return tipomonedaType;
	}
	
	public static Tipomoneda getTipomoneda(TipomonedaType tipomonedaType) {
		Tipomoneda tipomoneda = new Tipomoneda();
		switch (tipomonedaType) {
		case DOLAR:
			tipomoneda.setIdtipomoneda(0);
			break;
		case NUEVO_SOL:
			tipomoneda.setIdtipomoneda(1);
			break;
		case EURO:
			tipomoneda.setIdtipomoneda(2);
			break;
		default:
			tipomoneda.setIdtipomoneda(null);
			break;
		}
		return tipomoneda;
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
