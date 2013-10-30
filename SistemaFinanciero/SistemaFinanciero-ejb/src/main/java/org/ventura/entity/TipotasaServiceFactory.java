package org.ventura.entity;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ventura.entity.GeneratedTipotasaPasiva.TipotasaPasivaType;
import org.ventura.entity.tasas.Tipotasa;

public class TipotasaServiceFactory {

	@Produces
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TICAH)
	public Tipotasa gettipotasaCuentaaporte(InjectionPoint p) {
		Tipotasa tipotasa = new Tipotasa();
		tipotasa.setDenominacion("TASA_INTERES_CUENTA_AHORRO");
		tipotasa.setDescripcion("TASA DE INTERES A LA CUENTA DE AHORRO");
		tipotasa.setIdtipotasa(1);
		tipotasa.setEstado(true);	
		return tipotasa;
	}

}
