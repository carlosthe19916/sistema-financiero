package org.ventura.entity.listener;

import javax.persistence.PrePersist;

import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;

public class CuentaaporteListener {

	@PrePersist
	private void prueba(Cuentaaporte cuentaaporte){
		System.out.println("prueba");
	}
}
