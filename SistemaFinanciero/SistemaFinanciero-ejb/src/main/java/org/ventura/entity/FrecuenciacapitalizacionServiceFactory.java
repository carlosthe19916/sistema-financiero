package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedFrecuenciacapitalizacion.FrecuenciacapitalizacionType;
import org.ventura.entity.schema.cuentapersonal.Frecuenciacapitalizacion;

public class FrecuenciacapitalizacionServiceFactory {

	@Produces
	@GeneratedFrecuenciacapitalizacion
	public Frecuenciacapitalizacion getFrecuenciacapitalizacion(InjectionPoint p) {
		Frecuenciacapitalizacion frecuenciacapitalizacion = new Frecuenciacapitalizacion();
		Annotated annotated = p.getAnnotated();
		GeneratedFrecuenciacapitalizacion generatedFrecuenciacapitalizacion = annotated.getAnnotation(GeneratedFrecuenciacapitalizacion.class);
		FrecuenciacapitalizacionType frecuenciacapitalizacionType = generatedFrecuenciacapitalizacion.strategy();
		switch (frecuenciacapitalizacionType) {
		case DIARIA:
			frecuenciacapitalizacion.setDenomicacion("DIARIA");
			frecuenciacapitalizacion.setEstado(true);
			frecuenciacapitalizacion.setNumerodias(1);
			break;
		case QUINCENAL:
			frecuenciacapitalizacion.setDenomicacion("QUINCENAL");
			frecuenciacapitalizacion.setEstado(true);
			frecuenciacapitalizacion.setNumerodias(15);
			break;
		case MENSUAL:
			frecuenciacapitalizacion.setDenomicacion("MENSUAL");
			frecuenciacapitalizacion.setEstado(true);
			frecuenciacapitalizacion.setNumerodias(30);
			break;
		case ANUAL:
			frecuenciacapitalizacion.setDenomicacion("ANUAL");
			frecuenciacapitalizacion.setEstado(true);
			frecuenciacapitalizacion.setNumerodias(360);
			break;
		default:
			break;
		}
		return frecuenciacapitalizacion;
	}
}