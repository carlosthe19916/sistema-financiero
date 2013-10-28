package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Socio;

public class SocioListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Socio socio) {
		if (socio.getCuentaaporte() != null) {
			Cuentaaporte cuentaaporte = socio.getCuentaaporte();
			socio.setCuentaaporte(cuentaaporte);;
		}
	}
}
