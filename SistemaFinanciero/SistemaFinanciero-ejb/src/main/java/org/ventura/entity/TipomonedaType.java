package org.ventura.entity;

public enum TipomonedaType {
	/*
	 * Cuenta activa, se permite 
	 * todo tipo de operaciones sobre ella*/
	DOLAR, 
	
	/*
	 * Cuenta dada de baja*/
	NUEVO_SOL, 
	
	/*
	 * Cuenta a la espera de corregir errores
	 * para volver a estar activa*/
	EURO
}
