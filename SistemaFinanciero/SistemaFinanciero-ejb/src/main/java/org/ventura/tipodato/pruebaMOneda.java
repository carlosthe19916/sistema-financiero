package org.ventura.tipodato;

import java.math.BigDecimal;

import org.testng.annotations.Test;


public class pruebaMOneda {

	@Test
	public void pruebaMOneda(){
		BigDecimal a = new BigDecimal("147.");
		BigDecimal b = new BigDecimal("147.0");
		BigDecimal c = new BigDecimal("147.00");
		BigDecimal d = new BigDecimal("147.02");
		BigDecimal e = new BigDecimal("147.14");
		
		Moneda moneda = new Moneda(a);
		System.out.println(moneda);
		moneda = new Moneda(b);
		System.out.println(moneda);
		moneda = new Moneda(c);
		System.out.println(moneda);
		moneda = new Moneda(d);
		System.out.println(moneda);
		moneda = new Moneda(e);
		System.out.println(moneda);
	}
}
