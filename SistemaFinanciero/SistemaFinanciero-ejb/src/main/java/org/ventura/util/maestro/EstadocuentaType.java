package org.ventura.util.maestro;

public enum EstadocuentaType {

	/**
	 * Cuenta en espera de aprobar la apertura
	 */
	PENDIENTE_DE_APERTURA,

	/**
	 * Cuenta activa para realizar todo tipo de movimientos
	 */
	ACTIVO,

	/**
	 * Cuenta activa pero no se puede realizar ningun tipo de movimiento
	 */
	CONGELADO,

	/**
	 * Cuenta inactiva no se puede realizar ningunn tipo de movimientos pero
	 * ademas la cuenta ya no esta asignada al cliente
	 */
	INACTIVO

}
