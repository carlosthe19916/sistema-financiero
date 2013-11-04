package org.ventura.entity;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

	@Qualifier
	@Target({ TYPE, METHOD, FIELD, PARAMETER })
	@Retention(RUNTIME)
	public @interface GeneratedFrecuenciacapitalizacion {

		FrecuenciacapitalizacionType strategy() default FrecuenciacapitalizacionType.DIARIA;

		public enum FrecuenciacapitalizacionType {
			DIARIA, QUINCENAL,MENSUAL,ANUAL 
		}
}
