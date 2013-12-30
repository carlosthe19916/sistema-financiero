package org.ventura.util.maestro;

import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.GeneratedTipotasaPasiva.TipotasaPasivaType;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tipotasa;

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
	
	public static EstadoAperturaType getEstadoaperturaType(Estadoapertura estadoapertura) {
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
	
	public static TipotasaPasivaType getTipoTasaPasiva(Tipotasa tipotasapasiva) {
		TipotasaPasivaType tipotasapasivaType;
		Integer id = tipotasapasiva.getIdtipotasa();
		switch (id) {
		case 6:
			tipotasapasivaType = TipotasaPasivaType.COMPRA_DOLAR_CON_SOL;
			break;
		case 7:
			tipotasapasivaType = TipotasaPasivaType.COMPRA_DOLAR_CON_EURO;
			break;
		case 8:
			tipotasapasivaType = TipotasaPasivaType.COMPRA_EURO_CON_SOL;
			break;
		case 9:
			tipotasapasivaType = TipotasaPasivaType.COMPRA_EURO_CON_DOLAR;
			break;
		case 10:
			tipotasapasivaType = TipotasaPasivaType.VENTA_DOLAR_CON_SOL;
			break;
		case 11:
			tipotasapasivaType = TipotasaPasivaType.VENTA_DOLAR_CON_EURO;
			break;
		case 12:
			tipotasapasivaType = TipotasaPasivaType.VENTA_EURO_CON_SOL;
			break;
		case 13:
			tipotasapasivaType = TipotasaPasivaType.VENTA_EURO_CON_DOLAR;
			break;
		default:
			tipotasapasivaType = null;
			break;
		}
		return tipotasapasivaType;
	}
	
	public static Tipotasa getTipoTasaPasiva(TipotasaPasivaType tipotasapasivaType) {
		Tipotasa tipotasapasiva = new Tipotasa();

		switch (tipotasapasivaType) {
		case COMPRA_DOLAR_CON_SOL:
			tipotasapasiva.setIdtipotasa(6);
			break;
		case COMPRA_DOLAR_CON_EURO:
			tipotasapasiva.setIdtipotasa(7);
			break;
		case COMPRA_EURO_CON_SOL:
			tipotasapasiva.setIdtipotasa(8);
			break;
		case COMPRA_EURO_CON_DOLAR:
			tipotasapasiva.setIdtipotasa(9);
			break;
		case VENTA_DOLAR_CON_SOL:
			tipotasapasiva.setIdtipotasa(10);
			break;
		case VENTA_DOLAR_CON_EURO:
			tipotasapasiva.setIdtipotasa(11);
			break;
		case VENTA_EURO_CON_SOL:
			tipotasapasiva.setIdtipotasa(12);
			break;
		case VENTA_EURO_CON_DOLAR:
			tipotasapasiva.setIdtipotasa(13);
			break;
		default:
			tipotasapasiva.setIdtipotasa(null);
			break;
		}
		return tipotasapasiva;
	}
	
	public static Tipotransaccioncompraventa getTipotransaccioncompraventa(TipoTransaccionCompraVentaType tipoTransaccioncompraventaType) {
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

	public static TipoTransaccionCompraVentaType getTipotransaccioncompraventa(Tipotransaccioncompraventa tipotransaccioncompraventa) {
		TipoTransaccionCompraVentaType tipotransaccioncompraventaType;
		Integer id = tipotransaccioncompraventa.getIdtipotransaccioncompraventa();
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
	
	public static Tipotasa getTipotasa(TipotasaType tipotasaType) {
		Tipotasa tipotasa =  new Tipotasa();
		switch (tipotasaType) {
		case CUENTA_AHORRO_TASA_INTERES:
			tipotasa.setIdtipotasa(1);
			break;
		case CUENTA_CORRIENTE_TASA_INTERES:
			tipotasa.setIdtipotasa(1);
			break;
		default:
			tipotasa = null;
			break;
		}
		return tipotasa;
	}
}
