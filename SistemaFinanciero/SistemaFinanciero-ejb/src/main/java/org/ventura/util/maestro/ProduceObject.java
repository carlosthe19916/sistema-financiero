package org.ventura.util.maestro;

import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.seguridad.Rol;

public class ProduceObject {

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

	public static EstadoAperturaType getEstadoaperturaType(
			Estadoapertura estadoapertura) {
		EstadoAperturaType estadoAperturaType;
		Integer id = estadoapertura.getIdestadoapertura();
		switch (id) {
		case 1:
			estadoAperturaType = EstadoAperturaType.ABIERTO;
			break;
		case 2:
			estadoAperturaType = EstadoAperturaType.CERRADO;
			break;
		default:
			estadoAperturaType = null;
			break;
		}
		return estadoAperturaType;
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
			tipotransaccion.setIdestadocuenta(null);
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

	public static TipocuentabancariaType getTipocuentabancaria(
			Tipocuentabancaria tipotransaccion) {
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

	public static Tipotransaccioncompraventa getTipotransaccioncompraventa(
			TipoTransaccionCompraVentaType tipoTransaccioncompraventaType) {
		Tipotransaccioncompraventa tipotransaccioncompraventa = new Tipotransaccioncompraventa();
		switch (tipoTransaccioncompraventaType) {
		case COMPRA:
			tipotransaccioncompraventa.setIdtipotransaccioncompraventa(1);
			break;
		case VENTA:
			tipotransaccioncompraventa.setIdtipotransaccioncompraventa(2);
			break;
		default:
			tipotransaccioncompraventa.setIdtipotransaccioncompraventa(null);
			break;
		}
		return tipotransaccioncompraventa;
	}

	public static TipoTransaccionCompraVentaType getTipotransaccioncompraventa(
			Tipotransaccioncompraventa tipotransaccioncompraventa) {
		TipoTransaccionCompraVentaType tipotransaccioncompraventaType;
		Integer id = tipotransaccioncompraventa
				.getIdtipotransaccioncompraventa();
		switch (id) {
		case 1:
			tipotransaccioncompraventaType = TipoTransaccionCompraVentaType.COMPRA;
			break;
		case 2:
			tipotransaccioncompraventaType = TipoTransaccionCompraVentaType.VENTA;
			break;
		default:
			tipotransaccioncompraventaType = null;
			break;
		}
		return tipotransaccioncompraventaType;
	}

	public static Tipodocumento getTipodocumento(TipodocumentoType tipodocumentoType) {
		Tipodocumento tipodocumento = new Tipodocumento();
		switch (tipodocumentoType) {
		case DNI:
			tipodocumento.setIdtipodocumento(1);
			break;
		case RUC:
			tipodocumento.setIdtipodocumento(5);
			break;
		default:
			tipodocumento = null;
			break;
		}
		return tipodocumento;
	}
	
	public static RolType getRol(Rol rol) {
		RolType rolType = null;
		Integer id = rol.getIdrol();
		switch (id) {
		case 1:
			rolType = RolType.ADMIN;
			break;
		case 2:
			rolType = RolType.CAJA;
			break;
		case 3:
			rolType = RolType.JEFE_CAJA;
			break;
		case 4:
			rolType = RolType.ADMINISTRADOR;
			break;
		default:
			rolType = null;
			break;
		}
		return rolType;
	}

	public static Rol getRol(RolType rolType) {
		Rol rol = new Rol();
		switch (rolType) {
		case ADMIN:
			rol.setIdrol(1);
			break;
		case CAJA:
			rol.setIdrol(2);
			break;
		case JEFE_CAJA:
			rol.setIdrol(3);
			break;
		case ADMINISTRADOR:
			rol.setIdrol(4);
			break;
		default:
			rol = null;
			break;
		}
		return rol;
	}
}
