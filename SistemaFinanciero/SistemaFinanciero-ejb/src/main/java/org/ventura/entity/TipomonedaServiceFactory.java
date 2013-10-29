package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.maestro.Tipomoneda;

public class TipomonedaServiceFactory {

	@Produces
	@GeneratedTipomoneda
	public Tipomoneda getTipomoneda(InjectionPoint p) {
		Tipomoneda tipomoneda = new Tipomoneda();
		Annotated annotated = p.getAnnotated();
		GeneratedTipomoneda generatedTipomoneda = annotated.getAnnotation(GeneratedTipomoneda.class);
		TipomonedaType tipomonedaType = generatedTipomoneda.strategy();
		switch (tipomonedaType) {
		case DOLAR:
			tipomoneda.setDenominacion("DOLAR");
			tipomoneda.setAbreviatura("$");
			tipomoneda.setEstado(true);
			tipomoneda.setIdtipomoneda(2);
			break;
		case NUEVO_SOL:
			tipomoneda.setDenominacion("NUEVO SOL");
			tipomoneda.setAbreviatura("S/.");
			tipomoneda.setEstado(true);
			tipomoneda.setIdtipomoneda(1);
			break;
		case EURO:
			tipomoneda.setDenominacion("EURO");
			tipomoneda.setAbreviatura("€");
			tipomoneda.setEstado(true);
			tipomoneda.setIdtipomoneda(2);
			break;
		default:
			break;
		}
		return tipomoneda;
	}

}
