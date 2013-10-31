package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.schema.cuentapersonal.Cuentacorrientehistorial;

public class CuentacorrientehistorialListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Cuentacorrientehistorial cuentacorrientehistorial) {

		if (cuentacorrientehistorial.getCuentacorriente() != null) {
			Integer idcuentacorriente = cuentacorrientehistorial.getCuentacorriente().getIdcuentacorriente();
			cuentacorrientehistorial.setIdcuentacorriente(idcuentacorriente);
		}
	}
}
