package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.schema.cuentapersonal.Cuentaahorrohistorial;

public class CuentaahorrohistorialListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Cuentaahorrohistorial cuentaahorrohistorial) {

		if (cuentaahorrohistorial.getCuentaahorro() != null) {
			Integer idcuentaahorro = cuentaahorrohistorial.getCuentaahorro().getIdcuentaahorro();
			cuentaahorrohistorial.setIdcuentaahorro(idcuentaahorro);;
		}
	}
}
