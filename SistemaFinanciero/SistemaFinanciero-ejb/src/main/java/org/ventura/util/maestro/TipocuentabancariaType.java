package org.ventura.util.maestro;

public enum TipocuentabancariaType {

	/**
	 * Cuenta en espera de aprobar la apertura
	 */
	CUENTA_AHORRO,

	/**
	 * Cuenta activa para realizar todo tipo de movimientos
	 */
	CUENTA_PLAZO_FIJO,

	/**
	 * Cuenta activa pero no se puede realizar ningun tipo de movimiento
	 */
	CUENTA_CORRIENTE

}
