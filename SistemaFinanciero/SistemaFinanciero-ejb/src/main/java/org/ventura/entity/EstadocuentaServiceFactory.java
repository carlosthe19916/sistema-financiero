package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;

public class EstadocuentaServiceFactory {

	@Produces
	@GeneratedEstadocuenta(strategy = EstadocuentaType.ACTIVO)
	public Estadocuenta getEstadocuentaActivo(InjectionPoint p) {
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("ACTIVO");
		estadocuenta.setAbreviatura("A");
		estadocuenta.setEstado(true);
		estadocuenta.setIdestadocuenta(1);
		return estadocuenta;
	}

	@Produces
	@GeneratedEstadocuenta(strategy = EstadocuentaType.INACTIVO)
	public Estadocuenta getEstadocuentaInactivo(InjectionPoint p) {
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("INACTIVO");
		estadocuenta.setAbreviatura("I");
		estadocuenta.setEstado(true);
		estadocuenta.setIdestadocuenta(2);
		return estadocuenta;
	}

	@Produces
	@GeneratedEstadocuenta(strategy = EstadocuentaType.CONGELADO)
	public Estadocuenta getEstadocuentaCongelado(InjectionPoint p) {
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setDenominacion("CONGELADO");
		estadocuenta.setAbreviatura("C");
		estadocuenta.setEstado(true);
		estadocuenta.setIdestadocuenta(3);
		return estadocuenta;
	}

}
