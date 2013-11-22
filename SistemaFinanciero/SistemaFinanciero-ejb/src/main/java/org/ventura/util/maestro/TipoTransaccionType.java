package org.ventura.util.maestro;

public enum TipoTransaccionType {
	/**
	 * Objeto Abierto pero no se permiten movimiento de depositos o retiros
	 */
	DEPOSITO,

	/**
	 * Objeto Abierto en el que si se permiten movimientos de depositos o
	 * retiros
	 */
	RETIRO,

	/**
	 * Objeto Cerrado en el que no se permiten operaciones de ningun tipo
	 */
	TRANSFERENCIA
}
