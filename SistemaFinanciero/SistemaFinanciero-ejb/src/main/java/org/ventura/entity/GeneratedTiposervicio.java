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
public @interface GeneratedTiposervicio {

	TiposervicioType strategy() default TiposervicioType.CUENTA_APORTE;

	public enum TiposervicioType {
		CUENTA_APORTE, CUENTA_AHORRO, CUENTA_CORRIENTE, CUENTA_PLAZO_FIJO, CREDIMAS, CREDICASERITO
	}
}
