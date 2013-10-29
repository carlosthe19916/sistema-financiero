package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;

public class TitularcuentahistorialListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Titularcuentahistorial titularcuentahistorial) {

		if (titularcuentahistorial.getTitularcuenta() != null) {
			Integer idTitularcuenta = titularcuentahistorial.getTitularcuenta()
					.getIdtitularcuenta();
			titularcuentahistorial.setIdtitularcuenta(idTitularcuenta);

		}
	}
}
