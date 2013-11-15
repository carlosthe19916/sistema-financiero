package org.ventura.util.maestro;

public enum EstadoMovimientoType {

	/**
	 * Objeto Abierto pero no se permiten movimiento de depositos o retiros
	 */
	ABIERTO_CONGELADO,

	/**
	 * Objeto Abierto en el que si se permiten movimientos de depositos o
	 * retiros
	 */
	ABIERTO_DESCONGELADO,

	/**
	 * Objeto Cerrado en el que no se permiten operaciones de ningun tipo
	 */
	CERRADO
}
