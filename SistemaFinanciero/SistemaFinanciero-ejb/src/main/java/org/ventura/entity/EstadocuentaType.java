package org.ventura.entity;

public enum EstadocuentaType {
	/*
	 * Cuenta activa, se permite 
	 * todo tipo de operaciones sobre ella*/
	ACTIVO, 
	
	/*
	 * Cuenta dada de baja*/
	INACTIVO, 
	
	/*
	 * Cuenta a la espera de corregir errores
	 * para volver a estar activa*/
	CONGELADO
}
