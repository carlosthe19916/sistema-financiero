package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Aporte;


public class AporteListener {
	
	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Aporte aporte) {

		if (aporte.getNumerocuentaaporte() != null) {
			String numeroCuentaaporte = aporte.getCuentaaporte().getNumerocuentaaporte();
			aporte.setNumerocuentaaporte(numeroCuentaaporte);
			}
	}

}
