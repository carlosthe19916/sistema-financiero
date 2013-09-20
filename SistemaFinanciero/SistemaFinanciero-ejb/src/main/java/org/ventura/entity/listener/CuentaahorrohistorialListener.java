package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Titularcuentahistorial;

public class CuentaahorrohistorialListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Cuentaahorrohistorial cuentaahorrohistorial) {

		if (cuentaahorrohistorial.getCuentaahorro() != null) {
			String numeroCuentaahorro = cuentaahorrohistorial.getCuentaahorro().getNumerocuentaahorro();
			cuentaahorrohistorial.setNumerocuentaahorro(numeroCuentaahorro);
		}
	}
}
