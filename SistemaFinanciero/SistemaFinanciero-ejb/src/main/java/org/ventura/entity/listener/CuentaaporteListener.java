package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Socio;

public class CuentaaporteListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Cuentaaporte cuentaaporte) {
		/*if (socio.getCuentaaporte() != null) {
			Cuentaaporte cuentaaporte = socio.getCuentaaporte();
			socio.setCuentaaporte(cuentaaporte);;
		}*/
	}
}
