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
	
	@Produces
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TICC)
	public Tipotasa gettipotasaCuentacorriente(InjectionPoint p) {
		Tipotasa tipotasa = new Tipotasa();
		tipotasa.setDenominacion("TASA_INTERES_CUENTA_AHORRO");
		tipotasa.setDescripcion("TASA DE INTERES A LA CUENTA DE AHORRO");
		tipotasa.setIdtipotasa(5);
		tipotasa.setEstado(true);	
		return tipotasa;
	}
	
	@Produces
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TICEAF)
	public Tipotasa gettipotasaCuentaplazofijoTICEAF(InjectionPoint p) {
		Tipotasa tipotasa = new Tipotasa();
		tipotasa.setDenominacion("TASA DE INTERES COMPENSATORIA EFECTIVA ANUAL FIJA");
		tipotasa.setDescripcion("TASA DE INTERES A LA CUENTA A PLAZO FIJO");
		tipotasa.setIdtipotasa(2);
		tipotasa.setEstado(true);	
		return tipotasa;
	}
	
	@Produces
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.TREA)
	public Tipotasa gettipotasaCuentaplazofijoTREA(InjectionPoint p) {
		Tipotasa tipotasa = new Tipotasa();
		tipotasa.setDenominacion("TASA DE RENDIMIENTO EFECTIVO ANUAL");
		tipotasa.setDescripcion("TASA DE INTERES A LA CUENTA A PLAZO FIJO");
		tipotasa.setIdtipotasa(3);
		tipotasa.setEstado(true);	
		return tipotasa;
	}
	
	@Produces
	@GeneratedTipotasaPasiva(strategyTasaPasiva = TipotasaPasivaType.ITF)
	public Tipotasa gettipotasaCuentaplazofijoITF(InjectionPoint p) {
		Tipotasa tipotasa = new Tipotasa();
		tipotasa.setDenominacion("IMPUESTOS A LAS TRANSACCIONES FINANCIERAS");
		tipotasa.setDescripcion("TASA DE INTERES A LA CUENTA A PLAZO FIJO");
		tipotasa.setIdtipotasa(4);
		tipotasa.setEstado(true);	
		return tipotasa;
	}

}
