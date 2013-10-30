package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedTiposervicio.TiposervicioType;
import org.ventura.entity.tasas.Tiposervicio;

public class TiposervicioServiceFactory {

	@Produces
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_APORTE)
	public Tiposervicio getTiposervicioCuentaaporte(InjectionPoint p) {
		Tiposervicio tiposervicio = new Tiposervicio();
		tiposervicio.setDenominacion("CUENTA_APORTE");
		tiposervicio.setDescripcion("SERVICIO DE TASA PARA LAS CUENTAS DE APORTE");
		tiposervicio.setIdtiposervicio(1);
		tiposervicio.setEstado(true);	
		return tiposervicio;
	}

	@Produces
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_AHORRO)
	public Tiposervicio getTiposervicioCuentaahorro(InjectionPoint p) {
		Tiposervicio tiposervicio = new Tiposervicio();
		tiposervicio.setDenominacion("CUENTA_AHORRO");
		tiposervicio.setDescripcion("SERVICIO DE TASA PARA LAS CUENTAS DE AHORRO");
		tiposervicio.setIdtiposervicio(2);
		tiposervicio.setEstado(true);	
		return tiposervicio;
	}
	
	@Produces
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_CORRIENTE)
	public Tiposervicio getTiposervicioCuentacorriente(InjectionPoint p) {
		Tiposervicio tiposervicio = new Tiposervicio();
		tiposervicio.setDenominacion("CUENTA_CORRIENTE");
		tiposervicio.setDescripcion("SERVICIO DE TASA PARA LAS CUENTAS DE CORRIENTE");
		tiposervicio.setIdtiposervicio(3);
		tiposervicio.setEstado(true);	
		return tiposervicio;
	}
	
	@Produces
	@GeneratedTiposervicio(strategy = TiposervicioType.CUENTA_PLAZO_FIJO)
	public Tiposervicio getTiposervicioCuentaplazofijo(InjectionPoint p) {
		Tiposervicio tiposervicio = new Tiposervicio();
		tiposervicio.setDenominacion("CUENTA_PLAZO_FIJO");
		tiposervicio.setDescripcion("SERVICIO DE TASA PARA LAS CUENTAS A PLAZO FIJO");
		tiposervicio.setIdtiposervicio(4);
		tiposervicio.setEstado(true);	
		return tiposervicio;
	}

}
