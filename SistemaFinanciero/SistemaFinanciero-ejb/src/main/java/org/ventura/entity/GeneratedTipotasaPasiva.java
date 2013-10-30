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

	TipotasaPasivaType strategyTasaPasiva() default TipotasaPasivaType.NINGUNO;

	public enum TipotasaPasivaType {
		NINGUNO,

		TICAH,

		TICC,

		TICEAF,

		TREA,

		ITF
	}
}
