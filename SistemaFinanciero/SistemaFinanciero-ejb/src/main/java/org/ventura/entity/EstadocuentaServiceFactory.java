package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;

public class EstadocuentaServiceFactory {

	@Produces
	@GeneratedEstadocuenta
	public Estadocuenta getEstadocuenta(InjectionPoint p) {
		Estadocuenta estadocuenta = new Estadocuenta();
		Annotated annotated = p.getAnnotated();
		GeneratedEstadocuenta generatedEstadocuenta = annotated.getAnnotation(GeneratedEstadocuenta.class);
		EstadocuentaType estadocuentaType = generatedEstadocuenta.strategy();
		switch (estadocuentaType) {
		case ACTIVO:
			estadocuenta.setDenominacion("ACTIVO");
			estadocuenta.setAbreviatura("A");
			estadocuenta.setEstado(true);
			estadocuenta.setIdestadocuenta(1);
			break;
		case INACTIVO:
			estadocuenta.setDenominacion("INACTIVO");
			estadocuenta.setAbreviatura("I");
			estadocuenta.setEstado(true);
			estadocuenta.setIdestadocuenta(2);
			break;
		case CONGELADO:
			estadocuenta.setDenominacion("CONGELADO");
			estadocuenta.setAbreviatura("C");
			estadocuenta.setEstado(true);
			estadocuenta.setIdestadocuenta(3);
			break;
		default:
			break;
		}
		return estadocuenta;
	}

}
