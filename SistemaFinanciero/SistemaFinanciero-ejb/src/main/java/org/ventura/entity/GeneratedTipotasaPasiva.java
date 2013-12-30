package org.ventura.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface GeneratedTipotasaPasiva {

	TipotasaPasivaType strategyTasaPasiva() default TipotasaPasivaType.TICAH;

	public enum TipotasaPasivaType {
		TICAH,

		TICC,

		TICEAF,

		TREA,

		ITF,
		
		COMPRA_DOLAR_CON_SOL, 
		
		COMPRA_DOLAR_CON_EURO, 
		
		COMPRA_EURO_CON_SOL, 
		
		COMPRA_EURO_CON_DOLAR, 
		
		VENTA_DOLAR_CON_SOL, 
		
		VENTA_DOLAR_CON_EURO, 
		
		VENTA_EURO_CON_SOL, 
		
		VENTA_EURO_CON_DOLAR
	}
}
