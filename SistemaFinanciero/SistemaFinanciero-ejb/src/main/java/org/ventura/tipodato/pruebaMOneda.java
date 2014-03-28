package org.ventura.tipodato;

import java.math.BigDecimal;

import org.testng.annotations.Test;


public class pruebaMOneda {

	@Test
	public void pruebaMOneda(){
		BigDecimal number = new BigDecimal("7788128.254");
		String s = Moneda.getMonedaFormat(number);
		System.out.println(s);
	}
	
	
}
