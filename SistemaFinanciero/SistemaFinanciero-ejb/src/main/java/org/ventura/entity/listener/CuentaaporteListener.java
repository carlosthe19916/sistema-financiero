package org.ventura.entity.listener;

import javax.persistence.PrePersist;

import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.IllegalEntityException;

public class CuentaaporteListener {

	@PrePersist
	private void prueba(Cuentaaporte cuentaaporte){
		System.out.println("prueba");
	}
}
